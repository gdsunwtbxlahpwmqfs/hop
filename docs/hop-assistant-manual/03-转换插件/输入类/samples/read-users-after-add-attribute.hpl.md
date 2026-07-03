# Pipeline: read-users-after-add-attribute

## Basic Information

- **Pipeline Name:** read-users-after-add-attribute
- **Description:** LDAP Input: Read users after add-attribute pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-add-attribute.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
