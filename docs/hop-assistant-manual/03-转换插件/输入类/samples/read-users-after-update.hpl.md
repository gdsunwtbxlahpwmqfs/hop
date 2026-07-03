# Pipeline: read-users-after-update

## Basic Information

- **Pipeline Name:** read-users-after-update
- **Description:** LDAP Input: Read users after update-user pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-update.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
