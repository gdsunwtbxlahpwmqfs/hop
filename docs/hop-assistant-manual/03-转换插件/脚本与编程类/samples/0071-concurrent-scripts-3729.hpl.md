# Pipeline: 0071-concurrent-scripts-3729

## Basic Information

- **Pipeline Name:** 0071-concurrent-scripts-3729
- **Source File:** `03-转换插件/脚本与编程类/samples/0071-concurrent-scripts-3729.hpl`

## Transforms

| Name | Type |
|------|------|
| ECMAScript | SuperScript |
| Groovy | SuperScript |
| Python | SuperScript |
| generate 10k rows | RowGenerator |
| preview | Dummy |

## Hops

| From | To |
|------|----|
| ECMAScript | preview |
| Groovy | preview |
| Python | preview |
| generate 10k rows | ECMAScript |
| generate 10k rows | Groovy |
| generate 10k rows | Python |
