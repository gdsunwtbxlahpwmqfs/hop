# 概念

## 工具

Hop Conf::
<!-- include not found: hop-conf.adoc -->
Hop Encrypt::
<!-- include not found: hop-encrypt.adoc -->
Hop Gui::
<!-- include not found: hop-gui.adoc -->
Hop Run::
<!-- include not found: hop-run.adoc -->
Hop Search::
<!-- include not found: hop-search.adoc -->
Hop Server::
<!-- include not found: hop-server.adoc -->
## 元素类型

Action::
<!-- include not found: action.adoc -->
Hop::
<!-- include not found: hop.adoc -->
Pipeline::
<!-- include not found: pipeline.adoc -->
![Pipeline](../assets/images/concepts/pipeline.png)

Transform::
<!-- include not found: transform.adoc -->
Workflow::
<!-- include not found: workflow.adoc -->
![Workflow](../assets/images/concepts/workflow.png)

## 项目和环境

Project::
<!-- include not found: project.adoc -->
Environment::
<!-- include not found: environment.adoc -->
![环境示例](../assets/images/concepts/environments.png)

## Metadata

Hop Metadata 是共享元数据的中央存储库，包含关系型数据库连接、运行配置、服务器、Git 仓库等。
Metadata 以 JSON 格式持久化，默认存储在项目的基础文件夹中。

[元数据驱动架构](metadata-driven-architecture.md)

// 数据类型
## 数据类型

作为在 Qi Hop 中处理数据时获得一致、可预测结果的最佳实践，您必须考虑 Qi Hop 引擎如何处理转换和作业中的不同数据类型和字段元数据。

> **📝 注意:** 作为规则，数据在 Qi Hop 内部不会被元数据修改。数据仅在 Qi Hop 写入文件或类似对象时被修改，但不会在写入数据库时修改。

Qi Hop 数据类型在内部映射到 Java 数据类型，因此这些数据类型的 Java 行为适用于您在 Workflow 和 Pipeline 中使用的相关字段、参数和变量。下表描述了这些映射关系。

| Qi Hop | Java 数据类型 | 描述 |
|---|---|---|
| BigNumber | BigDecimal | 任意无限精度的数字。 |
| Binary | Byte[] | 包含任何类型二进制数据的字节数组。 |
| Boolean | Boolean | 布尔值 true 或 false。 |
| Date | Date | 毫秒精度的日期时间值。 |
| Integer | Long | 有符号 64 位长整型。 |
| Internet | Address | InetAddress 互联网协议 (IP) 地址。 |
| Number | Double | 双精度浮点值。 |
| String | String | UTF-8 (Unicode) 编码的可变无限长度文本。 |
| Timestamp | Timestamp | 允许指定纳秒精度的小数秒。 |

> **📝 注意:** Qi Hop 还附带了许多额外的复杂数据类型（如 Avro、JSON、Graph），这些类型没有与 Java 数据类型的一对一映射。这些数据类型仅适用于特定的 Transform，不能用于通用 Transform。

### 转换和比较

*空值和排序顺序*

- 空值处理和排序行为遵循映射类型的 Java 比较语义，除非下文另有说明。

*字符串解析/格式化*

- 从 String 转换为其他类型时，Hop 使用 Transform 元数据中定义的格式设置（例如日期掩码、小数符号和分组字符）。
- 从其他类型转换为 String 时，Hop 应用相同的基于元数据的格式。

由于这些原因，在处理 String 类型时明确指定格式非常重要。
一个常见问题是小数分隔符，不同环境间可能不同。
例如，EU 区域设置通常使用 `,` 作为小数分隔符，而 US 区域设置使用 `.`。
使用显式格式可以避免这些不一致。

### Timestamp

Timestamp 值可以从 Date、String、Number 或另一个 Timestamp 创建。

*转换*

- *Date -> Timestamp*：表示同一时刻；小数秒默认为 `0`。
- *String -> Timestamp*：使用指定的日期掩码解析（例如 `yyyy-MM-dd HH:mm:ss`）。
- *Number -> Timestamp*：解释为**自纪元以来的纳秒数**。

### UUID

UUID 值映射到 Java UUID 对象，以 16 字节类型存储。
使用 String 类型存储 UUID 需要 32 字节。
在许多情况下，存储差异是显而易见的，例如在具有原生 UUID 支持的数据库中插入时。

使用 MongoDB 时，Hop 使用 **STANDARD** 表示写入 UUID，对应 BSON Binary 子类型 `4`。

*排序*

数据库可能按原始二进制值排序 UUID 列（如 PostgreSQL 的情况）。
但 Hop 将 UUID 比较为两个 64 位的有符号 long——先比较最高有效位 (MSB)，再比较最低有效位 (LSB)。
这些排序方法可能不同。
为确保结果一致，请在 Hop 内部或完全在数据库中执行所有排序。

*注意事项*

将 UUID 写入不支持原生 UUID 类型的数据库（如 MySQL）会失败。
在写入之前将它们转换为 String。

### JSON

Hop 中的 JSON 类型遵循官方 JSON 标准：https://www.json.org/json-en.html。
它被视为键/值对的*无序*集合。
这意味着 JSON 值的行为可能与其 String 表示不同。例如，考虑这两个 JSON 对象：

```json
{ "a": 1, "b": 2 }
```
```json
{ "b": 2, "a": 1 }
```

当作 String 处理时，它们是不同的。
当作 JSON 处理时，它们被认为是相等的。
因为 Hop 中的 JSON 比较执行结构检查，所以它通常比字符串比较更健壮——但也稍慢。

*排序*

Hop 排序 JSON 值的方式类似于 PostgreSQL 排序 JSONB 值的方式。
键按字母顺序排序，值按以下类型顺序比较：

```
NULL < MISSING < BINARY < STRING < NUMBER < BOOLEAN < ARRAY < OBJECT
```

数组逐元素比较；如果所有比较的元素都相等，则较长的数组被认为更大。

*MongoDB*

JSON 类型在读取或写入 MongoDB 时支持所有标准 JSON 值。
不支持属于 MongoDB 扩展 JSON 的值（例如 Date 对象）。
要完全支持 MongoDB 特定的 BSON 值，请改用 String 类型。

## 其他

以下条目是 Hop 中使用的概念的字母排序列表，将在 Hop 工具和文档的各个位置提及。

Lazy Loading::
如果启用，正在读取的数据的所有数据转换（字符解码、数据转换、修剪等）将尽可能延后执行，实际上将数据作为二进制字段读取。
启用延迟转换可以显著降低读取数据的 CPU 开销。
#何时避免使用#：如果数据转换需要在流的后续阶段执行，延后转换可能会减慢速度而不是加快速度。
#何时使用#：延迟转换可能加快速度的用例包括：1）数据被读取并写入另一个文件而不进行转换，2）数据需要排序且无法放入内存。
在这种情况下，使用延迟转换序列化到磁盘更快，因为编码和类型转换被延后，或 3）批量加载数据到数据库而不需要数据转换。
批量加载工具通常直接读取文本，生成此文本更快（这不适用于 Table Output）。
