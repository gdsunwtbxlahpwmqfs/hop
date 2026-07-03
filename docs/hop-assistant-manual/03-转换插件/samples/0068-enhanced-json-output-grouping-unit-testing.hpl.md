# Pipeline: 0069-enhanced-json-output-grouping-unit-testing

## Basic Information

- **Pipeline Name:** 0069-enhanced-json-output-grouping-unit-testing
- **Source File:** `03-转换插件/samples/0068-enhanced-json-output-grouping-unit-testing.hpl`

## Transforms

| Name | Type |
|------|------|
| CSV file input | CSVInput |
| Replace comma | ReplaceString |
| Replace comma 2 | ReplaceString |
| Select values | SelectValues |
| Select values 2 | SelectValues |
| Sort rows | SortRows |
| json array of multiple results | EnhancedJsonOutput |
| single json result with value array | EnhancedJsonOutput |
| verify_grouping | Dummy |
| verify_single_item_grouping | Dummy |

## Hops

| From | To |
|------|----|
| Sort rows | json array of multiple results |
| Sort rows | single json result with value array |
| CSV file input | Sort rows |
| json array of multiple results | Replace comma |
| single json result with value array | Replace comma 2 |
| Replace comma | Select values |
| Select values | verify_grouping |
| Replace comma 2 | Select values 2 |
| Select values 2 | verify_single_item_grouping |
