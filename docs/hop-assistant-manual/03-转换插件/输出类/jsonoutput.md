# ![JSON Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/JSO.svg) JSON Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### General 标签页

General 标签页允许指定 transform 操作类型、输出 JSON 结构和 transform 输出文件。
此文件将用于转储所有生成的 JSON。

#### Settings 部分

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称；此名称在单个 pipeline 中必须唯一。 |
| Operation a | 指定 transform 操作类型。 |
| Json block name | 此值将用作 JSON 块的名称。 |
| Nr. rows in a block | 合并为一个 JSON 数组的行数。 |
| Output value | 输出字段名称。 |
| Compatibility mode | 向后兼容模式，更多信息请参见此处。 |

#### Output File 部分

| 选项 | 描述 |
|---|---|
| Filename | 输出文件的完整路径。 |
| Append | 勾选后，新行将追加到现有文件中。 |
| Create Parent folder | 勾选后，当父文件夹不存在时将自动创建；否则如果文件夹不存在 transform 将失败。 |
| Do not open create at start | 如果未勾选： |
| Extension | 输出文件扩展名。 |
| Encoding | 输出文件编码。 |
| Include date in filename? | 如果勾选，输出文件名将包含文件名值 + 当前日期。 |
| Include time in filename | 如果勾选，输出文件名将包含文件创建时间。 |
| Show filename(s) button | 可用于测试完整输出文件路径。 |
| Add file to result filenames? | 如果勾选，创建的输出文件路径可从 transform 结果中访问。 |

### Fields 标签页

此标签页用于将输入 transform 字段映射到输出 JSON 值。

| 选项 | 描述 |
|---|---|
| Element name | 作为键的 JSON 元素名称。 |
| Fieldname | 输入 transform 字段名称。 |

## 兼容模式
文档的这一部分将解释启用兼容模式时的差异。对于所有新开发，**不**推荐使用此模式。
假设我们正在生成一个简单的 <键,值> 列表，键名为"name"和"value"，及其对应的值。

我们将使用以下设置：

- Json block name = "data"
- Nr rows in block = 3
- Compatibility mode = 未勾选（这是默认选项）

这将输出：

第一个文件：

```json
{
  "data" : [ {
    "name" : "item 1",
    "value" : "value 1"
  }, {
    "name" : "item 2",
    "value" : "value 2"
  }, {
    "name" : "item 3",
    "value" : "value 3"
  } ]
}
```
第二个文件：

```json
{
  "data" : [ {
    "name" : "item 4",
    "value" : "value 4"
  } ]
}
```

如果启用兼容模式且 transform 具有以下设置：

- Json block name = "data"
- Nr rows in block = 3
- 'Compatibility mode' 已勾选

这将输出：

第一个文件：
```json
{
    "data": [
        {
            "name": "item 1"
        },
        {
            "value": "value 1"
        },
        {
            "name": "item 2"
        },
        {
            "value": "value 2"
        },
        {
            "name": "item 3"
        },
        {
            "value": "value 3"
        }
    ]
}
```
第二个文件：
```json
{
    "data": [
        {
            "name": "item 4"
        },
        {
            "value": "value 4"
        }
    ]
}
```

如您所见，启用兼容模式后，每个字段将作为单独的对象处理。
