# Pipeline: json-normalize-complex-transactions

## Basic Information

- **Pipeline Name:** json-normalize-complex-transactions
- **Source File:** `03-转换插件/数据验证与质量类/samples/json-normalize-complex-transactions.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read complex transactions | JsonNormalizeInput |

## Hops

| From | To |
|------|----|
| read complex transactions | preview |

## Notes

Larger sample: 120 rows from $.transactions[*], deeply nested payer / merchant / analytics / risk, 25 flattened columns. Optional risk object is absent on some rows (ignore missing fields = Y). lineItems is a JSON array — STRINGIFY in normalize options. Data: ../data/json-normalize-complex-transactions.json

---
