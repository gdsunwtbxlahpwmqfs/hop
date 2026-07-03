# Pipeline: 0009-xml-join-with-variables-5396

## Basic Information

- **Pipeline Name:** 0009-xml-join-with-variables-5396
- **Source File:** `03-转换插件/输出类/samples/0009-xml-join-with-variables-5396.hpl`

## Transforms

| Name | Type |
|------|------|
| OrderHeaders | ExcelInput |
| OrderList | ExcelInput |
| Placeholder | Constant |
| Placeholder 2 | Constant |
| XML Join transform | XMLJoin |
| xmlOrderHeaders | AddXML |
| xmlOrderList | AddXML |
| keep xmloutput1 | SelectValues |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| OrderHeaders | Placeholder 2 |
| OrderList | Placeholder |
| Placeholder | xmlOrderList |
| Placeholder 2 | xmlOrderHeaders |
| xmlOrderHeaders | XML Join transform |
| xmlOrderList | XML Join transform |
| XML Join transform | keep xmloutput1 |
| keep xmloutput1 | Copy rows to result |
