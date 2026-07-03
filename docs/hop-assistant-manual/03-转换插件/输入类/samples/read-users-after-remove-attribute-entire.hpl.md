# Pipeline: read-users-after-remove-attribute-entire

## Basic Information

- **Pipeline Name:** read-users-after-remove-attribute-entire
- **Description:** LDAP Input: Read users after remove-attribute-entire pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-remove-attribute-entire.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
