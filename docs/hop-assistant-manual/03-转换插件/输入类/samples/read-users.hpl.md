# Pipeline: read-users

## Basic Information

- **Pipeline Name:** read-users
- **Description:** LDAP Input: Read all users
- **Source File:** `03-转换插件/输入类/samples/read-users.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
