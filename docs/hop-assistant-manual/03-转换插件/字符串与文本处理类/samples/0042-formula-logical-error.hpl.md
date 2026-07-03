# Pipeline: 0042-formula-logical-error

## Basic Information

- **Pipeline Name:** 0042-formula-logical-error
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-logical-error.hpl`

## Transforms

| Name | Type |
|------|------|
| card | DataGrid |
| privacy and card and size | Formula |
| verify | Dummy |

## Hops

| From | To |
|------|----|
| card | privacy and card and size |
| privacy and card and size | verify |

## Notes

The formula IF([card] > 0, [card], NA()) fails because NA() is not convertible to an integer type.

---
