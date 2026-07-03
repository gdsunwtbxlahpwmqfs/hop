# Pipeline: read-samples-build-hop-run

## Basic Information

- **Pipeline Name:** read-samples-build-hop-run
- **Source File:** `03-转换插件/samples/read-samples-build-hop-run.hpl`

## Transforms

| Name | Type |
|------|------|
| abort | Abort |
| build hop-run.sh | ScriptValueMod |
| errors? | FilterRows |
| execute-hop-run.hwf | WorkflowExecutor |
| execute-hop-run.hwf prio 1 | WorkflowExecutor |
| get hpl/hwf filenames | GetFileNames |
| ignore list LU | StreamLookup |
| log errors | WriteToLog |
| prio 1 list LU | StreamLookup |
| prio 1 samples | DataGrid |
| prio 1? | FilterRows |
| samples to ignore | DataGrid |
| skip ignored | FilterRows |
| skip technology samples (for now) | FilterRows |
| wait for prio1 | BlockUntilTransformsFinish |

## Hops

| From | To |
|------|----|
| execute-hop-run.hwf | errors? |
| errors? | log errors |
| log errors | abort |
| samples to ignore | ignore list LU |
| ignore list LU | skip ignored |
| skip ignored | skip technology samples (for now) |
| skip technology samples (for now) | build hop-run.sh |
| get hpl/hwf filenames | prio 1 list LU |
| prio 1 samples | prio 1 list LU |
| prio 1? | execute-hop-run.hwf prio 1 |
| prio 1? | wait for prio1 |
| prio 1 list LU | ignore list LU |
| build hop-run.sh | prio 1? |
| wait for prio1 | execute-hop-run.hwf |
| execute-hop-run.hwf prio 1 | errors? |
