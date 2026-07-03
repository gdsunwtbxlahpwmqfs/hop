# Pipeline: 0005-metastructure

## Basic Information

- **Pipeline Name:** 0005-metastructure
- **Source File:** `03-转换插件/日志与监控类/samples/0005-metastructure.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| Metadata structure of stream | TransformMetaStructure |
| id | Sequence |
| str,num,int | RandomValue |
| sysdate | SystemInfo |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | str,num,int |
| str,num,int | sysdate |
| sysdate | Metadata structure of stream |
| Metadata structure of stream | OUTPUT |
