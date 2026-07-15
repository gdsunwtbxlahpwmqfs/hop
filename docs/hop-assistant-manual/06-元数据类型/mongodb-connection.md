# MongoDB 连接

## 描述

![width="24px"](../assets/images/icons/MongoDB_Leaf_FullColor_RGB.svg)

描述一个 MongoDB 连接。

在项目级别指定的 MongoDB 连接可以在多个 transform 或其他 plugin 类型的（实例）中重复使用。

## 相关 plugin

- [MongoDB Input](pipeline/transforms/mongodbinput.md)
- [MongoDB Output](pipeline/transforms/mongodboutput.md)

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| MongoDB Connection name |  | 此连接的名称 |
| Hostname | localhost | 集群主机名 |
| Port | 27017 | MongoDB 数据库的端口 |
| Database name |  |  |
| Authentication database |  |  |
| Username |  |  |
| Password |  |  |
| Authentication mechanism | PLAIN | SCRAM_SHA1、MONGODB_CR 或 PLAIN |
| User Kerberos | false |  |
| Connection timeout (ms) |  |  |
| Socket timeout (ms) |  |  |
| Read preference | PRIMARY | PRIMARY、PRIMARY_PREFERRED、SECONDARY、SECONDARY_PREFERRED 或 NEAREST |
| Use all replica set members? | false |  |
| Specify the read preference tag sets |  |  |
| Use an SSL socket factory? | false |  |
| Write concern |  |  |
| Replication timeout (ms) |  |  |
| Journaled? | true |  |

## 示例（集成测试）

./integration-tests/mongo/tests/mongo-update/mongo-insert-in-collection.hpl
./integration-tests/mongo/tests/mongo-update/mongo-update-validation.hpl
./integration-tests/mongo/tests/mongo-update/mongo-update-collection.hpl
./integration-tests/mongo/tests/mongo-insert/mongo-insert-validation.hpl
./integration-tests/mongo/tests/mongo-insert/mongo-insert-in-collection.hpl
