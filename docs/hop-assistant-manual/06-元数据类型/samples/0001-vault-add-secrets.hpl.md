# Pipeline: 0001-vault-add-secrets

## Basic Information

- **Pipeline Name:** 0001-vault-add-secrets
- **Source File:** `06-元数据类型/samples/0001-vault-add-secrets.hpl`

## Transforms

| Name | Type |
|------|------|
| REST client | Rest |
| Secrets | DataGrid |

## Hops

| From | To |
|------|----|
| Secrets | REST client |
