# Pipeline: 0036-text-file-output

## Basic Information

- **Pipeline Name:** 0036-text-file-output
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-text-file-output.hpl`

## Transforms

| Name | Type |
|------|------|
| files/textfile/text-file-output.xml - fields | getXMLData |
| files/textfile/text-file-output.xml - general | getXMLData |
| inject into /tmp/text-file-output-mdi.hpl | MetaInject |

## Hops

| From | To |
|------|----|
| files/textfile/text-file-output.xml - fields | inject into /tmp/text-file-output-mdi.hpl |
| files/textfile/text-file-output.xml - general | inject into /tmp/text-file-output-mdi.hpl |
