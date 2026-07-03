# Pipeline: check-if-file-is-locked-basic

## Basic Information

- **Pipeline Name:** check-if-file-is-locked-basic
- **Source File:** `03-转换插件/文件与编码操作类/samples/check-if-file-is-locked-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Check if file is locked | FileLocked |
| get customers-100.txt | GetFileNames |
| locked? | FilterRows |
| log locked | WriteToLog |
| log not locked | WriteToLog |

## Hops

| From | To |
|------|----|
| get customers-100.txt | Check if file is locked |
| Check if file is locked | locked? |
| locked? | log locked |
| locked? | log not locked |
