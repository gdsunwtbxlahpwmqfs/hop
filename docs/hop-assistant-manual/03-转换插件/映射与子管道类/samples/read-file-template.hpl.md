# Pipeline: read-file-template

## Basic Information

- **Pipeline Name:** read-file-template
- **Source File:** `03-转换插件/映射与子管道类/samples/read-file-template.hpl`

## Transforms

| Name | Type |
|------|------|
| nb zip codes per state | MemoryGroupBy |
| sort state | SortRows |
| Write to log | WriteToLog |
| cast dates | SelectValues |
| read customer data | TextFileInput2 |

## Hops

| From | To |
|------|----|
| read customer data | cast dates |
| cast dates | nb zip codes per state |
| nb zip codes per state | sort state |
| sort state | Write to log |
