### 命令行配置

`hop-conf` 脚本提供了许多选项来编辑环境定义。

#### 创建环境

====
Windows::
--
```bash
hop-conf.bat --environment-create \
             --environment hop2 \
             --environment-project hop2
             --environment-purpose=Development \
             --environment-config-files="C:\<YOUR_ENV_FILE_PATH>\env-variables.json"
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-conf.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Conf
-DHOP_AUTO_CREATE_CONFIG=Y
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig  --environment-create
--environment hop2 --environment-project hop2 --environment-purpose Development
--environment-config-files "C:\<YOUR_ENV_FILE_PATH\env-variables.json"
===[Starting HopConfig]=========================================================
Creating environment 'hop2'
Environment 'hop2' was created in Hop configuration file C:\<YOUR_PATH>\hop\config\hop-config.json
Warning: referenced project 'hop2' doesn\'t exist
Found existing environment configuration file: C:\<YOUR_ENV_FILE_PATH>\variables.json
Purpose: Development
Project name: hop2
Config file: C:\<YOUR_ENV_FILE_PATH>\env-variables.json
```
--

Linux, macOS::
--
```bash
$ sh hop-conf.sh \
     --environment-create \
     --environment hop2 \
     --environment-project hop2 \
     --environment-purpose=Development \
     --environment-config-files=<YOUR_ENV_FILE_PATH>/env-variables.json
```

预期输出：

```shell
Creating environment 'hop2'
Environment 'hop2' was created in Hop configuration file <YOUR_PATH>/hop/config/hop-config.json
Warning: referenced project 'hop2' doesn't exist
Found existing environment configuration file: <YOUR_ENV_FILE_PATH>/env-variables.json
  hop2
    Purpose: Development
    Project name: hop2
      Config file: <YOUR_ENV_FILE_PATH>/env-variables.json
```
--
====

从日志中可以看到，创建了一个空文件用于设置变量：

```json
{ }
```

#### 在环境中设置变量

此命令向环境配置文件中添加变量：

&nbsp;

====
Windows::
--

```
hop-conf.bat --config-file "C:\<YOUR_ENV_FILE_PATH>\env-variables.json" --config-file-set-variables "DB_HOSTNAME=localhost,DB_PASSWORD=abcd"
```
预期输出：

```shell
C:\<YOUR_PATH\hop>echo off
===[Environment Settings - hop-conf.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig
--config-file "C:\<YOUR_ENV_FILE_PATH>\env-variables.json"
--config-file-set-variables "DB_HOSTNAME=localhost,DB_PASSWORD=abcd"
===[Starting HopConfig]=========================================================
Configuration file 'C:\<YOUR_ENV_FILE_PATH>/env-variables.json' was modified.
```
--

Linux, macOS::
--
```bash
./hop-conf.sh --config-file <YOUR_ENV_FILE_PATH>/env-variables.json --config-file-set-variables DB_HOSTNAME=localhost,DB_PASSWORD=abcd
```

预期输出：

```shell
Configuration file '<YOUR_ENV_FILE_PATH>/env-variables.json' was modified.
```
--
====

如果你查看文件 `env-variables.json`，你会看到变量已被添加：

```json
{
  "variables" : [ {
    "name" : "DB_HOSTNAME",
    "value" : "localhost",
    "description" : ""
  }, {
    "name" : "DB_PASSWORD",
    "value" : "abcd",
    "description" : ""
  } ]
}
```

请注意，你也可以使用 `--describe-variable` 选项为变量添加描述。
请运行不带选项的 hop-conf 来查看所有可能性。

#### 删除环境

以下示例从 Hop 配置文件中删除环境：

&nbsp;

====
Windows::
--
```shell
hop-conf.bat -ed --environment hop2
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-conf.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\* -Djava.library.path=lib\core;lib\beam -Xmx2048m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Conf -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig  -ed --environment hop2
===[Starting HopConfig]=========================================================
Lifecycle environment 'hop2' was deleted from Hop configuration file C:\<YOUR_PATH>\hop\config\hop-config.json
```

--

Linux, macOS::
--
```shell
./hop-conf.sh -ed --environment hop2
```

预期输出：

```shell
Lifecycle environment 'hop2' was deleted from Hop configuration file <YOUR_PATH>/hop/config/hop-config.json
```

--
====

### Projects Plugin 配置

有各种选项可以配置 `Projects` plugin 本身的行为。
在 Hop 配置文件 `hop-config.json` 中我们可以找到以下选项：

```json
{
    "projectMandatory" : true,
    "environmentMandatory" : false,
    "defaultProject" : "default",
    "defaultEnvironment" : null,
    "standardParentProject" : "default",
    "standardProjectsFolder" : "/home/matt/test-stuff/"
}
```

| 选项 | 描述 | hop-conf 选项 |
|---|---|---|
| projectMandatory |  |  |
| 这将阻止任何人在不指定项目的情况下使用 hop-run |  |  |
| ```--project-mandatory``` |  |  |
| environmentMandatory |  |  |
| 这将阻止任何人在不指定环境的情况下使用 hop-run |  |  |
| ```--environment-mandatory``` |  |  |
| defaultProject |  |  |
| 未指定时使用的默认项目 |  |  |
| ```--default-project``` |  |  |
| defaultEnvironment |  |  |
| 未指定时使用的默认环境 |  |  |
| ```--default-environment``` |  |  |
| standardParentProject |  |  |
| 创建新项目时建议的标准父项目 |  |  |
| ```--standard-parent-project``` |  |  |
| standardProjectsFolder |  |  |
| 在 GUI 中创建新项目时默认浏览的文件夹 |  |  |
| ```--standard-projects-folder``` |  |  |
