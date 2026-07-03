# Pipeline: transformscheck-if-webservice-is-available

## Basic Information

- **Pipeline Name:** transformscheck-if-webservice-is-available
- **Source File:** `03-转换插件/网络与服务类/samples/check-if-webservice-is-available.hpl`

## Transforms

| Name | Type |
|------|------|
| generate 1 row | RowGenerator |
| Check if webservice is available | WebServiceAvailable |
| available? | FilterRows |
| log available | WriteToLog |
| log not available | WriteToLog |

## Hops

| From | To |
|------|----|
| generate 1 row | Check if webservice is available |
| available? | log available |
| available? | log not available |
| Check if webservice is available | available? |
