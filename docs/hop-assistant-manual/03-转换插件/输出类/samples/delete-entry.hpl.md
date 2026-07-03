# Pipeline: delete-entry

## Basic Information

- **Pipeline Name:** delete-entry
- **Description:** LDAP Output: Delete entry
- **Source File:** `03-转换插件/输出类/samples/delete-entry.hpl`

## Transforms

| Name | Type |
|------|------|
| Delete DN | DataGrid |
| LDAP Delete Entry | LDAPOutput |

## Hops

| From | To |
|------|----|
| Delete DN | LDAP Delete Entry |
