# Pipeline: 0073-json-output-enhanced-split-files-3871

## Basic Information

- **Pipeline Name:** 0073-json-output-enhanced-split-files-3871
- **Source File:** `03-转换插件/输出类/samples/0073-json-output-enhanced-split-files-3871.hpl`

## Transforms

| Name | Type |
|------|------|
| Enhanced JSON Output | EnhancedJsonOutput |
| fake books | Fake |
| generate 100k rows | RowGenerator |

## Hops

| From | To |
|------|----|
| fake books | Enhanced JSON Output |
| generate 100k rows | fake books |
