# Pipeline: cleanup-ous

## Basic Information

- **Pipeline Name:** cleanup-ous
- **Description:** LDAP Output: Cleanup - Delete OUs after entries are deleted
- **Source File:** `03-转换插件/输出类/samples/cleanup-ous.hpl`

## Transforms

| Name | Type |
|------|------|
| Delete OUs | DataGrid |
| LDAP Delete OUs | LDAPOutput |

## Hops

| From | To |
|------|----|
| Delete OUs | LDAP Delete OUs |
