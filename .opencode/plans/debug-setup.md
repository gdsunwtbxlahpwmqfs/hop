# Apache Hop 可调试架构 — Desktop + CLI 方案

## 需创建的 5 个文件

### 1. `.vscode/settings.json`

```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "java.compile.nullAnalysis.mode": "disabled",
  "java.jdt.ls.vmargs": "-Xmx4G",
  "maven.executable.path": "${workspaceFolder}/mvnw",
  "maven.terminal.customEnv": [
    { "environmentVariable": "MAVEN_OPTS", "value": "-Xmx2G" }
  ],
  "files.exclude": {
    "**/target/**": true,
    "**/.classpath": true,
    "**/.project": true,
    "**/.settings": true
  }
}
```

### 2. `.vscode/tasks.json`

```json
{
  "version": "2.0.0",
  "tasks": [
    {
      "label": "Build All (Fast)",
      "type": "shell",
      "command": "./mvnw install -Dfast-build -DskipTests -q",
      "group": "build",
      "problemMatcher": ["$javac"]
    },
    {
      "label": "Build Desktop Only",
      "type": "shell",
      "command": "./mvnw compile -pl ui,rcp/app -am -Dfast-build -q",
      "group": "build",
      "problemMatcher": ["$javac"]
    },
    {
      "label": "Build Module",
      "type": "shell",
      "command": "./mvnw compile -pl ${input:module} -am -Dfast-build -q",
      "group": "build",
      "problemMatcher": ["$javac"]
    }
  ],
  "inputs": [
    {
      "type": "promptString",
      "id": "module",
      "description": "Maven module to build (e.g. ui)",
      "default": "ui"
    }
  ]
}
```

### 3. `.vscode/launch.json`

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Desktop GUI (HopGui)",
      "request": "launch",
      "mainClass": "org.apache.hop.ui.hopgui.HopGui",
      "projectName": "hop-ui",
      "preLaunchTask": "Build Desktop Only",
      "console": "internalConsole",
      "args": "",
      "vmArgs": "-Xshare:off -Duser.timezone=UTC -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US --add-opens java.xml/jdk.xml.internal=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/sun.nio.cs=ALL-UNNAMED --add-opens java.base/sun.security.action=ALL-UNNAMED --add-opens java.base/sun.util.calendar=ALL-UNNAMED --add-opens java.security.jgss/sun.security.krb5=ALL-UNNAMED --add-exports java.base/sun.nio.ch=ALL-UNNAMED"
    },
    {
      "type": "java",
      "name": "CLI (Hop)",
      "request": "launch",
      "mainClass": "org.apache.hop.hop.Hop",
      "projectName": "hop-engine",
      "preLaunchTask": "Build All (Fast)",
      "console": "integratedTerminal",
      "args": "",
      "vmArgs": "-Duser.timezone=UTC -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US --add-opens java.xml/jdk.xml.internal=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/sun.nio.cs=ALL-UNNAMED --add-opens java.base/sun.security.action=ALL-UNNAMED --add-exports java.base/sun.nio.ch=ALL-UNNAMED"
    },
    {
      "type": "java",
      "name": "Run Pipeline (HopRun)",
      "request": "launch",
      "mainClass": "org.apache.hop.run.HopRun",
      "projectName": "hop-engine",
      "preLaunchTask": "Build All (Fast)",
      "console": "integratedTerminal",
      "args": "",
      "vmArgs": "-Duser.timezone=UTC -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-exports java.base/sun.nio.ch=ALL-UNNAMED"
    },
    {
      "type": "java",
      "name": "HopServer",
      "request": "launch",
      "mainClass": "org.apache.hop.www.HopServer",
      "projectName": "hop-engine",
      "preLaunchTask": "Build All (Fast)",
      "console": "integratedTerminal",
      "args": "",
      "vmArgs": "-Duser.timezone=UTC -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.net=ALL-UNNAMED --add-opens java.base/java.nio=ALL-UNNAMED --add-exports java.base/sun.nio.ch=ALL-UNNAMED"
    }
  ]
}
```

### 4. `ui/src/main/resources/log4j2.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %c{1} - %msg%n"/>
    </Console>
  </Appenders>
  <Loggers>
    <Root level="info"/>
    <Logger name="org.apache.hop" level="debug"/>
    <Logger name="org.eclipse" level="warn"/>
  </Loggers>
</Configuration>
```

### 5. `dev-scripts/build-quick.sh`

```bash
#!/bin/bash
set -e
cd "$(dirname "$0")/.."
./mvnw install -Dfast-build -DskipTests "$@"
```

创建后执行 `chmod +x dev-scripts/build-quick.sh`

---

## 使用方式

```bash
# 终端快速编译
./dev-scripts/build-quick.sh

# VS Code 调试桌面 GUI
# F5 → 选择 "Desktop GUI (HopGui)"
```

## 余下待做

- Web GUI (RAP) 调试 — 需 Docker Compose + JPDA
- REST API 调试 — 同 Web，共享 Tomcat 容器
