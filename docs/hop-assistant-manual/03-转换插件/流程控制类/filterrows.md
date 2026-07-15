# ![Filter Rows transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/filterrows.svg) Filter Rows

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 用法 
一旦此 transform 连接到前一个 transform（一个或多个，接收输入），您可以点击"<field>"、"="和"<value>"区域来构建条件。下游 transform 可以连接到 True 或 False hop。

右键点击条件可以进行编辑、删除、移动或添加子条件。

您可以在"filter row" transform 中使用 REGEX 表达式。更多内容请参阅 Transform 部分中的"Filter Rows"。

> **💡 提示:** 要输入 IN LIST 操作符，请使用以分号分隔的字符串值。

> **💡 提示:** 列表也适用于整数等数值类型。在这种情况下，值列表必须以字符串数据类型输入，例如：2;3;7;8。

请注意，所有 transform 都是并行执行的，因此某些情况下 true 和 false 路径都会运行。

Filter Rows transform 只能检测输入流中的字段。如果您想基于变量值过滤行，可以修改前一个 transform。例如，一个 Table Input transform 可以在 SQL 中将变量作为另一个字段包含进来，如"select field1, field2, {openvar}myvar{closevar} as field3 from table1"，然后在 filter row 条件中可以使用 field1 = field3。或者，您可以使用 'Get Variables' transform 在字段中设置参数。有多种 SQL 查询语句会始终返回结果，例如使用 IF EXISTS 或 IS NULL，如果您始终需要返回字段和结果的话。

示例可在名为"filter-rows-basic.hpl"的 samples 项目中找到。

如果您因为字段尚不存在（由于 metadata injection）而无法选择所需字段，请参阅此文档：https://hop.apache.org//manual/latest/pipeline/transforms/filterrows.html#_mdi_example 以及 samples 项目中的示例：metadata-injection/filter-rows-mdi-parent.hpl。

基本上，要使用一个尚不可选的 MDI 字段（例如在 Filter Rows transform 中），您可以设置一个具有类似字段和类似条件的 transform。然后在文本编辑器中打开 pipeline，将字段名称更新为尚不存在的 MDI 字段名称。

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 可以根据需要更改此 transform 的名称。 |
| Send 'true' data to transform | 满足指定条件的行将被发送到此 transform。 |
| Send 'false' data to transform | 不满足指定条件的行将被发送到此 transform。 |
| The Condition |  |
| Add Condition | 点击添加条件。添加条件会将原始条件转换为子级条件。 |

## 过滤

### 基于变量值过滤行

Filter Rows transform 只能检测输入流中的字段。如果您想基于变量值过滤行，可以修改前一个 transform，例如 Table Input transform。例如，一个 Table Input transform 可以在 SQL 中将变量作为另一个字段包含进来，如：
```sql
SELECT field1, field2, ${myvar} AS field3 FROM table1
```
然后在 filter row 条件中可以使用 field1 = field3。或者，您可以使用 'Get Variables' transform 在字段中设置参数。

您可以在"filter row" transform 中使用 REGEX 表达式。

查询示例：

```sql
SELECT field1,
field2,
${myvar} AS field3
FROM table
WHERE field1=xxxx
```

然后在 filter row 条件中，可以设置：

```bash
field1 = field3
```

*提示* - 除了如上所述向 Table Input 添加字段外，您也可以使用"Get Variables" transform 向数据流添加字段。

> **💡 提示:** 您可以在"filter row" transform 中使用 REGEX 表达式。

请注意，所有 transform 都是并行执行的，因此某些情况下 true 和 false 路径都会运行。
在某些情况下，使用 SQL 查询中的 WHERE 过滤可能比使用 Filter Rows transform 更好。如果您需要为两种情况（true/false）都返回输入行，可以使用多种 SQL 查询方法，例如使用 IF EXISTS 或 IS NULL 来始终返回结果。

### 过滤特殊字符

要过滤特殊字符，如显式的 EOF（例如来自旧版 COBOL 文件），请在"filter row" transform 中使用 REGEX 表达式，语法为："\x{openvar}1A{closevar}"，其中 \x 表示十六进制表示，括号中的 1A 是要匹配的 EOF 字符的十六进制值。

## Metadata Injection 支持

此 transform 的所有字段均支持 metadata injection。
您可以将此 transform 与 ETL Metadata Injection 配合使用，在运行时向 pipeline 传递 metadata。

如果您因为字段尚不存在（由于 metadata injection）而无法选择所需字段，请参阅此文档：https://hop.apache.org//manual/latest/pipeline/transforms/filterrows.html#_mdi_example 以及 samples 项目中的此示例：metadata-injection/filter-rows-mdi-parent.hpl。

基本上，要使用一个尚不可选的 MDI 字段（例如在 Filter Rows transform 中），您可以设置一个具有类似字段和类似条件的 transform。然后在文本编辑器中打开 pipeline，将字段名称更新为尚不存在的 MDI 字段名称。

## 条件字段的特殊说明

Filter Rows transform 是一个特殊的 MDI 场景，因为它具有嵌套的过滤条件结构。
条件以 XML 格式给出。
条件 XML 的格式与我们以 XML 格式存储在 .HPL 文件中的 pipeline metadata 相同。
我们没有 .HPL XML 格式和条件的 DTD（文档类型定义）。

获取 XML 条件的方法很简单：

1. 创建一个包含所需不同条件的示例 Filter transform。
此示例 transform 为您提供所有信息，例如您使用的函数值。
2. 选中 transform，将其复制到剪贴板，然后粘贴到文本编辑器中。
或者，您可以保存 .HPL 文件，然后在文本编辑器中打开它。
3. 找到 <condition> 元素及其嵌套元素，并根据您的 MDI 场景进行相应修改。

## 示例
samples 项目在文件"filter-rows-basic.hpl"中演示了一些概念。

## MDI 示例

下面的示例过滤条件将以下过滤条件注入到 Filter Rows transform 中。

完整的示例 pipeline 可在 samples 项目中找到：`metadata-injection/filter-rows-mdi-parent.hpl` 和 `metadata-injection/filter-rows-mdi-child.hpl`

```
stateCode = FL
AND
housenr > 100
```
```xml
<condition>
            <negated>N</negated>
            <conditions>
                <condition>
                    <negated>N</negated>
                    <leftvalue>stateCode</leftvalue>
                    <function>=</function>
                    <rightvalue/>
                    <value>
                        <name>constant</name>
                        <type>String</type>
                        <text>FL</text>
                        <length>-1</length>
                        <precision>-1</precision>
                        <isnull>N</isnull>
                        <mask/>
                    </value>
                </condition>
                <condition>
                    <negated>N</negated>
                    <operator>AND</operator>
                    <leftvalue>housenr</leftvalue>
                    <function>&gt;</function>
                    <rightvalue/>
                    <value>
                        <name>constant</name>
                        <type>Integer</type>
                        <text>100</text>
                        <length>-1</length>
                        <precision>0</precision>
                        <isnull>N</isnull>
                        <mask>####0;-####0</mask>
                    </value>
                </condition>
            </conditions>
        </condition>
```
