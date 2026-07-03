# Pipeline: file-exists-basic

## Basic Information

- **Pipeline Name:** file-exists-basic
- **Source File:** `03-转换插件/文件与编码操作类/samples/file-exists-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| get customers-100.txt filename | GetVariable |
| check if customers-100.txt exists | FileExists |
| generate 1 row | RowGenerator |
| exists? | FilterRows |
| log exists | WriteToLog |
| log does not exist | WriteToLog |

## Hops

| From | To |
|------|----|
| get customers-100.txt filename | check if customers-100.txt exists |
| generate 1 row | get customers-100.txt filename |
| check if customers-100.txt exists | exists? |
| exists? | log exists |
| exists? | log does not exist |
