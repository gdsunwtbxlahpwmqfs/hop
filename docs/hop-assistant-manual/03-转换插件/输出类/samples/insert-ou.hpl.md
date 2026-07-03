# Pipeline: insert-ou

## Basic Information

- **Pipeline Name:** insert-ou
- **Description:** LDAP Output: Insert organizational unit
- **Source File:** `03-转换插件/输出类/samples/insert-ou.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Insert OU | LDAPOutput |
| OUs | DataGrid |

## Hops

| From | To |
|------|----|
| OUs | LDAP Insert OU |
