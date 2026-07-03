# Pipeline: cleanup-entries

## Basic Information

- **Pipeline Name:** cleanup-entries
- **Description:** LDAP Output: Cleanup - Delete all entries under People OU
- **Source File:** `03-转换插件/输出类/samples/cleanup-entries.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculate Length | Calculator |
| Filter OU | FilterRows |
| LDAP Delete Entries | LDAPOutput |
| LDAP Find All Entries | LDAPInput |
| Sort Descending | SortRows |

## Hops

| From | To |
|------|----|
| LDAP Find All Entries | Filter OU |
| Calculate Length | Sort Descending |
| Sort Descending | LDAP Delete Entries |
| Filter OU | Calculate Length |
