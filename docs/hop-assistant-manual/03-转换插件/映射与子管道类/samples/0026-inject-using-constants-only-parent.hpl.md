# Pipeline: 0026-inject-using-constants-only-parent

## Basic Information

- **Pipeline Name:** 0026-inject-using-constants-only-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0026-inject-using-constants-only-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| ETL metadata injection | MetaInject |

## Hops

| From | To |
|------|----|
| ETL metadata injection | Dummy (do nothing) |
