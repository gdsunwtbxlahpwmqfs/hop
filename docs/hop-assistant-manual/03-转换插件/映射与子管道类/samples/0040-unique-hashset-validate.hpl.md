# Pipeline: 0040-unique-hashset-validate

## Basic Information

- **Pipeline Name:** 0040-unique-hashset-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0040-unique-hashset-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/unique-rows-hashset.hpl | getXMLData |
| validate | Dummy |
| ${java.io.tmpdir}/unique-rows-hashset.hpl fields | getXMLData |
| validate fields | Dummy |

## Hops

| From | To |
|------|----|
| ${java.io.tmpdir}/unique-rows-hashset.hpl | validate |
| ${java.io.tmpdir}/unique-rows-hashset.hpl fields | validate fields |
