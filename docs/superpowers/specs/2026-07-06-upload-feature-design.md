# Hop Web 文件浏览器上传功能设计（tus 协议）

- **日期**: 2026-07-06
- **目标环境**: 仅 Hop Web (RAP)
- **目标 UI**: ExplorerPerspective 文件树视图
- **上传目标**: 服务器本地目录（rootFolder 子路径）
- **协议**: tus（[tus.io](https://tus.io/protocols/resumable-upload) 可恢复上传协议）

## 1. 需求

在 Hop Web 的 Explorer 文件树视图工具栏新增「上传」按钮，点击后在新浏览器标签页打开自包含上传页面，支持：

- **直接上传**（小文件 = 单分片）
- **分片上传**（大文件按 `chunkSize` 自动分片）
- **进度条**（每文件实时进度）
- **可暂停 / 可继续**（页内 + 跨页面刷新断点续传）
- **可取消**（清理服务端暂存）
- **断网自动重试**（tus-js-client 内置）
- **完成后自动刷新** Explorer 文件树
- **同名冲突**：默认自动改名（追加 `_1`），UI 可选「覆盖」

## 2. 总体架构与数据流

```
[Explorer 文件树]
   |  点击"上传"按钮
   |  计算 dest 目录（getSelectedFile()/tif.path，回退 rootFolder）
   |  EnvironmentUtils.openUrl("/upload/?dest=<encoded>")
   v
[新浏览器标签页: GET /upload/]
   |  TusUploadServlet 返回自包含 HTML/CSS/JS 页面
   |  回显目标目录
   v
[tus-js-client]
   |  POST /upload/          (创建会话)
   |  HEAD /upload/{id}      (查偏移，断点续传)
   |  PATCH /upload/{id}     (分片，可暂停/继续)
   |  DELETE /upload/{id}    (取消，清理暂存)
   v
[TusUploadServlet]
   |  分片暂存 -> 重组 -> 写入 dest 目录
   |  完成时在 HttpSession 写标记 hop.upload.lastChange=<timestamp>
   v
[ExplorerPerspective]
   |  ServerPush 轮询会话标记
   |  检测到变化 -> refresh()
```

**为什么这样切分：** 真正的分片/暂停/续传必须在浏览器 JS 里发生（SWT/RAP 一旦 POST 就无法暂停）。所以上传 UI 是一个自包含的 servlet 页面（仿 `LoginServlet` 提供 `/login` 页面的模式），用 `tus-js-client` 驱动。Explorer 只负责「打开它 + 传目标目录 + 完成后刷新」。

## 3. 服务端：`TusUploadServlet` + 最小 tus 协议

**位置**: `rap/src/main/java/org/apache/hop/ui/hopgui/upload/TusUploadServlet.java`

实现最小 tus 协议核心端点：

| 方法 | 路径 | 作用 |
|---|---|---|
| `OPTIONS` | `/upload/` | 能力声明：`Tus-Version: 1.0.0`、`Tus-Resumable: 1.0.0`、`Tus-Extension: creation,termination`、`Tus-Max-Size: <configured>` |
| `POST` | `/upload/` | 创建会话：读 `Upload-Length`、`Upload-Metadata`（base64 filename/destPath），生成 UUID，建暂存文件 + 元数据，返回 `201 Created` + `Location: /upload/{id}` |
| `HEAD` | `/upload/{id}` | 返回 `Upload-Offset`、`Upload-Length`（断点续传查偏移） |
| `PATCH` | `/upload/{id}` | 校验 `Upload-Offset` 一致（否则 `409 Conflict`），`Content-Type: application/offset+octet-stream`，追加分片到暂存文件，更新 offset。`offset == length` 时**完成**：原子移动/改名到 dest 目录，会话标记完成 |
| `DELETE` | `/upload/{id}` | 取消：删暂存文件 + 元数据，返回 `204 No Content` |
| `GET` | `/upload/` | 返回上传页面 HTML（含目标目录回显） |
| `GET` | `/upload-resources/*` | 静态资源（upload.js/upload.css/tus.min.js/图标），**不鉴权**（仿 `/login-resources/*`） |

所有 tus 响应必须含 `Tus-Resumable: 1.0.0` 头。

### 配套类

**`UploadManager`** (`rap/.../upload/UploadManager.java`)
- 内存元数据 Map：`id -> UploadSession{totalLength, offset, filename, destPath, tempFile, createdAt, status}`
- 线程安全（`ConcurrentHashMap` + 文件写加锁）
- 过期清理：后台守护线程（`ScheduledExecutorService`），默认 24h 未完成则删暂存文件
- 完成/取消/失败时清理元数据

**`UploadConfig`** (`rap/.../upload/UploadConfig.java`) — 配置常量（系统属性/环境变量覆盖）：
- `HOP_WEB_UPLOAD_MAX_SIZE`：单文件最大（默认 `0` = 不限）
- `HOP_WEB_UPLOAD_CHUNK_SIZE`：默认 `5MB`（仅作为客户端建议）
- `HOP_WEB_UPLOAD_TEMP_DIR`：暂存目录（默认 `<HOP_CONFIG_FOLDER>/uploads-tmp`）
- `HOP_WEB_UPLOAD_EXPIRE_HOURS`：默认 `24`

## 4. 客户端：上传页面（HTML/CSS/JS + tus-js-client）

**资源位置**: `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/`
- `upload.html` — 页面骨架（由 servlet 的 `serveResource()`/`readResource()` 读取，仿 `LoginServlet` 模式）
- `upload.css` — 样式（沿用 Hop Web 浅/深色主题观感）
- `upload.js` — 我们的 UI 逻辑（文件队列、每文件进度条、暂停/继续/取消按钮、目录回显、错误提示、重名策略选择）
- `tus.min.js` — **vendor 的 [tus-js-client](https://github.com/tus/tus-js-client) 单文件**（MIT 许可，与 Apache 2.0 兼容）
- `images/upload.svg` — 图标

### 需求映射（全部由 tus-js-client 原生支持）

| 需求 | 实现 |
|---|---|
| 直接上传 | 小文件 = 单分片 tus（`chunkSize` 设大），统一代码路径 |
| 分片上传 | `chunkSize` 配置；大文件自动分多片 |
| 进度条 | tus `onProgress` 回调 -> 更新每文件进度条 |
| 可暂停 | `upload.pause()` / `upload.resume()`（断网自动重试也内置） |
| 可取消 | `upload.abort()` + 调 `DELETE /upload/{id}` 清理服务端 |
| 跨页面续传 | tus-js-client `fingerprint` + `localStorage` 持久化 uploadUrl；重开页面自动 `HEAD` 查偏移续传 |

页面结构：
- 顶部：目标目录回显（只读）+ 重名策略单选（默认「自动改名」，可选「覆盖」）
- 中部：拖拽区 + 「选择文件」按钮；文件队列表（文件名/大小/进度/速度/状态/操作）
- 每行操作：暂停/继续、取消、重试（失败时）
- 底部：全部暂停、全部取消、关闭窗口

## 5. ExplorerPerspective 集成

**改动文件**: [ExplorerPerspective.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/ui/src/main/java/org/apache/hop/ui/hopgui/perspective/explorer/ExplorerPerspective.java)

- 新增常量 `TOOLBAR_ITEM_UPLOAD = "ExplorerPerspective-Toolbar-10600-Upload"`（沿用编号方案）。
- 新增 `@GuiToolbarElement` 方法 `uploadFile()`：
  1. **仅 web 可见**：注册时按 `EnvironmentUtils.getInstance().isWeb()` 控制可见性（仿 [HopGui.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/ui/src/main/java/org/apache/hop/ui/hopgui/HopGui.java) 登出按钮的 web-only 模式）。桌面端隐藏此按钮。
  2. **取目标目录**：`getSelectedFile()` -> `tif.path`；若为文件或无选中，回退 `getRootFolder()`（仿 `createFolder()` 第 2588-2605 行）。
  3. **路径安全校验**：dest 必须 resolve 到 `rootFolder` 子路径（拒绝 `..` 越界）。
  4. **打开标签页**：`EnvironmentUtils.getInstance().openUrl(contextPath + "/upload/?dest=" + URLEncoder.encode(dest, UTF_8))`。
  5. **启动刷新监听**：用 `ServerPushSessionFacade` 起轮询，监听 `HttpSession` 的 `hop.upload.lastChange` 标记变化 -> 调 `refresh()`。

**i18n**: 在 explorer 的 [messages_en_US.properties](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/ui/src/main/resources/org/apache/hop/ui/hopgui/perspective/explorer/messages/messages_en_US.properties) 与 `messages_zh_CN.properties` 新增：
- `ExplorerPerspective.ToolbarElement.Upload.Tooltip`
- `ExplorerPerspective.Menu.Upload`
- 相关错误消息键

## 6. 认证与安全

- **保护 `/upload/*`**: 扩展 [AuthenticationFilter.isProtectedPath()](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/auth/AuthenticationFilter.java#L103) 增加 `/upload` 前缀，并在 [HopWebServletContextListener](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/HopWebServletContextListener.java#L136) 的 filter mapping 增加 `/upload`。会话校验逻辑同 `/ui`：`session.getAttribute(AuthConstants.SESSION_ATTR_USER) != null`，否则 tus fetch 收到 `401`。
- `/upload-resources/*` 保持**不鉴权**（静态 JS/CSS，仿 `/login-resources/*`），让上传页能加载资源。
- **路径穿越**: servlet 解析 dest 时强制 `Paths.resolve(rootFolder, dest).normalize()`，校验结果仍以 `rootFolder` 为前缀，拒绝越界。
- **文件名净化**: 去除路径分隔符、控制字符；保留扩展名。
- **大小限制**: `POST` 时校验 `Upload-Length` 不超过 `HOP_WEB_UPLOAD_MAX_SIZE`（`0` 表示不限）。
- **同源 + 会话鉴权**即可；tus 自定义头 + 同源 Cookie，无需额外 CSRF token。

## 7. Servlet 注册

**改动文件**: [HopWebServletContextListener.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/HopWebServletContextListener.java)

在 `contextInitialized` 中新增 `registerUploadComponents(event)`（仿 `registerAuthComponents`）：

```java
ServletRegistration.Dynamic uploadServlet =
    context.addServlet("hopUploadServlet", new TusUploadServlet());
uploadServlet.addMapping("/upload", "/upload/*", "/upload-resources/*");
uploadServlet.setAsyncSupported(true);   // 大分片可能耗时，开启异步更稳妥

// 扩展现有 authFilter 的 mapping（或新增一个 filter registration）
authFilter.addMappingForUrlPatterns(
    EnumSet.of(DispatcherType.REQUEST), false, "/upload", "/upload/*");
```

**改动文件**: [AuthConstants.java](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/auth/AuthConstants.java) — 新增：
- `PATH_UPLOAD = "/upload"`
- `PATH_UPLOAD_RESOURCES = "/upload-resources"`

## 8. 新增/改动文件清单

**新增（`rap` 模块）**:
- `rap/src/main/java/org/apache/hop/ui/hopgui/upload/TusUploadServlet.java`
- `rap/src/main/java/org/apache/hop/ui/hopgui/upload/UploadManager.java`
- `rap/src/main/java/org/apache/hop/ui/hopgui/upload/UploadSession.java`（数据类）
- `rap/src/main/java/org/apache/hop/ui/hopgui/upload/UploadConfig.java`
- `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/upload.html`
- `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/upload.js`
- `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/upload.css`
- `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/tus.min.js`（vendor）
- `rap/src/main/resources/org/apache/hop/ui/hopgui/upload/images/upload.svg`
- 测试：`rap/src/test/java/org/apache/hop/ui/hopgui/upload/TusUploadServletTest.java`
- 测试：`rap/src/test/java/org/apache/hop/ui/hopgui/upload/UploadManagerTest.java`

**改动**:
- `HopWebServletContextListener.java` — 注册 `TusUploadServlet` + filter mapping
- `AuthenticationFilter.java` — `isProtectedPath` 加 `/upload`
- `AuthConstants.java` — 加路径常量
- `ExplorerPerspective.java` — 加 `TOOLBAR_ITEM_UPLOAD` + `uploadFile()` + ServerPush 刷新
- explorer `messages_en_US.properties` / `messages_zh_CN.properties` — 上传相关 i18n
- `rap/pom.xml` — 在 `apache-rat-plugin` 排除 `tus.min.js`（第三方文件保留 MIT 头注释）

## 9. 边界与错误处理

- **断网**: tus-js-client 自动重试 PATCH（指数退避）。
- **暂停后关页**: 跨页面续传——tus-js-client `fingerprint` + `localStorage` 持久化 uploadUrl；重开页面 `HEAD` 查偏移续传。服务端 `UploadManager` 保留暂存文件直至过期或显式取消。
- **磁盘满/IO 错**: PATCH 返回 `5xx`，UI 标红 + 显示重试按钮。
- **并发同名**: 服务端原子 rename；冲突时按策略（自动改名 `_1`/`_2`... 或覆盖）。
- **暂存清理**: `UploadManager` 后台线程定期清过期暂存文件（默认 24h）。
- **dest 越界**: servlet 拒绝并返回 `400`。
- **超限**: `POST` 时拒绝并返回 `413 Request Entity Too Large`。
- **会话过期**: 上传中 session 失效 -> tus 收到 `401` -> UI 提示「请重新登录」并暂停。
- **服务端重启**: 暂存文件丢失，`HEAD`/`PATCH` 返回 `404` -> tus-js-client 自动重新 `POST` 创建新会话从头传（tus 标准行为）。

## 10. 测试策略

- **服务端单测**（JUnit5 + Mockito）:
  - `TusUploadServletTest`: 完整 tus 流程（POST -> HEAD -> PATCH -> 完成）、offset 不匹配返回 `409`、DELETE 清理、路径穿越拒绝、超限拒绝、dest 越界拒绝、OPTIONS 能力声明、并发同名处理。
  - `UploadManagerTest`: 元数据生命周期、过期清理、并发写入。
- **前端**: 手动验证（RAP UI 测试 SWTBot 不覆盖新标签页 JS）；关键 JS 纯函数可加少量单测。
- **构建校验**: `./mvnw spotless:apply` + `apache-rat:check`（确认 rat 排除 `tus.min.js` 生效）+ `checkstyle:check`。

## 11. 配置项汇总

| 配置 | 默认值 | 系统属性 / 环境变量 |
|---|---|---|
| 单文件最大 | `0`（不限） | `HOP_WEB_UPLOAD_MAX_SIZE` |
| 分片大小（客户端建议） | `5MB` | `HOP_WEB_UPLOAD_CHUNK_SIZE` |
| 暂存目录 | `<HOP_CONFIG_FOLDER>/uploads-tmp` | `HOP_WEB_UPLOAD_TEMP_DIR` |
| 过期时间 | `24` 小时 | `HOP_WEB_UPLOAD_EXPIRE_HOURS` |

## 12. 范围之外（YAGNI）

以下不在本期范围，留作后续增强：
- 上传到任意 VFS（S3/SFTP/HDFS）目标
- 桌面端 (RCP) 上传按钮（桌面端文件本来就在本地）
- 模态「浏览」对话框 (`HopVfsFileDialog`) 内的上传
- 上传历史记录持久化到数据库
- 多用户并发上传配额管理
