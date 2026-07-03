# Pipeline: 0002-xsd-validator-test

## Basic Information

- **Pipeline Name:** 0002-xsd-validator-test
- **Source File:** `03-转换插件/输出类/samples/0002-xsd-validator-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Get invalidate xml | GetVariable |
| Get validate xml | GetVariable |
| XSD validator | XSDValidator |

## Hops

| From | To |
|------|----|
| XSD validator | Dummy (do nothing) |
| Get validate xml | XSD validator |
| Get invalidate xml | XSD validator |
