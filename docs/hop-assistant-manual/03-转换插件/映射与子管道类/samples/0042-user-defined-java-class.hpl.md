# Pipeline: 0042-user-defined-java-class

## Basic Information

- **Pipeline Name:** 0042-user-defined-java-class
- **Source File:** `03-转换插件/映射与子管道类/samples/0042-user-defined-java-class.hpl`

## Transforms

| Name | Type |
|------|------|
| 0042-user-defined-java-class-template.hpl | MetaInject |
| definitions | getXMLData |
| fields with Strings | getXMLData |
| files/user-defined-java-class.xml | getXMLData |
| infos | getXMLData |
| parameters | getXMLData |
| targets | getXMLData |

## Hops

| From | To |
|------|----|
| definitions | 0042-user-defined-java-class-template.hpl |
| infos | 0042-user-defined-java-class-template.hpl |
| targets | 0042-user-defined-java-class-template.hpl |
| parameters | 0042-user-defined-java-class-template.hpl |
| files/user-defined-java-class.xml | 0042-user-defined-java-class-template.hpl |
| fields with Strings | 0042-user-defined-java-class-template.hpl |
