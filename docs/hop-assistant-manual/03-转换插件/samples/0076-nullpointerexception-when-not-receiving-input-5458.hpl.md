# Pipeline: 0076-nullpointerexception-when-not-receiving-input-5458

## Basic Information

- **Pipeline Name:** 0076-nullpointerexception-when-not-receiving-input-5458
- **Source File:** `03-转换插件/samples/0076-nullpointerexception-when-not-receiving-input-5458.hpl`

## Transforms

| Name | Type |
|------|------|
| Detect empty stream | DetectEmptyStream |
| File Metadata | FileMetadataPlugin |
| Filter rows | FilterRows |
| Get file names | GetFileNames |
| get nb files | MemoryGroupBy |
| all files | Dummy |
| 0 files? | FilterRows |
| ok | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Filter rows | all files |
| Filter rows | File Metadata |
| Get file names | Filter rows |
| File Metadata | Detect empty stream |
| Detect empty stream | get nb files |
| get nb files | 0 files? |
| 0 files? | ok |
| 0 files? | Abort |
