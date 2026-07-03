# Pipeline: 0001-vault-resolve-secrets-with-prefix

## Basic Information

- **Pipeline Name:** 0001-vault-resolve-secrets-with-prefix
- **Source File:** `06-元数据类型/samples/0001-vault-resolve-secrets-with-prefix.hpl`

## Transforms

| Name | Type |
|------|------|
| Get variables | GetVariable |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Get variables | Output |

## Notes

The vault-prefix resolver contains the path prefix making the expressions shorter.

This tests that functionality.

---
