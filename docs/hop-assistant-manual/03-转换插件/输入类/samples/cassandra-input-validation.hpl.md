# Pipeline: cassandra-input-validation

## Basic Information

- **Pipeline Name:** cassandra-input-validation
- **Source File:** `03-转换插件/输入类/samples/cassandra-input-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| TEST | Dummy |
| hop.customers | CassandraInput |

## Hops

| From | To |
|------|----|
| hop.customers | TEST |
