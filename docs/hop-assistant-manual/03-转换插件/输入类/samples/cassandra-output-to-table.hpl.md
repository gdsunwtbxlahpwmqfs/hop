# Pipeline: cassandra-output-to-table

## Basic Information

- **Pipeline Name:** cassandra-output-to-table
- **Source File:** `03-转换插件/输入类/samples/cassandra-output-to-table.hpl`

## Transforms

| Name | Type |
|------|------|
| customers-1k.txt | CSVInput |
| hop.customers | CassandraOutput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | hop.customers |

## Notes

Commit size set to 11 to check for dangling records

---
