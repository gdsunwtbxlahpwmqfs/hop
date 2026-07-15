# ![Row Normaliser transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/normaliser.svg) Row Normaliser

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Typefield | 类型字段的名称（下面示例中的 **key**）。 |
| Fields table |  |
| Get Fields | 点击以检索流中传入的所有字段列表。 |

## 示例

### 输入数据

| RecordID | FirstName | LastName | City |
|---|---|---|---|
| 345-12-0000 | Mitchel | Runolfsdottir | Jerryside |
| 976-67-7113 | Elden | Welch | Lake Jamaal |
| 824-21-0000 | Rory | Ledner | Scottieview |

### 规范化数据（示例 1）
设置 **Typefield** = "key" 并使用 **Get Fields** 按钮加载所有要规范化的字段，同时在所有行中设置 **New field** = "value"。结果为：

| key | value |
|---|---|
| RecordID | 345-12-0000 |
| FirstName | Mitchel |
| LastName | Runolfsdottir |
| City | Jerryside |
| RecordID | 976-67-7113 |
| FirstName | Elden |
| LastName | Welch |
| City | Lake Jamaal |
| RecordID | 824-21-0000 |
| FirstName | Rory |
| LastName | Ledner |
| City | Scottieview |

### 规范化数据（示例 2）
与示例 1 类似，但从 **Fields table** 中移除 **RecordID** 字段。结果为：

| RecordID | key | value |
|---|---|---|
| 345-12-0000 | FirstName | Mitchel |
| 345-12-0000 | LastName | Runolfsdottir |
| 345-12-0000 | City | Jerryside |
| 976-67-7113 | FirstName | Elden |
| 976-67-7113 | LastName | Welch |
| 976-67-7113 | City | Lake Jamaal |
| 824-21-0000 | FirstName | Rory |
| 824-21-0000 | LastName | Ledner |
| 824-21-0000 | City | Scottieview |
