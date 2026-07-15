# Hop Run

Hop Run 是一个命令行工具，用于运行你的 workflow 和 pipeline。

Hop Run 可以通过 Hop 安装目录中相应名称的脚本启动，在 Mac 和 Linux 上是 `hop-run.sh`，在 Windows 上是 `hop-run.bat`。

除了启动脚本之外，所有选项都是跨平台的；hop-run 在任何操作系统上的工作方式没有区别。

## Hop Run 选项

你可以通过在不带任何选项的情况下运行该命令来显示 Hop Run 的选项。

在 Windows 上运行 `hop-run.bat`。在 Mac 和 Linux 上运行 `hop-run.sh`。

你将在类似于下方的输出中看到选项列表：

&nbsp;
.Usage

=====

.Output of help
```bash
Usage: <main class> [-ho] [-e=<environmentOption>] [-f=<filename>]
                    [-j=<projectOption>] [-l=<level>]
                    [-r=<runConfigurationName>] [-ps=<parametersSeparator] [-p=<parameters>[,
                    <parameters>...]]... [-s=<systemProperties>[,
                    <systemProperties>...]]...
  -a, --startaction=<startActionName>
                          The name of the action where to start a workflow
  -au, --allow-unsupported
                          Allow running pipelines or workflows that contain
                          transforms or actions marked UNSUPPORTED on the
                          selected engine. Strict by default; this flag is the
                          CLI equivalent of the GUI "Run anyway" override.
  -e, --environment=<environmentOption>
                          The name of the lifecycle environment to use
  -f, --file=<filename>   The filename of the workflow or pipeline to run
  -h, --help              Displays this help message and quits
  -j, --project=<projectOption>
                          The name of the project to use
  -l, --level=<level>     The debug level, one of NOTHING, ERROR, MINIMAL, BASIC, DETAILED, DEBUG, ROWLEVEL
  -l, --level=<level>     The debug level, one of NOTHING, ERROR, MINIMAL, BASIC, DETAILED, DEBUG, ROWLEVEL
  -lf, --logfile=<logfile-name> The complete filename where hop-run will write the Hop console log
  -m, --metadata-export=<metadataExportFile>
                          A file containing exported metadata in JSON format
  -o, --printoptions      Print the used options
  -p, --parameters=<parameters>[,<parameters>...]
                          A list of PARAMETER=VALUE pairs
  -ps, --parameters-separator=<parametersSeparator>
                          A character to be used as separator for our list of PARAMETER=VALUE pairs (default is ",").
  -r, --runconfig=<runConfigurationName>
                          The name of the Run Configuration to use
  -s, --system-properties=<systemProperties>[,<systemProperties>...]
                          A comma separated list of KEY=VALUE pairs
  -v, --version           Print version information and exit
```

可用选项的详细信息列在下表中：

| 简写 | 完整选项 | 描述 |
|---|---|---|
| ```-e``` |  |  |
| ```--environment``` |  |  |
| 要使用的环境名称。 |  |  |
| ```-a``` |  |  |
| ```--startaction``` |  |  |
| 开始执行 workflow 的 Action 名称 |  |  |
| ```-au``` |  |  |
| ```--allow-unsupported``` |  |  |
| ```-f``` |  |  |
| ```--file``` |  |  |
| 要运行的 workflow 或 pipeline 的文件名 |  |  |
| ```-h``` |  |  |
| ```--help``` |  |  |
| 显示此帮助消息并退出。 |  |  |
| ```-j``` |  |  |
| ```--project``` |  |  |
| 运行 pipeline 或 workflow 时要使用的项目名称 |  |  |
| ```-l``` |  |  |
| ```--level``` |  |  |
| 调试级别，可选值：NOTHING, ERROR, MINIMAL, BASIC, DETAILED, DEBUG, ROWLEVEL |  |  |
| ```-lf``` |  |  |
| ```--logfile``` |  |  |
| hop-run 将写入 Hop 控制台日志的完整文件名 |  |  |
| ```-m``` |  |  |
| ```--metadata-export``` |  |  |
| 包含 JSON 格式导出 metadata 的文件。另请参见 [Hop Conf](hop-conf.md) 中的 metadata 导出选项 |  |  |
| ```-o``` |  |  |
| ```--printoptions``` |  |  |
| 打印使用的选项 |  |  |
| ```-p``` |  |  |
| ```--parameters``` |  |  |
| 以逗号分隔的 PARAMETER=VALUE 键值对列表 |  |  |
| ```-ps``` |  |  |
| ```--parameters-separator``` |  |  |
| 用作 PARAMETER=VALUE 键值对列表分隔符的字符（默认为 ","）。 |  |  |
| ```-r``` |  |  |
| ```--runconfig``` |  |  |
| 要使用的 Run Configuration 名称。 |  |  |
| ```-s``` |  |  |
| ```--system-properties``` |  |  |
| 以逗号分隔的 KEY=VALUE 键值对列表 |  |  |

=====

&nbsp;
## 可能的退出代码

Hop Run 目前以四种退出代码之一结束。

在除 `0` 以外的所有情况下，请检查错误日志以获取特定错误的更多详细信息。

| 退出代码 | 错误消息 | 描述 |
|---|---|---|
| 0 | 无 | 无错误消息。一切正常运行。 |
| 1 | Error found during execution! | 执行 workflow 或 pipeline 时出错。 |
| 2 | General error found, something went horribly wrong! | 发生了一般性错误，导致 Hop Run 完全失败。 |
| 9 | 无 | 解析提供的参数信息时出错。Hop Run 显示语法摘要并退出。 |

## 示例

下面的示例通过 `hop-run` 运行 samples 项目中的 `switch-case-basic.hpl` pipeline。
&nbsp;

====
Windows::
--
打开命令提示符（CMD）窗口，切换到 Qi Hop 解压目录并运行：

```
hop-run.bat -j samples -r local -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
```
你的输出将类似于以下内容：

```
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam "-Xmx2048m" -DHOP_AUDIT_FOLDER=.\audit
-DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=Run -DHOP_AUTO_CREATE_CONFIG=Y org.apache.hop.run.HopRun
-j samples -r local -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
===[Starting HopRun]=========================================================
2022/12/12 14:09:58 - HopRun - Enabling project 'samples'
2022/12/12 14:09:58 - HopRun - Starting pipeline: config/projects/samples/transforms/switch-case-basic.hpl
2022/12/12 14:09:58 - switch-case-basic - Executing this pipeline using the Local Pipeline Engine with run configuration 'local'
2022/12/12 14:09:58 - switch-case-basic - Execution started for pipeline [switch-case-basic]
2022/12/12 14:09:58 - Test Data.0 - Finished processing (I=0, O=0, R=0, W=5, U=0, E=0)
2022/12/12 14:09:58 - Switch id.0 - Finished processing (I=0, O=0, R=5, W=5, U=0, E=0)
2022/12/12 14:09:58 - Output 2.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:09:58 - Output default.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:09:58 - Output 1.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:09:58 - Output 4.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:09:58 - Output 3.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:09:58 - switch-case-basic - Pipeline duration : 0.542 seconds [  0.542" ]
```
--

Linux, macOS::
--

打开终端，切换到 Qi Hop 解压目录并运行：

```
./hop-run.sh -j samples -r local -f ${PROJECT_HOME}/transforms/switch-case-basic.hpl
```
你的输出将类似于以下内容：

```
2022/12/12 14:06:39 - HopRun - Enabling project 'samples'
2022/12/12 14:06:39 - HopRun - Relative path filename specified: config/projects/samples//transforms/switch-case-basic.hpl
2022/12/12 14:06:39 - HopRun - Starting pipeline: config/projects/samples//transforms/switch-case-basic.hpl
2022/12/12 14:06:39 - switch-case-basic - Executing this pipeline using the Local Pipeline Engine with run configuration 'local'
2022/12/12 14:06:39 - switch-case-basic - Execution started for pipeline [switch-case-basic]
2022/12/12 14:06:39 - Test Data.0 - Finished processing (I=0, O=0, R=0, W=5, U=0, E=0)
2022/12/12 14:06:39 - Switch id.0 - Finished processing (I=0, O=0, R=5, W=5, U=0, E=0)
2022/12/12 14:06:39 - Output 2.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:06:39 - Output default.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:06:39 - Output 4.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:06:39 - Output 3.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:06:39 - Output 1.0 - Finished processing (I=0, O=0, R=1, W=1, U=0, E=0)
2022/12/12 14:06:39 - switch-case-basic - Pipeline duration : 0.499 seconds [  0.499" ]
```
--
====

### 参数示例
以下是该命令参数解析方式的示例列表

#### 正常用法
```
--parameters=key1=value1,key2=value2
```
结果：

| 键 | 值 |
|---|---|
| key1 | value1 |
| key2 | value2 |
#### 值中包含空格的用法
```
--parameters=key1="This value contains spaces",key2=value2
```
结果：

| 键 | 值 |
|---|---|
| key1 | This value contains spaces |
| key2 | value2 |

#### 值中包含逗号的用法
```
--parameters=key1=\"value1,value2\"
```
结果：

| 键 | 值 |
|---|---|
| key1 | value1,value2 |
#### 在值中强制使用引号
```
--parameters=key1="\"\"String with spaces\"\""
```
结果：

| 键 | 值 |
|---|---|
| key1 | "String with spaces" |
