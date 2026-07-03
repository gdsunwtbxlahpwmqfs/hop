# Pipeline: read-transactions

## Basic Information

- **Pipeline Name:** read-transactions
- **Source File:** `03-转换插件/samples/read-transactions.hpl`

## Transforms

| Name | Type |
|------|------|
| Block Headers | BlockUntilTransformsFinish |
| DATA_LINE | ScriptValueMod |
| Data | Dummy |
| FINAL_RESULT | SelectValues |
| Group by DATA_FILE | GroupBy |
| Headers | Dummy |
| JSON input | JsonInput |
| JSON_FILE | ScriptValueMod |
| Lookup Header Label | StreamLookup |
| Microsoft Excel input | ExcelInput |
| sheet_row == 1 | FilterRows |

## Hops

| From | To |
|------|----|
| sheet_row == 1 | Headers |
| sheet_row == 1 | Data |
| Data | Block Headers |
| Headers | Lookup Header Label |
| Block Headers | Lookup Header Label |
| Lookup Header Label | DATA_LINE |
| DATA_LINE | Group by DATA_FILE |
| Group by DATA_FILE | JSON_FILE |
| JSON_FILE | JSON input |
| JSON input | FINAL_RESULT |
| Microsoft Excel input | sheet_row == 1 |

## Notes

This pipeline splits "Headers" and "Data" , then look up Headers to construct a JSON for each xlsx.

In the JSON doesn't matter the fields order, then extract the fields with JSON input

---
