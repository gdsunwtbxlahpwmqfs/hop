# Pipeline: 0088-yaml-input-validate

## Basic Information

- **Pipeline Name:** 0088-yaml-input-validate
- **Source File:** `03-转换插件/samples/0088-yaml-input-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| files/yaml-input.yaml | YamlInput |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| files/yaml-input.yaml | validate |
