# Pipeline: read-users-with-filter

## Basic Information

- **Pipeline Name:** read-users-with-filter
- **Description:** LDAP Input: Read users with filter (description attribute present)
- **Source File:** `03-转换插件/输入类/samples/read-users-with-filter.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
