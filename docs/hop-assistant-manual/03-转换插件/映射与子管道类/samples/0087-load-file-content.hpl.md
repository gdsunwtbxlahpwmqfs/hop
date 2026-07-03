# Pipeline: 0087-load-file-content

## Basic Information

- **Pipeline Name:** 0087-load-file-content
- **Source File:** `03-转换插件/映射与子管道类/samples/0087-load-file-content.hpl`

## Transforms

| Name | Type |
|------|------|
| 0087-load-file-content-template.hpl | MetaInject |
| golden-load-file-content | DataSetInput |
| golden-load-file-content-files | DataSetInput |
| golden-load-file-content-fields | DataSetInput |

## Hops

| From | To |
|------|----|
| golden-load-file-content | 0087-load-file-content-template.hpl |
| golden-load-file-content-files | 0087-load-file-content-template.hpl |
| golden-load-file-content-fields | 0087-load-file-content-template.hpl |
