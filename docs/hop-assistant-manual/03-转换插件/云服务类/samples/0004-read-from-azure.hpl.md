# Pipeline: 0004-read-from-azure

## Basic Information

- **Pipeline Name:** 0004-read-from-azure
- **Source File:** `03-转换插件/云服务类/samples/0004-read-from-azure.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Text file input | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Text file input | Dummy (do nothing) |
