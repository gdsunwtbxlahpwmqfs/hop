# Pipeline: mongo-insert-validation

## Basic Information

- **Pipeline Name:** mongo-insert-validation
- **Source File:** `03-转换插件/输入类/samples/mongo-insert-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| CUSTOMERS | Dummy |
| MongoDB input | MongoDbInput |

## Hops

| From | To |
|------|----|
| 1 row | MongoDB input |
| MongoDB input | CUSTOMERS |

## Notes

With input and batch size > 1 this is also testing for loss of records at the end of the stream.

---
