"use strict";

(function () {
  var XTERM_BASE = "rwt-resources/xterm";

  function buildWsUrl(ptyId) {
    var loc = window.location;
    var proto = loc.protocol === "https:" ? "wss:" : "ws:";
    var path = loc.pathname.replace(/\/ui-dark$|\/ui$/, "") + "/pty/" + encodeURIComponent(ptyId);
    return proto + "//" + loc.host + path;
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

  hop.Terminal = function (properties) {
    this._destroyed = false;
    this._terminal = null;
    this._ws = null;
    this._container = null;
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

      // Create container div that fills the parent
      var container = document.createElement("div");
      container.style.width = "100%";
      container.style.height = "100%";
      container.style.overflow = "hidden";
      parentEl.appendChild(container);
      this._container = container;

      // Load xterm.js script lazily
      this._loadXterm(function () {
        if (self._destroyed) return;
        self._initTerminal(container, ptyId);
      });
    },

    _loadXterm: function (callback) {
      var self = this;
      var base = XTERM_BASE;
      var loadedCount = 0;
      var requiredCount = 0;

      if (!window.Terminal) {
        requiredCount++;
      }
      if (!window.AttachAddon) {
        requiredCount++;
      }
      if (!window.FitAddon) {
        requiredCount++;
      }

      if (requiredCount === 0) {
        callback();
        return;
      }

      var checkLoaded = function () {
        if (++loadedCount >= requiredCount) {
          callback();
        }
      };

      if (!window.Terminal) {
        var script = document.createElement("script");
        script.src = base + "/xterm.js";
        script.onload = checkLoaded;
        script.onerror = function () {
          var fallback = document.createElement("script");
          fallback.src = "https://cdn.jsdelivr.net/npm/xterm@" + self._getXtermVersion() + "/lib/xterm.js";
          fallback.onload = checkLoaded;
          fallback.onerror = checkLoaded;
          document.head.appendChild(fallback);
        };
        document.head.appendChild(script);
      }

      if (!window.AttachAddon) {
        var attachScript = document.createElement("script");
        attachScript.src = base + "/xterm-addon-attach.js";
        attachScript.onload = checkLoaded;
        attachScript.onerror = function () {
          var fallback = document.createElement("script");
          fallback.src = "https://cdn.jsdelivr.net/npm/xterm-addon-attach@" + self._getAttachVersion() + "/lib/xterm-addon-attach.js";
          fallback.onload = checkLoaded;
          fallback.onerror = checkLoaded;
          document.head.appendChild(fallback);
        };
        document.head.appendChild(attachScript);
      }

      if (!window.FitAddon) {
        var fitScript = document.createElement("script");
        fitScript.src = base + "/xterm-addon-fit.js";
        fitScript.onload = checkLoaded;
        fitScript.onerror = function () {
          var fallback = document.createElement("script");
          fallback.src = "https://cdn.jsdelivr.net/npm/xterm-addon-fit@0.8.0/lib/xterm-addon-fit.js";
          fallback.onload = checkLoaded;
          fallback.onerror = checkLoaded;
          document.head.appendChild(fallback);
        };
        document.head.appendChild(fitScript);
      }
    },

    _initTerminal: function (container, ptyId) {
      var self = this;

      // Detect dark mode from RAP theme
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

      var terminal = new Terminal({
        cursorBlink: true,
        cursorStyle: "block",
        fontSize: 13,
        fontFamily: "Menlo, Monaco, 'Courier New', monospace",
        theme: theme,
        allowTransparency: false,
        cols: 80,
        rows: 24,
        convertEol: true,
      });
      self._terminal = terminal;

      // Connect WebSocket
      var wsUrl = buildWsUrl(ptyId);
      var ws = new WebSocket(wsUrl);
      self._ws = ws;

      ws.onopen = function () {
        if (self._destroyed) {
          ws.close();
          return;
        }
        var attachAddon = new AttachAddon.AttachAddon(ws);
        terminal.loadAddon(attachAddon);
        terminal.open(container);

        // Fit terminal to container
        var fitAddon = null;
        if (typeof FitAddon !== 'undefined') {
          try {
            fitAddon = new FitAddon.FitAddon();
            terminal.loadAddon(fitAddon);
            fitAddon.fit();
          } catch (e) {
            fitAddon = null;
          }
        }
        self._fitAddon = fitAddon;

        terminal.focus();

        // Re-fit on resize
        var ro = new ResizeObserver(function () {
          if (self._destroyed || !terminal.element) return;
          if (self._fitAddon) {
            try { self._fitAddon.fit(); } catch (e) {}
          }
        });
        ro.observe(container);
        self._resizeObserver = ro;
      };

      ws.onerror = function () {
        terminal.write("WebSocket connection error.\r\n");
        self._notifyError("WebSocket connection failed for PTY " + ptyId);
      };

      ws.onclose = function () {
        if (!self._destroyed) {
          terminal.write("\r\nConnection closed.\r\n");
        }
      };
    },

    _notifyError: function (message) {
      try {
        var remote = rap.getRemoteObject(this);
        if (remote) {
          remote.notify("terminalError", { message: message });
        }
      } catch (e) {}
    },

    _getXtermVersion: function () {
      return "5.3.0";
    },

    _getAttachVersion: function () {
      return "0.9.0";
    },

    // Called when server updates properties
    set: function (properties) {
      if (this._destroyed) return;

      if (properties.fontSizePercent !== undefined && this._terminal) {
        var base = 13;
        this._terminal.setOption("fontSize", Math.round(base * (properties.fontSizePercent / 100)));
        if (this._fitAddon) {
          try { this._fitAddon.fit(); } catch (e) {}
        }
      }
    },
  };

  rap.registerTypeHandler("hop.Terminal", {
    factory: function (properties) {
      return new hop.Terminal(properties);
    },
    destructor: function (widget) {
      widget._destroyed = true;
      if (widget._ws) {
        try { widget._ws.close(); } catch (e) {}
        widget._ws = null;
      }
      if (widget._terminal) {
        try { widget._terminal.dispose(); } catch (e) {}
        widget._terminal = null;
      }
      if (widget._container && widget._container.parentNode) {
        widget._container.parentNode.removeChild(widget._container);
      }
    },
    properties: ["ptyId", "shellPath", "workingDirectory", "fontSizePercent"],
    events: ["terminalError"],
  });

})();
