# Join 和 Lookup

Hop 提供了大量选项来丰富和组合数据，其中包括通过 join 和 lookup。本指南解释了各种 join 和 lookup 之间的区别，并帮助您为 pipeline 选择正确的 transform。

## Join

Join 是一种将 pipeline 中两个或多个不同数据流合并的方式。Join 通常需要您选择一组字段（join 键）来连接数据流。参与 join 的所有数据流中的数据需要按 join 键排序。

根据您使用的 transform 和指定的配置选项，join 可以双向改变您正在处理的数据量。例如，两个流之间匹配数有限的内部 `Merge join` 可以显著减少流中的行数。另一方面，`Join Rows (Cartesian Product)` 可能会导致您启动 pipeline 时的行数爆炸。

> **💡 提示:** 如果您的 pipeline 中有多个布局完全相同（相同字段、相同数据类型、相同顺序）的流，可以直接 join 或 merge 而不需要 transform。只需从两个或多个子流的最后一个 transform 创建一个 hop 到目标 transform。如果您合并的流布局存在差异，Hop GUI 会发出警告。

可用的 join transform 有：

| Transform | 适用场景 |
|---|---|
| [**Join Rows (Cartesian Product)**](pipeline/transforms/joinrows.md) |  |
| 当您想将一个流中的_所有_数据与另一个流中的_所有_数据进行组合时 |  |
| [**Merge Join**](pipeline/transforms/mergejoin.md) |  |
| 您想基于两个流中的匹配键合并来自两个不同流的数据，并希望继续使用两个流中（选定部分的）合并数据。 |  |
| [**Merge Rows (diff)**](pipeline/transforms/mergerows.md) |  |
| 您有两组布局相同的数据。您想比较两个数据集，并识别其中一个集合中哪些行相对于另一个数据集是新增的、相同的、更改的或删除的。 |  |
| [**Multiway merge join**](pipeline/transforms/multimerge.md) |  |
| 合并来自多个（通常超过两个）流的数据，类似于 merge join。此 transform 提供了一种快捷方式，无需将多个 merge join 串联在一起。 |  |
| [**XML Join**](pipeline/transforms/xmljoin.md) |  |
| 您需要从各种数据源构建包含多个嵌套元素的 XML 文件。此 transform 是非典型的，因为您执行 join 是为了构建输出，而不是像其他 join transform 那样合并传入数据以供进一步处理。 |  |
| [**Database join**](pipeline/transforms/databasejoin.md) |  |
| Database join 为每一行执行一个（参数化的）SQL 查询。虽然此 transform 给予了无与伦比的灵活性，但它为每一行执行数据库查询，因此是潜在的性能瓶颈。 |  |

## Lookup

Lookup 是一种让您用来自外部源的附加字段来丰富 pipeline 流的方式。这些 transform 允许您选择从 lookup 源检索哪些附加字段。

与 join 相反，lookup 作用于当前 pipeline 流，永远不会改变您正在处理的行数。

`Lookup` 类别中有一些 transform 被认为在 lookup 上下文中是不言自明的，不会在此详述。这些 transform 包括：

- [Call DB Procedure](pipeline/transforms/calldbproc.md)
- [Check if file is locked](pipeline/transforms/checkfilelocked.md)
- [Column exists](pipeline/transforms/columnexists.md)
- [Dynamic SQL row](pipeline/transforms/dynamicsqlrow.md)
- [File exists](pipeline/transforms/fileexists.md)
- [Fuzzy match](pipeline/transforms/fuzzymatch.md)
- [Get server status](pipeline/transforms/serverstatus.md)
- [HTTP client](pipeline/transforms/http.md)
- [HTTP post](pipeline/transforms/httppost.md)
- [REST client](pipeline/transforms/rest.md)
- [Table exists](pipeline/transforms/tableexists.md)
- [Web services lookup](pipeline/transforms/webservices.md)

| Transform | 适用场景 |
|---|---|
| [**Combination lookup/update**](pipeline/transforms/combinationlookup.md) |  |
| 在 lookup 上下文中，此 transform 从 [type 1 缓慢变化维度](https://en.wikipedia.org/wiki/Slowly_changing_dimension#Type_1:_overwrite) 获取数据。 |  |
| [**Dimension lookup/update**](pipeline/transforms/dimensionlookup.md) |  |
| 在 lookup 上下文中，此 transform 允许您在 [type 2 缓慢变化维度](https://en.wikipedia.org/wiki/Slowly_changing_dimension#Type_2:_add_new_row) 中执行 lookup。 |  |
| [**Database lookup**](pipeline/transforms/databaselookup.md) |  |
| 您需要基于 pipeline 流中的字段从单个数据库表中检索附加信息。 |  |
| [**Stream lookup**](pipeline/transforms/streamlookup.md) |  |
| 当您需要在相当有限的数据集上执行大量 lookup 时使用 Stream lookup。 |  |

## Lookup 缓存

[Combination lookup/update](pipeline/transforms/combinationlookup.md)、[Dimension lookup/update](pipeline/transforms/dimensionlookup.md) 和 [Database lookup](pipeline/transforms/databaselookup.md) 允许您缓存数据。缓存的数据存储在内存中以加速 lookup 过程。

对话框选项略有不同，但这些 transform 提供的缓存选项包括：

- 启用缓存？(布尔值)：开启或关闭缓存
- 缓存大小：在内存中缓存的行数
- 预加载缓存：在 pipeline 开始前加载数据到缓存
- 加载表中所有数据（仅限 database lookup）：在 pipeline 开始前将表的所有数据加载到内存。

配置缓存时请考虑可用的内存资源。缓存可以显著加速 lookup 过程，但内存是有限资源。
