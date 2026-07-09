/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.ui.core.gui;

import java.util.ArrayList;
import java.util.List;
import org.apache.hop.core.Const;
import org.apache.hop.core.gui.plugin.key.KeyboardShortcut;
import org.apache.hop.i18n.BaseMessages;
import org.eclipse.swt.SWT;

/**
 * Shared utility for displaying keyboard shortcuts with OS-appropriate symbols (e.g. ⌘⇧⌥ on macOS).
 * Used by menus, toolbars, and the keyboard shortcuts configuration tab so display is consistent.
 */
public final class ShortcutDisplayUtil {

  private static final Class<?> PKG = ShortcutDisplayUtil.class;

  private ShortcutDisplayUtil() {}

  /**
   * Returns the full shortcut string for menus and tooltips (e.g. "⌘⇧Z" on macOS, "Ctrl+Shift+Z" on
   * Windows/Linux).
   */
  public static String getShortcutDisplayString(KeyboardShortcut shortcut) {
    if (shortcut == null || shortcut.getKeyCode() == 0) {
      return "";
    }
    List<String> parts = getModifierDisplayStrings(shortcut);
    String keyText = getKeyDisplayText(shortcut.getKeyCode());
    if (!keyText.isEmpty()) {
      parts.add(keyText);
    }
    boolean isMacOS = Const.isOSX();
    return isMacOS ? String.join("", parts) : String.join("+", parts);
  }

  /**
   * Returns modifier symbols/labels in display order for menus and key badges. Uses the same
   * canonical order everywhere so menu bar and config panel match: Control → Option → Shift →
   * Command (⌃ ⌥ ⇧ ⌘ on macOS; Ctrl, Alt, Shift, Cmd on Windows/Linux). Does not include the main
   * key.
   */
  public static List<String> getModifierDisplayStrings(KeyboardShortcut shortcut) {
    List<String> out = new ArrayList<>();
    boolean isMacOS = Const.isOSX();
    // Same order on all platforms: Control, Option/Alt, Shift, Command
    if (shortcut.isControl())
      out.add(isMacOS ? "⌃" : BaseMessages.getString(PKG, "Shortcut.Key.Ctrl"));
    if (shortcut.isAlt()) out.add(isMacOS ? "⌥" : BaseMessages.getString(PKG, "Shortcut.Key.Alt"));
    if (shortcut.isShift())
      out.add(isMacOS ? "⇧" : BaseMessages.getString(PKG, "Shortcut.Key.Shift"));
    if (shortcut.isCommand())
      out.add(isMacOS ? "⌘" : BaseMessages.getString(PKG, "Shortcut.Key.Cmd"));
    return out;
  }

  public enum KeyCategory {
    MODIFIER,
    SPECIAL,
    FUNCTION,
    SINGLE_CHAR
  }

  /** Returns the category of a key for badge width calculation (locale-independent). */
  public static KeyCategory getKeyCategory(int keyCode) {
    if (keyCode == 32) return KeyCategory.SPECIAL;
    if (keyCode >= 65 && keyCode <= 90) return KeyCategory.SINGLE_CHAR;
    if (keyCode >= 97 && keyCode <= 122) return KeyCategory.SINGLE_CHAR;
    if (keyCode == 96) return KeyCategory.SINGLE_CHAR;
    if (keyCode == 127) return KeyCategory.SPECIAL;
    if ((keyCode >= 48 && keyCode <= 57) || "+-/*=".indexOf(keyCode) >= 0) {
      return KeyCategory.SINGLE_CHAR;
    }

    if ((keyCode & (1 << 24)) != 0) {
      int code = keyCode & (0xFFFF);
      if (code >= 10 && code <= 29) {
        return KeyCategory.FUNCTION;
      }
      return KeyCategory.SPECIAL;
    }

    if (keyCode == SWT.ESC) return KeyCategory.SPECIAL;
    if (keyCode == SWT.BS) return KeyCategory.SPECIAL;

    return KeyCategory.SINGLE_CHAR;
  }

  /**
   * Converts an SWT key code to the display string used in the configuration panel (e.g. "⌫" for
   * Delete on macOS, "↑" for arrow up).
   */
  public static String getKeyDisplayText(int keyCode) {
    boolean isMacOS = Const.isOSX();

    if (keyCode == SWT.KEYPAD_ADD) return "+";
    if (keyCode == SWT.KEYPAD_SUBTRACT) return "-";
    if (keyCode == SWT.KEYPAD_MULTIPLY) return "*";
    if (keyCode == SWT.KEYPAD_DIVIDE) return "/";
    if (keyCode == SWT.KEYPAD_EQUAL) return "=";
    if (keyCode == SWT.KEYPAD_DECIMAL) return ".";
    if (keyCode == SWT.KEYPAD_CR) return "↵";
    if (keyCode >= SWT.KEYPAD_0 && keyCode <= SWT.KEYPAD_9) {
      return String.valueOf(keyCode - SWT.KEYPAD_0);
    }

    if (keyCode == 32) return BaseMessages.getString(PKG, "Shortcut.Key.Space");
    if (keyCode >= 65 && keyCode <= 90) return String.valueOf((char) keyCode);
    if (keyCode >= 97 && keyCode <= 122)
      return String.valueOf(Character.toUpperCase((char) keyCode));
    if (keyCode == 96) return "`";
    if (keyCode == 127) return isMacOS ? "⌫" : BaseMessages.getString(PKG, "Shortcut.Key.Delete");
    if ((keyCode >= 48 && keyCode <= 57) || "+-/*=".indexOf(keyCode) >= 0) {
      return String.valueOf((char) keyCode);
    }

    if ((keyCode & (1 << 24)) != 0) {
      switch (keyCode & (0xFFFF)) {
        case 1:
          return BaseMessages.getString(PKG, "Shortcut.Key.ArrowUp");
        case 2:
          return BaseMessages.getString(PKG, "Shortcut.Key.ArrowDown");
        case 3:
          return BaseMessages.getString(PKG, "Shortcut.Key.ArrowLeft");
        case 4:
          return BaseMessages.getString(PKG, "Shortcut.Key.ArrowRight");
        case 5:
          return BaseMessages.getString(PKG, "Shortcut.Key.PageUp");
        case 6:
          return BaseMessages.getString(PKG, "Shortcut.Key.PageDown");
        case 7:
          return BaseMessages.getString(PKG, "Shortcut.Key.Home");
        case 8:
          return BaseMessages.getString(PKG, "Shortcut.Key.End");
        case 9:
          return BaseMessages.getString(PKG, "Shortcut.Key.Insert");
        case 10:
          return BaseMessages.getString(PKG, "Shortcut.Key.F1");
        case 11:
          return BaseMessages.getString(PKG, "Shortcut.Key.F2");
        case 12:
          return BaseMessages.getString(PKG, "Shortcut.Key.F3");
        case 13:
          return BaseMessages.getString(PKG, "Shortcut.Key.F4");
        case 14:
          return BaseMessages.getString(PKG, "Shortcut.Key.F5");
        case 15:
          return BaseMessages.getString(PKG, "Shortcut.Key.F6");
        case 16:
          return BaseMessages.getString(PKG, "Shortcut.Key.F7");
        case 17:
          return BaseMessages.getString(PKG, "Shortcut.Key.F8");
        case 18:
          return BaseMessages.getString(PKG, "Shortcut.Key.F9");
        case 19:
          return BaseMessages.getString(PKG, "Shortcut.Key.F10");
        case 20:
          return BaseMessages.getString(PKG, "Shortcut.Key.F11");
        case 21:
          return BaseMessages.getString(PKG, "Shortcut.Key.F12");
        case 22:
          return "F13";
        case 23:
          return "F14";
        case 24:
          return "F15";
        case 25:
          return "F16";
        case 26:
          return "F17";
        case 27:
          return "F18";
        case 28:
          return "F19";
        case 29:
          return "F20";
        default:
          break;
      }
    }

    if (keyCode == SWT.ESC)
      return isMacOS ? "⎋" : BaseMessages.getString(PKG, "Shortcut.Key.Escape");
    if (keyCode == SWT.BS)
      return isMacOS ? "⌫" : BaseMessages.getString(PKG, "Shortcut.Key.Backspace");

    return "";
  }
}
