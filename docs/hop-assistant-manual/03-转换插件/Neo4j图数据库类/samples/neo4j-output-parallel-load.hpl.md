# Pipeline: neo4j-output-parallel-load

## Basic Information

- **Pipeline Name:** neo4j-output-parallel-load
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/neo4j-output-parallel-load.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| COPIES | 4 | Parallelism |

## Transforms

| Name | Type |
|------|------|
| Create :Customer nodes | Neo4JOutput |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | Create :Customer nodes |
