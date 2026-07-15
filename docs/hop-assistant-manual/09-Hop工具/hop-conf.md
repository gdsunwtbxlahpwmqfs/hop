# Hop Conf - Hop 命令行配置工具

## 用法

Hop Conf 是一个命令行工具，用于管理环境。
可以使用 *-h* 标志运行 *hop-conf.sh* 脚本（`./hop-conf.sh -h`）来显示可用选项。

&nbsp;
.Usage

=====
```bash
Usage: <main class> [-h] [-ec] [-ed] [-el] [-em] [-ey] [-pc] [-pd] [-pl] [-pm]
                    [-pn] [-py] [-aza=<account>] [-azi=<blockIncrement>]
                    [-azk=<key>] [-cfg=<configFile>]
                    [-dc=<defaultProjectConfigFile>] [-de=<defaultEnvironment>]
                    [-dp=<defaultProject>] [-dv=<describeVariable>]
                    [-e=<environmentName>] [-ep=<environmentProject>]
                    [-eu=<environmentPurpose>] [-fj=<fatJarFilename>]
                    [-gck=<serviceAccountKeyFile>] [-gdc=<credentialsFile>]
                    [-gdt=<tokensFolder>] [-p=<projectName>]
                    [-pa=<projectMetadataBaseFolder>]
                    [-pb=<projectDataSetsCsvFolder>] [-pf=<projectConfigFile>]
                    [-ph=<projectHome>] [-pp=<projectCompany>]
                    [-pr=<projectParent>] [-ps=<projectDescription>]
                    [-pt=<projectDepartment>] [-pu=<projectUnitTestsBasePath>]
                    [-px=<projectEnforceExecutionInHome>]
                    [-sj=<standardProjectsFolder>]
                    [-sp=<standardParentProject>] [-sv=<setVariable>]
                    [-xm=<metadataJsonFilename>] [-cfd=<configDescribeVariables>
                    [,<configDescribeVariables>...]]...
                    [-cfv=<configSetVariables>[,<configSetVariables>...]]...
                    [-eg=<environmentConfigFiles>[,
                    <environmentConfigFiles>...]]... [-pv=<projectVariables>[,
                    <projectVariables>...]]...
      -aza, --azure-account=<account>
                            The account to use for the Azure VFS
      -azi, --azure-block-increment=<blockIncrement>
                            The block increment size for new files on Azure,
                              multiples of 512 only.
      -azk, --azure-key=<key>
                            The key to use for the Azure VFS
      -cfd, --config-file-describe-variables=<configDescribeVariables>[,
        <configDescribeVariables>...]
                            A list of variable=description combinations separated by
                              a comma
      -cfg, --config-file=<configFile>
                            Specify the configuration JSON file to manage
      -cfv, --config-file-set-variables=<configSetVariables>[,
        <configSetVariables>...]
                            A list of variable=value combinations separated by a
                              comma
      -dc, --default-projects-folder=<defaultProjectConfigFile>
                            The standard project configuration filename proposed
                              when creating projects
      -de, --default-environment=<defaultEnvironment>
                            The name of the default environment to use when none is
                              specified
      -dp, --default-project=<defaultProject>
                            The name of the default project to use when none is
                              specified
      -dv, --describe-variable=<describeVariable>
                            Describe a variable, use format VARIABLE=Description
  -e, --environment=<environmentName>
                            The name of the lifecycle environment to manage
      -ec, --environment-create
                            Create a new project lifecycle environment. Also specify
                              its name, purpose, the project name and the
                              configuration files.
      -ed, --environment-delete
                            Delete a lifecycle environment
      -eg, --environment-config-files=<environmentConfigFiles>[,
        <environmentConfigFiles>...]
                            A list of configuration files for this lifecycle
                              environment, comma separated
      -el, --environments-list
                            List the defined lifecycle environments
      -em, --environment-modify
                            Modify a lifecycle environment
      -ep, --environment-project=<environmentProject>
                            The project for the environment
      -eu, --environment-purpose=<environmentPurpose>
                            The purpose of the environment: Development, Testing,
                              Production, CI, ...
      -ey, --environment-mandatory
                            Make it mandatory to reference an environment
      -fj, --generate-fat-jar=<fatJarFilename>
                            Specify the filename of the fat jar to generate from
                              your current software installation
      -gck, --google-cloud-service-account-key-file=<serviceAccountKeyFile>
                            Configure the path to a Google Cloud service account
                              JSON key file
      -gdc, --google-drive-credentials-file=<credentialsFile>
                            Configure the path to a Google Drive credentials JSON
                              file
      -gdt, --google-drive-tokens-folder=<tokensFolder>
                            Configure the path to a Google Drive tokens folder
  -h, --help                Displays this help message and quits.
  -p, --project=<projectName>
                            The name of the project to manage
      -pa, --project-metadata-base=<projectMetadataBaseFolder>
                            The metadata base folder (relative to home)
      -pb, --project-datasets-base=<projectDataSetsCsvFolder>
                            The data sets CSV folder (relative to home)
      -pc, --project-create Create a new project. Also specify the name and its home
      -pd, --project-delete Delete a project
      -pf, --project-config-file=<projectConfigFile>
                            The configuration file relative to the home folder. The
                              default value is project-config.json
      -ph, --project-home=<projectHome>
                            The home directory of the project
      -pl, --projects-list   List the defined projects
      -pla, --list-action-types-in-project
                             List action types used in this project
      -plm, --list-metadata-types-in-project
                             List metadata types used in this project
      -plt, --list-transform-types-in-project
                             List transform types used in this project
      -pm, --project-modify Modify a project
      -pn, --projects-enabled
                            Enable or disable the projects plugin
      -pp, --project-company=<projectCompany>
                            The company
      -pr, --project-parent=<projectParent>
                            The name of the parent project to inherit metadata and
                              variables from
      -ps, --project-description=<projectDescription>
                            The description of the project
      -pt, --project-department=<projectDepartment>
                            The department
      -pu, --project-unit-tests-base=<projectUnitTestsBasePath>
                            The unit tests base folder (relative to home)
      -pv, --project-variables=<projectVariables>[,<projectVariables>...]
                            A list of variable=value combinations separated by a
                              comma
      -px, --project-enforce-execution=<projectEnforceExecutionInHome>
                            Validate before execution that a workflow or pipeline is
                              located in the project home folder or a sub-folder
                              (true/false).
      -py, --project-mandatory
                            Make it mandatory to reference a project
      -sj, --standard-projects-folder=<standardProjectsFolder>
                            GUI: The standard projects folder proposed when creating
                              projects
      -sp, --standard-parent-project=<standardParentProject>
                            The name of the standard project to use as a parent when
                              creating new projects
      -sv, --set-variable=<setVariable>
                            Set a variable, use format VAR=Value
      -xm, --export-metadata=<metadataJsonFilename>
                            Export project metadata to a single JSON file which you
                              can specify with this option. Also specify the -p
                              option.
      -v,  --version        Print version information and exit
```

可用选项列在下面：

.Hop-conf 选项

| 简写选项 | 完整选项 | 描述 |
|---|---|---|
| -h | --help | 显示此帮助消息并退出 |
| -v | --version | 打印版本信息并退出 |
| -ec | --environment-create | 创建环境 |
| -ed | --environment-delete | 删除环境 |
| -el | --environment-list | 列出已定义的环境 |
| -em | --environment-modify | 修改环境 |
| -pc | --project-create | 创建新项目。 |
| -pd | --prject-delete | 删除项目 |
| -pl | --projects-list | 列出已定义的项目 |
| -pla | --list-action-types-in-project | 列出此项目中使用的 Action 类型 |
| -plm | --list-metadata-types-in-project | 列出此项目中使用的 metadata 类型 |
| -plt | --list-transform-types-in-project | 列出此项目中使用的 Transform 类型 |
| -pm | --project-modify | 修改项目 |
| -dv | --describe-variable=<describeVariable> | 描述变量 |
| -e | -environment=<environmentName> | 要管理的环境名称 |
| -ep | --environment-project=<environmentProject> | 环境对应的项目 |
| -eu | --environment-purpose=<environmentPurpose> | 环境的用途：Development、Testing、Production、CI 等 |
| -fj | --generate-fat-jar=<fatJarFilename> | 指定从当前软件安装生成的 fat jar 文件名 |
| -xm | --export-metadata=<metadataJsonFilename> | 将项目 metadata 导出到单个 JSON 文件，可通过此选项指定。 |
| -p | --project=<projectName> | 项目名称 |
| -pa | --project-metadata-base=<projectMetadataBaseFolder> | metadata 基础文件夹（相对于 home） |
| -pb | --project-datasets-base-base=<projectDataSetsCsvFolder> | 数据集 CSV 文件夹（相对于 home） |
| -pf | --project-config-file=<projectConfigFile> | 相对于 home 文件夹的配置文件。 |
| -ph | --project-home=<projectHome> | 项目的 home 目录 |
| -pp | --project-company=<projectCompany> | 公司 |
| -ps | --project-description=<projectDescription> | 项目的描述 |
| -pt | --project-department=<projectDepartment> | 部门 |
| -pu | --project-unit-tests-base=<projectUnitTestsBasePath> | 单元测试基础文件夹（相对于 home） |
| -px | --project-enforce-execution=<projectEnforceExecutionInHome> | 执行前验证 workflow 或 pipeline 是否位于项目 home 文件夹或子文件夹中（true/false） |
| -sv |  |  |
| --set-variable=<setVariable> |  |  |
| -cfg | --config-file=<configFile> | 指定要管理的配置 JSON 文件 |
| -cfd | --config-file-describe-variables=<configDescribeVariables>[,<configDescribeVariables>...] | 以逗号分隔的 variable=description 组合列表 |
| -cfv | --config-file-set-variables=<configSetVariables> ,<configSetVariables>...] | 以逗号分隔的 variable=value 组合列表 |
| -eg | --environment-config-files=<environmentConfigFiles>[, <environmentConfigFiles>...] | 此生命周期环境的配置文件列表，以逗号分隔 |
| -pv | --project-variables=<projectVariables>[,<projectVariables>...] | 以逗号分隔的 variable=value 组合列表 |

=====

&nbsp;
### 项目变量示例
以下是该命令参数解析方式的示例列表

#### 正常用法
```
--project-variables=key1=value1,key2=value2
```
结果：

| 键 | 值 |
|---|---|
| key1 | value1 |
| key2 | value2 |
#### 值中包含空格的用法
```
--project-variables=key1="This value contains spaces",key2=value2
```
结果：

| 键 | 值 |
|---|---|
| key1 | This value contains spaces |
| key2 | value2 |

#### 值中包含逗号的用法
```
--project-variables=key1=\"value1,value2\"
```
结果：

| 键 | 值 |
|---|---|
| key1 | value1,value2 |
#### 在值中强制使用引号
```
--project-variables=key1="\"\"String with spaces\"\""
```
结果：

| 键 | 值 |
|---|---|
| key1 | "String with spaces" |

## 项目用法和配置

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

## 运行 Workflow 和 Pipeline

你可以在执行 pipeline 或 workflow 时指定环境或项目。
这样做可以自动配置 metadata 和变量，无需太多繁琐操作。

最简单的示例是通过执行 Apache Beam 示例中的 "complex" pipeline 来展示：

&nbsp;

====
Windows::
--

```shell
hop-run.bat --project samples --file 'beam/pipelines/complex.hpl' --runconfig Direct
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-run.bat]===================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS="-Xmx2048m" -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=Run -DHOP_AUTO_CREATE_CONFIG=Y
Consolidated parameters to pass to HopRun are
--project samples --file beam/pipelines/complex.hpl --runconfig Direct
Command to start HopRun will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam "-Xmx2048m" -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=Run -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.run.HopRun  --project samples
--file beam/pipelines/complex.hpl --runconfig Direct
===[Starting HopRun]=========================================================
2022/12/16 14:23:10 - HopRun - Enabling project 'samples'
2022/12/16 14:23:10 - HopRun - Relative path filename specified: config/projects/samples/beam/pipelines/complex.hpl
2022/12/16 14:23:10 - HopRun - Starting pipeline: config/projects/samples/beam/pipelines/complex.hpl
2022/12/16 14:23:21 - General - Created Apache Beam pipeline with name 'complex'
2022/12/16 14:23:21 - General - Handled transform (INPUT) : Customer data
2022/12/16 14:23:21 - General - Handled transform (INPUT) : State data
2022/12/16 14:23:21 - General - Handled Group By (TRANSFORM) : countPerState, gets data from 1 previous transform(s)
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : uppercase state, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Handled Merge Join (TRANSFORM) : Merge join
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Lookup count per state, gets data from 1 previous transform(s), targets=0, infos=1
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : name<n, gets data from 1 previous transform(s), targets=2, infos=0
2022/12/16 14:23:21 - General - Transform Label: N-Z reading from previous transform targeting this one using : name<n - TARGET - Label: N-Z
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Label: N-Z, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Transform Label: A-M reading from previous transform targeting this one using : name<n - TARGET - Label: A-M
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Label: A-M, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Switch / case, gets data from 2 previous transform(s), targets=4, infos=0
2022/12/16 14:23:21 - General - Transform CA reading from previous transform targeting this one using : Switch / case - TARGET - CA
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : CA, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Transform NY reading from previous transform targeting this one using : Switch / case - TARGET - NY
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : NY, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Transform FL reading from previous transform targeting this one using : Switch / case - TARGET - FL
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : FL, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Transform Default reading from previous transform targeting this one using : Switch / case - TARGET - Default
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Default, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Handled generic transform (TRANSFORM) : Collect, gets data from 4 previous transform(s), targets=0, infos=0
2022/12/16 14:23:21 - General - Handled transform (OUTPUT) : complex, gets data from Collect
2022/12/16 14:23:21 - General - Executing this pipeline using the Beam Pipeline Engine with run configuration 'Direct'  ----
```
--

Linux, macOS::
--
```bash
./sh hop-run.sh --project samples --file 'beam/pipelines/complex.hpl' --runconfig Direct
```

预期输出：

```shell
2022/12/16 14:27:37 - HopRun - Enabling project 'samples'
2022/12/16 14:27:37 - HopRun - Relative path filename specified: config/projects/samples/beam/pipelines/complex.hpl
2022/12/16 14:27:37 - HopRun - Starting pipeline: config/projects/samples/beam/pipelines/complex.hpl
2022/12/16 14:27:41 - General - Created Apache Beam pipeline with name 'complex'
2022/12/16 14:27:41 - General - Handled transform (INPUT) : Customer data
2022/12/16 14:27:41 - General - Handled transform (INPUT) : State data
2022/12/16 14:27:41 - General - Handled Group By (TRANSFORM) : countPerState, gets data from 1 previous transform(s)
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : uppercase state, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:41 - General - Handled Merge Join (TRANSFORM) : Merge join
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : Lookup count per state, gets data from 1 previous transform(s), targets=0, infos=1
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : name<n, gets data from 1 previous transform(s), targets=2, infos=0
2022/12/16 14:27:41 - General - Transform Label: N-Z reading from previous transform targeting this one using : name<n - TARGET - Label: N-Z
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : Label: N-Z, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:41 - General - Transform Label: A-M reading from previous transform targeting this one using : name<n - TARGET - Label: A-M
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : Label: A-M, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:41 - General - Handled generic transform (TRANSFORM) : Switch / case, gets data from 2 previous transform(s), targets=4, infos=0
2022/12/16 14:27:41 - General - Transform CA reading from previous transform targeting this one using : Switch / case - TARGET - CA
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : CA, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : NY, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : FL, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : Default, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : Collect, gets data from 4 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Handled transform (OUTPUT) : complex, gets data from Collect
2022/12/16 14:27:42 - General - Executing this pipeline using the Beam Pipeline Engine with run configuration 'Direct'
```
--
====

执行 Apache Beam pipeline 需要大量信息和 metadata。
让我们深入了解一些有趣的信息：

- 通过引用 `samples` 项目，Hop 知道项目所在位置（`config/projects/samples`）
- 由于我们知道项目的位置，我们可以用相对路径指定 pipeline 和 workflow
- 项目知道其 metadata 存储在哪里（`config/projects/samples/metadata`），因此它知道在哪里找到 `Direct` pipeline 运行配置（`config/projects/samples/metadata/pipeline-run-configuration/Direct.json`）
- 此运行配置定义了自己的 pipeline engine 特定变量，在本例中是输出文件夹：`DATA_OUTPUT={openvar}PROJECT_HOME{closevar}/beam/output/`
- 示例的输出因此被写入 `config/projects/samples/beam/output`

要引用环境，你可以使用 `-e` 或 `--environment` 执行。
唯一的区别是执行时会设置一些额外的环境变量。

## 云存储配置

Hop Conf 可用于通过 [VFS](vfs.md) 配置你的 AWS、Azure 和 Google Cloud（Cloud Storage 和 Drive）账户与 Hop 的连接

### Amazon Web Services S3

不适用

### Azure

设置账户、新文件的块增量大小和你的 Azure 密钥

```
      -aza, --azure-account=<account>
                            The account to use for the Azure VFS
      -azi, --azure-block-increment=<blockIncrement>
                            The block increment size for new files on Azure,
                              multiples of 512 only.
      -azk, --azure-key=<key>
                            The key to use for the Azure VFS
```
### Google

#### Google Cloud Storage

设置你的 Google Cloud 服务账户 JSON 密钥文件路径

```
      -gck, --google-cloud-service-account-key-file=<serviceAccountKeyFile>
                            Configure the path to a Google Cloud service account JSON key file
```
#### Google Drive

设置你的 Google Drive 凭证 JSON 文件或 Google Drive tokens 文件夹路径。

```
      -gdc, --google-drive-credentials-file=<credentialsFile>
                            Configure the path to a Google Drive credentials JSON
                              file
      -gdt, --google-drive-tokens-folder=<tokensFolder>
                            Configure the path to a Google Drive tokens folder
```
