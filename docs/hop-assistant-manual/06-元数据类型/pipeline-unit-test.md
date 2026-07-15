# Pipeline 单元测试

## 描述

![](../assets/images/icons/Test_tube_icon.svg)

描述 Pipeline 的测试。在测试期间，使用替代数据集作为给定 transform 的输入，并将输出与黄金数据进行测试。

当 Pipeline 执行产生的结果集与预期结果（黄金数据集）匹配时，测试成功。如果生成的结果与预期不符，测试失败。

Pipeline 中的特定 transform 可以被绕过或移除以进行测试，提供额外的灵活性。

此外，单元测试可以批量执行。

查看 [Pipeline 单元测试](../07-管道/pipeline-unit-testing.md)了解更多详情。

## 相关 plugin

无/全部

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Name |  | 此单元测试的名称 |
| Description |  | 此单元测试的描述 |
| Type of test | Development | 'Development' 或 'unit test' |
| The pipeline to test |  | 将被此单元测试测试的 Pipeline |
| Test pipeline filename (Optional) |  |  |
| Base test path (or use HOP_UNIT_TESTS_FOLDER) |  |  |
| Select this test automatically | false |  |
| Replace a database connection with another |  | 要测试的 Pipeline 中的数据库连接（Original DB）列表，替换为此单元测试中的数据库连接（Replacement DB） |
| Variables |  | 用于此测试的变量名和值的列表 |

## 示例

无
