# Pipeline: read-users-specific-attributes

## Basic Information

- **Pipeline Name:** read-users-specific-attributes
- **Description:** LDAP Input: Read users with only specific attributes (cn, uid, mail)
- **Source File:** `03-转换插件/输入类/samples/read-users-specific-attributes.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Read Users | LDAPInput |
| Preview | Dummy |

## Hops

| From | To |
|------|----|
| LDAP Read Users | Preview |
