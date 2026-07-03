# Pipeline: 0010-get-data-from-xml-nested

## Basic Information

- **Pipeline Name:** 0010-get-data-from-xml-nested
- **Source File:** `03-转换插件/输入类/samples/0010-get-data-from-xml-nested.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate filePath | RowGenerator |
| Get Batters | getXMLData |
| Get items | getXMLData |

## Hops

| From | To |
|------|----|
| Generate filePath | Get items |
| Get items | Get Batters |
| Get Batters | Dummy (do nothing) |
