# Pipeline: 0007-get-file-names

## Basic Information

- **Pipeline Name:** 0007-get-file-names
- **Source File:** `03-转换插件/云服务类/samples/0007-get-file-names.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Get file names | GetFileNames |
| Select values | SelectValues |
| Sparkle | RowGenerator |

## Hops

| From | To |
|------|----|
| Sparkle | Get file names |
| Get file names | Select values |
| Select values | Dummy (do nothing) |
