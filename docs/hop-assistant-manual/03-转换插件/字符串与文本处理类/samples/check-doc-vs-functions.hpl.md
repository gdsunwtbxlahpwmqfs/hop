# Pipeline: check-doc-vs-functions

## Basic Information

- **Pipeline Name:** check-doc-vs-functions
- **Source File:** `03-转换插件/字符串与文本处理类/samples/check-doc-vs-functions.hpl`

## Transforms

| Name | Type |
|------|------|
| exclude logical operators | FilterRows |
| function name? | FilterRows |
| join xml and doc | MergeJoin |
| missing doc or xml | WriteToLog |
| missing either way? | FilterRows |
| read formula.adoc | TextFileInput2 |
| read functions.xml | getXMLData |
| remove ==== | ReplaceString |
| sort XML function | SortRows |
| sort doc function | SortRows |
| abort | Abort |

## Hops

| From | To |
|------|----|
| read formula.adoc | function name? |
| function name? | remove ==== |
| remove ==== | sort doc function |
| read functions.xml | sort XML function |
| sort XML function | join xml and doc |
| sort doc function | join xml and doc |
| join xml and doc | missing either way? |
| missing either way? | exclude logical operators |
| exclude logical operators | missing doc or xml |
| missing doc or xml | abort |
