# Pipeline: read-ou-after-insert

## Basic Information

- **Pipeline Name:** read-ou-after-insert
- **Description:** LDAP Input: Read OU inserted by insert-ou pipeline
- **Source File:** `03-转换插件/输入类/samples/read-ou-after-insert.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read OU | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read OU | Verify Output |
