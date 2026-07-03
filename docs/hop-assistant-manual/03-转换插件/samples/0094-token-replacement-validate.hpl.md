# Pipeline: 0094-token-replacement-validate

## Basic Information

- **Pipeline Name:** 0094-token-replacement-validate
- **Source File:** `03-转换插件/samples/0094-token-replacement-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| input | DataGrid |
| Token Replacement | TokenReplacementPlugin |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| input | Token Replacement |
| Token Replacement | Validate |
