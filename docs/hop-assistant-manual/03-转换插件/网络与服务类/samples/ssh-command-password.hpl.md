# Pipeline: ssh-command-password

## Basic Information

- **Pipeline Name:** ssh-command-password
- **Description:** Runs a command over SSH (password auth) against the test SSH server and asserts the echoed marker comes back on stdOut.
- **Source File:** `03-转换插件/网络与服务类/samples/ssh-command-password.hpl`

## Transforms

| Name | Type |
|------|------|
| Run SSH command | SSH |
| marker not found? | FilterRows |
| OK | Dummy |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Run SSH command | marker not found? |
| marker not found? | OK |
| marker not found? | Abort |
