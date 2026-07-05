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

/**
 * Hop Web Login Page — client logic.
 *
 * Organised into four concerns:
 *   1. Config       — reads server-injected runtime config (context path, redirect, error).
 *   2. Validators   — pure functions for account / password format validation.
 *   3. UI components — field error display, password toggle, remember-me persistence.
 *   4. Form handler — ties everything together and submits via AJAX.
 */
(function () {
  'use strict';

  /* =======================================================================
     1. Config — parsed from the JSON injected by LoginServlet
     ===================================================================== */
  var config = { contextPath: '', redirect: '', error: false };
  var configEl = document.getElementById('login-config');
  if (configEl && configEl.textContent) {
    try {
      var parsed = JSON.parse(configEl.textContent);
      config.contextPath = parsed.contextPath || '';
      config.redirect = parsed.redirect || '';
      config.error = !!parsed.error;
    } catch (e) {
      console.warn('Failed to parse login config', e);
    }
  }

  /* =======================================================================
     2. Validators — pure functions returning { valid, message }
     ===================================================================== */

  /** Username: 2-64 chars, allows letters, digits, Chinese, dot, underscore, dash, @. */
  var USERNAME_RE = /^[a-zA-Z0-9\u4e00-\u9fa5._@\-]{2,64}$/;
  /** Password: 8+ chars with at least one letter and one digit. */
  var PASSWORD_RE = /^(?=.*[a-zA-Z])(?=.*\d)[\s\S]{8,}$/;

  /**
   * Validates the account field. Accepts any username/nickname (2–64 chars, common safe
   * characters including Chinese). Email and phone are also accepted but not required.
   * @param {string} value
   * @returns {{valid:boolean, message:string}}
   */
  function validateAccount(value) {
    if (!value) {
      return { valid: false, message: '请输入账号' };
    }
    if (value.length < 2) {
      return { valid: false, message: '账号至少 2 个字符' };
    }
    if (!USERNAME_RE.test(value)) {
      return { valid: false, message: '账号含不允许的字符' };
    }
    return { valid: true, message: '' };
  }

  /**
   * Validates the password field.
   * @param {string} value
   * @returns {{valid:boolean, message:string}}
   */
  function validatePassword(value) {
    if (!value) {
      return { valid: false, message: '请输入密码' };
    }
    if (value.length < 8) {
      return { valid: false, message: '密码至少 8 位字符' };
    }
    if (!PASSWORD_RE.test(value)) {
      return { valid: false, message: '密码须包含字母和数字' };
    }
    return { valid: true, message: '' };
  }

  /* =======================================================================
     3. UI components
     ===================================================================== */

  // DOM references
  var form = document.getElementById('loginForm');
  var usernameInput = document.getElementById('username');
  var passwordInput = document.getElementById('password');
  var usernameError = document.getElementById('usernameError');
  var passwordError = document.getElementById('passwordError');
  var toggleBtn = document.getElementById('togglePassword');
  var eyeOpen = toggleBtn.querySelector('.eye-open');
  var eyeClosed = toggleBtn.querySelector('.eye-closed');
  var rememberCheckbox = document.getElementById('remember');
  var submitBtn = document.getElementById('submitBtn');
  var btnText = submitBtn.querySelector('.btn-text');
  var btnLoading = submitBtn.querySelector('.btn-loading');
  var alertBanner = document.getElementById('alertBanner');
  var alertText = document.getElementById('alertText');

  /** localStorage keys for "remember me". */
  var STORAGE_KEY_USER = 'hop.login.rememberedUser';
  var STORAGE_KEY_REMEMBER = 'hop.login.remember';
  // Also remember the password hash so returning users skip the login form
  // entirely. Only the SHA-256 digest is ever stored — never the plaintext.
  var STORAGE_KEY_PWD = 'hop.login.rememberedPwd';

  /**
   * Shows or hides a field-level error message.
   * @param {HTMLElement} inputEl the input element
   * @param {HTMLElement} errorEl the error text element
   * @param {{valid:boolean, message:string}} result
   */
  function setFieldError(inputEl, errorEl, result) {
    if (result.valid) {
      inputEl.classList.remove('error');
      errorEl.textContent = '';
      errorEl.classList.remove('visible');
    } else {
      inputEl.classList.add('error');
      errorEl.textContent = result.message;
      errorEl.classList.add('visible');
    }
    updateSubmitState();
  }

  /** Clears the field error state (used on focus). */
  function clearFieldError(inputEl, errorEl) {
    inputEl.classList.remove('error');
    errorEl.textContent = '';
    errorEl.classList.remove('visible');
    updateSubmitState();
  }

  /**
   * Enables / disables the submit button based on overall form validity.
   * Uses a lightweight check (non-empty + previously validated) so the button
   * does not block until the user has interacted with both fields.
   */
  function updateSubmitState() {
    var hasErrors = usernameInput.classList.contains('error')
        || passwordInput.classList.contains('error');
    var hasValues = usernameInput.value.trim().length > 0
        && passwordInput.value.length > 0;
    submitBtn.disabled = hasErrors || !hasValues;
  }

  /** Shows the global alert banner. */
  function showAlert(message) {
    alertText.textContent = message;
    alertBanner.hidden = false;
    // Re-trigger animation
    alertBanner.style.animation = 'none';
    alertBanner.offsetHeight; // force reflow
    alertBanner.style.animation = '';
  }

  /** Hides the global alert banner. */
  function hideAlert() {
    alertBanner.hidden = true;
  }

  /** Shows the full-screen transition overlay (used after successful login). */
  function showTransitionOverlay() {
    var overlay = document.getElementById('transitionOverlay');
    if (overlay) {
      overlay.hidden = false;
    }
  }

  /* ----- Password visibility toggle ----- */
  toggleBtn.addEventListener('click', function () {
    var isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';
    // Use inline style.display (highest specificity) to avoid conflicts with
    // CSS flex/display rules that can override the `hidden` attribute.
    // password now visible (text)  -> show open eye, hide slashed eye
    // password now hidden (password) -> show slashed eye, hide open eye
    eyeOpen.style.display = isPassword ? 'block' : 'none';
    eyeClosed.style.display = isPassword ? 'none' : 'block';
    // Return focus to the input for a seamless experience
    passwordInput.focus();
  });

  /* ----- Field validation wiring ----- */

  // Validate on blur (non-intrusive), clear errors on input.
  usernameInput.addEventListener('blur', function () {
    if (usernameInput.value.trim()) {
      setFieldError(usernameInput, usernameError, validateAccount(usernameInput.value.trim()));
    }
  });
  usernameInput.addEventListener('input', function () {
    clearFieldError(usernameInput, usernameError);
    hideAlert();
  });
  usernameInput.addEventListener('focus', function () {
    clearFieldError(usernameInput, usernameError);
  });

  passwordInput.addEventListener('blur', function () {
    if (passwordInput.value) {
      setFieldError(passwordInput, passwordError, validatePassword(passwordInput.value));
    }
  });
  passwordInput.addEventListener('input', function () {
    clearFieldError(passwordInput, passwordError);
    hideAlert();
  });
  passwordInput.addEventListener('focus', function () {
    clearFieldError(passwordInput, passwordError);
  });

  /* ----- Remember me persistence ----- */

  /**
   * Restores the remembered credentials from localStorage on page load.
   *
   * If both username and password hash were remembered (user previously
   * checked "记住我"), performs an automatic silent login so the user
   * bypasses the form entirely. Otherwise pre-fills the username and
   * focuses the password field.
   */
  function restoreRememberedUser() {
    try {
      var remember = localStorage.getItem(STORAGE_KEY_REMEMBER);
      var savedUser = localStorage.getItem(STORAGE_KEY_USER);
      var savedPwd = localStorage.getItem(STORAGE_KEY_PWD);
      if (remember === 'true' && savedUser && savedPwd) {
        // Pre-fill the form (so if auto-login fails the user sees their
        // username and only needs to retype the password).
        usernameInput.value = savedUser;
        rememberCheckbox.checked = true;
        // Attempt silent auto-login with the remembered hash.
        silentLogin(savedUser, savedPwd);
      } else if (remember === 'true' && savedUser) {
        usernameInput.value = savedUser;
        rememberCheckbox.checked = true;
        setTimeout(function () { passwordInput.focus(); }, 100);
      } else {
        setTimeout(function () { usernameInput.focus(); }, 100);
      }
    } catch (e) {
      // localStorage may be unavailable (private mode) — silently ignore
      setTimeout(function () { usernameInput.focus(); }, 100);
    }
  }

  /**
   * Performs a silent login with remembered credentials. Shows a subtle
   * "signing in..." state on the button. Falls back to the manual form
   * (with username pre-filled) if the server rejects the credentials.
   * @param {string} username
   * @param {string} passwordHash SHA-256 hex digest
   */
  function silentLogin(username, passwordHash) {
    setLoading(true);
    var params = new URLSearchParams();
    params.append('username', username);
    params.append('password', passwordHash);
    params.append('remember', 'on');
    if (config.redirect) {
      params.append('redirect', config.redirect);
    }
    var loginUrl = config.contextPath + '/login';
    fetch(loginUrl, {
      method: 'POST',
      body: params.toString(),
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
        'X-Requested-With': 'XMLHttpRequest'
      }
    })
    .then(function (response) {
      return response.json().then(function (data) {
        data._status = response.status;
        return data;
      });
    })
    .then(function (data) {
      if (data.success && data.redirect) {
        showTransitionOverlay();
        setLoading(false);
        window.location.href = data.redirect;
      } else {
        // Silent login failed (e.g. password changed server-side) — clear
        // the stale remembered hash and let the user log in manually.
        setLoading(false);
        try { localStorage.removeItem(STORAGE_KEY_PWD); } catch (e) {}
        showAlert('已记住的账号信息已失效，请重新登录');
        passwordInput.focus();
      }
    })
    .catch(function () {
      setLoading(false);
      passwordInput.focus();
    });
  }

  /* =======================================================================
     3.5 Security — client-side SHA-256 pre-hash
     ===================================================================== */

  /**
   * Computes the SHA-256 digest of the password and returns it as a lowercase hex
   * string. The plaintext password is NEVER sent over the wire — only this digest
   * is transmitted. The server applies its own salt + iterations on top for DB
   * storage (defense in depth).
   *
   * Uses the Web Crypto API (available in all modern browsers and secure
   * contexts). Falls back to a pure-JS implementation if SubtleCrypto is
   * unavailable (e.g. older browsers / non-secure context on some browsers).
   *
   * @param {string} text
   * @returns {Promise<string>} 64-char lowercase hex digest
   */
  function sha256Hex(text) {
    if (window.crypto && window.crypto.subtle) {
      var data = new TextEncoder().encode(text);
      return window.crypto.subtle.digest('SHA-256', data).then(function (buf) {
        var bytes = new Uint8Array(buf);
        var hex = '';
        for (var i = 0; i < bytes.length; i++) {
          hex += (bytes[i] < 16 ? '0' : '') + bytes[i].toString(16);
        }
        return hex;
      });
    }
    // Fallback: pure-JS SHA-256 (rare path; keeps the page working everywhere).
    return Promise.resolve(sha256HexFallback(text));
  }

  /**
   * Minimal pure-JavaScript SHA-256 implementation for fallback environments.
   * Produces output identical to Web Crypto. Adapted from the public-domain
   * snippet widely used in browsers without SubtleCrypto.
   * @param {string} text
   * @returns {string}
   */
  function sha256HexFallback(text) {
    function rrot(x, n) { return (x >>> n) | (x << (32 - n)); }
    var H = [0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
             0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19];
    var K = [0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1,
             0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
             0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786,
             0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
             0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147,
             0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
             0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b,
             0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
             0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a,
             0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
             0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2];
    var bytes = new TextEncoder().encode(text);
    var l = bytes.length;
    var withPad = new Uint8Array(((l + 9 + 63) >> 6) << 6);
    withPad.set(bytes);
    withPad[l] = 0x80;
    var bitLen = l * 8;
    withPad[withPad.length - 4] = (bitLen >>> 24) & 0xff;
    withPad[withPad.length - 3] = (bitLen >>> 16) & 0xff;
    withPad[withPad.length - 2] = (bitLen >>> 8) & 0xff;
    withPad[withPad.length - 1] = bitLen & 0xff;
    for (var off = 0; off < withPad.length; off += 64) {
      var w = new Array(64);
      for (var i = 0; i < 16; i++) {
        w[i] = (withPad[off + i * 4] << 24) | (withPad[off + i * 4 + 1] << 16) |
               (withPad[off + i * 4 + 2] << 8) | (withPad[off + i * 4 + 3]);
      }
      for (var i = 16; i < 64; i++) {
        var s0 = rrot(w[i - 15], 7) ^ rrot(w[i - 15], 18) ^ (w[i - 15] >>> 3);
        var s1 = rrot(w[i - 2], 17) ^ rrot(w[i - 2], 19) ^ (w[i - 2] >>> 10);
        w[i] = (w[i - 16] + s0 + w[i - 7] + s1) | 0;
      }
      var a = H[0], b = H[1], c = H[2], d = H[3], e = H[4], f = H[5], g = H[6], h = H[7];
      for (var i = 0; i < 64; i++) {
        var S1 = rrot(e, 6) ^ rrot(e, 11) ^ rrot(e, 25);
        var ch = (e & f) ^ (~e & g);
        var t1 = (h + S1 + ch + K[i] + w[i]) | 0;
        var S0 = rrot(a, 2) ^ rrot(a, 13) ^ rrot(a, 22);
        var mj = (a & b) ^ (a & c) ^ (b & c);
        var t2 = (S0 + mj) | 0;
        h = g; g = f; f = e; e = (d + t1) | 0;
        d = c; c = b; b = a; a = (t1 + t2) | 0;
      }
      H[0] = (H[0] + a) | 0; H[1] = (H[1] + b) | 0; H[2] = (H[2] + c) | 0;
      H[3] = (H[3] + d) | 0; H[4] = (H[4] + e) | 0; H[5] = (H[5] + f) | 0;
      H[6] = (H[6] + g) | 0; H[7] = (H[7] + h) | 0;
    }
    var hex = '';
    for (var i = 0; i < 8; i++) {
      hex += (H[i] >>> 0).toString(16).padStart(8, '0');
    }
    return hex;
  }

  /* =======================================================================
     4. Form submission
     ===================================================================== */

  /** Sets the submit button to its loading state. */
  function setLoading(loading) {
    if (loading) {
      submitBtn.classList.add('loading');
      submitBtn.disabled = true;
      btnText.hidden = true;
      btnLoading.hidden = false;
    } else {
      submitBtn.classList.remove('loading');
      submitBtn.disabled = false;
      btnText.hidden = false;
      btnLoading.hidden = true;
    }
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();
    hideAlert();

    var username = usernameInput.value.trim();
    var password = passwordInput.value;

    // Final validation pass
    var accountResult = validateAccount(username);
    var passwordResult = validatePassword(password);
    setFieldError(usernameInput, usernameError, accountResult);
    setFieldError(passwordInput, passwordError, passwordResult);
    if (!accountResult.valid || !passwordResult.valid) {
      // Focus first invalid field
      if (!accountResult.valid) {
        usernameInput.focus();
      } else {
        passwordInput.focus();
      }
      return;
    }

    setLoading(true);

    // SHA-256 the password client-side so the plaintext never traverses the
    // network. The server applies its own salt + iterations on top of this
    // digest for database storage.
    sha256Hex(password).then(function (passwordHash) {
      // Persist "remember me" preference. When checked, store the username
      // and the password hash so the next visit can auto-login. Only the
      // SHA-256 digest is stored — never the plaintext password.
      try {
        if (rememberCheckbox.checked) {
          localStorage.setItem(STORAGE_KEY_REMEMBER, 'true');
          localStorage.setItem(STORAGE_KEY_USER, username);
          localStorage.setItem(STORAGE_KEY_PWD, passwordHash);
        } else {
          localStorage.removeItem(STORAGE_KEY_REMEMBER);
          localStorage.removeItem(STORAGE_KEY_USER);
          localStorage.removeItem(STORAGE_KEY_PWD);
        }
      } catch (e) {
        // Ignore localStorage errors
      }

      // Build form data — use URLSearchParams so the request is sent as
      // application/x-www-form-urlencoded, which the servlet can parse via
      // request.getParameter(). Send the SHA-256 hash, never the plaintext.
      var params = new URLSearchParams();
      params.append('username', username);
      params.append('password', passwordHash);
      params.append('remember', rememberCheckbox.checked ? 'on' : '');
      if (config.redirect) {
        params.append('redirect', config.redirect);
      }

      // Submit via AJAX for a smooth, no-reload experience
      var loginUrl = config.contextPath + '/login';
      fetch(loginUrl, {
        method: 'POST',
        body: params.toString(),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
          'X-Requested-With': 'XMLHttpRequest'
        }
      })
      .then(function (response) {
        // Parse JSON regardless of status code
        return response.json().then(function (data) {
          data._status = response.status;
          return data;
        });
      })
      .then(function (data) {
        if (data.success && data.redirect) {
          // Show transition overlay immediately — the RAP /ui page takes several
          // seconds to send its first response, and the browser keeps showing the
          // login page (with a spinning button) until that response arrives.
          showTransitionOverlay();
          // Stop the button spinner so it doesn't look stuck.
          setLoading(false);
          // Navigate to the target page.
          window.location.href = data.redirect;
        } else {
          setLoading(false);
          showAlert(data.message || '登录失败，请重试');
          passwordInput.value = '';
          passwordInput.focus();
        }
      })
      .catch(function () {
        setLoading(false);
        // Fallback: submit the form traditionally if fetch fails
        // (e.g. network error or unsupported browser). Sends the hash, not the
        // plaintext password.
        var fallbackForm = document.createElement('form');
        fallbackForm.method = 'POST';
        fallbackForm.action = loginUrl;
        appendHidden(fallbackForm, 'username', username);
        appendHidden(fallbackForm, 'password', passwordHash);
        appendHidden(fallbackForm, 'remember', rememberCheckbox.checked ? 'on' : '');
        if (config.redirect) {
          appendHidden(fallbackForm, 'redirect', config.redirect);
        }
        document.body.appendChild(fallbackForm);
        fallbackForm.submit();
      });
    }).catch(function () {
      // SHA-256 computation failed (extremely rare) — abort safely.
      setLoading(false);
      showAlert('密码加密失败，请刷新页面后重试');
    });
  });

  /** Helper: appends a hidden input to a form. */
  function appendHidden(form, name, value) {
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    form.appendChild(input);
  }

  /* =======================================================================
     Init
     ===================================================================== */

  // Show server-side error flag if present (non-AJS fallback login attempt)
  if (config.error) {
    showAlert('用户名或密码错误');
  }

  restoreRememberedUser();
  updateSubmitState();
})();
