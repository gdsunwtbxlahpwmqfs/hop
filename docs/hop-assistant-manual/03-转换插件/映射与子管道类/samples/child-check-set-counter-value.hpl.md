# Pipeline: check-set-counter-value

## Basic Information

- **Pipeline Name:** check-set-counter-value
- **Source File:** `03-转换插件/映射与子管道类/samples/child-check-set-counter-value.hpl`

## Transforms

| Name | Type |
|------|------|
| counter <= 10? | FilterRows |
| get counter | GetVariable |
| log counter | WriteToLog |
| set ${COUNTER} | SetVariable |
| set ${END_LOOP} | SetVariable |
| set new_counter | Calculator |

## Hops

| From | To |
|------|----|
| get counter | counter <= 10? |
| counter <= 10? | set new_counter |
| counter <= 10? | set ${END_LOOP} |
| set new_counter | log counter |
| log counter | set ${COUNTER} |
