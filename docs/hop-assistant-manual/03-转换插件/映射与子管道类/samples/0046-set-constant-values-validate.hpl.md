# Pipeline: 0046-set-constant-values-validate

## Basic Information

- **Pipeline Name:** 0046-set-constant-values-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0046-set-constant-values-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/set-constant-values.hpl fields | getXMLData |
| /tmp/set-constant-values.hpl | getXMLData |
| validate fields | Dummy |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/set-constant-values.hpl fields | validate fields |
| /tmp/set-constant-values.hpl | validate |
