# 简单评估

## 描述

`Simple evaluation` action 评估结果流中变量或字段的内容或值。

用 Simple Evaluation 的输出连接两个 action。

如果条件评估成功，将调用绿色（成功）hop；如果条件评估不成功，将调用红色（失败）hop。

## 用法

- Null 值等同于空字符串。例如，要检测任何数据类型的 null，请将 Type 设置为 String，将 Success condition 设置为 "If value is equal to"，并使 Value 文本框为空。

- Simple evaluation action 功能有限。对于更复杂的条件，请参见：https://hop.apache.org/manual/latest/workflow/actions/eval.html

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Evaluate | 评估类型：字段或变量。 |
| Field name | 要评估的字段 |
| Type | 数据类型。 |
| Success when source variable set | 如果源变量已设置，则 action 始终成功，否则将失败。 |
| Success condition a |  |
| Value | 要比较的值。 |

## 技巧

**评估变量**

| 字段 | 值 | 说明 |
|---|---|---|
| Source: Evaluate | Variable | 选择此项以评估之前设置的变量 |
| Source: Variable name | 例如 {openvar}VAR1{closevar} | 使用常规语法输入变量名 |
| Source: Type | String、Number 等 | 变量的类型 |
| Success on: Success when source variable set |  | 选择此选项时，当源变量已设置时走 true 路径。 |
| Success On: Success condition | 等于/不等于等 | 选择为获得成功结果而需满足的条件 |
| Success On: Value |  | 用于与变量比较的值 |

**评估字段**

| 字段 | 值 | 说明 |
|---|---|---|
| Source: Evaluate | Field from previous result | 选择此项以评估字段值（由 pipeline 使用 Copy rows to result 生成） |
| Source: Field name |  | 输入结果行中的字段名 |
| Source: Type | String、Number 等 | 字段的类型 |
| Success On: Success condition | 等于/不等于等 | 选择为获得成功结果而需满足的条件 |
| Success On: Value |  | 用于与字段比较的值 |
