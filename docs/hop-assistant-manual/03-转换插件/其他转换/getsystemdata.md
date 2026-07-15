# ![Get System Info transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/systeminfo.svg) Get System Info

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 名称，此名称在单个 Pipeline 中必须唯一。 |
| Name | 您希望为特定数据类型指定的名称 |
| Type | 可用系统数据类型的列表 |

## 信息类型

| Function | Description | Serialization code (for metadata injection) |
|---|---|---|
| System date (variable) | 系统时间，每次查询日期时都会变化。 | `system date (variable)` |
| System date (fixed) | 系统时间，在 Pipeline 启动时确定。 | `system date (fixed)` |
| Start date range (Pipeline) | 日期范围的开始，基于 ETL 日志表中的信息。 |  |
| End date range (Pipeline) | 日期范围的结束，基于 ETL 日志表中的信息。 |  |
| Start date range (Workflow) | 日期范围的开始，基于 ETL 日志表中的信息。 |  |
| End date range (Workflow) | 日期范围的结束，基于 ETL 日志表中的信息。 |  |
| Yesterday 00:00:00 | 昨天的开始。 | `yesterday start` |
| Yesterday 23:59:59 | 昨天的结束。 | `yesterday end` |
| Today 00:00:00 | 今天的开始。 | `today start` |
| Today 23:59:59 | 今天的结束。 | `today end` |
| Tomorrow 00:00:00 | 明天的开始。 | `tomorrow start` |
| Tomorrow 23:59:59 | 明天的结束。 | `tomorrow end` |
| First day of last month 00:00:00 | 上个月的开始。 | `last month start` |
| Last day of last month 23:59:59 | 上个月的结束。 | `last month end` |
| First day of this month 00:00:00 | 本月的开始。 | `this month start` |
| Last day of this month 23:59:59 | 本月的结束。 | `this month end` |
| First day of next month 00:00:00 | 下个月的开始。 | `next month start` |
| Last day of next month 23:59:59 | 下个月的结束。 | `next month end` |
| Copy of transform | Transform 的副本编号。 |  |
| Pipeline name | Pipeline 的名称。 | This code has a leading white space: `{sp}pipeline name` |
| Pipeline file name | Pipeline 的文件名（仅 XML）。 | This code has leading white space :  `{sp}pipeline file name` |
| User that modified the pipeline last |  | `User modified` |
| Date when the pipeline was modified last |  | `Date modified` |
| Hostname (Network setup) | 返回服务器的主机名。 | `Hostname` |
| Hostname (Real) |  | `Hostname real` |
| IP address | 返回服务器的 IP 地址。 | `IP address` |
| Current process identifier (PID) | Java 进程当前运行所在的 PID | `Current PID` |
| JVM max memory |  | `jvm max memory` |
| JVM total memory |  | `jvm total memory` |
| JVM free memory |  | `jvm free memory` |
| JVM available memory |  | `jvm available memory` |
| Available processors |  | `available processors` |
| JVM CPU time (milliseconds) |  | `jvm cpu time` |
| Total physical memory size (bytes) |  | `total physical memory size` |
| Total swap space size (bytes) |  | `total swap space size` |
| Committed virtual size (bytes) |  | `committed virtual memory size` |
| Free physical memory size (bytes) |  | `free physical memory size` |
| Free swap space size (bytes) |  | `free swap space size` |
| First day of last week 00:00:00 |  | `last week start` |
| Last day of last week 23:59:59 |  | `last week end` |
| Last working day of last week 23:59:59 |  | `last week open end` |
| First day of last week 00:00:00 (US) |  | `last week start us` |
| Last day of last week 23:59:59 (US) |  | `last week end us` |
| First day of this week 00:00:00 |  | `this week start` |
| Last day of this week 23:59:59 |  | `this week end` |
| Last working day of this week 23:59:59 |  | `this week open end` |
| First day of this week 00:00:00 (US) |  | `this week start us` |
| Last day of this week 23:59:59 (US) |  | `this week end us` |
| First day of next week 00:00:00 |  | `next week start` |
| Last day of next week 23:59:59 |  | `next week end` |
| Last working day of next week 23:59:59 |  | `next week open end` |
| First day of next week 00:00:00 (US) |  | `next week start us` |
| Last day of next week 23:59:59 (US) |  | `next week end us` |
| First day of last quarter 00:00:00 |  | `prev quarter start` |
| Last day of last quarter 23:59:59 |  | `prev quarter end` |
| First day of this quarter 00:00:00 |  | `this quarter start` |
| Last day of this quarter 23:59:59 |  | `this quarter end` |
| First day of next quarter 00:00:00 |  | `next quarter start` |
| Last day of next quarter 23:59:59 |  | `next quarter end` |
| First day of last year 00:00:00 |  | `prev year start` |
| Last day of last year 23:59:59 |  | `prev year end` |
| First day of this year 00:00:00 |  | `this year start` |
| Last day of this year 23:59:59 |  | `this year end` |
| First day of next year 00:00:00 |  | `next year start` |
| Last day of next year 23:59:59 |  | `next year end` |
| Previous action result |  | `previous result result` |
| Previous action exit status |  | `previous result exist status` |
| Previous action nr |  | `previous result entry nr` |
| Previous action nr errors |  | `previous result nr errors` |
| Previous action nr lines input |  | `previous result nr lines input` |
| Previous action nr lines output |  | `previous result nr lines output` |
| Previous action nr lines read |  | `previous result nr lines read` |
| Previous action nr lines updated |  | `previous result nr lines updated` |
| Previous action nr lines written |  | `previous result nr lines written` |
| Previous action nr lines deleted |  | `previous result nr lines deleted` |
| Previous action nr lines rejected |  | `previous result nr lines rejected` |
| Previous action nr rows |  | `previous result nr rows` |
| Previous action stopped |  | `previous result is stopped` |
| Previous action nr files |  | `previous result nr files` |
| Previous action nr files retrieved |  | `previous result nr files retrieved` |
| Previous action log text |  | `previous result log text` |
