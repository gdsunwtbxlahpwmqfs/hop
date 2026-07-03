# Pipeline: read-users-with-paging

## Basic Information

- **Pipeline Name:** read-users-with-paging
- **Description:** LDAP Input: Read users with paging enabled
- **Source File:** `03-转换插件/输入类/samples/read-users-with-paging.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
