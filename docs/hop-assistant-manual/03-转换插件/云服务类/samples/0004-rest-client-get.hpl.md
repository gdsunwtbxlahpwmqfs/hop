# Pipeline: 0004-rest-client-get

## Basic Information

- **Pipeline Name:** 0004-rest-client-get
- **Source File:** `03-转换插件/云服务类/samples/0004-rest-client-get.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Dummy line | RowGenerator |
| REST client | Rest |
| check 1 line status 200 | FilterRows |
| count lines | MemoryGroupBy |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Dummy line | REST client |
| REST client | count lines |
| count lines | check 1 line status 200 |
| check 1 line status 200 | Abort |
| count lines | Write to log |
