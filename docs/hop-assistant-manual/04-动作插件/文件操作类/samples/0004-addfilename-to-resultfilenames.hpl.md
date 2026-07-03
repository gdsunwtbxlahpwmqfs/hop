# Pipeline: 0004-addfilename-to-results-filenames-used

## Basic Information

- **Pipeline Name:** 0004-addfilename-to-results-filenames-used
- **Source File:** `04-动作插件/文件操作类/samples/0004-addfilename-to-resultfilenames.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Get file names | GetFileNames |

## Hops

| From | To |
|------|----|
| Get file names | Dummy (do nothing) |
