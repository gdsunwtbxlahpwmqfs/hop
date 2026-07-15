# Configuration Perspective

图标：![width="24px"](../assets/images/icons/gear.svg)

键盘快捷键：`CTRL-Shift-C`

## 描述

Configuration perspective 为所有 Qi Hop 配置选项提供了一个集中位置。

## 用法

Configuration perspective 将配置选项分组到以下标签页中：

- **General** 选项控制 Qi Hop 和 Hop Gui 的行为。
- **Look &amp; Feel** 控制 Hop Gui 在你桌面或浏览器中的外观。
- **Plugin** 选项提供配置选项以控制可用 Qi Hop plugin 的行为。
- **System variables** 允许你设置和配置 Qi Hop 全局变量。

## 详细配置标签页概览

### General

General 配置标签页允许你配置核心 Qi Hop 选项。

| 选项 | 描述 | 默认值 |
|---|---|---|
| Hop 配置文件名 |  |  |
| `<INSTALLATION_PATH>/config/hop-config.json` |  |  |
| 预览数据批量大小 |  |  |
| 1000 |  |  |
| 使用数据库缓存 | 为关系型数据库启用缓存 | yes |
| 启动时打开上次文件 | Hop GUI 启动时重新打开上次使用的文件并选择上次使用的文件 | yes |
| 自动保存已更改文件 | 自动保存已更改的 workflow 和 pipeline 文件 | yes |
| 自动拆分 hop |  |  |
| yes |  |  |
| 显示 Copy 或 Distribute 对话框 | 显示弹出对话框，询问用户确认数据应复制还是分发（轮询）到 pipeline 中的后续 transform | yes |
| 退出时询问用户 | 显示弹出对话框，让用户在退出 Hop GUI 之前确认 | yes |
| 清除自定义参数（transform/plugin） | 删除 transform 和 plugin 对话框中的所有自定义标志和参数 | 点击时 |
| 自动折叠面板树 | 折叠或展开上下文对话框中的类别 | no（展开） |
| 显示工具提示 | 在 Hop GUI 中可用之处显示工具提示 | yes |
| 显示帮助工具提示 | 在 Hop GUI 中可用之处显示帮助工具提示 | yes |
| 在画布上使用双击？ |  |  |
| 在 transform 和 action 对话框等上使用双击而非单击。 |  |  |
| no |  |  |
| 在画布上的名称周围绘制边框？ |  |  |
| 如果启用此选项，将在画布上的 transform 和 action 名称周围绘制边框。 |  |  |
| no |  |  |
| 在文件对话框中使用全局书签 | 文件对话框中的书签默认是全局的（跨项目）。启用此项可使书签特定于项目 | yes |

### Look & Feel

`Look & Feel` 配置选项允许你配置 Hop Gui 行为的各个方面。

| 选项 | 描述 | 默认值 |
|---|---|---|
| 默认字体 | Hop GUI 中使用的默认字体 | Noto Sans - 10 |
| 固定宽度字体 | Hop GUI 中使用的默认固定宽度字体 | Monospace - 10 |
| 工作区字体 | 工作区中使用的字体 | Noto Sans - 10 |
| 注释字体 | workflow 和 pipeline 注释使用的字体 | Noto Sans - 10 |
| 工作区图标大小 | workflow 和 pipeline 中 action 和 transform 图标的默认大小 | 32 |
| 工作区线宽 | workflow 和 action 中 hop 使用的线宽 | 1 |
| 对话框中间百分比 | 用于确定对话框位置的百分比 | 35 |
| UI 缩放级别 | Hop GUI 中 workflow 和 pipeline 使用的默认缩放级别 | 100% |
| 画布网格大小 | Hop GUI 编辑器中 workflow 和 pipeline 使用的默认网格大小 | 16 |
| 显示画布网格 | 在 Hop GUI 中为 workflow 和 pipeline 显示网格（点状） | no |
| 隐藏菜单栏 | 不显示菜单栏。如果启用（默认），菜单选项可从 Hop GUI 左上角的 Qi Hop 图标访问。 | yes |
| 在表格上方显示工具栏 | 在表格视图中显示带有剪切/复制/粘贴、上下移动行等选项的工具栏（例如在预览对话框、transform 配置选项中） | yes |
| 深色模式 | 使用深色模式。在 Hop Web 和 Windows 上你可以切换此选项；在 macOS 和其他桌面平台上它跟随操作系统主题。在 Hop Web 中更改它会重新加载页面以应用主题。 | N/A |
| 首选语言 |  |  |

### Plugins

Plugins 标签页包含由 Qi Hop 中各种 plugin 提供的配置选项。

默认情况下提供以下 plugin：

- [Azure Blob Storage](../14-虚拟文件系统/azure-blob-storage-vfs.md#_configuration.md) VFS 配置选项。
- [Dropbox](../14-虚拟文件系统/dropbox-vfs.md#_configuration.md) VFS 配置选项。
- **Explorer perspective**：文件浏览器 perspective 的配置选项：
  ** 非延迟加载的初始深度：控制打开文件夹时立即加载多少层文件夹级别。
  ** 最大文件加载大小：设置打开文件时将加载的最大文件大小（以 MB 为单位）。
  ** 默认显示文件浏览器面板：启用后，打开 explorer perspective 时默认显示文件浏览器面板（项目树）。禁用后，面板初始隐藏。
- [Google Cloud](../index.md) 配置选项（服务账号 JSON 密钥文件）。
- [Google Drive](../14-虚拟文件系统/google-drive-vfs.md#_configuration.md) VFS 配置选项。
- [Project](../index.md) 配置选项
- Welcome Dialog：指定 Hop GUI 启动时是显示还是隐藏欢迎对话框。

### System Variables

System Variables 标签页包含 Hop GUI 中可用的系统变量。

查看 [Variables 文档](../12-变量与项目管理/variables.md) 以获取有关可用变量及其默认值的更多信息。
