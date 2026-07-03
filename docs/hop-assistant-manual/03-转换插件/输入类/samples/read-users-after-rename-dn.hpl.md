# Pipeline: read-users-after-rename-dn

## Basic Information

- **Pipeline Name:** read-users-after-rename-dn
- **Description:** LDAP Input: Read users after rename-dn pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-rename-dn.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
