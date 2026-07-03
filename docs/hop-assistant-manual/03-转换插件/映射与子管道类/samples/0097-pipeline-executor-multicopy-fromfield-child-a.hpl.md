# Pipeline: 0097-pipeline-executor-multicopy-fromfield-child-a

## Basic Information

- **Pipeline Name:** 0097-pipeline-executor-multicopy-fromfield-child-a
- **Description:** Child A: succeeds only when P_EXPECTED_CHILD is A.
- **Source File:** `03-转换插件/映射与子管道类/samples/0097-pipeline-executor-multicopy-fromfield-child-a.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| P_EXPECTED_CHILD |  | Marker from parent row; must be A in this pipeline. |

## Transforms

| Name | Type |
|------|------|
| Abort wrong pipeline or parameter | Abort |
| Generate rows | RowGenerator |
| Label this pipeline A | Constant |
| OK | Dummy |
| Parameter matches this child | FilterRows |
| Read expected from parameter | GetVariable |

## Hops

| From | To |
|------|----|
| Generate rows | Read expected from parameter |
| Read expected from parameter | Label this pipeline A |
| Label this pipeline A | Parameter matches this child |
| Parameter matches this child | OK |
| Parameter matches this child | Abort wrong pipeline or parameter |
