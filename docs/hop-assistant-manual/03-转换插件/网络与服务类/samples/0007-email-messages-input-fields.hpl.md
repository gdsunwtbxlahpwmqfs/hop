# Pipeline: 0007-email-messages-input-fields

## Basic Information

- **Pipeline Name:** 0007-email-messages-input-fields
- **Source File:** `03-转换插件/网络与服务类/samples/0007-email-messages-input-fields.hpl`

## Transforms

| Name | Type |
|------|------|
| Email messages input | MailInput |
| preview | Dummy |
| rm dates, size, body | SelectValues |

## Hops

| From | To |
|------|----|
| Email messages input | rm dates, size, body |
| rm dates, size, body | preview |
