# Pipeline: 0098-pipeline-executor-group10-segmented-main-4copy

## Basic Information

- **Pipeline Name:** 0098-pipeline-executor-group10-segmented-main-4copy
- **Description:** Regression: Pipeline executor group_size=10 with filename from field. Twenty rows in two homogeneous batches (rows 1-10 child A, 11-20 child B). Each child run must receive only rows whose expected_child matches that pipeline. Four executor copies stress shared-meta races.
- **Source File:** `03-转换插件/映射与子管道类/samples/0098-pipeline-executor-group10-segmented-main-4copy.hpl`

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

group_size=10: two full batches (rows 1-10 child A, 11-20 child B). filenameInField uses child_path; static filename in meta is child-b. Child pipelines abort if any result row has the wrong expected_child. copies=4.

---
