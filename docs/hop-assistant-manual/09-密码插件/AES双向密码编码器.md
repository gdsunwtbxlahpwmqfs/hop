# AES 双向密码编码器（AES Two Way Password Encoder）

## 警告

AES 前缀自 2.11 版本起已弃用，请使用 AES2 前缀，该前缀提供更好的加密强度。从 AES 迁移到 AES2 前缀需要您重新生成密码字符串。

## 核心功能

AES 双向密码编码器插件允许您使用提供的密钥字符串对密码进行加密和解密。

## 配置

此编码器的配置是针对整个 Hop 环境的，因此只能使用系统属性进行配置。以下属性也可以通过 hop-run 指定为系统属性：

- **`HOP_PASSWORD_ENCODER_PLUGIN`**：将此项设置为此插件的 ID：AES2
- **`HOP_AES_ENCODER_KEY`**：指定用于编码或解码密码的密钥

## 注意事项

请注意，密码使用前缀 `AES2 `（即 AES2 加一个空格）进行编码。

这意味着它与标准的 Hop 编码密码（前缀为 `Encrypted `，即 Encrypted 加一个空格）不同。

因此，您需要选择其中一种方式对密码进行编码，不支持混合使用密码编码方式。

## 最佳实践

确保仅使用变量和参数来指定密码。

使用项目插件将密码存储在环境中。这样您就可以在特定于给定环境（开发、测试、生产等）的单个文件中存储密码。

保护密码文件。即使密码已加密，小心驶得万年船。无法读取的密码（无论是加密的还是其他形式）在任何情况下都不会被看到。因此，请对最终存储密码的文件使用适当的文件安全措施。

此建议对于密钥同样适用，甚至更为重要。

您也可以使用变量来指定密钥。

## Hop Encrypt

您可以通过在环境中设置变量，然后使用 hop-encrypt 查看输出来测试您的设置：

```bash
export HOP_PASSWORD_ENCODER_PLUGIN=AES2
export HOP_AES_ENCODER_KEY=ddsfsdfsfsdf
sh hop-encrypt.sh -hop MyPassword222
AES2 696N5ATiXqU0AxdkLpN+UT67Ud5P6TMkq7OGSRc=
```
