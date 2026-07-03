# Pipeline: read-ous

## Basic Information

- **Pipeline Name:** read-ous
- **Description:** LDAP Input: Read organizational units
- **Source File:** `03-转换插件/输入类/samples/read-ous.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read OUs | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read OUs | Preview |
