# Hop Encrypt

Hop Encrypt 是一个命令行工具，用于对密码进行加密（混淆），以便在 XML、密码或 Hop metadata 文件中使用。

## 用法

```bash
hop-encrypt usage:

  encr <-hop|-server> <password>
  Options:
    -hop: generate an obfuscated or encrypted password
    -server : generate an obfuscated password to include in the hop-server password file 'pwd/hop.pwd'

This command line tool obfuscates or encrypts a plain text password for use in XML, password or metadata files.
Make sure to also copy the password encryption prefix to indicate the obfuscated nature of the password.
Hop will then be able to make the distinction between regular plain text passwords and obfuscated ones.
```

## 选项

| 选项 | 描述 |
|---|---|
| <password> | 要混淆的密码 |
| -hop | 生成混淆或加密的密码 |
| -server | 生成用于包含在 hop-server 密码文件 `pwd/hop.pwd` 中的混淆密码 |

## 示例

### Hop 示例

加密（混淆）密码 `abcd`，用于 Hop workflow、pipeline 和其他 metadata 文件。

&nbsp;

====
Windows::
--
```shell
hop-encrypt.bat -hop abcd
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-encrypt.bat]====================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=GUI
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=GUI org.apache.hop.core.encryption.Encr -hop abcd
===[Starting HopEncrypt]=========================================================
Encrypted 2be98afc86aa7f2e4cb79ce10df90acde
```

--

Linux, macOS::
--
```shell
./hop-encrypt.sh -hop abcd
```

预期输出：

```shell
Encrypted 2be98afc86aa7f2e4cb79ce10df90acde
```

--

====

### Hop Server 示例

加密（混淆）密码 `abcd`，用于 Hop Server。

&nbsp;

====
Windows::
--
```shell
hop-encrypt.bat -server abcd
```

预期输出：

```shell
C:\<YOUR_PATH>\hop>echo off
===[Environment Settings - hop-encrypt.bat]====================================
Java identified as "C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java"
HOP_OPTIONS=-Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows -DHOP_PLATFORM_RUNTIME=GUI
Command to start Hop will be:
"C:\Program Files\Microsoft\jdk-11.0.17.8-hotspot\\bin\java" -classpath lib\core\*;lib\beam\*;lib\swt\win64\*
-Djava.library.path=lib\core;lib\beam -Xmx64m -DHOP_AUDIT_FOLDER=.\audit -DHOP_PLATFORM_OS=Windows
-DHOP_PLATFORM_RUNTIME=GUI org.apache.hop.core.encryption.Encr -server abcd
===[Starting HopEncrypt]=========================================================
OBF:1s3g1s3i1s3k1s3m
```

--

Linux, macOS::
--
```shell
./hop-encrypt.sh -server abcd
```

预期输出：

```shell
OBF:1s3g1s3i1s3k1s3m
```

--
====

## 在 Windows 上使用 hop-encrypt 的注意事项

在 Windows 上使用 `hop-encrypt` 工具时，你可能需要注意一些字符，这些字符在 Windows 命令行中被视为保留字符，必须进行适当处理，否则该工具将无法加密你的密码。

如[此页面](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmd)所述，在 Windows 上，_与号 &、管道符 | 和括号 ( ) 是特殊字符，在将它们作为参数传递时必须在其前面加上转义字符 ^ 或使用引号_。因此，当你运行 hop-encrypt.bat 命令时，应在任何特殊字符前使用转义字符 ^，并且密码字符串必须放在双引号中，如下例所示：

--
```shell
./hop-encrypt.bat -hop "hello^&world"
```

--
```shell
./hop-encrypt.bat -server "hello^&world"
```

如果你不在特殊字符前使用转义字符 ^，也不将明文字符串放在双引号中，该命令将始终失败。
