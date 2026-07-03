# Pipeline: 0004-concat-some-fields

## Basic Information

- **Pipeline Name:** 0004-concat-some-fields
- **Source File:** `03-转换插件/samples/0004-concat-some-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| OUTPUT | Dummy |
| concat with comma | ConcatFields |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | concat with comma |
| concat with comma | OUTPUT |

## Notes

### Note 1

This reads a file with lazy conversion.

We check to see that the fields are concatenated but also that binary to normal

storage conversion correctly takes place in the transform.

---

### Note 2

We need to ensure that concat allows for non-concatenated fields

to pass through the step without being altered.

---
