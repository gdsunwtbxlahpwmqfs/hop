# Pipeline: 0018-combination-lookup-child

## Basic Information

- **Pipeline Name:** 0018-combination-lookup-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0018-combination-lookup-child.hpl`

## Transforms

| Name | Type |
|------|------|
| A | DataGrid |
| A x B x C | JoinRows |
| B | DataGrid |
| C | DataGrid |
| Sort on a,b,c | SortRows |
| h2.junk_dim | CombinationLookup |

## Hops

| From | To |
|------|----|
| A | A x B x C |
| B | A x B x C |
| C | A x B x C |
| A x B x C | Sort on a,b,c |
| Sort on a,b,c | h2.junk_dim |
