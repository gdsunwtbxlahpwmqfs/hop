# Pipeline: json-input-nested-elements

## Basic Information

- **Pipeline Name:** json-input-nested-elements
- **Source File:** `03-转换插件/输入类/samples/json-input-nested-elements.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read configuration variables 2 | JsonInput |
| read dataflow run configuration | JsonInput |
| remove configuration_variables element | SelectValues |

## Hops

| From | To |
|------|----|
| read dataflow run configuration | read configuration variables 2 |
| read configuration variables 2 | remove configuration_variables element |
| remove configuration_variables element | preview |

## Notes

This sample reads the JSON definition for the Dataflow pipeline run configuration metadata item.

This metadata item is defined in ${PROJECT_HOME}/metadata/metadata/pipeline-run-configuration/DataFlow.json

In the first JSON Input transform, we read the general run configuration elements.

We also pick up the repeating element (array) of configuration variables as a String field.

In the second JSON Input transform, we read and parse the configuration variables.

This second transform uses the "configuration_variables" to read from, instead of reading from a file like we did in the first JSON Input transform.

---
