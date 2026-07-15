# ![Metadata Injection transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/GenericTransform.svg) Metadata Injection

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 用法

Metadata Injection Transform（MDI Transform）将数据和 Transform 属性插入到在运行时动态配置的 Pipeline 中。这通常用于增强 Pipeline 的可重用性。

例如，用例：如果您有 10 个不同的 CSV 文件定义，不想为每个单独的文件创建 10 个 Pipeline，可以使用 ETL metadata injection。在 MDI Transform 配置完成后（将数据和属性映射到模板/目标 Pipeline），在运行时它会动态填充目标 Pipeline 中任何未配置的 Transform 属性。例如，在模板文件中，如果您将 Table input Database 字段留空，并且在 MDI Transform 中进行了配置，它将动态填充 Table 字段和 Stream 字段。

基本上，ETL Metadata Injection Transform 覆盖了命名 Pipeline 的默认行为。并非所有传入流都需要具有相同的布局。从不同布局的不同流向 metadata injection Transform 提供输入是完全可行的。

*示例文件：*

- *配置 MDI：* read-and-inject-metadata.hpl

- *读取并填充模板：* read-file-template.hpl

[了解更多 metadata injection](pipeline/metadata-injection.md)

## 选项

### General

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Pipeline | 通过输入路径指定您的模板 Pipeline。 |

ETL Metadata Injection Transform 具有两个包含字段的选项卡。
每个选项卡如下所述。

### Inject Metadata 选项卡
此选项卡显示目标/模板文件中将在运行时修改的 Transform 和属性。将每个属性映射到您希望动态填充的源。

| 选项 | 描述 |
|---|---|
| Target injection transform key | 列出模板 Pipeline 中每个 Transform 可注入 metadata 的可用字段。 |
| Target description | 描述目标字段与其目标 Transform 的关系。 |
| Source transform | 列出与要注入到目标字段作为 metadata 的字段关联的 Transform。 |
| Source field | 列出要注入到目标字段作为 metadata 的字段。 |

要将源字段指定为要注入的 metadata，请执行以下操作：

1. 在 Target injection transform key 列中，双击要指定源字段的字段。
Source field 对话框打开。

2. 选择一个源字段并点击 OK。

3. 可选地，选择 Use constant value 通过以下操作之一为注入的 metadata 指定常量值：
- 手动输入值。
- 使用内部变量设置值（例如 {openvar}Internal.transform.Unique.Count{closevar}）。
- 使用手动指定值和参数值的组合（例如 {openvar}FILE_PREFIX{closevar}_{openvar}FILE_DATE{closevar}.txt）。

在为分组值列表（如字段或文件名）指定常量值时，请注意目前没有完美的解决方案。最佳实践是使用 [Data Grid](pipeline/transforms/datagrid.md) Transform 来注入一组完整的常量值。您可以在此 metadata injection Transform 中映射它们。它会尽力满足您的需求，允许您在组中注入具有指定常量值的单行。

#### 向 ETL Metadata Injection Transform 注入 Metadata

对于向 ETL Metadata Injection Transform 自身注入 metadata，以下例外情况适用：

- 要注入指定字段的方法（例如通过 FILENAME），请在输入 Transform 的字段中设置 PIPELINE_SPECIFICATION_METHOD 常量。
然后您可以将该字段作为源映射到 ETL Metadata Injection Transform 中的 PIPELINE_SPECIFICATION_METHOD 常量。

- ETL Metadata Injection Transform 将 metadata 插入到原始注入中的目标字段由 [GROUP NAME].[FIELD NAME] 定义。
例如，如果 GROUP NAME 是 'OUTPUT_FIELDS'，FIELD NAME 是 'OUTPUT_FIELDNAME'，则目标字段设置为 'OUTPUT_FIELDS.OUTPUT_FIELDNAME'。

### Options 选项卡

| 选项 | 描述 |
|---|---|
| transform to read from (optional) | 可选地，在模板 Pipeline 中选择一个 Transform，以将数据直接传递到当前 Pipeline 中 ETL Metadata Injection Transform 之后的 Transform。 |
| Field name | 如果选择了 transform to read from，输入从模板 Pipeline 中的 Transform 直接传递的字段名称。 |
| Type | 如果选择了 transform to read from，选择从模板 Pipeline 中的 Transform 直接传递的字段类型。 |
| Length | 如果选择了 transform to read from，输入从模板 Pipeline 中的 Transform 直接传递的字段长度。 |
| Precision | 如果选择了 transform to read from，输入从模板 Pipeline 中的 Transform 直接传递的字段精度。 |
| Optional target file (hpl after injection) | 对于初始 Pipeline 开发或调试，指定一个可选文件，用于在 metadata injection 发生后创建和保存模板 Pipeline。 |
| Streaming source transform | 在当前 Pipeline 中选择一个源 Transform，以将数据直接传递到模板 Pipeline 中的 Streaming target transform。 |
| Streaming target transform | 在模板 Pipeline 中选择目标 Transform，以接收来自 Streaming source transform 的数据。 |
| Run resulting pipeline | 选择以注入 metadata 并运行模板 Pipeline。 |

*故障排除*

- 在 "Optional target file (hpl after injection)" 中输入 Pipeline 文件名，以便在注入后生成文件。此 Pipeline 将包含注入后的所有属性，可以查看和执行以进行测试。
