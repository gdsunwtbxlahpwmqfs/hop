# Pipeline: apache-tika-basic

## Basic Information

- **Pipeline Name:** apache-tika-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/apache-tika-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Sort rows | SortRows |
| count words | MemoryGroupBy |
| get ASF ICLA | Tika |
| keep word | SelectValues |
| log nb words | WriteToLog |
| remove special chars | ScriptValueMod |
| split newline to rows | SplitFieldToRows3 |
| split words to rows | SplitFieldToRows3 |
| trim, lower | StringOperations |
| word is not null | FilterRows |

## Hops

| From | To |
|------|----|
| get ASF ICLA | split words to rows |
| keep word | Sort rows |
| Sort rows | count words |
| count words | log nb words |
| split words to rows | split newline to rows |
| split newline to rows | trim, lower |
| word is not null | keep word |
| trim, lower | remove special chars |
| remove special chars | word is not null |
