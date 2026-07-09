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

  console.log('[Hop] fullscreen.js loaded');

  function toggleFullscreen() {
    console.log('[Hop] toggleFullscreen called');
    var el = document.documentElement;
    if (!document.fullscreenElement && !document.webkitFullscreenElement) {
      console.log('[Hop] entering fullscreen');
      if (el.requestFullscreen) {
        el.requestFullscreen();
      } else if (el.webkitRequestFullscreen) {
        el.webkitRequestFullscreen();
      }
    } else {
      console.log('[Hop] exiting fullscreen');
      if (document.exitFullscreen) {
        document.exitFullscreen();
      } else if (document.webkitExitFullscreen) {
        document.webkitExitFullscreen();
      }
    }
  }

  document.addEventListener('click', function(event) {
    var target = event.target;
    var menuItem = target.closest('[role="menuitem"]');
    if (!menuItem) {
      return;
    }

    var text = menuItem.textContent ? menuItem.textContent.trim() : '';
    console.log('[Hop] clicked menuitem: "' + text + '"');

    if (text === 'Full screen' || text === '全屏') {
      console.log('[Hop] Fullscreen menu item clicked');
      toggleFullscreen();
    }
  }, true);

  document.addEventListener('keydown', function(event) {
    if ((event.altKey || event.metaKey) && event.key === 'F11') {
      event.preventDefault();
      toggleFullscreen();
    }
    if (event.key === 'F11') {
      event.preventDefault();
      toggleFullscreen();
    }
  }, true);
})();