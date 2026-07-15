# Execution Data Profile

## 描述

Qi Hop Execution Data Profile 在数据流经 Pipeline 时构建数据概况。可以选择和配置多个数据采样器，以微调所概况数据的类型和细节。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此 Execution Data Profile 的名称 |
| Description | 此 Execution Data Profile 的描述 |
| Data Samplers to use | 与此 Execution Data Profile 一起使用的一个或多个数据采样器。详见下文。 |

## 数据采样器

| 数据采样器 | 描述 | 选项 |
|---|---|---|
| Data profile output rows | 允许对 transform 输出行执行一些基本的数据概况分析 |  |
| First output rows | 采样 transform 输出的前几行 | Sample size（默认：100） |
| Last output rows | 采样 transform 输出的最后几行 | Sample size（默认：100） |
| Random output rows | 对 transform 的输出行进行蓄水池采样 | Sample size（默认：100） |
