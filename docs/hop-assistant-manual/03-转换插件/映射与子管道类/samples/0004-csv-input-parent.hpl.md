# Pipeline: 0004-csv-input-parent

## Basic Information

- **Pipeline Name:** 0004-csv-input-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0004-csv-input-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0004-csv-input-child.hpl | MetaInject |
| CSV Input fields | DataGrid |
| CSV Input metadata | GetVariable |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| CSV Input fields | 0004-csv-input-child.hpl |
| 0004-csv-input-child.hpl | Verify |
| CSV Input metadata | 0004-csv-input-child.hpl |
