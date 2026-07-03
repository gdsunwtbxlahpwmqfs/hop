# Pipeline: 0008-zip-action-verify-generated-files-in-zip-are-valid

## Basic Information

- **Pipeline Name:** 0008-zip-action-verify-generated-files-in-zip-are-valid
- **Source File:** `04-动作插件/文件操作类/samples/0008-zip-action-verify-generated-files-in-zip-are-valid.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Detect empty stream | DetectEmptyStream |
| build filename | ScriptValueMod |
| date -> str | SelectValues |
| generate 1 row | RowGenerator |
| get current year | Calculator |
| get today | SystemInfo |
| read file from zip | TextFileInput2 |

## Hops

| From | To |
|------|----|
| generate 1 row | get today |
| get today | get current year |
| get current year | date -> str |
| date -> str | build filename |
| build filename | read file from zip |
| read file from zip | Detect empty stream |
| Detect empty stream | Abort |

## Notes

verify https://github.com/apache/hop/issues/3472

---
