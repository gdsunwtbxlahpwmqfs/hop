# Rap UI极速调试方法

## 一、核心概念：两类模块，两个部署位置

**这是最重要的区分，搞错会导致插件消失或功能异常。**

| 模块类型 | 模块示例 | 部署位置 | 打包命令 |
|---------|---------|---------|---------|
| **核心模块** | `core`, `engine`, `ui`, `rap`, `rest` | `WEB-INF/lib/` | `jar:jar` 即可 |
| **插件模块** | `plugins/transforms/*`, `plugins/actions/*`, `plugins/misc/*` 等 | `WEB-INF/plugins/<类别>/<名称>/` | **必须用 `package`**（生成 Jandex 索引） |

### 为什么插件必须用 `package` 而非 `jar:jar`？

Hop 的插件发现机制依赖 **Jandex 索引**（`META-INF/jandex.idx`）来扫描 `@Transform`、`@Action` 等注解。

- `jar:jar` — 只打包 class 和资源，**不生成 Jandex 索引**
- `package` — 触发 `jandex-maven-plugin`，生成完整索引

**用 `jar:jar` 打包插件会导致插件在 UI 中消失（无法被发现）。**

验证 Jandex 索引是否存在：
```bash
jar -tf ${MODULE}/target/${JAR_NAME} | grep jandex
# 应输出: META-INF/jandex.idx
```

---

## 二、两种调试路径

| 路径 | 耗时 | 适用场景 |
|------|------|---------|
| **A — 直接替换 JAR** | ~20 s | 小改、频繁迭代（推荐日常使用） |
| **B — 完整重建 WAR** | ~2 min | 大改、涉及多模块、需要启动脚本自动检测 |

---

### 路径 A：直接替换 JAR（最快）

#### A.1 核心模块（core, engine, ui, rap, rest）

```bash
# 1. 编译 + 打包
./mvnw package -pl ${MODULE} -am -DskipTests -q

# 2. 复制到 WEB-INF/lib/
cp ${MODULE}/target/${JAR_NAME} tomcat-run/webapps/ROOT/WEB-INF/lib/

# 3. 清理缓存并重启
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

示例（修改 `ui` 模块）：
```bash
./mvnw package -pl ui -am -DskipTests -q && \
cp ui/target/hop-ui-2.19.0-SNAPSHOT.jar tomcat-run/webapps/ROOT/WEB-INF/lib/ && \
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

#### A.2 插件模块（plugins/*）— 关键差异

```bash
# 1. 编译 + 打包（必须用 package，不能用 jar:jar）
./mvnw package -pl ${MODULE} -am -DskipTests -q

# 2. 复制到 WEB-INF/plugins/<类别>/<名称>/（不是 WEB-INF/lib/！）
cp ${MODULE}/target/${JAR_NAME} tomcat-run/webapps/ROOT/WEB-INF/plugins/${CATEGORY}/${NAME}/

# 3. 清理缓存并重启
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

示例（修改 `plugins/transforms/languagemodelchat` 插件）：
```bash
./mvnw package -pl plugins/transforms/languagemodelchat -am -DskipTests -q && \
cp plugins/transforms/languagemodelchat/target/hop-transform-languagemodelchat-2.19.0-SNAPSHOT.jar \
   tomcat-run/webapps/ROOT/WEB-INF/plugins/transforms/languagemodelchat/ && \
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

示例（修改 `plugins/misc/projects` 插件）：
```bash
./mvnw package -pl plugins/misc/projects -am -DskipTests -q && \
cp plugins/misc/projects/target/hop-misc-projects-2.19.0-SNAPSHOT.jar \
   tomcat-run/webapps/ROOT/WEB-INF/plugins/misc/projects/ && \
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

> **注意**：插件目录中除了 JAR，还有 `lib/` 子目录（存放依赖）和 `version.xml`，这些不需要手动管理，只需替换主 JAR。

---

### 路径 B：完整重建 WAR（最稳）

```bash
# 1. 安装模块到本地仓库
./mvnw install -pl ${MODULE} -am -DskipTests -Dassemblies=false -q

# 2. 重新打包 web assembly
./mvnw package -pl assemblies/web -am -DskipTests -Dfast-build -q

# 3. 重启开发服务器（自动检测 war 更新并提取）
./start-hop-web.sh --force-build
```

---

## 三、一行命令模板

### 核心模块

```bash
export MODULE=ui && \
JAR=$(basename $(ls $MODULE/target/hop-*.jar 2>/dev/null | grep -v tests | head -1)) && \
./mvnw package -pl $MODULE -am -DskipTests -q && \
cp $MODULE/target/$JAR tomcat-run/webapps/ROOT/WEB-INF/lib/ && \
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

### 插件模块

```bash
export MODULE=plugins/transforms/languagemodelchat && \
export PLUGIN_DIR=tomcat-run/webapps/ROOT/WEB-INF/plugins/transforms/languagemodelchat && \
JAR=$(basename $(ls $MODULE/target/hop-*.jar 2>/dev/null | grep -v tests | head -1)) && \
./mvnw package -pl $MODULE -am -DskipTests -q && \
cp $MODULE/target/$JAR "$PLUGIN_DIR/" && \
rm -rf tomcat-run/work/Catalina && bash start-hop-web.sh --restart
```

---

## 四、模块路径与部署位置速查

| 模块 | Maven 路径 (`-pl`) | JAR 文件名 | 部署目录 |
|------|-------------------|-----------|---------|
| Core | `core` | `hop-core-*.jar` | `WEB-INF/lib/` |
| Engine | `engine` | `hop-engine-*.jar` | `WEB-INF/lib/` |
| UI | `ui` | `hop-ui-*.jar` | `WEB-INF/lib/` |
| RAP | `rap` | `hop-ui-rap-*.jar` | `WEB-INF/lib/` |
| REST | `rest` | `hop-rest-*.jar` | `WEB-INF/lib/` |
| Transform `<name>` | `plugins/transforms/<name>` | `hop-transform-<name>-*.jar` | `WEB-INF/plugins/transforms/<name>/` |
| Action `<name>` | `plugins/actions/<name>` | `hop-action-<name>-*.jar` | `WEB-INF/plugins/actions/<name>/` |
| Misc `<name>` | `plugins/misc/<name>` | `hop-misc-<name>-*.jar` | `WEB-INF/plugins/misc/<name>/` |
| Database `<name>` | `plugins/databases/<name>` | `hop-database-<name>-*.jar` | `WEB-INF/plugins/databases/<name>/` |
| Tech `<name>` | `plugins/tech/<name>` | `hop-tech-<name>-*.jar` | `WEB-INF/plugins/tech/<name>/` |
| Valuetype `<name>` | `plugins/valuetypes/<name>` | `hop-valuetype-<name>-*.jar` | `WEB-INF/plugins/valuetypes/<name>/` |

实际版本号参考 `pom.xml` `<version>` 字段（当前为 `2.19.0-SNAPSHOT`）。

---

## 五、原理

### 5.1 文件依赖链

```
Java 源文件 (.java)         ── 在 ${MODULE}/src/main/java/
        ↓ compile
  Class 文件 (.class)        ── 在 ${MODULE}/target/classes/
        ↓ package (含 jandex 索引)
    JAR 文件 (.jar)          ── 在 ${MODULE}/target/
        ↓ 复制到 webapp（位置取决于模块类型）
         ├─ 核心模块 → WEB-INF/lib/
         └─ 插件模块 → WEB-INF/plugins/<类别>/<名称>/
        ↓ Tomcat 类加载
   Tomcat 容器中的 RAP 服务
```

### 5.2 插件发现机制

```
HOP_PLUGIN_BASE_FOLDERS = tomcat-run/webapps/ROOT/WEB-INF/plugins
        ↓ 扫描每个子目录
  读取 version.xml 获取版本
        ↓ 加载 JAR
  读取 META-INF/jandex.idx 扫描注解
        ↓ 匹配 @Transform / @Action / @DatabaseMetaPlugin 等
  注册到 Hop 的插件注册表
```

**如果 Jandex 索引缺失，插件注解不会被扫描到，插件在 UI 中不可见。**

### 5.3 时间戳增量检测（路径 B）

`start-hop-web.sh` 对比 `hop.war` 与 `webapp` 目录的修改时间：

- `webapp` 不存在或为空 → 提取
- `hop.war` 比 `webapp` 更新 → 重新提取
- 两者都不满足 → 使用已有缓存

---

## 六、常见坑

### 6.1 插件用 `jar:jar` 打包后消失

**症状**：修改插件代码后用 `jar:jar` 打包部署，插件在 UI 弹框中消失。

**原因**：`jar:jar` 不生成 `META-INF/jandex.idx`，Hop 无法发现 `@Transform` 等注解。

**解决**：插件模块必须用 `mvn package`。

```bash
# 错误 - 插件会消失
./mvnw jar:jar -pl plugins/transforms/languagemodelchat -am -DskipTests

# 正确 - 包含 Jandex 索引
./mvnw package -pl plugins/transforms/languagemodelchat -am -DskipTests
```

### 6.2 插件 JAR 复制到错误位置

**症状**：插件代码更新了但功能没变化，或插件消失。

**原因**：插件 JAR 复制到了 `WEB-INF/lib/` 而非 `WEB-INF/plugins/<类别>/<名称>/`。

**解决**：插件必须复制到 plugins 目录：

```bash
# 错误 - 插件不会被正确加载
cp $JAR tomcat-run/webapps/ROOT/WEB-INF/lib/

# 正确 - 复制到插件目录
cp $JAR tomcat-run/webapps/ROOT/WEB-INF/plugins/transforms/languagemodelchat/
```

### 6.3 `mvn compile` ≠ 重新打包 JAR

- `compile` 只更新 `target/classes/` 下的 class 文件
- `package`（或 `jar:jar`）才会重新生成 `target/hop-*.jar`

### 6.4 RAP 环境下 SWT 方法缺失

某些 SWT 桌面端特有方法在 RAP 下不存在，调用会直接 500：

```java
// 安全写法
if (!EnvironmentUtils.getInstance().isWeb()) {
  text.showSelection();
}
```

常见缺失：`Text.showSelection()`、`StyledText.getContent()` 等桌面特有方法。

### 6.5 RAP 后台线程 UI 更新不刷新

后台线程用 `asyncExec` 更新 UI 后，浏览器端看不到。需要主动推送：

```java
display.asyncExec(() -> {
  ClientPushSession pushSession = RAPClientPushUtils.createClientPushSession(
    "update-prefix-" + connectionId
  );
  pushSession.start();
  // ... UI 更新逻辑
});
```

注意：`start()` **必须**在 `asyncExec` 回调内部调用。

### 6.6 Tomcat 缓存

`tomcat-run/work/Catalina/` 目录缓存编译后的 JSP 和类。修改 JAR 后必须清缓存再重启。

### 6.7 不要在项目根目录执行 `jar -xf`

提取 JAR 内容验证时，**不要在项目根目录执行**，否则会创建与源码包路径冲突的游离文件。

```bash
# 错误 - 会在项目根目录创建 org/ 目录
jar -xf module.jar org/apache/hop/.../messages.properties

# 正确 - 在临时目录中提取
mkdir -p /tmp/jar-check && cd /tmp/jar-check && jar -xf /path/to/module.jar
```

---

## 七、修改未生效排查流程

```bash
# 1. 确认 JAR 中包含 Jandex 索引（插件必须）
jar -tf ${MODULE}/target/${JAR_NAME} | grep jandex

# 2. 确认 JAR 复制到了正确位置
# 核心模块: WEB-INF/lib/
# 插件模块: WEB-INF/plugins/<类别>/<名称>/
ls -la tomcat-run/webapps/ROOT/WEB-INF/${DEST_DIR}/${JAR_NAME}

# 3. 验证时间戳依次递增
ls -la ${MODULE}/src/.../YourFile.java     # 源文件
ls -la ${MODULE}/target/classes/.../YourFile.class  # 编译产物
ls -la ${MODULE}/target/${JAR_NAME}         # 打包 JAR
ls -la tomcat-run/.../${JAR_NAME}           # 部署的 JAR

# 4. 确认清理了 Tomcat 缓存
ls tomcat-run/work/Catalina/  # 应不存在

# 5. 查看服务器日志确认启动无异常
tail -30 tomcat-run/logs/catalina.$(date +%Y-%m-%d).log
```

---

## 八、最佳实践

1. **日常迭代用路径 A**（~20 秒），大改动用路径 B
2. **插件打包用 `package`，核心模块可以用 `jar:jar`**
3. **保持服务器运行**：开发中保持 Tomcat 启动，避免反复重启
4. **并行编译加速**：
   ```bash
   ./mvnw package -pl ${MODULE} -am -DskipTests -T 1C -q
   ```
5. **远程调试端口**：5005 (JDWP)，可在 IDE 中配置 Socket 连接
6. **查看日志**：
   ```bash
   tail -f tomcat-run/logs/catalina.$(date +%Y-%m-%d).log
   ```
7. **仅重启 Tomcat**：使用 `--restart` 参数跳过部署，快速生效 JAR 热更新

---

## 九、访问地址

| 服务 | URL |
|------|-----|
| Web UI | http://localhost:8080/ui |
| REST API | http://localhost:8080/hop/ |
| Swagger UI | http://localhost:8080/hop/api/ |
| Debug Port | 5005 (JDWP) |
