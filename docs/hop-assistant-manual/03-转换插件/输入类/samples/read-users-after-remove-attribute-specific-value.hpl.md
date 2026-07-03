# Pipeline: read-users-after-remove-attribute-specific-value

## Basic Information

- **Pipeline Name:** read-users-after-remove-attribute-specific-value
- **Description:** LDAP Input: Read users after remove-attribute-specific-value pipeline
- **Source File:** `03-转换插件/输入类/samples/read-users-after-remove-attribute-specific-value.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Verify Output | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Verify Output |
