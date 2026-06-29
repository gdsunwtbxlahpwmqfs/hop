# 用户自定义Java表达式（User Defined Java Expression）

用户自定义Java表达式转换允许您输入 Java 表达式，用于计算新值或替换字段中的值。

表达式所依赖的任何自定义 jar 库需要放置在 `plugins/transforms/janino/lib` 文件夹中。

## 主要选项

| 选项 | 说明 |
|------|------|
| 新字段名（New field name） | 表达式计算结果的输出字段名称 |
| Java 表达式（Java expression） | 用于计算的 Java 表达式，可引用输入字段 |
| 值类型（Value type） | 表达式结果的数据类型 |
| 替换值（Replace value） | 选择"是"以替换现有字段值，或"否"以创建新字段 |

## 注意事项

- 表达式所依赖的自定义 jar 库需放置在 `plugins/transforms/janino/lib` 目录下。
- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
