# Pipeline: 0027-bug-read-file-template-child

## Basic Information

- **Pipeline Name:** 0027-bug-read-file-template-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0027-bug-read-file-template-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Colorado | WriteToLog |
| New York | WriteToLog |
| error (default) | WriteToLog |
| read customer data | TextFileInput2 |
| state switch | SwitchCase |

## Hops

| From | To |
|------|----|
| state switch | error (default) |
| read customer data | state switch |
| state switch | Colorado |
| state switch | New York |
