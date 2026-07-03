# Pipeline: 0017-json-output-verify

## Basic Information

- **Pipeline Name:** 0017-json-output-verify
- **Source File:** `03-转换插件/映射与子管道类/samples/0017-json-output-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| JSON input | JsonInput |
| foo = bar? | FilterRows |
| OK | Dummy |
| KO | Abort |

## Hops

| From | To |
|------|----|
| JSON input | foo = bar? |
| foo = bar? | OK |
| foo = bar? | KO |
