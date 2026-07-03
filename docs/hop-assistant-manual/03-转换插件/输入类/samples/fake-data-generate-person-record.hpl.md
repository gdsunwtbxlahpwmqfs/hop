# Pipeline: fake-data-generate-person-record

## Basic Information

- **Pipeline Name:** fake-data-generate-person-record
- **Source File:** `03-转换插件/输入类/samples/fake-data-generate-person-record.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 1000 rows | RowGenerator |
| Generate random person record | Fake |
| Results | Dummy |

## Hops

| From | To |
|------|----|
| Generate 1000 rows | Generate random person record |
| Generate random person record | Results |

## Notes

Fake Data transform generates data based on categories

---
