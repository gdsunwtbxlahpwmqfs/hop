/* Hop Web Upload — tus-js-client upload manager */

(function () {
  'use strict';

  function showError(msg) {
    var d = document.createElement('div');
    d.style.cssText = 'position:fixed;top:0;left:0;right:0;background:red;color:white;padding:10px;z-index:9999;font-size:14px;word-break:break-all;';
    d.textContent = 'JS Error: ' + msg;
    document.body.appendChild(d);
  }

  try {

  // ----- Config -----
  var config = {};
  try {
    var el = document.getElementById('upload-config');
    if (el) config = JSON.parse(el.textContent);
  } catch (e) { /* ignore */ }

  var TUS_ENDPOINT = (config.contextPath || '') + '/upload/';
  var DEST = config.dest || '';
  var CHUNK_SIZE = config.chunkSize || 5 * 1024 * 1024;
  var MAX_SIZE = config.maxSize || 0;

  // ----- i18n (injected by server) -----
  var I18N = window.i18n || {};

  var uploads = {};        // uploadId -> { file, upload, status, ... }
  var fileCounter = 0;

  // ----- DOM refs -----
  var dropZone = document.getElementById('drop-zone');
  var fileInput = document.getElementById('file-input');
  var selectLink = document.getElementById('select-link');
  var fileTbody = document.getElementById('file-tbody');
  var emptyMsg = document.getElementById('empty-msg');
  var destDisplay = document.getElementById('dest-display');
  var btnPauseAll = document.getElementById('btn-pause-all');
  var btnCancelAll = document.getElementById('btn-cancel-all');
  var btnClose = document.getElementById('btn-close');
  var strategyRadios = document.querySelectorAll('input[name="strategy"]');

  // ----- Apply i18n to DOM -----
  function applyI18n() {
    setText('page-heading', I18N.pageHeading);
    setText('dest-dir-label', I18N.destDirLabel);
    setText('strategy-label', I18N.strategyLabel);
    setText('strategy-rename', I18N.strategyRename);
    setText('strategy-overwrite', I18N.strategyOverwrite);
    applyDropAreaText(I18N.dropAreaText);
    setText('th-name', I18N.tableName);
    setText('th-size', I18N.tableSize);
    setText('th-progress', I18N.tableProgress);
    setText('th-speed', I18N.tableSpeed);
    setText('th-status', I18N.tableStatus);
    setText('th-action', I18N.tableAction);
    setText('empty-msg', I18N.empty);
    setText('btn-pause-all', I18N.pauseAll);
    setText('btn-cancel-all', I18N.cancelAll);
    setText('btn-close', I18N.close);
    if (document.title) {
      document.title = I18N.pageTitle || document.title;
    }
  }
  function setText(id, val) {
    var el = document.getElementById(id);
    if (el) el.textContent = val || '';
  }
  function applyDropAreaText(template) {
    var el = document.getElementById('drop-area-text');
    if (!el) return;
    if (template) {
      var parts = template.split('{0}');
      if (parts.length > 1) {
        var before = parts[0];
        var rest = parts[1].split('{1}');
        el.innerHTML = before + '<a href="#" id="select-link">' + (rest[0] || '') + '</a>' + (rest[1] || '');
        selectLink = document.getElementById('select-link');
        if (selectLink) {
          selectLink.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            fileInput.click();
          });
        }
      } else {
        el.textContent = template;
      }
    }
  }
  applyI18n();

  // ----- Helpers -----
  function formatSize(bytes) {
    if (bytes === 0) return '0 B';
    var units = ['B', 'KB', 'MB', 'GB'];
    var i = Math.floor(Math.log(bytes) / Math.log(1024));
    return (bytes / Math.pow(1024, i)).toFixed(i > 0 ? 1 : 0) + ' ' + units[i];
  }

  function formatSpeed(bytesPerSec) {
    if (bytesPerSec <= 0) return '';
    return formatSize(bytesPerSec) + '/s';
  }

  function getStrategy() {
    for (var i = 0; i < strategyRadios.length; i++) {
      if (strategyRadios[i].checked) return strategyRadios[i].value;
    }
    return 'rename';
  }

  function now() { return Date.now(); }

  function hasActiveUploads() {
    for (var id in uploads) {
      var s = uploads[id].status;
      if (s === 'running' || s === 'paused') return true;
    }
    return false;
  }

  // ----- Row management -----
  function createRow(file) {
    var id = 'upload-' + (++fileCounter);
    var tr = document.createElement('tr');
    tr.id = id;

    var sizeStr = formatSize(file.size);
    tr.innerHTML =
      '<td class="col-name"><div class="file-name-cell">' +
      '<span class="file-icon">&#x1F4C4;</span>' +
      '<span class="file-label">' + escapeHtml(file.name) + '</span></div></td>' +
      '<td class="col-size">' + sizeStr + '</td>' +
      '<td class="col-progress">' +
      '<div class="progress-wrapper">' +
      '<div class="progress-bar"><div class="progress-fill" id="' + id + '-fill"></div></div>' +
      '<span class="progress-pct" id="' + id + '-pct">0%</span>' +
      '</div></td>' +
      '<td class="col-speed"><span id="' + id + '-speed"></span></td>' +
      '<td class="col-status"><span class="status-text" id="' + id + '-status">' + (I18N.statusWaiting || 'Waiting') + '</span></td>' +
      '<td class="col-action" id="' + id + '-actions">' +
      '<button class="action-btn" id="' + id + '-pause">' + (I18N.statusPause || 'Pause') + '</button> ' +
      '<button class="action-btn danger" id="' + id + '-cancel">' + (I18N.remove || 'Remove') + '</button>' +
      '</td>';

    fileTbody.appendChild(tr);
    emptyMsg.style.display = 'none';
    updateButtons();

    document.getElementById(id + '-pause').addEventListener('click', function () {
      togglePause(id);
    });
    document.getElementById(id + '-cancel').addEventListener('click', function () {
      cancelUpload(id);
    });

    return id;
  }

  function removeRow(id) {
    var tr = document.getElementById(id);
    if (tr) tr.remove();
    delete uploads[id];
    if (fileTbody.children.length === 0) {
      emptyMsg.style.display = 'block';
    }
    updateButtons();
  }

  function updateRow(id) {
    var u = uploads[id];
    if (!u) return;

    var fill = document.getElementById(id + '-fill');
    var pct = document.getElementById(id + '-pct');
    var speedEl = document.getElementById(id + '-speed');
    var statusEl = document.getElementById(id + '-status');
    var pauseBtn = document.getElementById(id + '-pause');

    if (!fill) return;

    var pctVal = u.total > 0 ? Math.round((u.offset / u.total) * 100) : 0;
    fill.style.width = pctVal + '%';
    pct.textContent = pctVal + '%';

    if (u.status === 'running') {
      fill.className = 'progress-fill';
      statusEl.className = 'status-text running';
      statusEl.textContent = I18N.statusUploading || 'Uploading';
      pauseBtn.textContent = I18N.statusPause || 'Pause';
      pauseBtn.disabled = false;
      speedEl.textContent = formatSpeed(u.speed);
    } else if (u.status === 'paused') {
      fill.className = 'progress-fill';
      statusEl.className = 'status-text paused';
      statusEl.textContent = I18N.statusPaused || 'Paused';
      pauseBtn.textContent = I18N.statusResume || 'Resume';
      pauseBtn.disabled = false;
      speedEl.textContent = '';
    } else if (u.status === 'done') {
      fill.className = 'progress-fill done';
      fill.style.width = '100%';
      pct.textContent = '100%';
      statusEl.className = 'status-text done';
      statusEl.textContent = I18N.statusCompleted || 'Completed';
      pauseBtn.textContent = I18N.statusPause || 'Pause';
      pauseBtn.disabled = true;
      speedEl.textContent = '';
    } else if (u.status === 'error') {
      fill.className = 'progress-fill error';
      statusEl.className = 'status-text error';
      statusEl.textContent = I18N.statusFailed || 'Failed';
      pauseBtn.disabled = true;
      speedEl.textContent = '';
      showRetryButton(id);
    } else if (u.status === 'cancelled') {
      fill.className = 'progress-fill error';
      statusEl.className = 'status-text error';
      statusEl.textContent = I18N.statusCancelled || 'Cancelled';
      pauseBtn.disabled = true;
      speedEl.textContent = '';
    }
  }

  function showRetryButton(id) {
    var actions = document.getElementById(id + '-actions');
    if (!actions) return;
    var existing = document.getElementById(id + '-retry');
    if (existing) return;
    var btn = document.createElement('button');
    btn.className = 'action-btn';
    btn.id = id + '-retry';
    btn.textContent = I18N.statusRetry || 'Retry';
    btn.addEventListener('click', function () { retryUpload(id); });
    actions.appendChild(btn);
  }

  function updateButtons() {
    var hasActive = hasActiveUploads();
    btnPauseAll.disabled = !hasActive;
    btnCancelAll.disabled = !hasActive;
  }

  // ----- Core upload logic -----
  function createUploadOptions(file, id) {
    var meta = {
      filename: file.name,
      destPath: DEST
    };
    if (getStrategy() === 'overwrite') {
      meta.strategy = 'overwrite';
    }
    return {
      endpoint: TUS_ENDPOINT,
      retryDelays: [0, 1000, 3000, 5000, 10000],
      chunkSize: CHUNK_SIZE,
      metadata: meta,
      storeFingerprintForResuming: true,
      fingerprint: function (f) {
        return Promise.resolve(
          'hop-upload-' + f.name + '-' + f.size + '-' + f.lastModified + '-' + DEST
        );
      },
      onError: function (error) {
        appendDebug('onError: ' + (error && error.message ? error.message : String(error)));
        var u = uploads[id];
        if (u) {
          u.status = 'error';
          u.error = error;
          updateRow(id);
          updateButtons();
        }
      },
      onProgress: function (bytesSent, bytesTotal) {
        appendDebug('onProgress: ' + bytesSent + '/' + bytesTotal);
        var u = uploads[id];
        if (!u) return;
        var nowT = now();
        if (u.lastProgressTime) {
          var elapsed = (nowT - u.lastProgressTime) / 1000;
          if (elapsed > 0) {
            u.speed = (bytesSent - u.lastProgressBytes) / elapsed;
          }
        }
        u.lastProgressTime = nowT;
        u.lastProgressBytes = bytesSent;
        u.offset = bytesSent;
        u.total = bytesTotal;
        updateRow(id);
      },
      onSuccess: function () {
        var u = uploads[id];
        if (u) {
          u.status = 'done';
          u.offset = u.total;
          updateRow(id);
          updateButtons();
        }
        // Notify host to refresh file tree, but do NOT auto-close
        notifyUploadComplete(u ? u.file.name : '');
      },
      onShouldRetry: function (err) {
        return !(err && err.originalRequest && err.originalRequest.status === 401);
      }
    };
  }

  function startUpload(file) {
    appendDebug('startUpload: ' + file.name + ' (' + file.size + ' bytes)');
    var id = createRow(file);
    var upload = new tus.Upload(file, createUploadOptions(file, id));

    uploads[id] = {
      file: file,
      upload: upload,
      status: 'running',
      offset: 0,
      total: file.size,
      speed: 0,
      lastProgressTime: 0,
      lastProgressBytes: 0,
      error: null
    };

    try {
      upload.start();
      appendDebug('upload.start() OK for ' + file.name);
    } catch (e) {
      appendDebug('upload.start() ERROR: ' + e.message);
    }
    updateRow(id);
    updateButtons();
  }

  function togglePause(id) {
    var u = uploads[id];
    if (!u) return;
    if (u.status === 'paused') {
      u.status = 'running';
      try {
        u.upload.start();
      } catch (e) { /* ignore */ }
    } else if (u.status === 'running') {
      try {
        u.upload.abort();
      } catch (e) { /* ignore */ }
      u.status = 'paused';
    }
    updateRow(id);
    updateButtons();
  }

  function cancelUpload(id) {
    var u = uploads[id];
    if (!u) return;
    u.status = 'cancelled';
    try {
      u.upload.abort(true);
    } catch (e) { /* ignore */ }
    if (u.upload.url) {
      var xhr = new XMLHttpRequest();
      xhr.open('DELETE', u.upload.url, true);
      xhr.setRequestHeader('Tus-Resumable', '1.0.0');
      xhr.send();
    }
    removeRow(id);
  }

  function retryUpload(id) {
    var u = uploads[id];
    if (!u) return;
    var btn = document.getElementById(id + '-retry');
    if (btn) btn.remove();
    u.status = 'running';
    u.offset = 0;
    u.speed = 0;
    u.lastProgressTime = 0;
    u.lastProgressBytes = 0;
    u.upload = new tus.Upload(u.file, createUploadOptions(u.file, id));
    u.upload.start();
    updateRow(id);
    updateButtons();
  }

  // ----- Bulk actions -----
  function pauseAll() {
    var hasRunning = false;
    for (var id in uploads) {
      if (uploads[id].status === 'running') { hasRunning = true; break; }
    }
    for (var id2 in uploads) {
      if (hasRunning && uploads[id2].status === 'running') {
        togglePause(id2);
      } else if (!hasRunning && uploads[id2].status === 'paused') {
        togglePause(id2);
      }
    }
  }

  function cancelAll() {
    var ids = Object.keys(uploads);
    for (var i = 0; i < ids.length; i++) {
      cancelUpload(ids[i]);
    }
  }

  // ----- File input -----
  function handleFiles(files) {
    appendDebug('handleFiles: ' + (files ? files.length : 0) + ' files');
    if (!files || files.length === 0) return;
    for (var i = 0; i < files.length; i++) {
      var file = files[i];
      if (MAX_SIZE > 0 && file.size > MAX_SIZE) {
        var msg = (I18N.errorTooLarge || 'File "{0}" exceeds the maximum upload size of {1}');
        msg = msg.replace('{0}', file.name).replace('{1}', formatSize(MAX_SIZE));
        alert(msg);
        continue;
      }
      startUpload(file);
    }
  }

  // ----- Events -----
  fileInput.addEventListener('change', function () {
    appendDebug('fileInput.change fired');
    handleFiles(this.files);
    this.value = '';
  });

  dropZone.addEventListener('click', function (e) {
    if (e.target === dropZone || e.target.classList.contains('drop-content') || e.target.tagName === 'P') {
      fileInput.click();
    }
  });

  dropZone.addEventListener('dragover', function (e) {
    e.preventDefault();
    dropZone.classList.add('drag-over');
  });

  dropZone.addEventListener('dragleave', function () {
    dropZone.classList.remove('drag-over');
  });

  dropZone.addEventListener('drop', function (e) {
    e.preventDefault();
    dropZone.classList.remove('drag-over');
    handleFiles(e.dataTransfer.files);
  });

  btnPauseAll.addEventListener('click', pauseAll);
  btnCancelAll.addEventListener('click', cancelAll);

  btnClose.addEventListener('click', function () {
    if (hasActiveUploads()) {
      var confirmMsg = I18N.confirmClose || 'There are active uploads. They will be paused. Continue?';
      if (!confirm(confirmMsg)) {
        return;
      }
      // Pause all running uploads before closing
      for (var id in uploads) {
        if (uploads[id].status === 'running') {
          try { uploads[id].upload.abort(); } catch (e) { /* ignore */ }
          uploads[id].status = 'paused';
          updateRow(id);
        }
      }
    }
    if (window.onHopUploadClose) {
      window.onHopUploadClose();
    } else {
      window.close();
    }
  });

  // ----- Debug -----
  var debugLines = [];
  function appendDebug(msg) {
    debugLines.push(msg);
    var dbg = document.getElementById('js-debug');
    if (!dbg) {
      dbg = document.createElement('div');
      dbg.id = 'js-debug';
      dbg.style.cssText = 'position:fixed;bottom:0;left:0;right:0;background:#333;color:#0f0;padding:4px 8px;z-index:9999;font-size:11px;font-family:monospace;max-height:200px;overflow-y:auto;white-space:pre-wrap;';
      document.body.appendChild(dbg);
    }
    dbg.textContent = debugLines.join('\n');
  }

  // ----- Display dest -----
  if (destDisplay) {
    if (DEST) {
      destDisplay.textContent = DEST;
    } else {
      destDisplay.textContent = '(未指定,将上传到服务器工作目录)';
      destDisplay.style.color = 'var(--warning)';
    }
  }

  // ----- Debug: mark JS as loaded -----
  appendDebug('JS OK | dest=' + DEST + ' | endpoint=' + TUS_ENDPOINT + ' | tus=' + (typeof tus !== 'undefined' ? 'yes' : 'no'));

  // ----- Utility -----
  function escapeHtml(str) {
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(str));
    return div.innerHTML;
  }

  // ----- Notify host (BrowserFunction if embedded, postMessage if standalone) -----
  // Only refresh the file tree; the dialog stays open for the user to close manually.
  function notifyUploadComplete(fileName) {
    if (window.onHopUploadComplete) {
      window.onHopUploadComplete(JSON.stringify({ type: 'hop-upload-complete', file: fileName }));
    } else {
      try {
        window.parent.postMessage({ type: 'hop-upload-complete' }, '*');
      } catch (e) { /* ignore */ }
    }
  }

  } catch (err) {
    showError(err && err.message ? err.message : String(err));
  }

})();
