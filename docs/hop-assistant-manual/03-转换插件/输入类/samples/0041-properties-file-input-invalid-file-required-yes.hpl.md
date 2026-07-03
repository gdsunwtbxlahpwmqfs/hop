# Pipeline: 0041-properties-file-input-invalid-file-required-yes

## Basic Information

- **Pipeline Name:** 0041-properties-file-input-invalid-file-required-yes
- **Source File:** `03-转换插件/输入类/samples/0041-properties-file-input-invalid-file-required-yes.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| read non existing file required Y | PropertyInput |

## Hops

| From | To |
|------|----|
| read non existing file required Y | Dummy (do nothing) |

## Notes

Test if transform FAILS when missing a file and required is set to Y

---
