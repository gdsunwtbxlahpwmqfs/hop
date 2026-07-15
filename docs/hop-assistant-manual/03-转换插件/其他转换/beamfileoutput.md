# ![Beam Output Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-output.svg) Beam File Output

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Output location | 输出位置。 |
| File prefix | 必须放在文件名前面的文本。 |
| File suffix | 必须放在文件名后面的文本。 |
| Windowed writes? | 每个窗口写入一个文件。 |
| File definition to use | 存储在 metastore 中要使用的文件定义。 |
| Edit | 编辑现有文件定义。 |
| New | 创建新文件定义。 |
| Manage | 打开 MetaStore Explorer。 |

## 文件定义

文件定义可用于定义文件结构定义。

| Option | Description |
|---|---|
| Name | 文件定义名称。 |
| Description | 文件定义描述。 |
| Field separator | 分隔字段的字符。 |
| Field enclosure | 包围字段的字符。 |
| Field definitions | 字段列表。 |
| Field name | 字段名称。 |
| Type | 字段数据类型。 |
| Format | 字段格式。 |
| Length | 字段长度。 |
| Precision | 字段精度。 |
