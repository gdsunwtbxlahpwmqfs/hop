# Qi Hop 安全待办：未鉴权接口清单

## 背景

Hop Web (RAP) 模式下，`/hop/*` 路径下的所有 Hop Server Servlet **均无身份认证**。

### 鉴权链路确认

- **容器层**：[assemblies/web/.../WEB-INF/web.xml](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/assemblies/web/src/main/resources/WEB-INF/web.xml) 无任何 `security-constraint` / `login-config` / `auth-constraint` 配置
- **过滤器层**：[AuthenticationFilter.isProtectedPath()](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/auth/AuthenticationFilter.java#L116-124) **只保护** `/ui` 和 `/ui-dark`，`/hop/*` 直接放行
  ```java
  return path.equals("/ui")
      || path.equals("/ui-dark")
      || path.startsWith("/ui/")
      || path.startsWith("/ui-dark/");
  ```
- **分发器层**：[HopServerServlet.doGet()](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/HopServerServlet.java#L78-102) 仅做路径查找，不校验身份
- **业务层**：所有 `@HopServerServlet` 注解类的 `doGet/doPut/doPost` 开头**只有 `isJettyMode()` 检查**（用于区分独立 Jetty 还是 WAR 部署），无任何 `getAttribute("user")` / Basic Auth / Token 校验

### 影响

- **内网部署**：可接受（默认信任网络）
- **公网/DMZ 部署**：高危。未授权用户可远程执行任意 pipeline / workflow、读取运行状态、上传恶意导出包

---

## 未鉴权接口清单（共 29 个）

> 所有路径前缀为 `/hop/`，来自 `@HopServerServlet(id=...)` 注解 + `CONTEXT_PATH` 常量
> HTTP 方法：GET / POST / PUT（具体见各 servlet 实现）

### 🔴 高危（可远程执行代码 / 篡改状态）

| # | 路径 | Servlet | 功能 | 风险 |
|---|------|---------|------|------|
| 1 | `/hop/execPipeline` | [ExecPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/ExecPipelineServlet.java) | 按文件路径执行 pipeline | **远程执行任意 pipeline** |
| 2 | `/hop/execWorkflow` | [ExecWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/ExecWorkflowServlet.java) | 按文件路径执行 workflow | **远程执行任意 workflow** |
| 3 | `/hop/asyncRun` | [AsyncRunServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/plugins/misc/async/src/main/java/org/apache/hop/www/async/AsyncRunServlet.java) | 异步执行 workflow | **远程执行任意 workflow** |
| 4 | `/hop/startPipeline` | [StartPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/StartPipelineServlet.java) | 准备并启动 pipeline | **启动已注册 pipeline** |
| 5 | `/hop/startExec` | [StartExecutionPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/StartExecutionPipelineServlet.java) | 启动已准备好的 pipeline | **启动已注册 pipeline** |
| 6 | `/hop/startWorkflow` | [StartWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/StartWorkflowServlet.java) | 启动 workflow | **启动已注册 workflow** |
| 7 | `/hop/stopPipeline` | [StopPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/StopPipelineServlet.java) | 停止 pipeline | 中断他人作业 |
| 8 | `/hop/stopWorkflow` | [StopWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/StopWorkflowServlet.java) | 停止 workflow | 中断他人作业 |
| 9 | `/hop/pausePipeline` | [PausePipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/PausePipelineServlet.java) | 暂停/继续 pipeline | 中断他人作业 |
| 10 | `/hop/addPipeline` | [AddPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/AddPipelineServlet.java) | 添加 pipeline（deprecated） | 注入恶意作业定义 |
| 11 | `/hop/addWorkflow` | [AddWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/AddWorkflowServlet.java) | 添加 workflow（deprecated） | 注入恶意作业定义 |
| 12 | `/hop/addExport` | [AddExportServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/AddExportServlet.java) | 上传资源导出包（deprecated） | **上传任意 zip 并执行** |
| 13 | `/hop/registerPipeline` | [RegisterPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RegisterPipelineServlet.java) | 注册 pipeline | 注入恶意作业定义 |
| 14 | `/hop/registerWorkflow` | [RegisterWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RegisterWorkflowServlet.java) | 注册 workflow | 注入恶意作业定义 |
| 15 | `/hop/registerPackage` | [RegisterPackageServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RegisterPackageServlet.java) | 上传资源导出包 | **上传任意 zip 并执行** |
| 16 | `/hop/prepareExec` | [PrepareExecutionPipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/PrepareExecutionPipelineServlet.java) | 准备 pipeline 执行 | 准备阶段注入 |
| 17 | `/hop/removePipeline` | [RemovePipelineServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RemovePipelineServlet.java) | 移除 pipeline | 破坏他人作业 |
| 18 | `/hop/removeWorkflow` | [RemoveWorkflowServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RemoveWorkflowServlet.java) | 移除 workflow | 破坏他人作业 |
| 19 | `/hop/deleteExecInfo` | [DeleteExecutionInfoServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/DeleteExecutionInfoServlet.java) | 删除执行信息（deprecated） | 删除审计记录 |
| 20 | `/hop/sniffTransform` | [SniffTransformServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/SniffTransformServlet.java) | 嗅探 transform 数据 | 读取业务数据样本 |

### 🟡 中危（信息泄露）

| # | 路径 | Servlet | 功能 | 风险 |
|---|------|---------|------|------|
| 21 | `/hop/status` | [GetStatusServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetStatusServlet.java) | 服务器整体状态 | 暴露 CPU/内存/作业列表 |
| 22 | `/hop/pipelineStatus` | [GetPipelineStatusServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetPipelineStatusServlet.java) | 单个 pipeline 状态 | 暴露作业内部状态 |
| 23 | `/hop/workflowStatus` | [GetWorkflowStatusServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetWorkflowStatusServlet.java) | 单个 workflow 状态 | 暴露作业内部状态 |
| 24 | `/hop/asyncStatus` | [AsyncStatusServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/plugins/misc/async/src/main/java/org/apache/hop/www/async/AsyncStatusServlet.java) | 异步任务状态 | 暴露异步任务信息 |
| 25 | `/hop/getExecInfo` | [GetExecutionInfoServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetExecutionInfoServlet.java) | 获取执行信息 | 暴露执行历史 |
| 26 | `/hop/registerExecInfo` | [RegisterExecutionInfoServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/RegisterExecutionInfoServlet.java) | 注册执行信息 | 可伪造执行记录 |
| 27 | `/hop/pipelineImage` | [GetPipelineImageServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetPipelineImageServlet.java) | 生成 pipeline PNG | 暴露作业拓扑图 |
| 28 | `/hop/workflowImage` | [GetWorkflowImageServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/GetWorkflowImageServlet.java) | 生成 workflow PNG | 暴露作业拓扑图 |
| 29 | `/hop/webService` | [WebServiceServlet](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/WebServiceServlet.java) | 输出 transform 字段内容 | **读取业务数据** |

---

## TODO 行动项

### P0 — 立即缓解（部署侧，无需改代码）

- [ ] **生产环境强制前置反代鉴权**：Nginx / Apache HTTPD 对 `/hop/*` 路径强制 Basic Auth 或 OIDC
  ```nginx
  location /hop/ {
    auth_basic "Restricted";
    auth_basic_user_file /etc/nginx/.htpasswd;
    proxy_pass http://localhost:8080;
  }
  ```
- [ ] **网络层隔离**：Hop Web 的 8080 端口禁止公网访问，仅允许内网/VPN
- [ ] **防火墙规则**：生产服务器只放行 80/443（由反代转发到 8080），不直接暴露 Tomcat
- [ ] **更新部署文档**：在 [离线部署说明书.md](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/离线部署说明书.md) 中明确 `/hop/*` 未鉴权的安全注意事项

### P1 — 短期修复（代码侧，1-2 周）

- [ ] **扩展 AuthenticationFilter 保护范围**：将 `/hop/` 加入 [isProtectedPath()](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rap/src/main/java/org/apache/hop/ui/hopgui/auth/AuthenticationFilter.java#L116-124)
  ```java
  return path.equals("/ui")
      || path.equals("/ui-dark")
      || path.startsWith("/ui/")
      || path.startsWith("/ui-dark/")
      || path.startsWith("/hop/");   // 新增
  ```
- [ ] **豁免必要路径**：健康检查用的 `/hop/status` 可能需要保留匿名访问，或改用 `/hop/status` + 共享秘钥参数
- [ ] **健康检查脚本同步调整**：[hop-start.sh:71](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/deploy-scripts/hop-start.sh#L71) 和 [hop-verify.sh](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/deploy-scripts/hop-verify.sh) 改为支持 Basic Auth 头：
  ```bash
  curl -fsS -u "${HEALTH_USER}:${HEALTH_PASS}" "${HEALTH_URL}"
  ```
- [ ] **引入配置开关**：`HOP_HOP_SERVER_REQUIRE_AUTH=true/false`，默认 `true`（破坏性变更，需在 release notes 标注）

### P2 — 中期增强（1 个月）

- [ ] **审计日志**：所有 `/hop/*` 请求记录调用者 IP、用户、操作、参数、响应码（便于事后追溯）
- [ ] **细粒度权限**：只读权限（GET `/hop/*Status`）与执行权限（POST `/hop/exec*`、`/hop/start*`）分离
- [ ] **API Token / OAuth2**：替代 Basic Auth，支持服务间调用
- [ ] **CSRF 防护**：对 POST/PUT 类操作添加 CSRF Token 校验
- [ ] **速率限制**：对 `/hop/sniffTransform`、`/hop/webService` 这类可读取业务数据的接口加速率限制

### P3 — 长期演进

- [ ] **与 REST API 鉴权统一**：`/hop/*` 复用 `/api/v1/*`（[rest 模块](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/rest)）的鉴权机制
- [ ] **路径混淆/禁用**：生产环境可通过 `HOP_DISABLE_HOP_SERVER_SERVLETS=true` 完全禁用 `/hop/*` 分发器（仅保留 `/ui` 和 `/api/v1/*`）
- [ ] **CVE 漏洞响应流程**：建立 security@hop.apache.org 的漏洞披露响应机制

---

## 附：受影响部署模式

| 部署模式 | 受影响 | 说明 |
|---------|-------|------|
| Hop Web (RAP) on Tomcat | ✅ | `/hop/*` 完全暴露，无鉴权 |
| Hop Server (独立 Jetty) | ⚠️ | 部分受影响：[WebServer.java:219](file:///Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop/engine/src/main/java/org/apache/hop/www/WebServer.java#L219) 支持 `ConstraintSecurityHandler` + 用户名密码，但需手动配置 |
| Hop Client (桌面) | ❌ | 不启动 Web 服务，不受影响 |
| Hop REST API (`/api/v1/*`) | ❌ | 独立模块，有独立鉴权（本清单不涉及） |

---

## 附：验证脚本

```bash
# 验证任意一个未鉴权接口（应返回 200 + XML/JSON 内容，无 401）
for path in status pipelineStatus workflowStatus asyncStatus getExecInfo \
            pipelineImage workflowImage webService sniffTransform; do
  echo -n "/hop/$path/ → "
  curl -s -o /dev/null -w "%{http_code}\n" "http://localhost:8080/hop/$path/"
done

# 预期输出：全部 200 或 404（404 = 缺少必要参数，但仍表示路由可达）
# 若出现 401/403 → 已修复
```

---

**生成时间**：2026-07-19
**审计范围**：`@HopServerServlet` 注解扫描 + `CONTEXT_PATH` 常量提取
**接口总数**：29（engine 模块 27 + plugins/misc/async 2）
