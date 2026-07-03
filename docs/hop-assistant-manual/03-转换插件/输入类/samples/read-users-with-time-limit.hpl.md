# Pipeline: read-users-with-time-limit

## Basic Information

- **Pipeline Name:** read-users-with-time-limit
- **Description:** LDAP Input: Read users with time limit
- **Source File:** `03-转换插件/输入类/samples/read-users-with-time-limit.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
