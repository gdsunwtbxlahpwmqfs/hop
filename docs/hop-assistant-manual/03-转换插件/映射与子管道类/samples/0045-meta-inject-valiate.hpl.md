# Pipeline: 0045-meta-inject-valiate

## Basic Information

- **Pipeline Name:** 0045-meta-inject-valiate
- **Source File:** `03-转换插件/映射与子管道类/samples/0045-meta-inject-valiate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/meta-inject.hpl mappings | getXMLData |
| validate mappings | Dummy |
| /tmp/meta-inject.hpl fields | getXMLData |
| validate fields | Dummy |
| /tmp/meta-inject.hpl | getXMLData |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/meta-inject.hpl mappings | validate mappings |
| /tmp/meta-inject.hpl fields | validate fields |
| /tmp/meta-inject.hpl | validate |
