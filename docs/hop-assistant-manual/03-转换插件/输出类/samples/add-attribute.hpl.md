# Pipeline: add-attribute

## Basic Information

- **Pipeline Name:** add-attribute
- **Description:** LDAP Output: Add attribute to existing entry
- **Source File:** `03-转换插件/输出类/samples/add-attribute.hpl`

## Transforms

| Name | Type |
|------|------|
| Add Description | DataGrid |
| LDAP Add Attribute | LDAPOutput |

## Hops

| From | To |
|------|----|
| Add Description | LDAP Add Attribute |
