# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/网络与服务类/samples/ssh-password.hpl`

## Transforms

| Name | Type |
|------|------|
| Write to log | WriteToLog |
| Run SSH commands | SSH |

## Hops

| From | To |
|------|----|
| Run SSH commands | Write to log |
