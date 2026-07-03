# Pipeline: 0003-http-endpoint-double-precision-VERIFY

## Basic Information

- **Pipeline Name:** 0003-http-endpoint-double-precision-VERIFY
- **Source File:** `03-转换插件/数据库操作类/samples/0003-http-endpoint-double-precision-VERIFY.hpl`

## Transforms

| Name | Type |
|------|------|
| Assert result | Dummy |
| Read housing table | TableInput |

## Hops

| From | To |
|------|----|
| Read housing table | Assert result |
