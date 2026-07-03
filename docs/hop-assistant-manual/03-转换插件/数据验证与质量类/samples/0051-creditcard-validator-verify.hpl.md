# Pipeline: 0051-creditcard-validator-verify

## Basic Information

- **Pipeline Name:** 0051-creditcard-validator-verify
- **Source File:** `03-转换插件/数据验证与质量类/samples/0051-creditcard-validator-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Credit card validator | CreditCardValidator |
| Error cards | DataGrid |
| Valid cards | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Valid cards | Credit card validator |
| Error cards | Credit card validator |
| Credit card validator | Verify |
