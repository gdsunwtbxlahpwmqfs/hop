# Pipeline: 0041-properties-file-input-invalid-file-required-no

## Basic Information

- **Pipeline Name:** 0041-properties-file-input-invalid-file-required-no
- **Source File:** `03-转换插件/输入类/samples/0041-properties-file-input-invalid-file-required-no.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| read non existing file required N | PropertyInput |

## Hops

| From | To |
|------|----|
| read non existing file required N | Dummy (do nothing) |

## Notes

Test if transform does not fail when missing a file and required is set to N

---
