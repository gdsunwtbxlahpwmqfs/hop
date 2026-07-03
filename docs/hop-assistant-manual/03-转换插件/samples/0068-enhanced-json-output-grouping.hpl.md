# Pipeline: 0069-enhanced-json-output-grouping

## Basic Information

- **Pipeline Name:** 0069-enhanced-json-output-grouping
- **Source File:** `03-转换插件/samples/0068-enhanced-json-output-grouping.hpl`

## Transforms

| Name | Type |
|------|------|
| Clone row | CloneRow |
| Fake data | Fake |
| Generate rows | RowGenerator |
| Sort rows | SortRows |
| count objects | GroupBy |
| count single objects | GroupBy |
| expect 10 rows | FilterRows |
| expect 100 rows | FilterRows |
| failed multi json generation | Abort |
| failed single json generation | Abort |
| json array of multiple results | EnhancedJsonOutput |
| parse json array | JsonInput |
| parse single json object | JsonInput |
| single json result with value array | EnhancedJsonOutput |

## Hops

| From | To |
|------|----|
| Generate rows | Clone row |
| Clone row | Fake data |
| Fake data | Sort rows |
| Sort rows | json array of multiple results |
| Sort rows | single json result with value array |
| json array of multiple results | parse json array |
| single json result with value array | parse single json object |
| parse single json object | count single objects |
| parse json array | count objects |
| count objects | expect 100 rows |
| count single objects | expect 10 rows |
| expect 100 rows | failed multi json generation |
| expect 10 rows | failed single json generation |
