# Pipeline: 0044-join-rows-same-fields

## Basic Information

- **Pipeline Name:** 0044-join-rows-same-fields
- **Source File:** `03-转换插件/查找与连接类/samples/0044-join-rows-same-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| B | DataGrid |
| Data | Dummy |
| Join rows | JoinRows |
| Meta | Dummy |
| Metadata of stream | TransformMetaStructure |
| equal IDs | FilterRows |

## Hops

| From | To |
|------|----|
| A | Join rows |
| B | Join rows |
| Metadata of stream | Meta |
| Join rows | equal IDs |
| equal IDs | Data |
| equal IDs | Metadata of stream |
