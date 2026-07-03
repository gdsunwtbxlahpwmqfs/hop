# Pipeline: read-users-onelevel-scope

## Basic Information

- **Pipeline Name:** read-users-onelevel-scope
- **Description:** LDAP Input: Read users with one level search scope
- **Source File:** `03-转换插件/输入类/samples/read-users-onelevel-scope.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
