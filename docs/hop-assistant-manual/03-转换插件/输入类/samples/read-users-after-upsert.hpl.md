# Pipeline: read-users-after-upsert

## Basic Information

- **Pipeline Name:** read-users-after-upsert
- **Description:** LDAP Input: Read users after upsert-user pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-upsert.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
