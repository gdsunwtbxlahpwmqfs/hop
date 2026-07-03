# Pipeline: 001-get-initial-lastmodified-value

## Basic Information

- **Pipeline Name:** 001-get-initial-lastmodified-value
- **Source File:** `03-转换插件/网络与服务类/samples/001-get-initial-lastmodified-value.hpl`

## Transforms

| Name | Type |
|------|------|
| Get file names | GetFileNames |
| Get initial lastmodified value | SelectValues |
| Set variables | SetVariable |

## Hops

| From | To |
|------|----|
| Get file names | Get initial lastmodified value |
| Get initial lastmodified value | Set variables |
