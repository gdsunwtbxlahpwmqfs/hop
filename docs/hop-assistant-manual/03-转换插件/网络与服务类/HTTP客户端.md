# HTTP 客户端（HTTP client）

HTTP 客户端转换使用附加选项对基础 URL 执行简单调用。调用格式如下：

```
http://<URL>?param1=value1&param2=value2&param3..
```

结果会存储在具有指定名称的字符串字段中。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| URL | 基础 URL 字符串。 |
| 是否从字段接受 URL？（Accept URL from field?） | 如果您希望从上一个转换获取 URL，请启用此选项。启用后还可指定输入字段的名称。 |
| URL 字段名（URL field name） | 包含 URL 的传入字段名称。 |
| 连接超时（Connection timeout） | 连接超时时间。 |
| 套接字超时（Socket timeout） | 如果服务器未返回数据时需等待的秒数。 |
| 连接关闭等待时间（Connection close wait time） | 连接关闭后的等待时间。 |
