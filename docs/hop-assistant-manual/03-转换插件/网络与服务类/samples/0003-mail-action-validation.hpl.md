# Pipeline: 0002-mail-action-validation

## Basic Information

- **Pipeline Name:** 0002-mail-action-validation
- **Source File:** `03-转换插件/网络与服务类/samples/0003-mail-action-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Detect empty stream | DetectEmptyStream |
| Filter rows | FilterRows |
| Load file content in memory | LoadFileInput |
| log filename | WriteToLog |
| log invalid mails | WriteToLog |
| log no mails | WriteToLog |
| ok | Dummy |

## Hops

| From | To |
|------|----|
| Detect empty stream | log no mails |
| Filter rows | log invalid mails |
| Filter rows | ok |
| Load file content in memory | Detect empty stream |
| Load file content in memory | log filename |
| log filename | Filter rows |
| log invalid mails | Abort |
| log no mails | Abort |
