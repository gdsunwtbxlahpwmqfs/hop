# Pipeline: upsert-user

## Basic Information

- **Pipeline Name:** upsert-user
- **Description:** LDAP Output: Upsert user (insert if not exists, update if exists)
- **Source File:** `03-转换插件/输出类/samples/upsert-user.hpl`

## Transforms

| Name | Type |
|------|------|
| LDAP Upsert User | LDAPOutput |
| User Upsert | DataGrid |

## Hops

| From | To |
|------|----|
| User Upsert | LDAP Upsert User |
