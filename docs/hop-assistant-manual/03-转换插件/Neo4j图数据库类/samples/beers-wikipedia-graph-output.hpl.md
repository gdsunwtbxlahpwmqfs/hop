# Pipeline: beers-wikipedia-graph-output

## Basic Information

- **Pipeline Name:** beers-wikipedia-graph-output
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/beers-wikipedia-graph-output.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/beers-wikipedia.hop | CubeInput |
| Update Belgian Beers graph | Neo4jGraphOutput |

## Hops

| From | To |
|------|----|
| /tmp/beers-wikipedia.hop | Update Belgian Beers graph |
