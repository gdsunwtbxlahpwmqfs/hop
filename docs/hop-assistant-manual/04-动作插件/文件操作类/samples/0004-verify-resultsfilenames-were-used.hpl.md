# Pipeline: 0004-verify-resultsfilenames-were-used

## Basic Information

- **Pipeline Name:** 0004-verify-resultsfilenames-were-used
- **Source File:** `04-动作插件/文件操作类/samples/0004-verify-resultsfilenames-were-used.hpl`

## Transforms

| Name | Type |
|------|------|
| Get files from copied directory | GetFileNames |
| Detect empty stream | DetectEmptyStream |
| Test Failed | Abort |
| Success | Dummy |

## Hops

| From | To |
|------|----|
| Get files from copied directory | Detect empty stream |
| Detect empty stream | Test Failed |
| Get files from copied directory | Success |
