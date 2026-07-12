# Rap UI极速调试方法

## 一、两种调试路径

修改代码后，按场景选择：

| 路径 | 耗时 | 适用场景 |
|------|------|---------|
| **A — 直接替换 JAR** | ~20 s | 小改、频繁迭代（推荐日常使用） |
| **B — 完整重建 WAR** | ~2 min | 大改、涉及多模块、需要启动脚本自动检测 |

---

### 路径 A：直接替换 JAR（最快）

```bash
# 1. 编译 + 打包 JAR
./mvnw compile -pl ${MODULE} -am -DskipTests \
  && ./mvnw jar:jar -pl ${MODULE} -am -DskipTests

# 2. 复制 JAR 到 webapp 目录
cp ${MODULE}/target/${JAR_NAME} tomcat-run/webapps/ROOT/WEB-INF/lib/

# 3. 清理 Tomcat 缓存并重启
rm -rf tomcat-run/work/Catalina \
  && bash start-hop-web.sh --restart
```

示例（修改 `plugins/misc/projects` 模块）：

```bash
./mvnw compile -pl plugins/misc/projects -am -DskipTests \
  && ./mvnw jar:jar -pl plugins/misc/projects -am -DskipTests \
  && cp plugins/misc/projects/target/hop-misc-projects-2.19.0-SNAPSHOT.jar tomcat-run/webapps/ROOT/WEB-INF/lib/ \
  && rm -rf tomcat-run/work/Catalina \
  && bash start-hop-web.sh --restart
```

示例（修改 `ui` 模块）：

```bash
./mvnw compile -pl ui -am -DskipTests \
  && ./mvnw jar:jar -pl ui -am -DskipTests \
  && cp ui/target/hop-ui-2.19.0-SNAPSHOT.jar tomcat-run/webapps/ROOT/WEB-INF/lib/ \
  && rm -rf tomcat-run/work/Catalina \
  && bash start-hop-web.sh --restart
```

---

### 路径 B：完整重建 WAR（最稳）

```bash
# 1. 安装模块到本地仓库（避免依赖传递问题）
./mvnw install -pl ${MODULE} -am -DskipTests -Dassemblies=false -q

# 2. 重新打包 web assembly
./mvnw package -pl assemblies/web -am -DskipTests -Dfast-build -q

# 3. 重启开发服务器（自动检测 war 更新并提取）
./start-hop-web.sh --force-build
```

启动脚本 `start-hop-web.sh` 使用时间戳对比自动检测：

```bash
if [ -z "$(ls -A "$WEBAPP_DIR" 2>/dev/null)" ] || [ "assemblies/web/target/hop.war" -nt "$WEBAPP_DIR" ]; then
  rm -rf "$WEBAPP_DIR"
  unzip -q assemblies/web/target/hop.war -d "$WEBAPP_DIR"
fi
```

服务器若正在运行，重启脚本会先执行停止 Tomcat。

---

## 二、通用调试流程（任意模块）

`${MODULE}` 替换为任何 Maven 模块路径即可（参考 §三）。

### 快速查 JAR 名

```bash
# 查看编译产物 JAR 名称
ls ${MODULE}/target/hop-*.jar
```

### 一行命令（路径 A）

```bash
export MODULE=plugins/transforms/json && \
JAR=$(basename $(ls $MODULE/target/hop-*.jar 2>/dev/null | grep -v tests | head -1)) && \
./mvnw compile -pl $MODULE -am -DskipTests && \
./mvnw jar:jar -pl $MODULE -am -DskipTests && \
cp $MODULE/target/$JAR tomcat-run/webapps/ROOT/WEB-INF/lib/ && \
rm -rf tomcat-run/work/Catalina && \
bash start-hop-web.sh --restart
```

---

## 三、模块路径与 JAR 名速查

| 模块 | Maven 路径 (`-pl`) | JAR 文件名 |
|------|-------------------|-----------|
| Core | `core` | `hop-core-*.jar` |
| Engine | `engine` | `hop-engine-*.jar` |
| UI | `ui` | `hop-ui-*.jar` |
| RAP | `rap` | `hop-ui-rap-*.jar` |
| REST | `rest` | `hop-rest-*.jar` |
| Transform `<name>` | `plugins/transforms/<name>` | `hop-transform-<name>-*.jar` |
| Action `<name>` | `plugins/actions/<name>` | `hop-action-<name>-*.jar` |
| Misc `<name>` | `plugins/misc/<name>` | `hop-misc-<name>-*.jar` |
| Database `<name>` | `plugins/databases/<name>` | `hop-database-<name>-*.jar` |
| Tech `<name>` | `plugins/tech/<name>` | `hop-tech-<name>-*.jar` |
| Valuetype `<name>` | `plugins/valuetypes/<name>` | `hop-valuetype-<name>-*.jar` |

实际版本号参考 `pom.xml` `<version>` 字段（当前为 `2.19.0-SNAPSHOT`）。

---

## 四、原理

### 4.1 文件依赖链

```
Java 源文件 (.java)         ── 在 ${MODULE}/src/main/java/
        ↓ compile
  Class 文件 (.class)        ── 在 ${MODULE}/target/classes/
        ↓ jar:jar / package
    JAR 文件 (.jar)          ── 在 ${MODULE}/target/
        ↓ 复制到 webapp
  Webapp WEB-INF/lib/       ── 在 tomcat-run/webapps/ROOT/WEB-INF/lib/
        ↓ Tomcat 类加载
   Tomcat 容器中的 RAP 服务
```

### 4.2 时间戳增量检测

`start-hop-web.sh` 对比 `hop.war` 与 `webapp` 目录的修改时间：

- `webapp` 不存在或为空 → 提取
- `hop.war` 比 `webapp` 更新 → 重新提取（即路径 B 的核心）
- 两者都不满足 → 使用已有缓存

### 4.3 路径 A 为何更快

路径 A 跳过 assembly 打包（WAR 重新组装），直接将编译后的 JAR 注入已提取的 `webapp` 目录。它不依赖启动脚本的自动检测，而是手动复制 + 清理 Tomcat 缓存 + 重启。

---

## 五、常见坑

### 5.1 `mvn compile` ≠ 重新打包 JAR

- `compile` 只更新 `${MODULE}/target/classes/` 下的 class 文件
- `jar:jar`（或 `package`）才会重新生成 `${MODULE}/target/hop-*.jar`
- **必须按顺序执行：先 `compile`，再 `jar:jar`**

### 5.2 RAP 环境下 SWT 方法缺失

某些 SWT 桌面端特有方法在 RAP 下不存在，调用会直接 500：

```java
// 安全写法
if (!EnvironmentUtils.getInstance().isWeb()) {
  text.showSelection();
}
```

常见缺失：`Text.showSelection()`、`StyledText.getContent()` 等桌面特有方法。

### 5.3 RAP 后台线程 UI 更新不刷新

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

### 5.4 Tomcat 缓存

`tomcat-run/work/Catalina/` 目录缓存编译后的 JSP 和类。修改 JAR 后必须清缓存再重启，否则旧类可能仍被加载。

### 5.5 路径差异

当前开发环境使用本地 Tomcat（`tomcat-run/`）而非 Docker，注意路径差异：
- **旧路径**（Docker）：`assemblies/web/target/webapp/WEB-INF/lib/`
- **新路径**（本地 Tomcat）：`tomcat-run/webapps/ROOT/WEB-INF/lib/`

### 5.6 修改未生效排查

```bash
# 1. 验证时间戳
ls -la ${MODULE}/src/main/java/.../YourFile.java
ls -la ${MODULE}/target/classes/.../YourFile.class
ls -la ${MODULE}/target/hop-*.jar
ls -la tomcat-run/webapps/ROOT/WEB-INF/lib/hop-*.jar

# 2. 四个时间戳必须依次递增
# 3. 若 JAR 没更新：重新执行 compile + jar:jar
# 4. 若 JAR 已复制但未生效：确认清除了 Tomcat 缓存
```

---

## 六、验证方法

对比时间戳确认每次重建均生效：

```bash
ls -la ui/src/main/java/org/apache/hop/ui/hopgui/assistant/LlmAssistantDialog.java \
      ui/target/classes/org/apache/hop/ui/hopgui/assistant/LlmAssistantDialog.class \
      ui/target/hop-ui-2.19.0-SNAPSHOT.jar \
      tomcat-run/webapps/ROOT/WEB-INF/lib/hop-ui-2.19.0-SNAPSHOT.jar
```

四个时间戳应依次递增。

---

## 七、最佳实践

1. **日常迭代用路径 A**（~20 秒），大改动用路径 B（自动化检测）
2. **保持服务器运行**：开发中保持 Tomcat 启动，避免反复重启
3. **并行编译加速**：
   ```
   ./mvnw compile -pl ${MODULE} -am -DskipTests -T 1C
   ```
4. **远程调试端口**：5005 (JDWP)，可在 IDE 中配置 Socket 连接
5. **查看日志**：
   ```bash
   tail -f tomcat-run/logs/catalina.out
   ```
6. **仅重启 Tomcat**：使用 `--restart` 参数跳过部署，快速生效 JAR 热更新

---

## 八、常见问题排查

### 8.1 编译失败

```bash
# 查看详细错误
./mvnw compile -pl ${MODULE} -am 2>&1 | grep -A 10 "error"

# 检查依赖树
./mvnw dependency:tree -pl ${MODULE}

# 清理后重试
./mvnw clean install -pl ${MODULE} -am -DskipTests -Dassemblies=false
```

### 8.2 服务器启动失败

```bash
# 日志
tail -f tomcat-run/logs/catalina.out

# 端口占用
lsof -i :8080
lsof -i :5005

# 重新构建并启动
./mvnw clean package -pl assemblies/web -am -DskipTests -Dfast-build
./start-hop-web.sh --force-build
```

### 8.3 调试端口连接失败

- 确认服务器已启动：`pgrep -f catalina`
- 确认端口开放：`lsof -i :5005`
- IDE 配置：Host `localhost` / Port `5005` / Transport `Socket`

---

## 九、访问地址

| 服务 | URL |
|------|-----|
| Web UI | http://localhost:8080/ui |
| REST API | http://localhost:8080/hop/ |
| Swagger UI | http://localhost:8080/hop/api/ |
| Debug Port | 5005 (JDWP) |
