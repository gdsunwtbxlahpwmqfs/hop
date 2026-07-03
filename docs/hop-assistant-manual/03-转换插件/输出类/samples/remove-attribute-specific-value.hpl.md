# Pipeline: remove-attribute-specific-value

## Basic Information

- **Pipeline Name:** remove-attribute-specific-value
- **Description:** LDAP Output: Remove specific attribute value
- **Source File:** `03-转换插件/输出类/samples/remove-attribute-specific-value.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Remove Attribute | LDAPOutput |
| Remove Phone Value | DataGrid |

## Hops

| From | To |
|------|----|
| Remove Phone Value | LDAP Remove Attribute |
