# ![Get filenames transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/getfilenames.svg) Get filenames

| == Supported Engines |  |
|---|---|
| Hop Engine | ![Supported,24](../../assets/images/check_mark.svg) |
| Spark | ![Supported,24](../../assets/images/check_mark.svg) |
| Flink | ![Supported,24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported,24](../../assets/images/check_mark.svg) |

## 用法

此 transform 有两种运行模式。

当在此 transform 内部配置 metadata 时，将丢弃来自前一个 transform 的输入，并生成新的行。

当通过文件字段提供 metadata 时，以下字段将附加到输出中：

- filename - 完整文件名，包括路径 (/tmp/hop/somefile.txt)
- short_filename - 仅文件名，不含路径 (somefile.txt)
- path - 仅路径 (/tmp/hop/)
- type
- exists
- ishidden
- isreadable
- iswriteable
- lastmodifiedtime
- size
- extension
- uri
- rooturi

## 文件选项卡

此选项卡定义您要获取文件名的文件位置。
有关指定文件位置的更多信息，请参阅 Text File Input transform 中的"使用正则表达式选择文件"部分。

示例：您有一个固定目录 c:\temp，期望将扩展名为 .dat 的文件放置在此。
在 file/directory 下指定 c:\temp，在 Wildcard 下使用类似 .*\.dat$ 的正则表达式。

| 选项 | 描述 |
|---|---|
| Is filename defined in a field? | 指示您要加载的文件是否在某个可指定的字段中。 |
| Get filename from field | 用于加载文件名的字段。 |
| Inclusion wildcard field (RegExp) | 如果要加载多个文件，请定义匹配要加载的文件名的正则表达式。 |
| Exclusion wildcard field (RegExp) | 定义匹配要从 transform 中排除的文件的正则表达式。 |
| Include subfolders? | 指示表达式是否考虑子文件夹中的文件。 |
| File or directory | 如果文件不由字段定义，请指定文件名或目录以加载多个文件。 |
| Add | 点击可将指定的文件、目录和正则表达式添加到已选文件列表中。 |
| Browse | 点击可选择文件。 |
| Inclusion wildcard (RegExp) | 如果要加载多个文件，请定义匹配要加载的文件名的正则表达式。 |
| Exclusion wildcard (RegExp) | 定义匹配要从 transform 中排除的文件的正则表达式。 |
| Selected files | 列出已选文件及其定义的包含/排除表达式。 |
| Delete | 点击可从已选文件列表中删除文件。 |
| Edit | 点击可修改已选文件列表中的文件。 |

## 过滤器

过滤器选项卡允许您根据以下条件过滤检索到的文件名：

- 所有文件和文件夹
- 仅文件
- 仅文件夹

它还提供以下功能：

- 在输出中包含行号的功能
- 限制返回行数的功能。
  limit 参数作用于返回的总行数，而不仅仅是返回的文件数。
- 将文件名添加到结果列表的功能
- 在未找到文件/文件夹时打印错误消息而不停止处理的功能
- 在未找到文件/文件夹时抛出异常并停止处理的功能

| 选项 | 描述 |
|---|---|
| Filter type | 选择过滤的对象。选择所有文件和文件夹、仅文件或仅文件夹。 |
| Include rownum in output? | 行号是否作为字段包含在输出中。 |
| Rownum fieldname | 报告行号的字段名称。 |
| Do not report error | 指示如果没有文件或文件夹匹配过滤条件，pipeline 是否静默失败。如果未选择，则在没有可用文件或文件夹时会报告错误。 |
| Raise an exception and stop processing | 指示如果没有文件或文件夹匹配过滤条件是否抛出异常，这也会阻止 pipeline 继续执行。如果未选择，pipeline 将在没有文件的情况下继续执行。 |
| Limit | 包含在输出中的文件和文件夹数量。 |
| Add filename to result? | 指示是否将文件名作为字段添加到输出中。 |

### 关于未找到文件时异常抛出的详细说明

如上所述，如果您在 _过滤器_ 选项卡中启用该开关，Hop 将在未找到文件时抛出异常并停止处理。

在这种情况下，我们需要了解根据您选择标识目标文件集的方式不同，异常的抛出方式也不同：

- 如果您在 _Selected files_ 表中将文件（或包含/排除表达式）指定为一个集合，则文件检索会考虑整个指定集合（一次性执行），并在未找到任何文件时抛出异常。
- 如果您通过 _Filenames from field_ 选项指定文件，则要检索的文件详细信息来自输入行。这意味着检索是逐行执行的，一旦用于指定搜索文件的某个详细信息失败，就会抛出异常。因此，在这种情况下，触发异常的评估是在评估每个输入行之后执行的，而不是评估输入行中找到的详细信息所返回和生成的整个数据集。
