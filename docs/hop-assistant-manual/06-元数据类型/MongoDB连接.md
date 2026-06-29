# MongoDB 连接（MongoDB Connection）

## 核心功能

描述一个 MongoDB 连接。在项目级别指定的 MongoDB 连接，可以在多个（实例的）转换或其他插件类型中重复使用。

## 主要参数

| 参数 | 默认值 | 说明 |
| --- | --- | --- |
| MongoDB Connection name（连接名称） | | 此连接使用的名称 |
| Hostname（主机名） | localhost | 集群主机名 |
| Port（端口） | 27017 | MongoDB 数据库的端口 |
| Database name（数据库名） | | |
| Authentication database（认证数据库） | | |
| Username（用户名） | | |
| Password（密码） | | |
| Authentication mechanism（认证机制） | PLAIN | SCRAM_SHA1、MONGODB_CR 或 PLAIN |
| User Kerberos（使用 Kerberos） | false | |
| Connection timeout (ms)（连接超时，毫秒） | | |
| Socket timeout (ms)（套接字超时，毫秒） | | |
| Read preference（读取偏好） | PRIMARY | PRIMARY、PRIMARY_PREFERRED、SECONDARY、SECONDARY_PREFERRED 或 NEAREST |
| Use all replica set members?（使用所有副本集成员） | false | |
| Specify the read preference tag sets（指定读取偏好标签集） | | |
| Use an SSL socket factory?（是否使用 SSL 套接字工厂） | false | |
| Write concern（写入关注） | | |
| Replication timeout (ms)（复制超时，毫秒） | | |
| Journaled?（是否启用日志） | true | |

## 相关插件

- MongoDB Input（MongoDB 输入）
- MongoDB Output（MongoDB 输出）
