# hop doc

`hop doc` 可用于以 [MarkDown](https://www.markdownguide.org/basic-syntax/) 格式为项目生成文档。
它会为每个 workflow 和 pipeline 创建单独的文件，并生成索引等。
然后你可以使用 [mkdocs](https://www.mkdocs.org/) 等 MarkDown 工具来发布这些文档。

## 选项

你可以运行 `hop help doc` 命令来查看选项：

在 Windows 上运行 `hop.bat help doc`。
在 Mac 和 Linux 上运行 `sh hop help doc`。

你将在类似于下方的输出中看到选项列表：

&nbsp;

.Usage

=====

.Output of help
```bash
Usage: hop doc [-hV] [-im] [-in] [-ip] [-e=<environmentOption>]
               [-j=<projectOption>] [-n=<projectName>] [-s=<sourceFolder>]
               [-t=<targetParentFolder>]
Generate documentation
  -e, --environment=<environmentOption>
                             The name of the lifecycle environment to use
  -h, --help                 Show this help message and exit.
      -im, --include-metadata
                             Include an overview of the available metadata
                               elements
      -in, --include-notes   List the text of any notes in alphabetical order
      -ip, --include-parameters
                             Include a list of parameters for each pipeline and
                                workflow
  -j, --project=<projectOption>
                             The name of the project to use
  -n, --project-name=<projectName>
                             The name of the project
  -s, --source-folder=<sourceFolder>
                             The source folder to document
  -t, --target-folder=<targetParentFolder>
                             Specify the target parent folder where the
                               documentation should end up
  -V, --version              Print version information and exit.
```
=====

如你所见，`projects` plugin 提供了选择项目或环境的选项，因此无需记住项目存储在哪个文件夹中。

## 示例

下面的示例为 `demo` 项目生成文档，并要求将 MarkDown 文件写入 `/tmp/hop/docs` 文件夹：

&nbsp;

====
Windows::
--
打开命令提示符（CMD）窗口，切换到 Qi Hop 解压目录并运行：

```
hop.bat doc -j demo --target-folder /tmp/hop/docs --include-notes --include-parameters --include-metadata
```
--

Linux, macOS::
--
打开终端，切换到 Qi Hop 解压目录并运行：

```
./hop doc -j demo -t /tmp/hop/docs --include-notes --include-parameters --include-metadata
```
====
