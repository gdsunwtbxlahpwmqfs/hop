# LLM 助理 UI 极小调试方法

## 快速验证修改是否生效

```bash
# 1. 编译（更新 class 文件）
./mvnw compile -pl ui -am -DskipTests

# 2. 打包 jar（compile 后必须执行！）
./mvnw jar:jar -pl ui -am -DskipTests

# 3. 复制到 webapp
cp ui/target/hop-ui-2.19.0-SNAPSHOT.jar assemblies/web/target/webapp/WEB-INF/lib/

# 4. 清理 Tomcat 缓存并重启
docker exec hop-web-dev rm -rf /usr/local/tomcat/work/Catalina
docker compose -f docker-compose.dev.yml restart hop-web
```

## 验证包是否最新

```bash
# 对比时间戳：源文件 → class → jar → webapp jar
ls -la ui/src/main/java/org/apache/hop/ui/hopgui/assistant/LlmAssistantDialog.java
ls -la ui/target/classes/org/apache/hop/ui/hopgui/assistant/LlmAssistantDialog.class
ls -la ui/target/hop-ui-2.19.0-SNAPSHOT.jar
ls -la assemblies/web/target/webapp/WEB-INF/lib/hop-ui-2.19.0-SNAPSHOT.jar
```

四个时间戳应依次递增，否则说明某步没更新。

## 常见坑

### 1. `mvn compile` ≠ 重新打包 jar
- `compile` 只更新 `target/classes/` 下的 class 文件
- `jar:jar` 才会重新生成 `target/hop-ui-*.jar`
- **结论**：改代码后必须跑 `jar:jar` 或 `package`

### 2. RAP (web) 环境 SWT 方法缺失
- `Text.showSelection()` — RAP 没有，web 环境下调用直接 500
- 处理方式：`if (!EnvironmentUtils.getInstance().isWeb()) { ... }`
- 其他可能缺失的方法：凡是 SWT 桌面端特有的都要小心

### 3. RAP 后台线程 UI 更新不刷新
- 后台线程 `asyncExec` 更新 UI 后，浏览器端看不到
- 需要 `ClientPushSession.start()` 主动推送到客户端
- 注意：`start()` 必须在 `asyncExec` 回调内部调用

### 4. Tomcat 缓存
- `work/Catalina/` 目录会缓存编译后的 JSP 和类
- 修改 jar 后建议删除缓存再重启

### 5. Docker 卷挂载
- `docker-compose.dev.yml` 中 webapp 是 volume 挂载
- 改宿主机 `assemblies/web/target/webapp/` 目录 = 改容器内文件
- 但需要重启 Tomcat 才会重新加载类

## 完整 build-web（首次或大改后用）

```bash
./dev-scripts/build-web.sh    # 约 25 分钟
./dev-scripts/start-web-dev.sh
```

## 日常快速迭代（改少量代码）

```bash
./mvnw compile -pl ui -am -DskipTests \
  && ./mvnw jar:jar -pl ui -am -DskipTests \
  && cp ui/target/hop-ui-2.19.0-SNAPSHOT.jar assemblies/web/target/webapp/WEB-INF/lib/ \
  && docker exec hop-web-dev rm -rf /usr/local/tomcat/work/Catalina \
  && docker compose -f docker-compose.dev.yml restart hop-web
```

约 20 秒完成。
