# Pipeline: 0005-merge-join-parent

## Basic Information

- **Pipeline Name:** 0005-merge-join-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0005-merge-join-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0004-csv-input-child.hpl | MetaInject |
| CSV Input fields | DataGrid |
| Verify | Dummy |
| left key fields | GetVariable |
| right key fields | GetVariable |

## Hops

| From | To |
|------|----|
| 0004-csv-input-child.hpl | Verify |
| CSV Input fields | 0004-csv-input-child.hpl |
| left key fields | 0004-csv-input-child.hpl |
| right key fields | 0004-csv-input-child.hpl |
