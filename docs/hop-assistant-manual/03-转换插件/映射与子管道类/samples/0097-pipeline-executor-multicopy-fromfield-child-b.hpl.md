# Pipeline: 0097-pipeline-executor-multicopy-fromfield-child-b

## Basic Information

- **Pipeline Name:** 0097-pipeline-executor-multicopy-fromfield-child-b
- **Description:** Child B: succeeds only when P_EXPECTED_CHILD is B.
- **Source File:** `03-转换插件/映射与子管道类/samples/0097-pipeline-executor-multicopy-fromfield-child-b.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| P_EXPECTED_CHILD |  | Marker from parent row; must be B in this pipeline. |

## Transforms

| Name | Type |
|------|------|
| Abort wrong pipeline or parameter | Abort |
| Generate rows | RowGenerator |
| Label this pipeline B | Constant |
| OK | Dummy |
| Parameter matches this child | FilterRows |
| Read expected from parameter | GetVariable |

## Hops

| From | To |
|------|----|
| Generate rows | Read expected from parameter |
| Read expected from parameter | Label this pipeline B |
| Label this pipeline B | Parameter matches this child |
| Parameter matches this child | OK |
| Parameter matches this child | Abort wrong pipeline or parameter |
