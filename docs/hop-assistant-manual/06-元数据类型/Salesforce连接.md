# Salesforce 连接（Salesforce Connection）

## 核心功能

描述一个可跨多个 Salesforce 转换重复使用的 Salesforce 连接。

Salesforce 连接元数据类型允许您定义一次连接设置，并在 hfxt data process 项目中的所有 Salesforce 转换中重复使用它们。这消除了重复输入连接信息的需要，并提供了对 Salesforce 认证凭据的集中化管理。

## 认证类型

Salesforce 连接支持三种认证方式：

### 用户名/密码认证（Username/Password Authentication）

使用 Salesforce 用户名和密码（带可选安全令牌）的传统认证方式。

### OAuth 2.0 认证（OAuth 2.0 Authentication）

具有自动令牌刷新功能的现代 OAuth 2.0 认证。需要通过浏览器进行交互式用户授权。

### OAuth JWT Bearer 认证（OAuth JWT Bearer Authentication）

使用 RSA 私钥签名的 JWT（JSON Web Token）进行服务器到服务器认证。非常适合无法进行交互式授权的自动化流程、CI/CD 管道和无头集成场景。

## 主要参数

| 参数 | 默认值 | 说明 |
| --- | --- | --- |
| Connection name（连接名称） | | 此连接使用的名称 |
| Authentication Type（认证类型） | Username/Password | 在用户名/密码、OAuth 或 OAuth JWT Bearer 认证之间选择 |

## 相关插件

- Salesforce Input（Salesforce 输入）
- Salesforce Insert（Salesforce 插入）
- Salesforce Update（Salesforce 更新）
- Salesforce Upsert（Salesforce 插入或更新）
- Salesforce Delete（Salesforce 删除）
