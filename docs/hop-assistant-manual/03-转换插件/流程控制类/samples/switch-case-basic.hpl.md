# Pipeline: switch-case-basic

## Basic Information

- **Pipeline Name:** switch-case-basic
- **Source File:** `03-转换插件/流程控制类/samples/switch-case-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Switch id | SwitchCase |
| Output 1 | Dummy |
| Output default | Dummy |
| Output 4 | Dummy |
| Output 3 | Dummy |
| Output 2 | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Switch id |
| Switch id | Output 1 |
| Switch id | Output 2 |
| Switch id | Output 3 |
| Switch id | Output 4 |
| Switch id | Output default |

## Notes

Switch on id, follow different streams for values 1-4 or use the default for all other values (id 5 in this example).

---
