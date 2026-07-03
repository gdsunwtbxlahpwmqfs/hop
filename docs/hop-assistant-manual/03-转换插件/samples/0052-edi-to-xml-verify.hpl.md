# Pipeline: 0052-edi-to-xml-verify

## Basic Information

- **Pipeline Name:** 0052-edi-to-xml-verify
- **Source File:** `03-转换插件/samples/0052-edi-to-xml-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| EDI to XML | TypeExitEdi2XmlTransform |
| Verify | Dummy |
| flatten XML | XMLInputStream |
| limit | SelectValues |
| sample.edi | LoadFileInput |

## Hops

| From | To |
|------|----|
| sample.edi | EDI to XML |
| EDI to XML | flatten XML |
| flatten XML | limit |
| limit | Verify |

## Notes

Sample taken from the EDIFACT wikipedia page:

https://en.wikipedia.org/wiki/EDIFACT

---
