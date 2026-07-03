# Pipeline: 0080-test-duplicate-field-logic-database-join

## Basic Information

- **Pipeline Name:** 0080-test-duplicate-field-logic-database-join
- **Source File:** `03-转换插件/samples/0080-test-duplicate-field-logic-database-join.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Metadata structure of stream | TransformMetaStructure |
| get field test | TableInput |
| join same field | DBJoin |

## Hops

| From | To |
|------|----|
| get field test | join same field |
| join same field | Metadata structure of stream |
| Metadata structure of stream | Dummy (do nothing) |
