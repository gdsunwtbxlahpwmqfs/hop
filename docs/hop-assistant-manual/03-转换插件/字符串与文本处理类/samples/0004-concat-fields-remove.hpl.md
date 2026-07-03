# Pipeline: 0004-concat-fields-remove

## Basic Information

- **Pipeline Name:** 0004-concat-fields-remove
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0004-concat-fields-remove.hpl`

## Transforms

| Name | Type |
|------|------|
| Metadata structure of stream | TransformMetaStructure |
| OUTPUT | Dummy |
| concat with comma | ConcatFields |
| files/customers-100.txt | CSVInput |
| META | Dummy |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | concat with comma |
| concat with comma | OUTPUT |
| concat with comma | Metadata structure of stream |
| Metadata structure of stream | META |

## Notes

Make sure all fields except for "id" are removed from the output.

---
