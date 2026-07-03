# Pipeline: build-formula-asciidoc-table

## Basic Information

- **Pipeline Name:** build-formula-asciidoc-table
- **Source File:** `03-转换插件/字符串与文本处理类/samples/build-formula-asciidoc-table.hpl`

## Transforms

| Name | Type |
|------|------|
| read functions | getXMLData |
| read examples | getXMLData |
| category header | ScriptValueMod |
| keep category | SelectValues |
| sort category, name | SortRows |
| remove % | ScriptValueMod |
| Properties input | PropertyInput |
| get category msg | StreamLookup |
| sort category | SortRows |
| function table | ScriptValueMod |
| JavaScript | ScriptValueMod |
| single line example | MemoryGroupBy |
| concat examples | ConcatFields |
| examples LU | StreamLookup |
| category LU | StreamLookup |
| add cat_counter | FieldsChangeSequence |
| keep function_adoc | SelectValues |

## Hops

| From | To |
|------|----|
| Properties input | get category msg |
| keep category | sort category |
| get category msg | category header |
| sort category | remove % |
| remove % | get category msg |
| read functions | sort category, name |
| read functions | keep category |
| single line example | JavaScript |
| read examples | concat examples |
| concat examples | single line example |
| JavaScript | examples LU |
| sort category, name | examples LU |
| category header | category LU |
| examples LU | category LU |
| category LU | add cat_counter |
| add cat_counter | function table |
| function table | keep function_adoc |
