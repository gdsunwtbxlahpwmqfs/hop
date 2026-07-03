# Pipeline: rest-canada-public-holidays

## Basic Information

- **Pipeline Name:** rest-canada-public-holidays
- **Source File:** `03-转换插件/输入类/samples/json-input-public-holidays-from-rest.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_COUNTRY | BE |  |

## Transforms

| Name | Type |
|------|------|
| clean | SelectValues |
| generate 1 row | RowGenerator |
| get ${PRM_COUNTRY} public holidays | Rest |
| read countries | JsonInput |
| read fields | JsonInput |
| read types | JsonInput |

## Hops

| From | To |
|------|----|
| generate 1 row | get ${PRM_COUNTRY} public holidays |
| get ${PRM_COUNTRY} public holidays | read fields |
| read fields | read countries |
| read countries | read types |
| read types | clean |

## Notes

This sample pipeline reads the public holidays for ${PRM_COUNTRY} in 2023 and parses the result.

The default value for ${PRM_COUNTRY} is set to 'BE' (Belgium).

Check your 2-character country code to run this sample pipeline for your country:

---
