# Pipeline: 0011-json-normalize-hop-config-layers

## Basic Information

- **Pipeline Name:** 0011-json-normalize-hop-config-layers
- **Description:** Integration test: hop-config-shaped JSON (${HOP_CONFIG_FOLDER}/hop-config.json), wide row then parallel branches; Select Values narrows rows for golden datasets.
- **Source File:** `03-转换插件/数据验证与质量类/samples/0011-json-normalize-hop-config-layers.hpl`

## Transforms

| Name | Type |
|------|------|
| wide hop-config root | JsonNormalizeInput |
| variables as rows | JsonNormalizeInput |
| lifecycle environments as rows | JsonNormalizeInput |
| project configurations as rows | JsonNormalizeInput |
| fields variables validate | SelectValues |
| fields lifecycles validate | SelectValues |
| fields project configs validate | SelectValues |
| preview variables | Dummy |
| preview lifecycles | Dummy |
| preview project configs | Dummy |

## Hops

| From | To |
|------|----|
| wide hop-config root | variables as rows |
| wide hop-config root | lifecycle environments as rows |
| wide hop-config root | project configurations as rows |
| variables as rows | fields variables validate |
| fields variables validate | preview variables |
| lifecycle environments as rows | fields lifecycles validate |
| fields lifecycles validate | preview lifecycles |
| project configurations as rows | fields project configs validate |
| fields project configs validate | preview project configs |
