# ![Split Fields transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/fieldsplitter.svg) Split Fields

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称；此名称在单个 Pipeline 中必须唯一 |
| Field to split | 要拆分的字段名称 |
| Delimiter | 确定字段的分隔符。 |
| Enclosure | 可以指定一个包裹字符串，将其放置在值周围时允许分隔符出现在值中。 |
| Escape string | 有时需要在值中包含分隔符字符，可使用转义字符串如反斜杠、双反斜杠等。 |
| Fields table | 此表格用于定义拆分创建的每个新字段的属性。 |

## 示例

以下是拆分字段的示例：

### 示例 1

SALES_VALUES 字段包含："500,300,200,100"

使用以下设置将字段拆分为四个新字段：

- Delimiter: ,
- Field: SALES1, SALES2, SALES3, SALES4
- Id:
- remove ID no, no, no, no
- type: Number, Number, Number, Number
- format: ###.##, ###.##, ###.##, ###.##
- group:
- decimal: .
- currency:
- length: 3, 3, 3, 3
- precision: 0, 0, 0, 0

### 示例 2

SALES_VALUES 字段包含 "Sales2=310.50, Sales4=150.23"

使用以下设置将字段拆分为四个新字段：

- Delimiter: ,
- Field: SALES1, SALES2, SALES3, SALES4
- Id: Sales1=, Sales2=, Sales3=, Sales4=
- remove ID yes, yes, yes, yes
- type: Number, Number, Number, Number
- format: ###.##, ###.##, ###.##, ###.##
- group:
- decimal: .
- currency:
- length: 7, 7, 7, 7
- precision: 2, 2, 2, 2
