# ![Snowflake Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/snowflakebulkloader.svg) Snowflake Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 批量加载器 选项卡

- **Connection**：批量加载时使用的数据库连接
- **Schema**：（可选）包含正在加载的表的 schema。
- **Table name**：正在加载的表的名称。
- **Staging location type**：用于存储文件的 Snowflake 暂存区类型。
- **User Location**：使用用户的主目录来存储正在加载的文件。
- **Table Location**：使用表的内部暂存区来存储正在加载的文件。
- **Internal Stage**：使用已创建的内部暂存区来存储正在加载的文件。
- **Internal Stage Name**：（当 Staging location type = Internal stage 时）要使用的内部暂存区名称。
- **Work directory**：在加载到 Snowflake 之前存储临时文件的本地工作目录。
- **On Error**：（Abort、Skip File、Skip File Percent、Continue）加载时遇到错误的行为。
- **Error limit**：（当 On Error = Skip File 或 Skip File Percent 时）跳过文件之前的错误限制。如果为空或 0，则文件将在第一个错误时被跳过。
- **Split load every ... rows**：将临时文件拆分为多个较小的文件将允许 Snowflake 并行执行批量加载，从而提高性能。这是每个文件应包含的行数。
- **Remove files after load**：（Y/N）加载后是否应从 Snowflake 暂存区中删除文件？您还需要将变量 `SNOWFLAKE_DEBUG_MODE` 设置为 `true` 以保留文件。

### 数据类型 选项卡

- **Data type**：正在批量加载的数据类型。
- **CSV**：
- **Trim whitespace**：（Y/N）加载 Snowflake 时是否应修剪字段值周围的空格。
- **Null if**：加载 Snowflake 时应转换为 null 的逗号分隔字符串列表。字符串不需要加引号。
- **Error on column count mismatch**：如果表中的列数与输出的列数不匹配，则不加载该行并抛出错误。
- **JSON**：正在加载的数据在输入流中的单个包含 JSON 的字段中接收。
- **Remove nulls**：是否应移除 JSON 中的 null 值，从而减少所需的存储量。
- **Ignore UTF8 errors?**：解析 JSON 时忽略任何 UTF8 字符编码错误。
- **Allow duplicate elements**：允许 JSON 多次包含相同的元素。如果同一元素出现多次，该元素的最后一个值将存储在 Snowflake 中。
- **Parse octal numbers**：将 JSON 中存储的任何数字解析为八进制而不是十进制。

### 字段 选项卡

- **Data type CSV**
- **Specifying fields**：（Y/N）是否明确指定从 Hop 到 Snowflake 的字段映射。如果未指定字段映射，则此 transform 的输入字段顺序必须与表中字段的顺序匹配。
- **Field mapping table**：（当勾选 Specifying fields 时。）字段不需要按任何顺序排列。
- **Stream field**：输入流上的字段
- **Table field**：要将输入字段映射到的表中的字段。
- **Get fields button**：从输入流中获取字段，并将它们映射到表中同名的字段。
- **Enter field mapping button**：打开一个窗口，帮助用户指定输入字段到表字段的映射。
- **Data type JSON**
- **JSON field**：输入流上包含要加载的 JSON 数据的字段。
