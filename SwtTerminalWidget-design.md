# SwtTerminalWidget 处理逻辑文档

## 概述

`SwtTerminalWidget` 是 Hop 项目中用于替代 `JediTerminalWidget` 的纯 SWT 终端组件。它使用 `StyledText` 控件进行渲染，`pty4j` 库管理 PTY 进程，完全避免了 SWT-AWT 桥接，从而解决 macOS Metal 图层（MTLLayer）的 resize 同步问题。

## 架构设计

```
┌─────────────────────────────────────────────────────────────────────┐
│                        SwtTerminalWidget                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────┐     用户输入      ┌─────────────────────┐         │
│  │  KeyListener│ ────────────────>│   handleKey()       │         │
│  │  (特殊键)   │                  │   (Ctrl/箭头/Enter) │         │
│  └─────────────┘                  └─────────┬───────────┘         │
│                                              │                     │
│  ┌──────────────┐     用户输入      ┌────────▼───────────┐         │
│  │VerifyListener│ ────────────────>│   sendToPty()      │         │
│  │  (可打印字符) │                  │   (写入 PTY 输出流) │         │
│  └──────────────┘                  └─────────────────────┘         │
│                                              │                     │
│                                              ▼                     │
│                                        ┌──────────┐               │
│                                        │  PTY     │               │
│                                        │ Process  │               │
│                                        │  (zsh等) │               │
│                                        └────┬─────┘               │
│                                             │ PTY 输出             │
│                                             ▼                     │
│  ┌────────────────────────────────────────────────────────────┐    │
│  │                    Reader Thread                           │    │
│  │  InputStream → byte[] → String → asyncExec(processOutput) │    │
│  └────────────────────────────────────────────────────────────┘    │
│                                             │                     │
│                                             ▼                     │
│  ┌────────────────────────────────────────────────────────────┐    │
│  │                    ANSI Parser (render())                  │    │
│  │  状态机: NORMAL → ESC → CSI → OSC                         │    │
│  │  解析并执行: SGR颜色/光标移动/擦除/滚动/交替屏幕            │    │
│  └────────────────────────────────────────────────────────────┘    │
│                                             │                     │
│                                             ▼                     │
│  ┌────────────────────────────────────────────────────────────┐    │
│  │                    StyledText Rendering                    │    │
│  │  insertTextAtCursor() / replaceTextRange() / append()      │    │
│  │  applyStyle() → StyleRange (前景色/背景色)                  │    │
│  └────────────────────────────────────────────────────────────┘    │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

## 核心处理流程

### 1. 用户输入处理

用户输入通过两个监听器处理，分工明确：

| 监听器 | 处理内容 | 原因 |
|--------|----------|------|
| `KeyListener` | Ctrl 组合键、方向键、Home/End、PageUp/Down、Del、Backspace、Enter | 这些键不会触发 VerifyListener |
| `VerifyListener` | 所有可打印字符（包括 IME 中文输入） | IME 组合输入必须通过此监听器捕获 |

**关键设计：`programmaticEdit` 标志位**

```java
private boolean programmaticEdit = false;

// 用户输入时 programmaticEdit = false
// VerifyListener 拦截输入，转发到 PTY，阻止直接编辑

// PTY 输出渲染时 programmaticEdit = true  
// VerifyListener 允许编辑通过
```

这确保了：
- 用户输入被转发到 PTY 进程
- PTY 输出能正常渲染到 StyledText
- IME 输入（中文等）能正常工作

### 2. PTY 输出处理

#### 2.1 Reader Thread

```java
readerThread = new Thread(() -> {
    InputStream input = ptyProcess.getInputStream();
    byte[] buffer = new byte[8192];
    while (running.get()) {
        int bytesRead = input.read(buffer);
        if (bytesRead == -1) break;
        final String text = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
        styledText.getDisplay().asyncExec(() -> {
            if (styledText != null && !styledText.isDisposed()) {
                processOutput(text);
            }
        });
    }
}, "SwtTerminal-Reader");
```

关键点：
- 使用 UTF-8 解码
- 通过 `asyncExec` 将处理切换到 SWT UI 线程

#### 2.2 ANSI 解析器（状态机）

解析器使用有限状态机处理 ANSI/VT100 转义序列：

```
STATE_NORMAL (普通字符)
    ├─ '\r' → carriageReturn()
    ├─ '\n' → lineFeed()
    ├─ '\b' → backspace()
    ├─ 0x1b → STATE_ESC
    └─ 其他 → 累积到 plain buffer

STATE_ESC (ESC 字符之后)
    ├─ '[' → STATE_CSI (Control Sequence Introducer)
    ├─ ']' → STATE_OSC (Operating System Command)
    ├─ 'M' → cursorUp(1)
    ├─ 'c' → eraseDisplay(2) (重置终端)
    └─ 其他 → 忽略

STATE_CSI (CSI 参数累积)
    ├─ '@'~'~' → handleCsi(params, cmd)
    └─ 其他 → csiBuffer.append(c)

STATE_OSC (OSC 序列)
    ├─ 0x07 → 结束
    └─ ESC + '\' → 结束
```

#### 2.3 CSI 命令处理（handleCsi）

| 命令 | 处理方法 | 说明 |
|------|----------|------|
| `m` | `handleSgr()` | SGR 颜色/样式设置 |
| `A` | `cursorUp(n)` | 光标上移 |
| `B` | `cursorDown(n)` | 光标下移 |
| `C` | `cursorForward(n)` | 光标右移 |
| `D` | `cursorBack(n)` | 光标左移 |
| `H`/`f` | `setCursor(row, col)` | 绝对光标定位 |
| `G`/`` ` `` | 行内绝对列定位 | 移动到指定列 |
| `d` | `setCursor(row, 1)` | 移动到指定行首 |
| `J` | `eraseDisplay(mode)` | 擦除显示 |
| `K` | `eraseLine(mode)` | 擦除行 |
| `P` | `deleteChar(n)` | 删除字符 |
| `X` | `eraseChar(n)` | 擦除字符（替换为空格） |
| `S` | `scrollUp(n)` | 向上滚动 |
| `L` | `insertLine(n)` | 插入行 |
| `M` | `deleteLine(n)` | 删除行 |
| `?1049h/l` | `enter/exitAlternateScreen()` | 交替屏幕缓冲区 |

### 3. 渲染机制

#### 3.1 文本插入（insertTextAtCursor）

```java
private void insertTextAtCursor(String text) {
    if (cursorOffset < charCount) {
        // 覆盖模式：替换当前位置的字符（不跨换行）
        int replaceLen = Math.min(text.length(), charCount - cursorOffset);
        styledText.replaceTextRange(cursorOffset, replaceLen, replaceText);
        // 剩余部分插入
        styledText.replaceTextRange(cursorOffset + replaceLen, 0, remaining);
    } else {
        // 追加模式
        styledText.append(text);
    }
    applyStyle(cursorOffset, text.length());
    cursorOffset += text.length();
}
```

#### 3.2 擦除操作（关键修复）

**问题**：之前使用 `replaceTextRange(offset, len, "")` 删除文本，导致行宽收缩和背景色丢失。

**修复**：用空格替换而不是删除：

```java
private void eraseLine(int mode) {
    char[] spaces = new char[eraseLen];
    java.util.Arrays.fill(spaces, ' ');
    styledText.replaceTextRange(offset, eraseLen, new String(spaces));
    applyStyle(offset, eraseLen);  // 应用当前背景色
}
```

#### 3.3 交替屏幕缓冲区

TUI 应用（vim、opencode）使用交替屏幕：

```java
private void enterAlternateScreen() {
    savedMainText = styledText.getText();      // 保存主屏幕内容
    savedMainCursorOffset = cursorOffset;
    styledText.setText("");                    // 清空显示
    cursorOffset = 0;
    alternateScreenActive = true;
}

private void exitAlternateScreen() {
    styledText.setText(savedMainText);         // 恢复主屏幕内容
    cursorOffset = savedMainCursorOffset;
    alternateScreenActive = false;
}
```

### 4. 尺寸管理

#### 4.1 PTY 尺寸更新

```java
private void updatePtySize() {
    Point size = styledText.getSize();
    GC gc = new GC(styledText);
    int charWidth = gc.getFontMetrics().getAverageCharWidth();
    gc.dispose();
    int lineHeight = styledText.getLineHeight();
    int cols = Math.max(1, (size.x - 8) / charWidth);
    int rows = Math.max(1, (size.y - 8) / lineHeight);
    ptyProcess.setWinSize(new WinSize(cols, rows));
}
```

触发时机：
- `styledText.addListener(SWT.Resize, ...)` — 控件尺寸变化时
- `triggerResize()` — 面板最大化/还原时手动触发

## 当前已知问题

### 问题 1：输入字符重复（已修复）

**现象**：输入 `opencode` 显示为 `ooppeennccooddee`

**根因**：`KeyListener` 和 `VerifyListener` 都发送了相同字符到 PTY

**修复**：删除 `handleKey()` 末尾的回退块，仅保留特殊键处理

### 问题 2：macOS Metal 图层同步问题（已修复）

**现象**：终端内容仅显示左上角一小块，不随面板尺寸变化

**根因**：`JediTerminalWidget` 使用 SWT-AWT 桥接，macOS Metal 图层（MTLLayer）不随 `setSize()` 同步更新

**修复**：完全重写为纯 SWT 实现，使用 `StyledText` 原生控件

### 问题 3：中文输入（已修复）

**现象**：无法输入中文

**根因**：`styledText.setEditable(false)` 阻止了 SWT 的 IME 管道

**修复**：设置 `setEditable(true)` + `VerifyListener` 拦截模式

### 问题 4：TUI 横条显示（待验证）

**现象**：opencode 界面出现横条

**根因**：可能是背景色处理或 SGR 属性（粗体/下划线）未实现

**待验证**：修复擦除操作后是否解决

### 问题 5：命令输出一闪而过（已修复）

**现象**：`ls` 输出后立即消失

**根因**：`eraseLine(0)` 使用删除而非空格替换，shell 重绘提示符时误删了输出

**修复**：所有擦除操作使用空格替换

## 已修复的问题列表

| 问题 | 修复版本 | 修复方式 |
|------|----------|----------|
| 终端内容不随面板最大化 | 第1版 | 纯 SWT 重写，消除 SWT-AWT 桥接 |
| ls 输出一闪而过 | 第2版 | 修复 `lineFeed()` 逻辑，正确处理换行 |
| TUI 背景残留 | 第3版 | 实现交替屏幕缓冲区 |
| 横条显示 | 第4版 | 擦除操作改为空格替换，保留背景色 |
| 中文输入 | 第4版 | `setEditable(true)` + `VerifyListener` |
| 提示符不显示 | 第5版 | 添加 `programmaticEdit` 标志位 |
| 字符重复输入 | 第6版 | 删除 `handleKey()` 末尾的回退块 |

## 待改进项

1. **SGR 属性扩展**：支持粗体（bold）、下划线、斜体等文本样式
2. **256 色和真彩色支持**：当前仅实现 16 色 ANSI 颜色
3. **鼠标支持**：当前不支持鼠标事件（TUI 应用可能需要）
4. **剪切板支持**：实现 copy/paste 功能
5. **滚动历史优化**：当前使用 StyledText 内置滚动，可能需要更高效的虚拟滚动实现

## 文件位置

- [SwtTerminalWidget.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/ui/src/main/java/org/apache/hop/ui/hopgui/terminal/SwtTerminalWidget.java)
- [HopGuiTerminalPanel.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/ui/src/main/java/org/apache/hop/ui/hopgui/terminal/HopGuiTerminalPanel.java) — 工厂方法切换到 SwtTerminalWidget
