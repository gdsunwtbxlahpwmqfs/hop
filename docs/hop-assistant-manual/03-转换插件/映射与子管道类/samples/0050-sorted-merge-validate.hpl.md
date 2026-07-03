# Pipeline: 0050-sorted-merge-validate

## Basic Information

- **Pipeline Name:** 0050-sorted-merge-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0050-sorted-merge-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/sorted-merge.hpl fields | getXMLData |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/sorted-merge.hpl fields | validate |
