"use strict";

(function () {

  function buildWsUrl(ptyId, shellPath, workingDirectory) {
    var loc = window.location;
    var proto = loc.protocol === "https:" ? "wss:" : "ws:";
    var path = loc.pathname.replace(/\/ui-dark$|\/ui$/, "") + "/pty/" + encodeURIComponent(ptyId);
    var params = [];
    if (shellPath) params.push("shell=" + encodeURIComponent(shellPath));
    if (workingDirectory) params.push("cwd=" + encodeURIComponent(workingDirectory));
    var query = params.length > 0 ? "?" + params.join("&") : "";
    return proto + "//" + loc.host + path + query;
  }

  if (!window.hop) {
    window.hop = {};
  }

  function getParentElement(parentId) {
    var obj = rap.getObject(parentId);
    if (obj) {
      if (obj.$el) {
        var el = obj.$el.get ? obj.$el.get(0) : obj.$el[0] || obj.$el;
        if (el) return el;
      }
      if (obj.getDomNode) return obj.getDomNode();
      if (obj._domNode) return obj._domNode;
    }
    return document.getElementById(parentId);
  }

  // Resize protocol: client sends text message starting with \x00RESIZE:cols,rows
  // Normal terminal input is sent as text without this prefix.
  // PTY output comes as binary from server.
  var RESIZE_PREFIX = "\x00RESIZE:";

  rwt.define("hop");

  hop.Terminal = function (properties) {
    this._destroyed = false;
    this._terminal = null;
    this._ws = null;
    this._container = null;
    this._fitAddon = null;
    this._resizeObserver = null;
    this._properties = properties || {};

    this._doCreate();
  };

  hop.Terminal.prototype = {

    _doCreate: function () {
      var parentId = this._properties.parent;
      var ptyId = this._properties.ptyId;
      if (!parentId || !ptyId) return;

      var self = this;
      var parentEl = getParentElement(parentId);
      if (!parentEl) {
        if (!this._destroyed) {
          setTimeout(function () { self._doCreate(); }, 100);
        }
        return;
      }

      var container = document.createElement("div");
      container.style.width = "100%";
      container.style.height = "100%";
      container.style.overflow = "hidden";
      parentEl.appendChild(container);
      this._container = container;

      this._initTerminal(container, ptyId);
    },

    _initTerminal: function (container, ptyId) {
      var self = this;

      var isDark = document.body.classList.contains("rwt-dark") ||
                   document.documentElement.classList.contains("rwt-dark");
      var theme = isDark
        ? { background: "#2b2b2b", foreground: "#bbbbbb", cursor: "#bbbbbb",
            black: "#2b2b2b", red: "#cc0000", green: "#4e9a06", yellow: "#c4a000",
            blue: "#3465a4", magenta: "#75507b", cyan: "#06989a", white: "#d3d7cf",
            brightBlack: "#555753", brightRed: "#ef2929", brightGreen: "#8ae234",
            brightYellow: "#fce94f", brightBlue: "#729fcf", brightMagenta: "#ad7fa8",
            brightCyan: "#34e2e2", brightWhite: "#eeeeee" }
        : { background: "#ffffff", foreground: "#000000", cursor: "#000000" };

      var baseFont = 13;
      var fontSizePercent = this._properties.fontSizePercent;
      if (fontSizePercent == null) fontSizePercent = 100;
      var fontSize = Math.round(baseFont * (fontSizePercent / 100));

      var terminal = new Terminal({
        cursorBlink: true,
        cursorStyle: "block",
        fontSize: fontSize,
        fontFamily: "Menlo, Monaco, 'Courier New', monospace",
        theme: theme,
        allowTransparency: false,
        cols: 80,
        rows: 24,
        convertEol: true,
      });
      self._terminal = terminal;

      var shellPath = this._properties.shellPath;
      var workingDirectory = this._properties.workingDirectory;
      var wsUrl = buildWsUrl(ptyId, shellPath, workingDirectory);
      var ws = new WebSocket(wsUrl);
      ws.binaryType = "arraybuffer";
      self._ws = ws;

      ws.onopen = function () {
        if (self._destroyed) {
          ws.close();
          return;
        }

        // Open terminal in container
        terminal.open(container);

        // Fit addon - delay to allow container layout to settle
        var fitAddon = null;
        if (typeof FitAddon !== 'undefined') {
          try {
            fitAddon = new FitAddon.FitAddon();
            terminal.loadAddon(fitAddon);
          } catch (e) {
            fitAddon = null;
          }
        }
        self._fitAddon = fitAddon;

        // Delay fit() to ensure container has proper dimensions
        setTimeout(function () {
          if (self._destroyed) return;
          self._doFit();
        }, 100);

        // Handle user input: send to WebSocket as text
        terminal.onData(function (data) {
          if (self._destroyed || !ws || ws.readyState !== WebSocket.OPEN) return;
          ws.send(data);
        });

        terminal.focus();

        // Observe container resize
        var ro = new ResizeObserver(function () {
          if (self._destroyed || !terminal.element) return;
          self._doFit();
        });
        ro.observe(container);
        self._resizeObserver = ro;
      };

      // Handle PTY output: write to terminal
      ws.onmessage = function (event) {
        if (self._destroyed) return;
        if (typeof event.data === "string") {
          terminal.write(event.data);
        } else if (event.data instanceof ArrayBuffer) {
          // Binary data from PTY - write to terminal
          var bytes = new Uint8Array(event.data);
          terminal.write(bytes);
        }
      };

      ws.onerror = function () {
        if (self._destroyed) return;
        terminal.write("\r\nWebSocket connection error.\r\n");
        self._notifyError("WebSocket connection failed for PTY " + ptyId);
      };

      ws.onclose = function () {
        if (!self._destroyed) {
          terminal.write("\r\nConnection closed.\r\n");
        }
      };
    },

    // Fit terminal to container and notify PTY of new size
    _doFit: function () {
      if (this._destroyed || !this._fitAddon || !this._terminal) return;
      try {
        this._fitAddon.fit();
      } catch (e) {
        return;
      }
      // Send resize notification to PTY
      if (this._ws && this._ws.readyState === WebSocket.OPEN) {
        var msg = RESIZE_PREFIX + this._terminal.cols + "," + this._terminal.rows;
        try {
          this._ws.send(msg);
        } catch (e) {}
      }
    },

    _notifyError: function (message) {
      try {
        var remote = rap.getRemoteObject(this);
        if (remote) {
          remote.notify("terminalError", { message: message });
        }
      } catch (e) {}
    },

    set: function (properties) {
      if (this._destroyed) return;

      if (properties.fontSizePercent !== undefined && this._terminal) {
        var base = 13;
        var fontSize = Math.round(base * (properties.fontSizePercent / 100));
        if (this._terminal.options) {
          this._terminal.options.fontSize = fontSize;
        } else if (this._terminal.setOption) {
          this._terminal.setOption("fontSize", fontSize);
        }
        this._doFit();
      }
    },

    setPtyId: function (ptyId) {
      this._properties.ptyId = ptyId;
    },

    setShellPath: function (shellPath) {
      this._properties.shellPath = shellPath;
    },

    setWorkingDirectory: function (workingDirectory) {
      this._properties.workingDirectory = workingDirectory;
    },

    setFontSizePercent: function (fontSizePercent) {
      this.set({ fontSizePercent: fontSizePercent });
    },

    destroy: function () {
      this._destroyed = true;
      if (this._ws) {
        try { this._ws.close(); } catch (e) {}
        this._ws = null;
      }
      if (this._terminal) {
        try { this._terminal.dispose(); } catch (e) {}
        this._terminal = null;
      }
      if (this._container && this._container.parentNode) {
        this._container.parentNode.removeChild(this._container);
      }
    },
  };

  rap.registerTypeHandler("hop.Terminal", {
    factory: function (properties) {
      return new hop.Terminal(properties);
    },
    destructor: function (widget) {
      widget.destroy();
    },
    properties: ["ptyId", "shellPath", "workingDirectory", "fontSizePercent"],
    events: ["terminalError"],
  });

})();
