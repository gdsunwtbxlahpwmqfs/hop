# Pipeline: 0001-xml-join-test

## Basic Information

- **Pipeline Name:** 0001-xml-join-test
- **Source File:** `03-转换插件/输出类/samples/0001-xml-join-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Create XPath | ScriptValueMod |
| Create XPath 2 | ScriptValueMod |
| OrderHeaderComments | ExcelInput |
| OrderHeaders | ExcelInput |
| OrderLineComments | ExcelInput |
| OrderLines | ExcelInput |
| OrderList | ExcelInput |
| OrderSubLines | ExcelInput |
| Placeholder | Constant |
| Placeholder 2 | Constant |
| Placeholder 3x | Constant |
| XML Join transform | XMLJoin |
| XML Join transform 2 | XMLJoin |
| XML Join transform 3 | XMLJoin |
| XML Join transform 4 | XMLJoin |
| XML Join transform 5 | XMLJoin |
| xmlOrderHeaderComments | AddXML |
| xmlOrderHeaders | AddXML |
| xmlOrderLineComments | AddXML |
| xmlOrderLines | AddXML |
| xmlOrderList | AddXML |
| xmlOrderSubLines | AddXML |
| xmlOutput only | SelectValues |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| OrderList | Placeholder |
| OrderHeaders | Placeholder 2 |
| OrderHeaderComments | xmlOrderHeaderComments |
| Placeholder 2 | xmlOrderHeaders |
| Placeholder | xmlOrderList |
| xmlOrderList | XML Join transform |
| xmlOrderHeaders | XML Join transform |
| Placeholder 3x | xmlOrderLines |
| OrderLines | Placeholder 3x |
| XML Join transform | XML Join transform 2 |
| xmlOrderHeaderComments | XML Join transform 2 |
| XML Join transform 2 | XML Join transform 3 |
| xmlOrderLines | XML Join transform 3 |
| XML Join transform 3 | XML Join transform 4 |
| xmlOrderLineComments | XML Join transform 4 |
| OrderLineComments | Create XPath |
| Create XPath | xmlOrderLineComments |
| OrderSubLines | Create XPath 2 |
| Create XPath 2 | xmlOrderSubLines |
| XML Join transform 4 | XML Join transform 5 |
| xmlOrderSubLines | XML Join transform 5 |
| XML Join transform 5 | xmlOutput only |
| xmlOutput only | Copy rows to result |

## Notes

This example produces a multi level XML document from one Excel source.

The sample makes use of the new XML Join transform which allows to add XML structures into an existing XML structure.

---
