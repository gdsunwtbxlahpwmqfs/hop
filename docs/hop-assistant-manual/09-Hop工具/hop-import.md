# Hop Import

Hop Import 是一个命令行工具，用于将第三方 metadata 导入并转换为 Qi Hop 格式。

## 用法

通过使用 `-h` 选项运行 hop-import 可以获取用法帮助，即使指定了其他选项也可以：

&nbsp;
.Usage

=====

```
Usage: <main class> [-efhlp] [-c=<targetConfigFilename>] [-i=<inputFolderName>]
                    [-j=<jdbcPropertiesFilename>]
                    [-k=<kettlePropertiesFilename>] [-o=<outputFolderName>]
                    [-s=<sharedXmlFilename>] [-t=<type>]
  -c, --target-config-file=<targetConfigFilename>
                        The target config file to write variables to
  -e, --skip-existing   Skip existing files in the target folders
  -f, --skip-folders    Skip import of sub-folders
  -h, --help            Displays this help message and quits
  -i, --input=<inputFolderName>
                        The input folder to read from
  -j, --jdbc-properties=<jdbcPropertiesFilename>
                        The jdbc.properties file to read from
  -k, --kettle-properties=<kettlePropertiesFilename>
                        The kettle.properties file to read from
  -l, --list-plugins    List the available import plugins
  -o, --output=<outputFolderName>
                        The output folder to write to
  -p, --skip-hidden     Skip import of hidden files and folders
  -s, --shared-xml=<sharedXmlFilename>
                        The shared.xml file to read from
  -t, --type=<type>     The type of import plugin to use (e.g. kettle)
  -v, --version         Print version information and exit   
```
=====

&nbsp;
## 示例

将一组 Kettle 文件和文件夹导入存储在 Amazon AWS S3 上的项目：

&nbsp;

====
Windows::
--
```shell
hop-import.bat \
  --type kettle \
  --input /projects/kettle/inventory \
  --output s3:///apache-hop/s3project \
  --target-config-file imported-env-conf.json \
  --kettle-properties /home/etl/.kettle/kettle.properties \
  --shared-xml /home/matt/.kettle/shared.xml
```
--

Linux, macOS::
--
```shell
sh hop-import.sh \
  --type kettle \
  --input /projects/kettle/inventory \
  --output s3:///apache-hop/s3project \
  --target-config-file imported-env-conf.json \
  --kettle-properties /home/etl/.kettle/kettle.properties \
  --shared-xml /home/matt/.kettle/shared.xml
```
--
====

日志将显示正在进行的操作，并在最后打印报告：

```
2021/06/22 16:39:29 - HopImport - Import is skipping existing target files
2021/06/22 16:39:29 - HopImport - Import is skipping hidden files and folders
2021/06/22 16:39:29 - HopImport - Import is not skipping sub-folders
2021/06/22 16:39:29 - HopImport - Finding files to import
2021/06/22 16:39:29 - HopImport - We found 84 kettle files.
2021/06/22 16:39:29 - HopImport - Importing files
2021/06/22 16:39:29 - HopImport -   - Saving file s3:///apache-hop/s3project/load-nodes/sample-model.json
2021/06/22 16:39:29 - HopImport -   - Saving file s3:///apache-hop/s3project/scaleable-file-processing/input/file02.csv
2021/06/22 16:39:29 - HopImport -   - Saving file s3:///apache-hop/s3project/graph-output/READ.me
2021/06/22 16:39:29 - HopImport -   - Saving file s3:///apache-hop/s3project/scaleable-file-processing/PDI/Check slave server.hpl
2021/06/22 16:39:29 - HopImport -   - Saving file s3:///apache-hop/s3project/remove-everything/remove all in database.hwf
...
2021/06/22 16:39:37 - HopImport -   - Saving file s3:///apache-hop/s3project/scaleable-file-processing/input/file01.csv
2021/06/22 16:39:37 - HopImport - Importing connections
2021/06/22 16:39:41 - HopImport - Importing variables
Creating new default Hop configuration file: s3:///apache-hop/s3project/imported-env-conf.json

2021/06/22 16:39:43 - HopImport - Imported:
2021/06/22 16:39:43 - HopImport - 10 jobs
2021/06/22 16:39:43 - HopImport - 31 transformations
2021/06/22 16:39:43 - HopImport - 43 other files
2021/06/22 16:39:43 - HopImport - 86 variables were imported into environment config file s3:///apache-hop/s3project/imported-env-conf.json
2021/06/22 16:39:43 - HopImport - You can use this as a configuration file in an environment.
2021/06/22 16:39:43 - HopImport - 3 database connections where saved in metadata folder s3:///apache-hop/s3project/metadata
2021/06/22 16:39:43 - HopImport -
2021/06/22 16:39:43 - HopImport - Connections with the same name and different configurations have only been saved once.
2021/06/22 16:39:43 - HopImport - Check the following file for a list of connections that might need extra attention: s3:///apache-hop/s3project/connections.csv
```
