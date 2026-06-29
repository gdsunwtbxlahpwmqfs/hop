# EDI 转 XML（Edi to XML）

EDI 转 XML 转换将符合 ISO 9735 标准（[EDIFACT](https://en.wikipedia.org/wiki/EDIFACT)）的 EDI 消息文本转换为通用 XML。转换后的 XML 文本更易于访问，允许使用 XPath 和 Get Data From XML 转换进行选择性数据提取。

## 使用说明

- 转换配置需要包含 EDI 文本的字段名，以及 XML 文本的输出字段名。
- 如果输出字段名留空，EDI 文本将被 XML 文本替换。
- XML 输出结构遵循如下模式：

```xml
<edifact>
  <SEGMENT>
    <element>
      <value></value>
    </element>
  </SEGMENT>
</edifact>
```

## 注意事项

- 在 Spark、Flink、Dataflow 引擎上可能受支持（Maybe Supported），Hop Engine 完全支持。
- 输入需为符合 ISO 9735（EDIFACT）标准的 EDI 消息文本。
