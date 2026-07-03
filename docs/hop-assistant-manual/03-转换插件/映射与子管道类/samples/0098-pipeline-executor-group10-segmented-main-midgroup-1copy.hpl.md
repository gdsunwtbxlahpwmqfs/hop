# Pipeline: 0098-pipeline-executor-group10-segmented-main-midgroup-1copy

## Basic Information

- **Pipeline Name:** 0098-pipeline-executor-group10-segmented-main-midgroup-1copy
- **Description:** Regression: group_size=10 with child_path switching after row 5 in one batch (5x A then 5x B). The executor must flush the partial group when the path changes so each child receives only matching rows. copies=1.
- **Source File:** `03-转换插件/映射与子管道类/samples/0098-pipeline-executor-group10-segmented-main-midgroup-1copy.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Sort rows | SortRows |
| Pipeline executor grouped | PipelineExecutor |
| Abort on child failure | Abort |
| Assert child pipelines succeeded | FilterRows |
| OK | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Sort rows |
| Sort rows | Pipeline executor grouped |
| Pipeline executor grouped | Assert child pipelines succeeded |
| Assert child pipelines succeeded | OK |
| Assert child pipelines succeeded | Abort on child failure |

## Notes

Single batch of 10: path switches after row 5. Expect child A with rows 1-5 only, then child B with rows 6-10 only (partial flush on path change). copies=1.

---
