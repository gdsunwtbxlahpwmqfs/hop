# Pipeline: 0002-mail-transform-validation

## Basic Information

- **Pipeline Name:** 0002-mail-transform-validation
- **Source File:** `03-转换插件/网络与服务类/samples/0002-mail-transform-validation.hpl`

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
| Filter rows | ok |
| Load file content in memory | Detect empty stream |
| Detect empty stream | log no mails |
| log no mails | Abort |
| Filter rows | log invalid mails |
| log invalid mails | Abort |
| Load file content in memory | log filename |
| log filename | Filter rows |
