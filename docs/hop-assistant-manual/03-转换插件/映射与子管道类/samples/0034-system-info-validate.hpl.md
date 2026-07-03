# Pipeline: 0034-system-info-validate

## Basic Information

- **Pipeline Name:** 0034-system-info-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0034-system-info-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Characters only | FilterRows |
| Validate | Dummy |
| cleanup fields | SelectValues |
| filename | GetVariable |
| flatten | Denormaliser |
| parse hop pipeline | XMLInputStream |
| useful bits | FilterRows |

## Hops

| From | To |
|------|----|
| filename | parse hop pipeline |
| parse hop pipeline | Characters only |
| Characters only | useful bits |
| flatten | cleanup fields |
| cleanup fields | Validate |
| useful bits | flatten |
