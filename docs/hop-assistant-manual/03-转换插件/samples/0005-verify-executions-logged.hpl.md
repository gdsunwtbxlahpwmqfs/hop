# Pipeline: 0005-verify-executions-logged

## Basic Information

- **Pipeline Name:** 0005-verify-executions-logged
- **Source File:** `03-转换插件/samples/0005-verify-executions-logged.hpl`

## Transforms

| Name | Type |
|------|------|
| list /tmp/executions | GetFileNames |
| count | GroupBy |
| count==0? | FilterRows |
| Nothing logged: abort | Abort |

## Hops

| From | To |
|------|----|
| list /tmp/executions | count |
| count | count==0? |
| count==0? | Nothing logged: abort |
