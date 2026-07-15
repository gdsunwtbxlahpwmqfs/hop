# Apache Beam Direct Pipeline Engine

## Beam Direct

Direct runner 可用于本地测试和开发。

Direct Runner 在您的机器上执行 pipeline，旨在验证 pipeline 尽可能严格地遵循 Apache Beam 模型。
Direct Runner 不专注于高效的 pipeline 执行，而是执行额外的检查以确保用户不会依赖于模型未保证的语义。

- 强制元素的不可变性
- 强制元素的可编码性
- 元素在所有位置以任意顺序处理
- 用户函数的序列化（DoFn、CombineFn 等）

使用 Direct Runner 进行测试和开发有助于确保 pipeline 在不同的 Beam runner 之间保持稳健。
此外，当 pipeline 在远程集群上执行时，调试失败的运行可能是一项艰巨的任务。
相反，对 pipeline 代码进行本地单元测试通常更快、更简单。
在本地对 pipeline 进行单元测试还允许您使用首选的本地调试工具。

### 选项

| 选项 | 说明 |
|---|---|
| Number of workers | 用于配置并行度的线程或子进程数量。 |
| User agent | 按照 [RFC2616](https://tools.ietf.org/html/rfc2616) 格式的用户代理字符串，用于向外部服务描述 pipeline。 |
| Temp location | 临时文件的路径。 |
| Plugins to stage (, delimited) | 逗号分隔的 plugin 列表。 |
| Transform plugin classes | Transform plugin 类列表。 |
| XP plugin classes | 扩展点 plugin 类列表。 |
| Streaming Hop transforms flush interval (ms) | 内部缓冲区通过网络完全发送并清空的时间间隔。 |
| Hop streaming transforms buffer size | 使用的内部缓冲区大小。 |
| Fat jar file location | Fat jar 位置。 |
