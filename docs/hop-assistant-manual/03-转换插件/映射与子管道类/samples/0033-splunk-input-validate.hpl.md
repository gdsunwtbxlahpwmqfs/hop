# Pipeline: 0033-splunk-input-validate

## Basic Information

- **Pipeline Name:** 0033-splunk-input-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0033-splunk-input-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| parse hop pipeline | XMLInputStream |
| filename | GetVariable |
| useful bits | FilterRows |
| flatten | Denormaliser |
| Characters only | FilterRows |
| ignore extras | FilterRows |
| cleanup fields | SelectValues |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| filename | parse hop pipeline |
| parse hop pipeline | Characters only |
| Characters only | useful bits |
| useful bits | ignore extras |
| ignore extras | flatten |
| flatten | cleanup fields |
| cleanup fields | Validate |
