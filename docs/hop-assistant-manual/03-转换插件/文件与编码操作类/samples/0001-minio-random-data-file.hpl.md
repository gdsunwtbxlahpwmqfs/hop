# Pipeline: 0001-minio-random-data-file

## Basic Information

- **Pipeline Name:** 0001-minio-random-data-file
- **Source File:** `03-转换插件/文件与编码操作类/samples/0001-minio-random-data-file.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| FILENAME |  | The filename to write to |

## Transforms

| Name | Type |
|------|------|
| 100k rows | RowGenerator |
| id | Sequence |
| random values | RandomValue |
| Write to file | TextFileOutput |

## Hops

| From | To |
|------|----|
| 100k rows | id |
| id | random values |
| random values | Write to file |
