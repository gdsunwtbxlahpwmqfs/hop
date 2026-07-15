# ![Get variables transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/getvariable.svg) Get variables

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法
请注意，workflow/环境变量仅设置一次。pipeline 需要启动才能获取任何新变量。正在运行或顺序或嵌套（嵌套 pipeline 在技术上与同一个 pipeline 相同）的 pipeline 无法获取新的变量值。当 pipeline 为 pipeline executor 中的每一行启动时，即视为 pipeline 已启动。

使用以下语法引用参数/变量：{openvar}myVariable{closevar}，例如来自前一个 pipeline。

*两种将字段、参数传递给下游变量的方式：*

参数在每个 pipeline 中必须唯一（大小写不区分）。pipeline executor 参数值优先于 pipeline 属性的参数值。发送方参数值优先于接收 pipeline 属性参数的默认值。

  - 使用 pipeline executor transform 或 Repeat action：
    - 发送：在 Parameters 选项卡上添加字段（参数名可以与字段名相同）进行发送。
    - 接收：在紧邻的下游 pipeline 中，使用 Get variables 通过格式 {openvar}myParam{closevar} 从 Parameters 设置变量/字段
  - 编辑任何上游 pipeline 的属性：
    - 发送：在 pipeline 属性的 parameters 选项卡上（编辑 pipeline 图标）添加参数，并可选择提供默认值。参数值可以在 pipeline 中使用各种 transform 来设置。
    - 接收：在紧邻的下游 pipeline 中，使用 Get variables 通过格式 {openvar}myParam{closevar} 从 Parameters 设置变量/字段

*其他：*

另请参阅 Set Variables transform。

要将 Variable 转换为 String 以外的数据类型，请使用 Select Values - Meta Data 选项卡。

要获取系统值（包括命令行参数），请使用 Get System Info transform。

例如，在变量列中，您可以指定：`{openvar}java.io.tmpdir{closevar}/hop/tempfile.txt`，它将在类 Unix 系统上被解析为 `/tmp/hop/tempfile.txt`。

## 故障排除

您必须始终指定数据类型，否则将出现如下错误：

``2023/07/21 09:30:23 - REST client.0 - ERROR: Because of an error, this transform can't continue: 
  2023/07/21 09:30:23 - REST client.0 - TOKEN_URL None : Unknown type 0 specified.
  2023/07/21 09:30:23 - REST client.0 - ERROR: org.apache.hop.core.exception.HopValueException:``

## 选项

| 选项 | 描述 |
|---|---|
| Transform Name | 此 transform 在 pipeline 工作区中显示的名称。 |
| Name | 字段的名称。 |
| Variable a | 允许您输入变量作为完整字符串来返回行或向输入行添加值。 |
| Type | 指定字段类型：String、Date、Number、Boolean、Integer、BigNumber、Serializable 或 Binary。 |
| Format | 允许您在确定类型后指定字段的格式。 |
| Length | 对于 Number：数字中有效数字的总位数；对于 String：字符串的总长度；对于 Date：打印输出的字符串长度（例如，输入 4 将仅返回年份）。 |
| Precision | 对于 Number：小数位数。 |
| Currency | 用于解释带有货币符号的数字。 |
| Decimal | 用于指示数字值中使用句点 (".") 还是逗号 (",")。 |
| Group | 用于指示数字值中使用句点 (".") 还是逗号 (",")。 |
| TrimType | 在处理前对此字段进行修剪：选择 none、left、right 或 both（左和右）。 |
