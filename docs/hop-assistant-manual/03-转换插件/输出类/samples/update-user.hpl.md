# Pipeline: update-user

## Basic Information

- **Pipeline Name:** update-user
- **Description:** LDAP Output: Update user attributes
- **Source File:** `03-转换插件/输出类/samples/update-user.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Update User | LDAPOutput |
| User Update | DataGrid |

## Hops

| From | To |
|------|----|
| User Update | LDAP Update User |
