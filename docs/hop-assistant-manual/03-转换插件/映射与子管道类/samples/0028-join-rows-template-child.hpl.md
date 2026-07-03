# Pipeline: 0028-join-rows-template-child

## Basic Information

- **Pipeline Name:** 0028-join-rows-template-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0028-join-rows-template-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Data grid 2 | DataGrid |
| Join rows (cartesian product) | JoinRows |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Join rows (cartesian product) |
| Data grid 2 | Join rows (cartesian product) |
| Join rows (cartesian product) | OUTPUT |
