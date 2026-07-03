# Pipeline: read-users-object-scope

## Basic Information

- **Pipeline Name:** read-users-object-scope
- **Description:** LDAP Input: Read users with object search scope
- **Source File:** `03-转换插件/输入类/samples/read-users-object-scope.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
