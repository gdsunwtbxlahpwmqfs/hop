# ![Excel writer transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/excelwriter.svg) Excel writer

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 输出格式

| Format | Extension | Backend | Notes |
|---|---|---|---|
| Excel 97–2003 | `.xls` | POI | 支持工作表密码保护 |
| Excel 2007+ | `.xlsx` | POI | 大文件流式模式；不支持工作表密码保护 |
| OpenDocument Spreadsheet | `.ods` | ODFDOM | 与 LibreOffice Calc 兼容；见下文 |

## 选项

### 文件和工作表选项卡

*文件部分*

| Option | Description |
|---|---|
| Extension | 选择 `xls`、`xlsx` 或 `ods`。这决定了输出文件格式。 |
| Stream XLSX data | 写入大型 XLSX 文件时勾选此选项（不适用于 `.xls` 或 `.ods`）。 |
| Create parent folder | 启用以创建父文件夹 |
| If output file exists | 选择重用现有文件或创建新文件。 |
| Add filename(s) to result | 勾选以将文件名添加到结果文件名中 |
| Wait for first row before creating file | 勾选此选项使 Transform 仅在看到一行数据后才创建文件。 |

*工作表部分*

| Option | Description |
|---|---|
| Sheet Name | Transform 将向其写入行的工作表名称。 |
| Make this the active sheet | 如果勾选，电子表格文件将默认在此工作表上打开（在 Excel、LibreOffice Calc 等中）。 |
| If sheet exists in output file | 输出文件已有此工作表（例如使用模板或写入现有文件时），你可以选择写入现有工作表或替换它。 |
| Protect Sheet | 使用可选密码锁定工作表。支持 `.xls` 和 `.ods` 输出。*protected by user* 字段仅适用于 `.xls`。不支持 `.xlsx`。 |

*模板部分*

创建新文件时（当替换现有文件或创建全新文件时），你可以选择创建现有模板文件的副本。
模板和输出文件必须使用相同的扩展名（`.xls`、`.xlsx` 或 `.ods`）。

创建新工作表时，Transform 可以从当前文档（模板或 Transform 正在写入的其他现有文件）中复制工作表。
如果目标工作表不存在，或者根据上面的配置要替换现有工作表，则创建新工作表。

### 内容选项卡

*内容选项部分*

| Option | Description |
|---|---|
| Start writing at cell | 这是以 Excel 表示法（字母列，数字行）开始写入的单元格 |
| When writing rows | Transform 可以覆盖现有单元格（快速），或将现有单元格向下移动（在工作表顶部追加新行） |
| Write Header | 如果勾选，写入的第一行将包含字段名 |
| Write Footer | 如果勾选，写入的最后一行将包含字段名 |
| Auto Size Columns | 如果勾选，Transform 尝试自动调整列宽以适应其内容。 |
| Force formula recalculation a | 如果勾选，Transform 尝试确保输出文件中的所有公式字段都已更新。 |
| Leave styles of existing cells unchanged | 如果勾选，Transform 不会尝试设置其正在写入的现有单元格的样式。 |

*写入现有工作表部分*

| Option | Description |
|---|---|
| Start writing at end of sheet | Transform 将尝试找到工作表的最后一行，并从那里开始写入。 |
| Offset by ... rows | 任何非 0 的数字将导致 Transform 在写入行之前向下（正数）或向上（负数）移动此行数。 |
| Begin by writing ... empty lines | 当选择 *shift existing cells down* 时，将在写入位置插入空行而不是简单地跳过。 |
| Omit Header | 追加到现有工作表时跳过标题行。 |

*字段部分*

字段部分用于指定必须写入 Excel 文件的字段。你可以选择使用 [Schema Definition](metadata-types/static-schema-definition.md) 或手动定义所需字段的布局。

如果你决定使用 [Schema Definition](metadata-types/static-schema-definition.md) 来定义字段布局，请使用 [Schema Mapping](pipeline/transforms/schemamapping.md) Transform 根据所选的 [Schema Definition](metadata-types/static-schema-definition.md) 调整传入流

`ignore manual fields` 忽略在 Transform 字段布局中手动定义的任何字段，仅使用 [Schema Definition](metadata-types/static-schema-definition.md) 中指定的布局。

| Option | Description |
|---|---|
| Schema Definition | 我们要引用的 [Schema Definition](metadata-types/static-schema-definition.md) 的名称。 |
| Name | 要写入的字段 |
| Type | 数据类型 |
| Format | 工作表中要使用的 Excel 格式。 |
| Style from cell | 从中复制样式的单元格（例如 A1、B3 等）（通常是模板中某个预设置样式的单元格） |
| Field Title | 如果设置，则用于标题/页脚代替 Hop 字段名 |
| Header/Footer style from cell | 从中复制标题/页脚样式的单元格（通常是模板中某个预设置样式的单元格） |
| Field Contains Formula | 如果字段包含 Excel 公式（无前导 '='），则设为 Yes。 |
| Hyperlink | 一个包含链接目标的字段。 |
| Cell Comment / Cell Author | 注释写入 `.xlsx` 和 `.ods`（OpenDocument 注释）。 |

## ODS 输出说明和限制

`.ods` 后端在 OpenDocument 格式允许的情况下，支持与 `.xls`/`.xlsx` 相同的 Transform 对话框选项。
已知差异：

- *公式* — Excel 语法转换为 OpenFormula（`of:=...`）。复杂的 Excel 专用函数可能无法转换。公式结果不由 Hop 计算；LibreOffice Calc 在打开文件时重新计算。
- *注释* — 存储为 ODF 注释。在 LibreOffice Calc 中可见；Microsoft Excel 可能会忽略 `.ods` 文件中的注释。
- *超链接* — 存储为 ODF `text:a` 元素。
- *格式掩码* — Excel 格式字符串以尽力而为的方式映射到 ODF 数字格式。
- *样式复制* — 复制引用单元格的样式名称，而非完整的 POI 单元格样式对象。
- *工作表保护* — 使用 ODF `table:protected` 和 SHA-1 密码哈希（与 LibreOffice Calc 兼容）。*protected by user* 字段不用于 `.ods`。
- *流式处理* — *Stream XLSX data* 选项仅适用于 `.xlsx`。
- *工作表名称* — `.ods` 不强制执行 31 个字符的 Excel 工作表名称限制。
