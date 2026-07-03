# Pipeline: 0045-meta-inject

## Basic Information

- **Pipeline Name:** 0045-meta-inject
- **Source File:** `03-转换插件/映射与子管道类/samples/0045-meta-inject.hpl`

## Transforms

| Name | Type |
|------|------|
| 0045-meta-inject-template.hpl | MetaInject |
| files/meta-inject.xml | getXMLData |
| files/meta-inject.xml field | getXMLData |
| files/meta-inject.xml mappings/mapping | getXMLData |

## Hops

| From | To |
|------|----|
| files/meta-inject.xml mappings/mapping | 0045-meta-inject-template.hpl |
| files/meta-inject.xml | 0045-meta-inject-template.hpl |
| files/meta-inject.xml field | 0045-meta-inject-template.hpl |
