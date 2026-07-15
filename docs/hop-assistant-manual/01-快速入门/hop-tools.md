# Hop 工具

Hop 包含一系列工具，这些工具针对 Hop 开发者和管理员需要执行的各种任务进行了优化。

## Hop GUI

Hop GUI 是 Hop 数据开发者创建、测试、运行和管理 Workflow 及 Pipeline 生命周期的可视化 IDE。
除了开发和生命周期管理功能外，Hop GUI 还包含用于管理项目和环境的工具和透视图，用于搜索和管理 metadata，用于管理和版本控制大量文件，以及用于在 Neo4j 图中浏览日志。

> **💡 提示:** Hop GUI 的设计是与平台无关的。
[Hop Web](../10-HopGUI/hop-web.md) 是 Hop GUI 的一个版本，可以在浏览器和移动设备上运行。

以下命令可针对你的操作系统启动 Hop GUI。

&nbsp;

====
Windows::
--
```bash
hop-gui.bat
```
--

Linux, macOS::
--
```bash
./hop-gui.sh
```
--
====

预期输出：Hop GUI 启动。

## Hop Conf

Hop Conf 是一个命令行工具，用于管理 Hop 配置的各个方面：项目、环境、云配置等。
下面示例中的 `hop-conf` 命令会列出你可用的项目。

&nbsp;

====

Windows::
--
```bash
hop-conf.bat -pl
```

预期输出：
```shell
C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\* -Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig  -pl
===[Starting HopConfig]=========================================================
Projects:
  default : config/projects/default
    Configuration file: file:\C:\<YOUR_PATH>\hop\config\projects\default\project-config.json
  samples : config/projects/samples
    Parent project: default
    Configuration file: file:\C:\<YOUR_PATH>\hop\config\projects\samples\project-config.json
```

--

Linux, macOS::
--
```bash
./hop-conf.sh -pl
```

预期输出：

```bash
Projects:
  default : config/projects/default
    Configuration file: file:<YOUR_PATH>/hop/config/projects/default/project-config.json
  samples : config/projects/samples
    Parent project: default
    Configuration file: file:<YOUR_PATH>/hop/config/projects/samples/project-config.json
```
--

====

请查看 [Hop 工具 -> Hop Conf](../09-Hop工具/hop-conf.md) 文档以获取更多信息。

## Hop Encrypt

Hop Encrypt 是一个命令行工具，用于混淆或加密明文密码，以便在 XML、密码或 metadata 文件中使用。
请务必同时复制密码加密前缀，以标明密码的混淆性质。
Hop 随后能够区分普通明文密码和混淆密码。

&nbsp;
下面示例打印出加密后的 Hop 密码 `mypassword`。

&nbsp;

====

Windows::
--
```bash
hop-encrypt.bat -hop mypassword
```

预期输出：

```shell
===[Environment Settings - hop-encrypt.bat]====================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=GUI
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\* -Djava.library.path=lib\core;lib\beam -Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=GUI org.apache.hop.core.encryption.Encr  -hop mypassword
===[Starting HopEncrypt]=========================================================
Encrypted 2be98afc86aa79f9dbb18bd63c99dbdde
```
--

Linux, macOS::
--
```bash
./hop-encrypt.sh -hop mypassword
```

预期输出：

```
Encrypted 2be98afc86aa79f9dbb18bd63c99dbdde
```
--

====

请查看 [Hop 工具 -> Hop Encrypt](../09-Hop工具/hop-encrypt.md) 文档以获取更多信息。

## Hop Run

Hop Run 是一个命令行工具，用于运行 Workflow 和 Pipeline，可以选择（列出或）指定项目、环境、属性和运行配置。
下面示例在 samples 项目（`-j`）中，使用[本地原生 Pipeline 运行配置](../07-管道/native-local-pipeline-engine.md)（`-r`）运行 `switch-case-basic.hpl` Pipeline。

&nbsp;

====

Windows::
--
```shell
hop-run.bat -r local -j samples -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-run.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS="-Xmx2048m" -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Run -DHOP_AUTO_CREATE_CONFIG=Y
Consolidated parameters to pass to HopRun are
-r local -j samples -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
Command to start HopRun will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam "-Xmx2048m" -DHOP_AUDIT_FOLDER=.\audit
-DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Run -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.run.HopRun
-r local -j samples -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
===[Starting HopRun]=========================================================
2022/12/16 07:07:47 - HopRun - Enabling project 'samples'
2022/12/16 07:07:47 - HopRun - Starting pipeline: config/projects/samples/transforms/switch-case-basic.hpl
2022/12/16 07:07:47 - switch-case-basic - Executing this pipeline using the Local Pipeline Engine with run configuration 'local'
2022/12/16 07:07:47 - switch-case-basic - Execution started for pipeline [switch-case-basic]
2022/12/16 07:07:47 - Test Data.0 - Finished processing (I=0, O=0, R=0, W=5, U=0, E=0)
2022/12/16 07:07:47 - Switch id.0 - Finished processing (I=0, O=0, R=5, W=5, U=0, E=0)
2022/12/16 07:07:47 - Output 2.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 07:07:47 - Output 1.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 07:07:47 - Output 4.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 07:07:47 - Output default.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 07:07:47 - Output 3.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 07:07:47 - switch-case-basic - Pipeline duration : 0.435 seconds [  0.435" ]
```

--

Linux, macOS::
--
```shell
./hop-run.sh -r local -j samples -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
```

预期输出：

```shell
2022/12/16 06:59:03 - HopRun - Enabling project 'samples'
2022/12/16 06:59:03 - HopRun - Relative path filename specified: config/projects/samples//transforms/switch-case-basic.hpl
2022/12/16 06:59:03 - HopRun - Starting pipeline: config/projects/samples//transforms/switch-case-basic.hpl
2022/12/16 06:59:03 - switch-case-basic - Executing this pipeline using the Local Pipeline Engine with run configuration 'local'
2022/12/16 06:59:03 - switch-case-basic - Execution started for pipeline [switch-case-basic]
2022/12/16 06:59:04 - Test Data.0 - Finished processing (I=0, O=0, R=0, W=5, U=0, E=0)
2022/12/16 06:59:04 - Switch id.0 - Finished processing (I=0, O=0, R=5, W=5, U=0, E=0)
2022/12/16 06:59:04 - Output default.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 06:59:04 - Output 1.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 06:59:04 - Output 4.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 06:59:04 - Output 3.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 06:59:04 - Output 2.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/16 06:59:04 - switch-case-basic - Pipeline duration : 0.668 seconds [  0.668" ]
```
--

====

[Hop 工具 -> Hop Run](../index.md)

## Hop Search

Hop Search 是一个命令行工具，用于搜索特定项目或环境中所有可用的 metadata。
下面示例在 samples 项目（`-j`）中搜索 `switch-case`。

&nbsp;

====

Windows::
--

```
hop-search.bat -j samples -x switch-case
```
预期输出：
```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-search.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Search
-DHOP_AUTO_CREATE_CONFIG=Y
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=Search -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig  -j samples -x switch-case
===[Starting HopConfig]=========================================================
Enabling project 'samples'
Searching in project : samples
Searching for [switch-case]  Case sensitive? true  Regular expression? true
Searching in location : Project samples
```-------------------------------------------------------------------------------
file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : null(switch-case) : matching property value: switch-case    file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : matching property value: switch-case
file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : pipeline transform property : filePrefix
```
--

Linux, macOS::
--
```shell
./hop-search.sh -j samples -x switch-case
```

预期输出：
```shell
Enabling project 'samples'
Searching in project : samples
Searching for [switch-case]  Case sensitive? true  Regular expression? true
Searching in location : Project samples
```-------------------------------------------------------------------------------
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : null(switch-case) : matching property value: switch-case
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : matching property value: switch-case
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : pipeline transform property : filePrefix
```
--

====

请查看 [Hop 工具 -> Hop Search](../09-Hop工具/hop-search.md) 文档以获取更多信息。

## Hop Server

Hop Server 是一个 Web 服务接口，用于管理和运行 Workflow 和 Pipeline。
下面示例命令在你的机器 `localhost` 的 `8081` 端口上启动最基本的服务器。

&nbsp;

====
Windows::
--
```bash
hop-server.bat localhost 8081
```

预期输出：

```
C:\<YOUR_PATH\hop>echo off
===[Environment Settings - hop-server.bat]====================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=GUI -DHOP_AUTO_CREATE_CONFIG=Y
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=GUI -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.www.HopServer  localhost 8081
===[Starting HopServer]=========================================================
2022/12/16 07:33:44 - HopServer - Enabling project 'default'
2022/12/16 07:33:44 - HopServer - Installing timer to purge stale objects after 1440 minutes.
2022/12/16 07:33:44 - HopServer - Created listener for webserver @ address : localhost:8081
```
你的 Hop Server 可通过 \http://localhost:8081 访问，使用默认用户名 `cluster`、密码 `cluster` 登录。

按 `CTRL-C` 停止服务器。

--

Linux, macOS::
--
```bash
./hop-server.sh localhost 8081
```

预期输出：

```shell
2022/12/16 07:20:19 - HopServer - Enabling project 'default'
2022/12/16 07:20:19 - HopServer - Installing timer to purge stale objects after 1440 minutes.
2022/12/16 07:20:19 - HopServer - Created listener for webserver @ address : localhost:8081
```

你的 Hop Server 可通过 \http://localhost:8081 访问，使用默认用户名 `cluster`、密码 `cluster` 登录。

按 `CTRL-C` 停止服务器。

--

====

了解更多关于 [Hop Server](../index.md) 的信息

## Hop Import

Hop Import 是一个命令行工具，用于将 PDI/Kettle 的 job 和 transformation 导入 Qi Hop。
Hop Import 不仅仅是将 job 转换为 Workflow、将 transformation 转换为 Pipeline：数据库连接会被转换为 Qi Hop 关系型数据库连接，变量会被解析并导入，所有内容都会打包成一个完整的 Qi Hop 项目。
&nbsp;
下面示例命令打印 `hop-import` 的帮助信息。请查看 [tech-manual::hop-vs-kettle/import-kettle-projects.adoc](https://hop.apache.org/tech-manual/latest/hop-vs-kettle/import-kettle-projects) 文档以获取更多信息。

&nbsp;

====

Windows::
--

```
hop-import.bat
```
--

Linux, macOS::
--

```
./hop-import.sh
```
--

====

了解更多请查看 [技术手册 -> Hop vs Kettle -> 导入 Kettle 项目](https://hop.apache.org/tech-manual/latest/hop-vs-kettle/import-kettle-projects)
