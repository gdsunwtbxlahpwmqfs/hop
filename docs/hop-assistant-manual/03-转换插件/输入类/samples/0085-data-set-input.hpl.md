# Pipeline: 0085-data-set-input

## Basic Information

- **Pipeline Name:** 0085-data-set-input
- **Source File:** `03-转换插件/输入类/samples/0085-data-set-input.hpl`

## Transforms

| Name | Type |
|------|------|
| golden-parquet-input | DataSetInput |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| golden-parquet-input | Validate |
