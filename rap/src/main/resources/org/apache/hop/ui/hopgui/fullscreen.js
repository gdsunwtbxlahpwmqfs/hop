/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

(function() {
  'use strict';

  // display-mode:fullscreen is true for BOTH browser-native fullscreen (F11,
  // macOS green button) AND Fullscreen API fullscreen (requestFullscreen).
  // document.fullscreenElement is only set for the latter.
  // JavaScript CANNOT exit browser-native fullscreen — only the user can (Esc/F11).
  var fullscreenMQ = window.matchMedia('(display-mode: fullscreen)');

  function isInApiFullscreen() {
    return !!(
      document.fullscreenElement ||
      document.webkitFullscreenElement ||
      document.mozFullScreenElement ||
      document.msFullscreenElement
    );
  }

  function isAnyFullscreen() {
    return fullscreenMQ.matches || isInApiFullscreen();
  }

  // Show a transient toast telling the user to press Esc/F11 to exit
  // browser-native fullscreen (JS cannot do it programmatically).
  function showNativeFullscreenHint() {
    // Avoid duplicate toasts
    if (document.getElementById('hop-fs-hint')) {
      return;
    }
    var hint = document.createElement('div');
    hint.id = 'hop-fs-hint';
    hint.textContent = '请按 Esc 或 F11 退出浏览器全屏';
    hint.style.cssText =
      'position:fixed;top:50%;left:50%;transform:translate(-50%,-50%);'
      + 'background:rgba(40,40,40,0.92);color:#fff;padding:16px 28px;'
      + 'border-radius:8px;font-size:15px;z-index:2147483647;'
      + 'box-shadow:0 4px 20px rgba(0,0,0,0.4);pointer-events:none;'
      + 'font-family:sans-serif;';
    document.body.appendChild(hint);
    setTimeout(function() {
      if (hint.parentNode) {
        hint.parentNode.removeChild(hint);
      }
    }, 2500);
  }

  window.hopToggleFullscreen = function() {
    var el = document.documentElement;

    if (!isAnyFullscreen()) {
      // Not in any fullscreen → enter API fullscreen
      var req =
        el.requestFullscreen ||
        el.webkitRequestFullscreen ||
        el.mozRequestFullScreen ||
        el.msRequestFullscreen;
      if (req) {
        req.call(el);
      }
    } else if (isInApiFullscreen()) {
      // In API fullscreen → exit it
      var exit =
        document.exitFullscreen ||
        document.webkitExitFullscreen ||
        document.mozCancelFullScreen ||
        document.msExitFullscreen;
      if (exit) {
        exit.call(document);
      }
    } else {
      // In browser-native fullscreen (F11/macOS) → JS cannot exit, show hint
      showNativeFullscreenHint();
    }
  };
})();
