# Pipeline: detect-empty-stream-basic

## Basic Information

- **Pipeline Name:** detect-empty-stream-basic
- **Source File:** `03-转换插件/流程控制类/samples/detect-empty-stream-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| detect empty stream | DetectEmptyStream |
| Dummy (do nothing) | Dummy |
| get empty file list | GetFileNames |

## Hops

| From | To |
|------|----|
| detect empty stream | Dummy (do nothing) |
| get empty file list | detect empty stream |

## Notes

the "Detect empty stream" transform checks if the stream is empty.

If it is, a single line with the correct stream layout and null values is generated.

---
