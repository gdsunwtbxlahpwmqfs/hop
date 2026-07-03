# Pipeline: remove-attribute-entire

## Basic Information

- **Pipeline Name:** remove-attribute-entire
- **Description:** LDAP Output: Remove entire attribute (all values)
- **Source File:** `03-转换插件/输出类/samples/remove-attribute-entire.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Remove Attribute | LDAPOutput |
| Remove Phone Entire | DataGrid |

## Hops

| From | To |
|------|----|
| Remove Phone Entire | LDAP Remove Attribute |
