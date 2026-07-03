# Pipeline: 0037-getfilenames-nofiles-exception

## Basic Information

- **Pipeline Name:** 0037-getfilenames-nofiles-exception
- **Source File:** `03-转换插件/输入类/samples/0038-getfilenames-nofiles-exception.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Get file names | GetFileNames |
| Abort | Abort |
| Success - No files found | Dummy |

## Hops

| From | To |
|------|----|
| Generate rows | Get file names |
| Get file names | Abort |
| Get file names | Success - No files found |
