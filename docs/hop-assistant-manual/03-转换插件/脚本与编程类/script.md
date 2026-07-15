# ![Script transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/script.svg) Script

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |  |
| Script engine |  |
| 您可以从下拉列表中选择任何已发现的 JSR-223 脚本引擎。 |  |
| Script |  |
| 您可以添加一个或多个脚本。有不同类型的脚本，您可以通过右键单击脚本选项卡来更改。 |  |
| Fields |  |
| 在此处您可以指定每行之后从 Transform 脚本上下文中检索的字段。 |  |

## 变量绑定

为了让您能够使用输入字段值或周围的 Java 生态系统，Hop 暴露了一组变量绑定。

| Variable | Description |
|---|---|
| input |  |
| 所有输入字段使用各自的名称。请注意，某些脚本语言对变量名有限制。请确保使用 "Select Values" transform 重命名不合适的字段名。 |  |
| `transform` |  |
| 这是对父 Transform 类 [Script](https://github.com/apache/hop/blob/master/plugins/transforms/abort/src/main/java/org/apache/hop/pipeline/transforms/script/Script.java) 的引用。您可以使用它来记录某些重要事件或写入额外的输出行 |  |
| `pipeline_status` |  |
| 此特殊变量可以设置为以下任意值： |  |
| `rowNumber` |  |
| 当前行号，从 1 开始。 |  |
| `rowMeta` |  |
| 输入行的 metadata。它是一个值 metadata 列表。 |  |
| `row` |  |
| 包含当前字段值集的对象数组。请确保使用 `rowMeta` 来寻址这些值，以确保进行适当的数据转换。 |  |
| `previousRow` |  |
| 前一行，如果 `row` 是第一行输入则为 null。 |  |
| `transformName` |  |
| Transform 的名称 |  |
| `pipelineName` |  |
| Pipeline 的名称 |  |

## 生成行

以下是使用简单循环在 3 种不同脚本引擎中生成 10 个输出行的脚本。这将执行 3 次，输出完全相同。对于这 3 个示例，您需要定义 2 个输出字段：

- `id` : 一个 Integer
- `name` : 一个 String

### ECMAScript

```javascript
var Long = Packages.java.lang.Long;
var RowDataUtil = Packages.org.apache.hop.core.row.RowDataUtil;

var START=100000;
var COUNT=10;
var END=START+COUNT;
var id=START;

for (var id=START;id<END;id++) {
  var outputRow = RowDataUtil.allocateRowData(rowMeta.size());
  outputRow[0] = new Long(id);
  outputRow[1] = "Qi Hop "+id;
  transform.putRow(outputRowMeta, outputRow);
}

pipeline_status=SKIP_PIPELINE;
```

### Groovy

```groovy
def COUNT=10;

id = 100000L;
(1..COUNT).each {
 outputRow = RowDataUtil.allocateRowData(rowMeta.size());
 outputRow[0] = id;
 outputRow[1] = "Qi Hop "+id
 transform.putRow(outputRowMeta, outputRow);

 id++;
}

pipeline_status=SKIP_PIPELINE;
```

### Python

```python
import java.lang.Long as Long

START=100000
COUNT=10
END=START+COUNT
id=START

for id in range(START,END):
	outputRow = RowDataUtil.allocateRowData(rowMeta.size())
	outputRow[0] = Long(id)
	outputRow[1] = "Qi Hop "+str(id)
	transform.putRow(outputRowMeta, outputRow)

pipeline_status=SKIP_PIPELINE
```

## 聚合行

以下是按不同组聚合行的脚本。数据按字段 `group` 排序，包含一个 `value` 字段，累加到 `sum` 字段中。
在启动脚本中我们定义变量 `sum=0` 和 `previousGroup=""`。

对于这 3 个示例，您需要定义 1 个输出字段：

- `sum` : 一个 Integer

### ECMAScript

```javascript
if (group!==previousGroup) {
  sum=value;
  previousGroup=group;
} else {
  sum+=value;
}

if (nextGroup==null) {
  pipeline_status=CONTINUE_PIPELINE;
} else {
  pipeline_status=SKIP_PIPELINE;
}
```

### Groovy

```groovy
if (!group.equalsIgnoreCase(previousGroup)) {
  sum=value;
  previousGroup=group;
} else {
  sum+=value;
}

if (nextGroup==null) {
  pipeline_status=CONTINUE_PIPELINE;
} else {
  pipeline_status=SKIP_PIPELINE;
}
```

### Python

```python
if group!=previousGroup:
  sum=value
  previousGroup=group
else:
  sum=sum+value

if nextGroup is None:
  pipeline_status=CONTINUE_PIPELINE
else:
  pipeline_status=SKIP_PIPELINE;
```

## 添加脚本语言

您可以通过将所需的库添加到 `plugins/transforms/script/lib` 文件夹来添加额外的脚本语言。

例如，要添加对 Ruby 脚本语言的支持，您需要添加以下 [JRuby 库](https://mvnrepository.com/artifact/org.jruby)：

- `jruby-stdlib-<version>.jar`
- `jruby-core-<version>.jar`

重启 Qi Hop GUI 后，您会注意到 `Scripting Engine` 下拉框中出现了 `ruby` 条目。
