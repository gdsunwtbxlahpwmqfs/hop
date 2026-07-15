# ![Enhanced JSON Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/JSO.svg) Enhanced JSON Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规选项卡

常规选项卡允许指定 Transform 操作的类型（为下一个 Transform 和/或文件输出 JSON），以及 Transform 的行为。

| Option | Description |
|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一。 |
| Operation a | 指定 Transform 操作类型。 |
| JSON block name | 如果指定，Transform 的输出将始终是一个带有单个一级节点的 JSON 对象，该节点的名称为此值。 |
| Output value | 生成的 JSON 块的输出字段名称（如果已启用）。 |
| Force arrays in JSON | 如果勾选，即使 Transform 结果是单个 JSON（对象）片段，输出也将是数组。 |
| Force single grouped Item | 如果勾选，值将按列分组，所有值将被包含在一个数组中。 |
| Pretty Print JSON | 如果勾选，JSON 输出将进行美化打印。 |

### 输出文件

| Option | Description |
|---|---|
| Filename | 输出文件的完整路径 |
| Append | 如果未勾选 - 每次 Transform 运行时都会创建新文件。 |
| Split JSON after n rows | 如果此数字 N 大于零，将生成的 JSON 文件拆分为每 N 行一个部分。 |
| Create Parent folder | 勾选此选项以创建文件夹结构（如果所提供路径中缺少某些文件夹）。 |
| Do not open create at start | 如果未勾选 - 文件（以及某些情况下的父文件夹）将在 Pipeline 初始化期间创建/打开以进行写入。 |
| Extension | 输出文件扩展名。 |
| Encoding | 输出文件编码 |
| Include date in filename? | 如果勾选 - 输出文件名将包含 File name 值 + 当前日期。 |
| Include time in filename | 如果勾选 - 输出文件名将包含文件创建时间。 |
| Show filename(s) button | 可用于测试完整输出文件路径 |
| Add file to result filenames? | 如果勾选 - 创建的输出文件路径可从 Transform 结果文件中访问 |

### Group Key 选项卡

此选项卡用于定义用于将行分组到 JSON 片段中的键字段。

键字段中具有相同值的行允许你从行数据生成 JSON 片段，并将它们分组到单个 JSON 数组中。此处定义的键字段也将原样转发到下一个 Transform。

如果未定义分组字段，所有行将被分组到一个 JSON 数组中，Transform 的输出将是单行单列。

| Option | Description |
|---|---|
| Fieldname | 输入 Transform 字段名，将有助于定义输入 Transform 字段键。 |
| Element name | JSON 元素名称。 |

### Fields 选项卡

此选项卡用于将输入 Transform 字段映射到输出 JSON 片段。

所选字段将被转换为 JSON 片段（通常是 JSON 对象），也可包含用于分组的字段（这些字段在所有片段中具有相同的值）。

然后这些片段将根据上面 Group Key 选项卡中定义的规则分组到 JSON 数组中。

| Option | Description |
|---|---|
| Fieldname | 输入 Transform 字段名。 |
| Element name | 在 JSON 中用于此字段的键名（可以与实际字段名不同）。 |
| JSON Fragment | 如果值设为 Y，则字段中包含的值是一个 JSON 片段，将被相应处理。 |
| Remove Element name | 如果值设为 Y，将忽略 Element name 并直接插入 JSON 片段而不进行包装。仅在 JSON Fragment = Y 时有效 |
| Remove if Blank | 如果值设为 Y 且输入字段中的值为 null，则相关属性将从 JSON 输出中省略 |

## 说明

请查看提供的示例 _json-output-generate-nested-structure.hpl_ 以更好地理解 Transform 的工作原理
