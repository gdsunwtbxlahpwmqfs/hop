# Pipeline: 0040-unique-hashset

## Basic Information

- **Pipeline Name:** 0040-unique-hashset
- **Source File:** `03-转换插件/映射与子管道类/samples/0040-unique-hashset.hpl`

## Transforms

| Name | Type |
|------|------|
| unique-rows-hashset.xml | getXMLData |
| unique-rows-hashset.xml fields | getXMLData |
| ETL metadata injection | MetaInject |

## Hops

| From | To |
|------|----|
| unique-rows-hashset.xml | ETL metadata injection |
| unique-rows-hashset.xml fields | ETL metadata injection |
