# 密码与密码 Plugin

Hop 提供密码混淆作为一种防止以明文形式存储密码的方式。

混淆是故意创建难以被人类理解的源代码或机器代码的行为[[1](https://en.wikipedia.org/wiki/Obfuscation_(software))]。

通过混淆密码，Hop 提供了一种使密码难以（但并非不可能！）被读取的存储方式。
Hop 会读取混淆的密码，并尽可能晚地将其转换为原始密码（例如在创建连接时传递给数据库）。

## Hop 中的密码混淆

混淆的密码以 'Encrypted' 前缀存储在 Hop metadata 中。
以下示例显示了一个带有混淆密码 'abcd' 的数据库连接。

```json
{
  "rdbms": {
    "POSTGRESQL": {
      "databaseName": "DBNAME",
      "pluginId": "POSTGRESQL",
      "indexTablespace": "",
      "dataTablespace": "",
      "accessType": 0,
      "hostname": "localhost",
      "password": "Encrypted 2be98afc86aa7f2e4cb79ce10df90acde",
      "port": "5432",
      "pluginName": "PostgreSQL",
      "servername": "",
      "attributes": {},
      "username": "dbuser"
    }
  },
  "name": "DBCONN"
}
```

## Hop Server 中的密码混淆

Hop Server 的混淆密码可以存储在服务器的 .pwd 文件中。

在下面的示例中，Hop 的默认服务器 hop.pwd 包含用户名 'cluster' 和默认混淆密码 'cluster'，带有 'OBF:' 前缀。

cluster: OBF:1v8w1uh21z7k1ym71z7i1ugo1v9q,default

## 使用 Hop Encrypt 进行密码加密（混淆）

查看 [Hop Encrypt](hop-tools/hop-encrypt.md) 了解有关如何在 Hop 或 Hop Server 中混淆或加密密码的更多信息。

## 密码 plugin

Hop 中的密码处理可以作为 plugin 实现。

以下是可用的 plugin 实现：

- [AES 双向密码编码器](password/passwords/aespasswords.md)
