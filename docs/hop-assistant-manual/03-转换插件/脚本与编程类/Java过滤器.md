# Java过滤器（Java Filter）

Java过滤器转换允许使用用户自定义的 Java 表达式对流进行过滤。

来自一个或多个转换的输入流，可以根据所编写表达式的评估结果，被重定向到两个不同的转换。

换言之，用户可以使用纯 Java 表达式执行 if 语句来过滤数据流：

```java
if (Condition)
  {matching-transform}
else
  {non-matching transform}
```

## 主要选项

| 选项 | 说明 |
|------|------|
| Java 表达式（Condition） | 用于过滤的 Java 条件表达式，返回布尔值 |
| 匹配的目标转换（Send true data to transform） | 满足条件的行将被发送到的目标转换 |
| 不匹配的目标转换（Send false data to transform） | 不满足条件的行将被发送到的目标转换 |

## 注意事项

- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
