# Pipeline: 0098-pipeline-executor-group10-segmented-child-b

## Basic Information

- **Pipeline Name:** 0098-pipeline-executor-group10-segmented-child-b
- **Description:** Validates result rows from parent: every row must have expected_child=B (batch size may be less than parent group_size when the child path changes mid-group).
- **Source File:** `03-转换插件/映射与子管道类/samples/0098-pipeline-executor-group10-segmented-child-b.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort wrong row | Abort |
| expected_child is B | FilterRows |
| Get rows from result | RowsFromResult |
| OK | Dummy |

## Hops

| From | To |
|------|----|
| Get rows from result | expected_child is B |
| expected_child is B | OK |
| expected_child is B | Abort wrong row |
