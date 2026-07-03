# Pipeline: 0097-pipeline-executor-multicopy-fromfield-main

## Basic Information

- **Pipeline Name:** 0097-pipeline-executor-multicopy-fromfield-main
- **Description:** Regression: Pipeline executor with multiple copies must run the pipeline path from the incoming row field, and parameters must stay aligned with that row. A static filename in the transform meta points at child-b only (similar to misconfiguration); execution must still follow child_path per row.
- **Source File:** `03-转换插件/映射与子管道类/samples/0097-pipeline-executor-multicopy-fromfield-main.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Sort rows | SortRows |
| Pipeline executor multicopy | PipelineExecutor |
| Abort on child failure | Abort |
| Assert child pipelines succeeded | FilterRows |
| OK | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Sort rows |
| Sort rows | Pipeline executor multicopy |
| Pipeline executor multicopy | Assert child pipelines succeeded |
| Assert child pipelines succeeded | OK |
| Assert child pipelines succeeded | Abort on child failure |

## Notes

40 rows alternate child A / child B. Pipeline executor uses 10 copies, filename from field child_path, and maps expected_child to P_EXPECTED_CHILD.

Each child pipeline aborts unless P_EXPECTED_CHILD matches that child (A vs B).

This fails when copies share the wrong pipeline template or mismatched parameters.

---
