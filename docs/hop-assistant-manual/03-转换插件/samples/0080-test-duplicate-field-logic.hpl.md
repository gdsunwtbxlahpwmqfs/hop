# Pipeline: 0080-test-duplicate-field-logic

## Basic Information

- **Pipeline Name:** 0080-test-duplicate-field-logic
- **Source File:** `03-转换插件/samples/0080-test-duplicate-field-logic.hpl`

## Transforms

| Name | Type |
|------|------|
| Metadata structure of stream | TransformMetaStructure |
| add same field multiple times | Constant |
| generate single row and field | RowGenerator |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| generate single row and field | add same field multiple times |
| add same field multiple times | Metadata structure of stream |
| Metadata structure of stream | Dummy (do nothing) |
