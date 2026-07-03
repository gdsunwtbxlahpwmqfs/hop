# Pipeline: get-data-from-xml

## Basic Information

- **Pipeline Name:** get-data-from-xml
- **Source File:** `03-转换插件/输入类/samples/get-data-from-xml.hpl`

## Transforms

| Name | Type |
|------|------|
| Get data from XML | getXMLData |
| sample xml | DataGrid |
| clean | SelectValues |
| preview | Dummy |

## Hops

| From | To |
|------|----|
| sample xml | Get data from XML |
| Get data from XML | clean |
| clean | preview |

## Notes

Sample XML taken from https://www.javatpoint.com/xml-example

---
