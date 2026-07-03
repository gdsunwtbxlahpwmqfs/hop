# Pipeline: 0007-add-checksum-child

## Basic Information

- **Pipeline Name:** 0007-add-checksum-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0007-add-checksum-child.hpl`

## Transforms

| Name | Type |
|------|------|
| CRC32 | CheckSum |
| Generate rows | RowGenerator |
| OUTPUT | Dummy |
| id | Sequence |
| mod | Calculator |
| sha384 | CheckSum |

## Hops

| From | To |
|------|----|
| Generate rows | id |
| id | mod |
| mod | CRC32 |
| CRC32 | sha384 |
| sha384 | OUTPUT |
