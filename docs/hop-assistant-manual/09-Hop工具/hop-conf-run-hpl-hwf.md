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
2022/12/16 14:27:42 - General - Transform NY reading from previous transform targeting this one using : Switch / case - TARGET - NY
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : NY, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Transform FL reading from previous transform targeting this one using : Switch / case - TARGET - FL
2022/12/16 14:27:42 - General - Handled generic transform (TRANSFORM) : FL, gets data from 1 previous transform(s), targets=0, infos=0
2022/12/16 14:27:42 - General - Transform Default reading from previous transform targeting this one using : Switch / case - TARGET - Default
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
