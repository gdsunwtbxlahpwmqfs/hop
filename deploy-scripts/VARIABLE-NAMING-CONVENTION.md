# Qi Hop 部署脚本 — 变量命名与使用规范

> 适用范围：`deploy-scripts/` 目录下所有 shell 脚本
> 核心参考标准：`hop-package.sh`、`hop-deploy.sh`
> 目的：消除同名不同义 / 同义不同名冲突，统一变量定义位置、命名风格与引用方式

---

## 1. 命名风格

| 类别 | 风格 | 示例 |
|------|------|------|
| 全局变量（脚本级配置/路径） | 全大写 + 下划线 | `INSTALL_BASE`、`CATALINA_HOME` |
| 局部变量（函数内） | 小写 + 下划线，必须 `local` | `local pid_file`、`local arch` |
| 环境变量（运行时跨脚本传递） | `HOP_` 前缀大写 | `HOP_CONFIG_FOLDER`、`HOP_LOG_LEVEL` |
| 布尔标志 | 全大写，值为 `true`/`false` | `SKIP_BUILD`、`DRY_RUN`、`USE_SYSTEMD` |
| 只读常量 | 全大写 | `RED`、`GREEN`、`NC` |
| 数组 | 全大写复数 | `IMAGES`、`cmds`（局部可小写） |

**禁止**：`camelCase`、`kebab-case`、无下划线的大写连写（如 `INSTALLBASE`）。

---

## 2. 定义位置规范

脚本结构按固定顺序分区，变量按类别落入对应分区：

```
1. set 选项
2. # --------------------- 颜色与日志 ---------------------
   RED/GREEN/YELLOW/BLUE/NC + log/warn/err/info
3. # --------------------- 默认配置 ---------------------
   SCRIPT_DIR、INSTALL_BASE、INSTANCE_NAME、端口、版本号、布尔开关等
4. # --------------------- 参数解析 ---------------------
   while/case 覆盖默认值
5. # --------------------- 派生路径（依赖默认配置 + 参数）---------------------
   JAVA_HOME、CATALINA_HOME、CATALINA_BASE、WEBAPP_DIR、LOG_FILE 等
6. # --------------------- 工具函数 ---------------------
   check_cmd、sha256_hash 等
7. # --------------------- 业务函数 ---------------------
8. # --------------------- 主流程 ---------------------
   main "$@"
```

- **共享变量**（多脚本共用，如 `INSTALL_BASE`）必须在 `默认配置` 区用 `${VAR:-default}` 模式定义，允许环境变量覆盖。
- **派生变量**（依赖输入参数才能计算，如 `CATALINA_BASE`）必须在 `参数解析` 之后定义。
- **脚本特有变量**（如 `PKG_NAME`、`OUTPUT_DIR`）在 `默认配置` 区直接赋值。

---

## 3. 默认值模式

| 场景 | 模式 | 说明 |
|------|------|------|
| 共享变量（允许环境/上游脚本覆盖） | `VAR="${VAR:-default}"` | hop-start/verify/uninstall 读取 hop-deploy 写入的值 |
| 脚本内部变量（不接受外部覆盖） | `VAR="value"` | 如 `PKG_NAME`、`BUILD_DATE` |
| 必须由命令行提供 | 参数解析中无默认值，未提供则报错 | — |

**规则**：凡是被多个脚本引用的变量（`INSTALL_BASE`、`INSTANCE_NAME`、`TOMCAT_PORT`、`HOP_USER`、`HOP_GROUP`、`SERVICE_NAME`、`HEALTH_CHECK_PATH`、`JAVA_HOME`、`CATALINA_HOME/BASE`、`WEBAPP_DIR`、`LOG_FILE`、`PID_FILE`）一律使用 `${VAR:-default}`。

---

## 4. 规范变量字典（canonical names）

以下为全目录统一的变量名与语义。**不得**另起同名不同义或同义不同名的变量。

### 4.1 脚本定位
| 变量 | 语义 | 定义方式 |
|------|------|----------|
| `SCRIPT_DIR` | 当前脚本所在目录（绝对路径） | `SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"` |
| `PROJECT_ROOT` | 项目根目录（仅 hop-package.sh 需要） | `PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"` |

### 4.2 安装与实例
| 变量 | 语义 | 默认值 |
|------|------|--------|
| `INSTALL_BASE` | Hop Web 安装根目录 | `/opt/qi`（LLM 栈用 `/opt/hop-llm`，独立语义） |
| `INSTANCE_NAME` | 多实例标识 | `qi-hop-001` |
| `SERVICE_NAME` | systemd 服务名 | 派生：`qi-hop-${INSTANCE_NAME}` |
| `HOP_USER` / `HOP_GROUP` | 运行用户/组 | `qi` |
| `HOP_UID` / `HOP_GID` | 用户/组 ID | `501` |
| `HOP_USER_HOME` | 用户家目录 | `/home/qi` |

### 4.3 Tomcat / JDK 路径（派生，参数解析后定义）
| 变量 | 语义 |
|------|------|
| `JAVA_HOME` | `${INSTALL_BASE}/jdk21` |
| `CATALINA_HOME` | Tomcat 软件目录（只读），`${INSTALL_BASE}/tomcat` |
| `CATALINA_BASE` | 运行实例目录（可写），`${INSTALL_BASE}/tomcat-run[-${INSTANCE_NAME}]` |
| `WEBAPP_DIR` | Web 应用部署目录，**统一为** `${CATALINA_BASE}/webapps/ROOT` |
| `LOG_FILE` | `${CATALINA_BASE}/logs/catalina.out` |
| `PID_FILE` | `${CATALINA_BASE}/temp/tomcat.pid` |
| `SETENV_FILE` | `${CATALINA_BASE}/bin/setenv.sh` |

### 4.4 Hop 数据目录（CATALINA_BASE 下）
| 变量 | 语义 |
|------|------|
| `HOP_CONFIG_FOLDER` | `${CATALINA_BASE}/config` |
| `HOP_AUDIT_FOLDER` | `${CATALINA_BASE}/audit` |
| `HOP_PLUGIN_BASE_FOLDERS` | `${CATALINA_BASE}/plugins`（**不是** WEB-INF/plugins） |
| `HOP_SHARED_JDBC_FOLDERS` | `${CATALINA_BASE}/jdbc-drivers`（**不是** WEB-INF/jdbc-drivers） |
| `HOP_REST_CONFIG_FOLDER` | `${CATALINA_BASE}/config` |
| `HOP_DOCS_FOLDER` | `${CATALINA_BASE}/docs` |
| `RWT_RESOURCE_LOCATION` | `${CATALINA_BASE}/rwt-resources` |

### 4.5 网络与端口
| 变量 | 语义 | 默认值 |
|------|------|--------|
| `TOMCAT_PORT` | Hop Web HTTP 端口 | `8080` |
| `HEALTH_CHECK_PATH` | 健康检查路径 | `/api/v1/` |
| `HEALTH_TIMEOUT` | 启动健康检查超时（秒） | `120` |
| `HEALTH_INTERVAL` | 轮询间隔（秒） | `3` |

### 4.6 离线包相关（易冲突，严格区分）
| 变量 | 语义 | 所属脚本 |
|------|------|----------|
| `STAGING_DIR` | **打包时**的组装输出目录（`${OUTPUT_DIR}/${PKG_NAME}`） | hop-package.sh |
| `PKG_DIR` | **部署时**的离线包解压根目录（含 jdk/、tomcat/、hop/） | hop-deploy.sh / hop-verify.sh |
| `OUTPUT_DIR` | 打包产物（tar.gz）输出目录 | hop-package.sh / hop-download-*.sh |
| `PKG_NAME` | 离线包名 | hop-package.sh |

> ⚠️ 历史冲突：`PKG_DIR` 曾在 hop-package.sh（输出暂存）与 hop-deploy.sh（输入包根）中同名不同义。
> 现规范：**hop-package.sh 改用 `STAGING_DIR`**；`PKG_DIR` 专指部署输入。

### 4.7 日志级别（输入 vs 运行时）
| 变量 | 语义 |
|------|------|
| `LOG_LEVEL` | 部署时 CLI 输入（hop-deploy.sh `--log-level`） |
| `HOP_LOG_LEVEL` | 运行时环境变量（Hop 读取），由 setenv.sh 从 `LOG_LEVEL` 导出 |

> 二者为**不同角色**，非同义不同名：`LOG_LEVEL` 是安装参数，`HOP_LOG_LEVEL` 是运行时变量。

### 4.8 LLM 栈（hop-deploy-llm.sh 专用，独立命名空间）
| 变量 | 语义 |
|------|------|
| `LLM_API_KEY` / `LLM_API_BASE` | 上游 LLM 凭证 |
| `DEFAULT_MODEL` | 默认模型名 |
| `LITELLM_PORT` | LiteLLM 监听端口（默认 4000） |
| `QDRANT_HTTP_PORT` / `QDRANT_GRPC_PORT` | Qdrant 端口 |
| `COMPOSE_CMD` | docker compose 命令数组 |

---

## 5. 公共工具函数规范

### 颜色与日志（每个脚本顶部，格式完全一致）
```bash
# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }
```

> **例外**：薄包装脚本（thin wrapper，如 `hop-stop.sh`，仅 `exec` 转发到主脚本）不需要颜色/日志段——它复用目标脚本的日志实现。

### sha256 兼容层（统一为全局 `SHA256_CMD` + `sha256_hash` 函数）
```bash
SHA256_CMD=""
if command -v sha256sum >/dev/null 2>&1; then
    SHA256_CMD="sha256sum"
elif command -v shasum >/dev/null 2>&1; then
    SHA256_CMD="shasum -a 256"
else
    err "未找到 sha256sum 或 shasum"; exit 1
fi
sha256_hash() { $SHA256_CMD "$1" 2>/dev/null | awk '{print $1}'; }
```

### check_cmd
```bash
check_cmd() { command -v "$1" >/dev/null 2>&1 || { err "缺少必要命令: $1"; return 1; }; }
```

---

## 6. set 选项规范

| 模式 | 用途 | 适用脚本 |
|------|------|----------|
| `set -Eeuo pipefail` | 构建类/下载类（任何命令失败即退出） | hop-package、hop-deploy、hop-download-*、hop-system-prepare |
| `set -Euo pipefail` | 运维类（手动处理错误，避免误退） | hop-start、hop-stop、hop-verify、hop-uninstall、hop-deploy-llm |

> `-E`（ERR trap 继承）所有脚本统一开启；`-e` 按脚本性质选择，**不得**无故混用。

---

## 7. 参数解析规范

统一模式：
```bash
while [[ $# -gt 0 ]]; do
    case "$1" in
        --flag) VAR="$2"; shift 2 ;;
        --bool) VAR=true; shift ;;
        -h|--help) sed -n '2,Np' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done
```

长选项用 `--kebab-case`；未知参数一律报错退出。

---

## 8. 变更要点（本次标准化）

1. **hop-package.sh**：`PKG_DIR` → `STAGING_DIR`（消除同名冲突）
2. **hop-start.sh**：
   - `SERVICE_NAME` 默认值修正为派生 `qi-hop-${INSTANCE_NAME}`，删除不可达的死代码
   - `WEBAPP_DIR` 由 `webapps` 改为 `webapps/ROOT`
   - `HOP_PLUGIN_BASE_FOLDERS` / `HOP_SHARED_JDBC_FOLDERS` 默认改为 `CATALINA_BASE` 下，对齐 hop-deploy.sh
3. **hop-deploy.sh**：`INSTALL_BASE`/`TOMCAT_PORT`/`HOP_USER`/`HOP_GROUP` 改为 `${VAR:-default}`；删除 `HOP_DOCS_FOLDER` 重复定义
4. **hop-verify.sh**：补充 `SCRIPT_DIR`、`PKG_DIR` 输入语义
5. **hop-system-prepare.sh**：移除未使用的 `SCRIPT_DIR`；`hop_user` 局部变量对齐 `HOP_USER`
6. **hop-download-llm-images.sh**：sha256 改为全局 `SHA256_CMD` 模式
7. 全目录：补齐颜色/日志段头、check_cmd、变量使用注释
