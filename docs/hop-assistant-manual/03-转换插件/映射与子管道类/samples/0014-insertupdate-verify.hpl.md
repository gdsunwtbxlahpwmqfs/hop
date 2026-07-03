# Pipeline: 0014-insertupdate-verify

## Basic Information

- **Pipeline Name:** 0014-insertupdate-verify
- **Source File:** `03-转换插件/映射与子管道类/samples/0014-insertupdate-verify.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| exportsFilePath | /Users/dcampen/exports/hop |  |
| schema | dbo |  |
| sqlObject | tenant |  |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| CountRows | TableInput |
| Write to log | WriteToLog |
| countRows <> 2 | FilterRows |

## Hops

| From | To |
|------|----|
| countRows <> 2 | Abort |
| CountRows | Write to log |
| Write to log | countRows <> 2 |
