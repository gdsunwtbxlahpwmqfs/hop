# Pipeline: json-normalize-hop-config-layers

## Basic Information

- **Pipeline Name:** json-normalize-hop-config-layers
- **Description:** Read hop-config-shaped JSON in two layers: one wide row (arrays as JSON text), then expand variables, lifecycleEnvironments, and projectConfigurations in parallel branches.
- **Source File:** `03-转换插件/数据验证与质量类/samples/json-normalize-hop-config-layers.hpl`

## Transforms

| Name | Type |
|------|------|
| wide hop-config root | JsonNormalizeInput |
| variables as rows | JsonNormalizeInput |
| lifecycle environments as rows | JsonNormalizeInput |
| project configurations as rows | JsonNormalizeInput |
| preview variables | Dummy |
| preview lifecycles | Dummy |
| preview project configs | Dummy |

## Hops

| From | To |
|------|----|
| wide hop-config root | variables as rows |
| wide hop-config root | lifecycle environments as rows |
| wide hop-config root | project configurations as rows |
| variables as rows | preview variables |
| lifecycle environments as rows | preview lifecycles |
| project configurations as rows | preview project configs |

## Notes

1) Wide row: Record JsonPath $ emits one row; nested JSON arrays are stringified (hop-config variables, lifecycleEnvironments, …).

2) Three JSON Normalize Input transforms read those strings (Source from previous transform). Record JsonPath $[*] unwraps each JSON array.

3) The first transform uses *copy* (distribute=N) so the single wide row reaches all three branches; round-robin would leave two streams empty.

File: ${HOP_CONFIG_FOLDER}/hop-config.json. Expect very wide output on large real configs.

---
