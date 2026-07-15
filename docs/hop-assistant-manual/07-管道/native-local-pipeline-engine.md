# 本地 Pipeline Engine

## 本地

本地 runner 在本地 Hop 引擎上运行。
这是您在开发时用于在本地机器上测试 pipeline 的运行时配置。

### 选项

| 选项 | 说明 | 默认值 |
|---|---|---|
| Row set size |  |  |
| 行集缓冲区大小。 |  |  |
| 10.000 |  |  |
| Safe mode |  |  |
| 检查通过 pipeline 的每一行，确保所有布局一致。 |  |  |
| false |  |  |
| Collect metrics |  |  |
| 收集指标以监控 pipeline 的性能。 |  |  |
| false |  |  |
| Sort transforms |  |  |
| 在执行前对 pipeline 中的 transform 执行[拓扑排序](https://en.wikipedia.org/wiki/Topological_sorting)。 |  |  |
| false |  |  |
| Log rows feedback |  |  |
| 启用日志行反馈，每处理 50.000（默认）行的倍数后显示一行日志。 |  |  |
| false |  |  |
| Feedback size in rows |  |  |
| 作为反馈返回的行数。 |  |  |
| 50.000 |  |  |
| Wait time buffer check (ms) |  |  |
| 这表示当 transform 的输入缓冲区中没有行时的轮询频率，值越低会在 pipeline 有许多空闲 transform 时导致更高的 CPU 负载。 |  |  |
| 20 |  |  |
| Sample type while running in the GUI |  |  |
| 在这里您可以指定当 pipeline 执行期间点击 transform 图标上的小网格图标时要查看哪些行。 |  |  |
| Last |  |  |
| Number of rows to sample in the GUI |  |  |
| 将收集的行数 |  |  |
| 100 |  |  |
| Make this pipeline transactional |  |  |
| 如果启用此选项，每个数据库将始终只使用一个连接。 |  |  |
| false |  |  |
