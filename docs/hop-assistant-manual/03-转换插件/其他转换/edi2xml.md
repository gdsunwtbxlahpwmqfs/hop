# ![Edi to XML transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/edi2xml.svg) Edi to XML

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

该 Transform 配置需要包含 EDI 文本的字段名，以及用于 XML 文本的输出字段名。
如果输出字段名留空，EDI 文本将被 XML 文本替换。

XML 输出的结构遵循以下模式：

```xml
<edifact>
	<SEGMENT>
		<element>
			<value></value>
			...
		</element>
		...
	</SEGMENT>
	...
</edifact>
```

转换规则如下：

- 文档的根节点是 "edifact" 标签
- edifact 消息中的每个段被转换为一个标签，使用段名作为标签名。
- 段中的每个字段由 "element" 标签表示
- 字段中的每个值由 "value" 标签表示
