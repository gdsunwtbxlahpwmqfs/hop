# Pipeline: 0046-java-filter

## Basic Information

- **Pipeline Name:** 0046-java-filter
- **Source File:** `03-转换插件/脚本与编程类/samples/0046-java-filter.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Java filter | JavaFilter |
| false | Dummy |
| true | Dummy |
| Java filter true | JavaFilter |
| true only | Dummy |
| Java filter false | JavaFilter |
| false only | Dummy |
| Java filter default | JavaFilter |
| true default | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Java filter |
| Java filter | true |
| Java filter | false |
| Java filter true | true only |
| Data grid | Java filter true |
| Java filter false | false only |
| Data grid | Java filter false |
| Java filter default | true default |
| Data grid | Java filter default |
