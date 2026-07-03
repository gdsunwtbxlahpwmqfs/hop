# Pipeline: beers-wikipedia-query-paths

## Basic Information

- **Pipeline Name:** beers-wikipedia-query-paths
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/beers-wikipedia-query-paths.hpl`

## Transforms

| Name | Type |
|------|------|
| How are 2 beers related? | Neo4jCypherOutput |
| Lookup source node label | StreamLookup |
| Lookup target node label | StreamLookup |
| get node data | JsonInput |
| get nodes | JsonInput |
| get relationship data | JsonInput |
| get relationships | JsonInput |
| node data | SelectValues |
| relationship data | SelectValues |
| Beers | DataGrid |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| How are 2 beers related? | get relationships |
| How are 2 beers related? | get nodes |
| get nodes | get node data |
| get relationships | get relationship data |
| Lookup source node label | Lookup target node label |
| node data | Lookup source node label |
| node data | Lookup target node label |
| get relationship data | relationship data |
| relationship data | Lookup source node label |
| Beers | How are 2 beers related? |
| get node data | node data |
| Lookup target node label | Output |
