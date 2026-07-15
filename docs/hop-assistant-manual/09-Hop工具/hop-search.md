# Hop Search

Hop Search 是一个命令行工具，用于在 Hop 或 Hop 项目中搜索 metadata。

&nbsp;
.Usage

=====

## 用法

```bash
Usage: <main class> [-hilx] [-e=<environmentOption>] [-j=<projectOption>]
                    <searchString>
      <searchString>         The string to search for
  -e, --environment=<environmentOption>
                             The name of the lifecycle environment to use
  -h, --help                 Displays this help message and quits
  -i, --case-insensitive     Perform a case insensitive search
  -j, --project=<projectOption>
                             The name of the project to use
  -l, --print-locations      Print which locations are being looked at
  -v, --version              Print version information and exit 
  -x, --regular-expression   The specified search string is a regular expression
```

## 选项

| 选项 | 描述 |
|---|---|
| <searchString> | 要搜索的字符串 |
| -e | 要使用的生命周期环境名称 |
| -h | 显示此帮助消息并退出 |
| -i | 执行不区分大小写的搜索 |
| -j | 要使用的项目名称 |
| -l | 打印正在查看的位置 |
| -v | 打印版本信息并退出 |
| -x | 指定的搜索字符串是一个正则表达式 |

=====

&nbsp;
## 示例

示例命令：在 `hop-samples` 项目中搜索字符串 `json`（不区分大小写）。

====
Windows::
--
```shell
hop-search.bat -j samples -i switch-case
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
-DHOP_PLATFORM_RUNTIME=Search -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.config.HopConfig -j samples -i switch-case
===[Starting HopConfig]=========================================================
Enabling project 'samples'
Searching in project : samples
Searching for [switch-case]
Case sensitive? false
Regular expression? false
Searching in location : Project samples
```-------------------------------------------------------------------------------
file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : null(switch-case) : matching property value: switch-case
file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : matching property value: switch-case
file:///C:/<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : pipeline transform property : filePrefix
file:///C:/<YOUR_PATH>/hop/config/projects/samples/transforms/switch-case-basic.hpl : null(switch-case-basic) : matching property value: switch-case-basic
```
--

Linux, macOS::
--

```
./hop-search.sh -j samples -i switch-case
```
预期输出：

```shell
Enabling project 'samples'
Searching in project : samples
Searching for [switch-case]
Case sensitive? false
Regular expression? false
Searching in location : Project samples
```-------------------------------------------------------------------------------
file://<YOUR_PATH>/hop/config/projects/samples/transforms/switch-case-basic.hpl : null(switch-case-basic) : matching property value: switch-case-basic
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : null(switch-case) : matching property value: switch-case
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : matching property value: switch-case
file://<YOUR_PATH>/hop/config/projects/samples/beam/pipelines/switch-case.hpl : switch-case(switch-case) : pipeline transform property : filePrefix
```
--
====
