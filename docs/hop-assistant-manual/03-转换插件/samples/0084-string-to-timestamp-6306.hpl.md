# Pipeline: 0084-string-to-timestamp-6306

## Basic Information

- **Pipeline Name:** 0084-string-to-timestamp-6306
- **Source File:** `03-转换插件/samples/0084-string-to-timestamp-6306.hpl`

## Transforms

| Name | Type |
|------|------|
| error | Dummy |
| input data | DataGrid |
| ok | Dummy |
| str -> timestamp | SelectValues |

## Hops

| From | To |
|------|----|
| input data | str -> timestamp |
| str -> timestamp | ok |
| str -> timestamp | error |
