# 高级项目和环境管理

## 配置文件

### 全局项目配置

定义项目的两个主要要素是其名称和主文件夹。
项目和环境因此定义在中心 Hop 配置文件 `hop-config.json` 中。
默认情况下，此文件位于 Hop 客户端分发的 `config/` 文件夹中。
你可以通过在系统中设置 `HOP_CONFIG_FOLDER` 环境变量来更改该文件夹。

&nbsp;
.projectsConfig

=====

在 `hop-config.json` 中，你会找到一个 `"projectsConfig"` 部分。
默认情况下它包含以下内容：

```json
{
  "projectsConfig" : {
    "enabled" : true,
    "projectMandatory" : true,
    "environmentMandatory" : false,
    "defaultProject" : "default",
    "defaultEnvironment" : null,
    "standardParentProject" : "default",
    "standardProjectsFolder" : null,
    "projectConfigurations" : [ {
      "projectName" : "default",
      "projectHome" : "config/projects/default",
      "configFilename" : "project-config.json"
    }, {
      "projectName" : "samples",
      "projectHome" : "config/projects/samples",
      "configFilename" : "project-config.json"
    } ],
    "lifecycleEnvironments" : [ ],
    "projectLifecycles" : [ ]
  }
}
```

如你所见，标准 Hop 客户端分发定义了两个项目：default 和 samples。

=====

&nbsp;
### 项目配置

每个项目都有额外的 metadata 和设置存储在名为 ```project-config.json``` 的项目配置文件中。对于 samples 项目，该文件为 ```config/projects/samples/project-config.json```。

&nbsp;
.project-config.json

=====

让我们看一下：

```json
{
  "metadataBaseFolder" : "${PROJECT_HOME}/metadata",
  "unitTestsBasePath" : "${PROJECT_HOME}",
  "dataSetsCsvFolder" : "${PROJECT_HOME}/datasets",
  "enforcingExecutionInHome" : true,
  "parentProjectName" : "default",
  "config" : {
    "variables" : [ ]
  }
}
```

=====

&nbsp;
#### 变量

你也可以在项目级别定义变量。
这使得引用输入和输出文件夹等不敏感的内容变得方便，这些内容可以安全地提交到版本控制中。

#### 父项目

从项目配置文件（`parentProjectName`）可以看出，一个项目可以有一个父项目，它将继承父项目的所有 metadata 对象以及其中定义的所有变量。

### 环境配置

Hop 环境及其主文件夹存储在 Hop 配置文件 'hop-config.json' 中。
该文件默认位于 Hop 安装的 *config* 文件夹中。
系统属性 `HOP_CONFIG_FOLDER` 也可用于指向不同的文件夹。

&nbsp;
.environmentConfig

=====

{
 "environmentConfig" : {
  "enabled" : true,
  "openingLastEnvironmentAtStartup" : true,
  "environmentConfigFilename" : "environment.json",
  "environmentFolders" : {
   "Project 1 - DEV" : "/projects/one/dev/",
   "Project 1 - UAT" : "/projects/one/uat/",
   "Project 1 - PRD" : "/projects/one/prd/",
   "Project 2 - DEV" : "/projects/two/dev/",
   "Project 2 - UAT" : "/projects/two/uat/",
   "Project 2 - PRD" : "/projects/two/prd/",
  }
}

=====

&nbsp;
## 命令行项目配置

除了 Hop GUI 和配置文件之外，项目和环境的所有方面和操作都可以通过 Hop Conf 命令行工具来管理。

### 命令行配置

```hop-conf``` 脚本提供了许多选项来编辑环境定义。

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
请运行不带选项的 hop-conf 以查看所有可能性。

#### 删除环境

以下示例从 Hop 配置文件中删除一个环境：

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

有多种选项可以配置 ```Projects``` plugin 本身的行为。
在 Hop 配置文件 ```hop-config.json``` 中，我们可以找到以下选项：

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
| 这将防止任何人在未指定项目的情况下使用 hop-run |  |  |
| ```--project-mandatory``` |  |  |
| environmentMandatory |  |  |
| 这将防止任何人在未指定环境的情况下使用 hop-run |  |  |
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
