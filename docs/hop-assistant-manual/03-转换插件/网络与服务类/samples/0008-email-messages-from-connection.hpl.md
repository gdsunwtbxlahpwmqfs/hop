# Pipeline: 0008-email-messages-from-connection

## Basic Information

- **Pipeline Name:** 0008-email-messages-from-connection
- **Source File:** `03-转换插件/网络与服务类/samples/0008-email-messages-from-connection.hpl`

## Transforms

| Name | Type |
|------|------|
| Email messages input | MailInput |
| preview | Dummy |
| rm dates, size | SelectValues |

## Hops

| From | To |
|------|----|
| Email messages input | rm dates, size |
| rm dates, size | preview |
