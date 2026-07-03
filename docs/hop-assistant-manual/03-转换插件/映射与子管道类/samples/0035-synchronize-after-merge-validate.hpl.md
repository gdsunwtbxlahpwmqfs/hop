# Pipeline: 0035-synchronize-after-merge-validate

## Basic Information

- **Pipeline Name:** 0035-synchronize-after-merge-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0035-synchronize-after-merge-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/sync-after-merge-mdi.hpl | XMLInputStream |
| general fields | SelectValues |
| general options | Denormaliser |
| key fields | SelectValues |
| keys options | Denormaliser |
| keys values | Denormaliser |
| lookup fields | SelectValues |
| lookup filter | FilterRows |
| lookup options | Denormaliser |
| lookup/key filter | FilterRows |
| lookup/value filter | FilterRows |
| transform filter | FilterRows |
| validate general | Dummy |
| validate keys | Dummy |
| validate lookup | Dummy |
| validate values | Dummy |
| value fields | SelectValues |

## Hops

| From | To |
|------|----|
| ${java.io.tmpdir}/sync-after-merge-mdi.hpl | transform filter |
| transform filter | general options |
| general options | general fields |
| general fields | validate general |
| ${java.io.tmpdir}/sync-after-merge-mdi.hpl | lookup filter |
| lookup filter | lookup options |
| lookup options | lookup fields |
| ${java.io.tmpdir}/sync-after-merge-mdi.hpl | lookup/key filter |
| lookup/key filter | keys options |
| keys options | key fields |
| lookup fields | validate lookup |
| key fields | validate keys |
| value fields | validate values |
| keys values | value fields |
| lookup/value filter | keys values |
| ${java.io.tmpdir}/sync-after-merge-mdi.hpl | lookup/value filter |
