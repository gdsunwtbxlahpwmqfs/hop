# 运行 Pipeline 单元测试

## 描述

`Run pipeline unit tests` action 运行一系列选定的 [pipeline 单元测试](../../07-管道/pipeline-unit-testing.md)。

如果所有测试运行无误，则 action 成功。

错误将被记录到日志中。

## 选项

| 选项 | 描述 |
|---|---|
| Action Name | Action 的名称。 |
| Test names | 要执行的单元测试名称 |

使用 `Get test names` 获取当前项目中可用的单元测试列表。
