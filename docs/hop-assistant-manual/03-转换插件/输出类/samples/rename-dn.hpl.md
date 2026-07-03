# Pipeline: rename-dn

## Basic Information

- **Pipeline Name:** rename-dn
- **Description:** LDAP Output: Rename DN
- **Source File:** `03-转换插件/输出类/samples/rename-dn.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Rename DN | LDAPOutput |
| Rename Data | DataGrid |

## Hops

| From | To |
|------|----|
| Rename Data | LDAP Rename DN |
