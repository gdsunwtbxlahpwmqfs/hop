# Pipeline: 0015-add-checksum

## Basic Information

- **Pipeline Name:** 0015-add-checksum
- **Source File:** `03-转换插件/计算与字段操作类/samples/0015-add-checksum.hpl`

## Transforms

| Name | Type |
|------|------|
| Append streams | Append |
| CRC32 | CheckSum |
| Generate rows | RowGenerator |
| Special cases null values | DataGrid |
| addler32 | CheckSum |
| id | Sequence |
| md5 | CheckSum |
| mod | Calculator |
| sha1 | CheckSum |
| sha256 | CheckSum |
| sha384 | CheckSum |
| sha512 | CheckSum |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Generate rows | id |
| id | mod |
| CRC32 | addler32 |
| md5 | sha1 |
| sha1 | sha256 |
| sha256 | sha384 |
| addler32 | md5 |
| Special cases null values | Append streams |
| mod | Append streams |
| Append streams | CRC32 |
| sha384 | sha512 |
| sha512 | Dummy (do nothing) |
