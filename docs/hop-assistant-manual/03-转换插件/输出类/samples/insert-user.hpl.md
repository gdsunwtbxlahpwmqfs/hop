# Pipeline: insert-user

## Basic Information

- **Pipeline Name:** insert-user
- **Description:** LDAP Output: Insert user entry
- **Source File:** `03-转换插件/输出类/samples/insert-user.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Insert User | LDAPOutput |
| User | DataGrid |

## Hops

| From | To |
|------|----|
| User | LDAP Insert User |
