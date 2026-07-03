# Pipeline: 0007-http-client-ignore-ssl

## Basic Information

- **Pipeline Name:** 0007-http-client-ignore-ssl
- **Source File:** `03-转换插件/网络与服务类/samples/0006-http-client-ignore-ssl.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| HTTP client without ignoring SSL | Http |
| Dummy (do nothing) | Dummy |
| HTTP client with ignoring SSL | Http |

## Hops

| From | To |
|------|----|
| Generate rows | HTTP client without ignoring SSL |
| HTTP client without ignoring SSL | Dummy (do nothing) |
| Generate rows | HTTP client with ignoring SSL |
