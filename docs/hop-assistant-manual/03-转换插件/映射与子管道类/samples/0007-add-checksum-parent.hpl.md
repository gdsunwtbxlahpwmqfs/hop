# Pipeline: 0007-add-checksum-parent

## Basic Information

- **Pipeline Name:** 0007-add-checksum-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0007-add-checksum-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0007-add-checksum-child.hpl | MetaInject |
| Verify | Dummy |
| crc32 | DataGrid |
| sha384 | DataGrid |

## Hops

| From | To |
|------|----|
| crc32 | 0007-add-checksum-child.hpl |
| 0007-add-checksum-child.hpl | Verify |
| sha384 | 0007-add-checksum-child.hpl |
