# Pipeline: 0008-zip-action-generate-files-to-zip

## Basic Information

- **Pipeline Name:** 0008-zip-action-generate-files-to-zip
- **Source File:** `04-动作插件/文件操作类/samples/0008-zip-action-generate-files-to-zip.hpl`

## Transforms

| Name | Type |
|------|------|
| 5k rows | CloneRow |
| generate 1 row | RowGenerator |
| get filename | ScriptValueMod |
| get today | SystemInfo |
| keep year | SelectValues |
| new_date -> new_date_str | SelectValues |
| new_date year | Calculator |
| today + counter | Calculator |
| unique years | Unique |
| wait for files | BlockUntilTransformsFinish |
| write files | TextFileOutput |
| zip-action-archive-files.hwf | WorkflowExecutor |

## Hops

| From | To |
|------|----|
| generate 1 row | get today |
| get today | 5k rows |
| today + counter | new_date year |
| 5k rows | today + counter |
| get filename | write files |
| new_date year | new_date -> new_date_str |
| new_date -> new_date_str | get filename |
| write files | keep year |
| unique years | zip-action-archive-files.hwf |
| keep year | wait for files |
| wait for files | unique years |
