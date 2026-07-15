# ![Call DB procedure transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/dbproc.svg) Call DB procedure

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称；该名称在单个 Pipeline 中必须唯一 |
| Connection | 存储过程所在的数据库连接名称 |
| Proc-name | 要调用的存储过程或函数的名称 |
| Find it | 点击可在指定的数据库连接上搜索可用的存储过程和函数（仅限 Oracle 和 SQL Server） |
| Enable auto | 在某些情况下你希望执行更新 |
| commit | 通过指定的存储过程在数据库中执行。 |
| Result name | 函数调用结果的名称；如果是存储过程则留空 |
| Result type | 函数调用结果的类型；在存储过程的情况下不使用。 |
| Parameters a | 存储过程或函数所需的参数列表 |
| Get Fields | 填充输入流中的所有字段以方便操作；删除不需要的行并重新排列剩余行的顺序 |
