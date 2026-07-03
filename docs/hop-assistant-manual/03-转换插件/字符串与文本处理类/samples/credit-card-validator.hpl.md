# Pipeline: credit-card-validator

## Basic Information

- **Pipeline Name:** credit-card-validator
- **Source File:** `03-转换插件/字符串与文本处理类/samples/credit-card-validator.hpl`

## Transforms

| Name | Type |
|------|------|
| credit cards | DataGrid |
| Credit card validator | CreditCardValidator |
| valid? | FilterRows |
| log 19 valid cards | WriteToLog |
| log 1 invalid card | WriteToLog |

## Hops

| From | To |
|------|----|
| credit cards | Credit card validator |
| Credit card validator | valid? |
| valid? | log 19 valid cards |
| valid? | log 1 invalid card |

## Notes

credit card numbers (Mastercard and Visa) generated with https://www.getcreditcardnumbers.com/

card nb 12 (card 4539845805918381) had one digit manually changed to make the validation fail.

---
