# Pipeline: 0035-synchronize-after-merge-template

## Basic Information

- **Pipeline Name:** 0035-synchronize-after-merge-template
- **Source File:** `03-转换插件/映射与子管道类/samples/0035-synchronize-after-merge-template.hpl`

## Transforms

| Name | Type |
|------|------|
| INPUT | Dummy |
| OUTPUT | Dummy |
| Synchronize after merge | SynchronizeAfterMerge |

## Hops

| From | To |
|------|----|
| Synchronize after merge | OUTPUT |
| INPUT | Synchronize after merge |
