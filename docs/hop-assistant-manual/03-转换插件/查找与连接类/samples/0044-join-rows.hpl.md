# Pipeline: 0044-join-rows

## Basic Information

- **Pipeline Name:** 0044-join-rows
- **Source File:** `03-转换插件/查找与连接类/samples/0044-join-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| B | DataGrid |
| Join rows (between range) | JoinRows |
| Join rows (id1=id2) | JoinRows |
| Verify equal | Dummy |
| Verify range | Dummy |

## Hops

| From | To |
|------|----|
| A | Join rows (id1=id2) |
| B | Join rows (id1=id2) |
| Join rows (id1=id2) | Verify equal |
| A | Join rows (between range) |
| B | Join rows (between range) |
| Join rows (between range) | Verify range |
