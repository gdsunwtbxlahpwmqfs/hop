# Pipeline: 0042-user-defined-java-class-validate

## Basic Information

- **Pipeline Name:** 0042-user-defined-java-class-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0042-user-defined-java-class-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| transform | getXMLData |
| definitions | getXMLData |
| fields | getXMLData |
| infos | getXMLData |
| targets | getXMLData |
| validate | Dummy |
| validate definitions | Dummy |
| validate fields | Dummy |
| validate infos | Dummy |
| validate targets | Dummy |

## Hops

| From | To |
|------|----|
| transform | validate |
| definitions | validate definitions |
| fields | validate fields |
| infos | validate infos |
| targets | validate targets |
