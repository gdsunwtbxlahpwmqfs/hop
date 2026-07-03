# Pipeline: 0005-write-to-remote-location

## Basic Information

- **Pipeline Name:** 0005-write-to-remote-location
- **Source File:** `03-转换插件/samples/0005-write-to-remote-location.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| id | Sequence |
| intValue | RandomValue |

## Hops

| From | To |
|------|----|
| Generate rows | id |
| id | intValue |
