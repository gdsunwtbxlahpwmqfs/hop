# AES 双向密码编码器

> **⚠️ 警告:** AES 前缀自版本 2.11 起已弃用，请使用 AES2 前缀，此前缀提供更强的加密强度。从 AES 迁移到 AES2 前缀需要重新生成密码字符串。

## 描述

AES 双向密码编码器 plugin 允许你使用提供的密钥字符串来加密和解密密码。

## 配置

此编码器的配置适用于整个 Hop 环境，因此只能通过系统属性进行设置。这些属性如下所列，也可以在使用 hop-run 时作为系统属性指定：

- *`HOP_PASSWORD_ENCODER_PLUGIN`*: 将此设置为此 plugin 的 ID：AES2
- *`HOP_AES_ENCODER_KEY`*: 指定用于编码或解码密码的密钥

## 注意事项

请注意，密码使用前缀 ```AES2 ``` 编码，即 AES2 加一个空格。
这意味着它与标准的 Hop 编码密码不同，后者使用前缀 ```Encrypted ```，即 Encrypted 加一个空格。
因此，你需要选择一种或另一种方式来编码密码。
不支持混合密码编码。

## 最佳实践

确保仅使用变量和参数来指定密码。
使用 projects plugin 将密码存储在环境中。
这样你可以将密码存储在特定于给定环境（开发、测试、生产等）的单个文件中。

保护密码文件。
尽管密码是加密的，但安全第一。
无法以任何方式读取的密码（无论是否加密）都无法被看到。
因此，请在你最终存储密码的文件上使用适当的文件安全措施。
此建议对于密钥尤其重要。

你也可以使用变量来指定密钥。

## Hop Encrypt

你可以通过在环境中设置变量，然后使用 hop-encrypt 来查看输出来测试你的设置：

```bash
export HOP_PASSWORD_ENCODER_PLUGIN=AES2
export HOP_AES_ENCODER_KEY=ddsfsdfsfsdf
sh hop-encrypt.sh -hop MyPassword222
AES2 696N5ATiXqU0AxdkLpN+UT67Ud5P6TMkq7OGSRc=
```
