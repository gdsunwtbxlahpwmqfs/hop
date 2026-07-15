# JavaScript

## 描述

`JavaScript` action 运行 Javascript 代码并返回一个布尔表达式。

结果可用于确定接下来要执行哪个 action。

您可以使用函数、过程调用、AND、&、OR、EQUAL 等。

Javascript workflow action 会进行评估并返回 true 或 false。

## 选项

| 选项 | 描述 |
|---|---|
| Action 名称 | workflow action 的名称。 |
| JavaScript | JavaScript 字段。 |

## 示例
```
typeof parent_workflow.getVariable("DETAIL_ID_DATASOURCE_ID") != "undefined" && parent_workflow.getVariable("DETAIL_ID_DATASOURCE_ID").toString() == ""
```

```
parent_workflow.getVariable("API_RETRY_COUNTER") != parent_workflow.getVariable("API_RETRY_LIMIT")
```

## 故障排除
- 尽量减少 JavaScript 中的换行。

## 评估

JavaScript action 的结果为 true 或 false。
换句话说，它需要以布尔表达式结束。

以下是一些可用于结束脚本的评估示例：

lines_input > 100

或者

true

或者

parent_workflow.getVariable("INPUT_DIRECTORY").equals("/tmp");

以下变量可用于表达式：

| 变量 | 描述 |
|---|---|
| ```errors``` | 上一个 workflow action 中的错误数（long）。 |
| ```lines_input``` | 从数据库或文件读取的行数（long）。 |
| ```lines_output``` | 写入数据库或文件的行数（long）。 |
| ```lines_updated``` | 数据库表中更新的行数（long）。 |
| ```lines_read``` | 从上一个 pipeline transform 读取的行数（long）。 |
| ```lines_written``` | 写入下一个 pipeline transform 的行数（long）。 |
| ```files_retrieved``` | 从 FTP 服务器检索的文件数（long）。 |
| ```exit_status``` | shell 脚本的退出状态（整数）。 |
| ```nr``` | 上一个 workflow action 的 workflow action 编号（long）；每执行下一个 workflow action 时递增。 |
| ```is_windows``` | 如果 Hop 在 Windows 上运行则使用（布尔值）。 |
| ```parent_workflow``` | 当前 workflow action 的父 workflow。 |
| ```__action__``` | 当前 workflow action。 |

## 变量

以下是评估变量字符串内容的方法：

parent_workflow.getVariable("NR_OF_ROWS") == 1000000;

由于我们可以访问 parent_workflow 对象，因此也可以通过这种方式在父 workflow 中设置变量：

parent_workflow.setVariable("NR_OF_ROWS", "1000000");

例如，您可以执行以下操作来在此 workflow action 中操作变量：

```javascript
useDate = parent_workflow.getVariable("use_date").equals("1");
if (useDate == 0) {
  date = new java.util.Date();
  date.setDate(date.getDate()-1); //Go back 1 full day
  dateFormat = new java.text.SimpleDateFormat("yyyyMMdd");
  newDateStr = dateFormat.format(date);
  parent_workflow.setVariable("start_date", newDateStr);
}
true;
```

## 上一步结果

当一个 workflow action 完成时，执行结果将是一个 Result 对象，以 "previous_result" 暴露给 JavaScript 引擎：

| 表达式 | 替代 | 数据类型 | 含义 |
|---|---|---|---|
| ```previous_result.getResult()``` |  | boolean | 如果上一个 workflow action 成功执行则为 true，如果有错误则为 false。 |
| ```previous_result.getExitStatus()``` | exit_status | int | 上一个 shell 脚本 workflow action 的退出状态 |
| ```previous_result.getActionNr()``` | nr | int | action 编号，每次执行 workflow action 时递增。 |
| ```previous_result.getNrErrors()``` | errors | long | 错误数量，也可通过变量 "errors" 获取。 |
| ```previous_result.getNrLinesInput()``` | lines_input | long | 从文件或数据库读取的行数。 |
| ```previous_result.getNrLinesOutput()``` | lines_output | long | 写入文件或数据库的行数。 |
| ```previous_result.getNrLinesRead()``` | lines_read | long | 从上一个 transform 读取的行数。 |
| ```previous_result.getNrLinesUpdated()``` | lines_updated | long | 在文件或数据库中更新的行数。 |
| ```previous_result.getNrLinesWritten()``` | lines_written | long | 写入下一个 transform 的行数。 |
| ```previous_result.getNrLinesDeleted()``` | lines_deleted | long | 删除的行数。 |
| ```previous_result.getNrLinesRejected()``` | lines_rejected | long | 被拒绝并通过错误处理传递给另一个 transform 的行数。 |
| ```previous_result.getRows()``` |  | List<RowMetaAndData> | 结果行，另见下文。 |
| ```previous_result.isStopped()``` |  | boolean | 标志，用于指示上一个 workflow action 是否已停止。 |
| ```previous_result.getResultFilesList()``` |  | List<ResultFile> | 上一个 workflow action 中使用的所有文件列表。 |
| ```previous_result.getNrFilesRetrieved()``` | files_retrieved | int | 从 FTP、SFTP 等检索的文件数。 |
| ```previous_result.getLogText()``` |  | String | 上一个 workflow action 及其子 action 的执行日志文本。 |
| ```previous_result.getLogChannelId()``` |  | String | 上一个 workflow action 的日志通道 ID。 |

### 行

我们暴露给 JavaScript 的 "rows" 变量可帮助您评估使用 "Copy rows to result" transform 传递给下一个 workflow action 的结果行。
以下是如何使用此数组的示例脚本：

```javascript
var firstRow = rows[0];
 
firstRow.getString("name", "").equals("Foo")
```

 如果表达式评估为 true，此脚本将沿着绿色的 workflow 跳转执行。当字段 "name" 包含字符串 "Foo" 时会发生这种情况。
