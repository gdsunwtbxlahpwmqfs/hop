# Pipeline: read-users-after-delete-entry

## Basic Information

- **Pipeline Name:** read-users-after-delete-entry
- **Description:** LDAP Input: Read users after delete-entry pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-delete-entry.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
