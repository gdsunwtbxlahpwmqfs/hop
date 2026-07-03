# Pipeline: 0008-http-post-ignore-ssl

## Basic Information

- **Pipeline Name:** 0008-http-post-ignore-ssl
- **Source File:** `03-转换插件/网络与服务类/samples/0006-http-post-ignore-ssl.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| HTTP post without ignoring SSL | HttpPost |
| Dummy (do nothing) | Dummy |
| HTTP post with ignoring SSL | HttpPost |

## Hops

| From | To |
|------|----|
| Generate rows | HTTP post without ignoring SSL |
| HTTP post without ignoring SSL | Dummy (do nothing) |
| Generate rows | HTTP post with ignoring SSL |
