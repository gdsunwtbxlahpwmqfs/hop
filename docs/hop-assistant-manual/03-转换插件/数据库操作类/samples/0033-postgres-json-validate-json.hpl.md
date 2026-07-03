# Pipeline: 0033-postgres-json-validate-json

## Basic Information

- **Pipeline Name:** 0033-postgres-json-validate-json
- **Source File:** `03-转换插件/数据库操作类/samples/0033-postgres-json-validate-json.hpl`

## Transforms

| Name | Type |
|------|------|
| JSONB input | TableInput |
| JSON input | TableInput |
| Sort JSONB | SortRows |
| Sort JSON | SortRows |
| Merge rows (diff) | MergeRows |
| IS identical? | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| JSONB input | Sort JSONB |
| JSON input | Sort JSON |
| Sort JSONB | Merge rows (diff) |
| Sort JSON | Merge rows (diff) |
| Merge rows (diff) | IS identical? |
| IS identical? | Abort |
