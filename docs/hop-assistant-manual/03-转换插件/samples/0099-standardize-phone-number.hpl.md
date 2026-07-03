# Pipeline: test-standardize-phone-number

## Basic Information

- **Pipeline Name:** test-standardize-phone-number
- **Source File:** `03-转换插件/samples/0099-standardize-phone-number.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Keys | DataGrid |
| Phones | DataGrid |
| Standardize phone number | StandardizePhoneNumber |
| Stream lookup | StreamLookup |

## Hops

| From | To |
|------|----|
| Stream lookup | Dummy (do nothing) |
| Phones | Standardize phone number |
| Keys | Stream lookup |
| Standardize phone number | Stream lookup |
