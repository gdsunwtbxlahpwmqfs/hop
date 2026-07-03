# Pipeline: 0034-system-info

## Basic Information

- **Pipeline Name:** 0034-system-info
- **Source File:** `03-转换插件/映射与子管道类/samples/0034-system-info.hpl`

## Transforms

| Name | Type |
|------|------|
| fields | DataGrid |
| 0034-system-info-template.hpl | MetaInject |

## Hops

| From | To |
|------|----|
| fields | 0034-system-info-template.hpl |
