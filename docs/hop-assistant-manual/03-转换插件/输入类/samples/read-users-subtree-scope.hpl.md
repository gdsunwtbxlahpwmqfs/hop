# Pipeline: read-users-subtree-scope

## Basic Information

- **Pipeline Name:** read-users-subtree-scope
- **Description:** LDAP Input: Read users with subtree search scope
- **Source File:** `03-转换插件/输入类/samples/read-users-subtree-scope.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
