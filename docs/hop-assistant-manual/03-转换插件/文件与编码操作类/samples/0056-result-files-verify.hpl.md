# Pipeline: 0056-result-files-verify

## Basic Information

- **Pipeline Name:** 0056-result-files-verify
- **Source File:** `03-转换插件/文件与编码操作类/samples/0056-result-files-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Get files from result | FilesFromResult |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Get files from result | Verify |
