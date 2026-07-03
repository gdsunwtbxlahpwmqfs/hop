# Pipeline: 0004-concat-fields

## Basic Information

- **Pipeline Name:** 0004-concat-fields
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0004-concat-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| OUTPUT | Dummy |
| concat with comma | ConcatFields |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | concat with comma |
| concat with comma | OUTPUT |

## Notes

This reads a file with lazy conversion.

We check to see that the fields are concatenated but also that binary to normal

storage conversion correctly takes place in the transform.

---
