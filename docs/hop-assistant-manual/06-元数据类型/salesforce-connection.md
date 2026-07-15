# Salesforce 连接

## 描述

![width="24px"](../assets/images/transforms/icons/SFI.svg)

描述一个可跨多个 Salesforce transform 重复使用的 Salesforce 连接。

Salesforce 连接元数据类型允许你一次性定义连接设置，并在 Qi Hop 项目的所有 Salesforce transform 中重复使用。这消除了重复连接信息的需要，并提供了对 Salesforce 认证凭证的集中管理。

## 相关 plugin

- [Salesforce Input](pipeline/transforms/salesforceinput.md)
- [Salesforce Insert](pipeline/transforms/salesforceinsert.md)
- [Salesforce Update](pipeline/transforms/salesforceupdate.md)
- [Salesforce Upsert](pipeline/transforms/salesforceupsert.md)
- [Salesforce Delete](pipeline/transforms/salesforcedelete.md)

## 认证类型

Salesforce 连接支持三种认证方式：

### 用户名/密码认证

使用 Salesforce 用户名和密码（带有可选的安全令牌）的传统认证方式。

### OAuth 2.0 认证

具有自动令牌刷新功能的现代 OAuth 2.0 认证。需要通过浏览器进行交互式用户授权。

### OAuth JWT Bearer 认证

使用 RSA 私钥签名的 JWT（JSON Web Token）进行服务器到服务器认证。非常适合无法进行交互式授权的自动化流程、CI/CD 流水线和无头集成。

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Connection name |  |  |
| 此连接的名称 |  |  |
| Authentication Type |  |  |
| Username/Password |  |  |
| 选择 Username/Password、OAuth 或 OAuth JWT Bearer 认证 |  |  |

### 用户名/密码认证选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Username |  |  |
| 你的 Salesforce 用户名或电子邮件地址 |  |  |
| Password |  |  |
| 你的 Salesforce 密码（可使用变量如 `{openvar}SF_PASS{closevar}`） |  |  |
| Security Token (Optional) |  |  |
| 你的 Salesforce 安全令牌（如果你的组织需要） |  |  |
| Target URL |  |  |
| https://login.salesforce.com/services/Soap/u/64.0 |  |  |
| Salesforce SOAP API 端点 URL |  |  |

### OAuth 2.0 认证选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Client ID |  |  |
| 你的 Salesforce Connected App Client ID |  |  |
| Client Secret |  |  |
| 你的 Salesforce Connected App Client Secret |  |  |
| Redirect URI |  |  |
| 在你的 Salesforce Connected App 中配置的重定向 URI |  |  |
| Instance URL |  |  |
| 你的 Salesforce 实例 URL（例如 https://yourcompany.my.salesforce.com） |  |  |
| Access Token |  |  |
| OAuth 访问令牌（授权后自动填充） |  |  |
| Refresh Token |  |  |
| OAuth 刷新令牌（授权后自动填充） |  |  |

### OAuth JWT Bearer 认证选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Username |  |  |
| 你的 Salesforce 用户名（例如 user@company.com） |  |  |
| Consumer Key |  |  |
| 你的 Salesforce Connected App 的 Consumer Key |  |  |
| Private Key |  |  |
| PKCS8 PEM 格式的 RSA 私钥。为了安全起见，使用变量如 `{openvar}SF_JWT_PRIVATE_KEY{closevar}` 来引用存储在外部密钥管理器中的密钥。密钥在保存时会被加密。点击 Browse 从文件加载。 |  |  |
| Token Endpoint |  |  |
| https://login.salesforce.com |  |  |
| Salesforce 令牌端点（沙箱环境使用 https://test.salesforce.com） |  |  |

## 创建连接

### 创建用户名/密码连接

按照以下步骤使用用户名和密码认证创建 Salesforce 连接：

1. 在 Hop GUI 中打开 Metadata Perspective
2. 在元数据树中导航到 Salesforce Connection
3. 右键点击并选择 "New Salesforce Connection"
4. 为你的连接输入一个描述性名称
5. 确保选择 "Username/Password" 作为 Authentication Type
6. 填写必填字段：
   - **Username**：你的 Salesforce 用户名或电子邮件地址
   - **Password**：你的 Salesforce 密码（或使用变量如 `{openvar}SF_PASS{closevar}`）
   - **Security Token (Optional)**：仅在组织启用了 IP 限制且你从未受信任的 IP 地址连接时才需要。如果你需要安全令牌，可以通过转到 Settings > My Personal Information > Reset My Security Token 从 Salesforce 请求
   - **Target URL**：默认的 `https://login.salesforce.com/services/Soap/u/64.0` 适用于大多数情况。沙箱环境使用 `https://test.salesforce.com/services/Soap/u/64.0`
7. 点击 "Test Connection" 验证你的设置
8. 如果测试成功，点击 "OK" 保存连接

> **📝 注意:** 安全令牌通常仅在从 Salesforce 组织的 Network Access 设置中未列入白名单的 IP 地址连接时才需要。大多数现代 Salesforce 配置不需要此令牌。

### 创建 OAuth 2.0 连接

OAuth 2.0 提供更安全的认证并支持自动令牌刷新。按照以下步骤操作：

#### 前提条件

在 Hop 中创建 OAuth 连接之前，你需要在 Salesforce 中设置一个 Connected App：

1. 登录 Salesforce
2. 转到 Setup > Apps > App Manager（在较新的 Salesforce 界面中，可能在 Setup > Platform Tools > Apps > App Manager 或 Setup > External Client Apps 下）
3. 点击 "New Connected App"
4. 填写必填字段：
   - Connected App Name：例如 "Qi Hop Integration"
   - API Name：将自动填充
   - Contact Email：你的电子邮件地址
5. 启用 OAuth 设置：
   - 勾选 "Enable OAuth Settings"
   - 设置 Callback URL：例如 `https://localhost:8080/oauth/callback`（可以是任何有效的 URL；你将从中提取授权码）
   - 选择 OAuth Scopes：至少选择 "Full access (full)" 或 "Perform requests at any time (refresh_token, offline_access)"
6. 保存 Connected App
7. 复制 Consumer Key (Client ID) 和 Consumer Secret (Client Secret) - 你将在 Hop 中需要这些

#### 授权和获取令牌

配置好 Connected App 后，在 Hop 中创建 OAuth 连接：

1. 在 Hop GUI 中打开 Metadata Perspective
2. 在元数据树中导航到 Salesforce Connection
3. 右键点击并选择 "New Salesforce Connection"
4. 为你的连接输入一个描述性名称
5. 选择 "OAuth" 作为 Authentication Type
6. 填写 OAuth 设置：
   - **Client ID**：粘贴 Salesforce Connected App 的 Consumer Key
   - **Client Secret**：粘贴 Salesforce Connected App 的 Consumer Secret
   - **Redirect URI**：使用你在 Salesforce 中配置的相同 Callback URL（例如 `https://localhost:8080/oauth/callback`）
   - **Instance URL**：你的 Salesforce 实例 URL（例如 `https://yourcompany.my.salesforce.com` 或 `https://yourcompany.lightning.force.com`）
7. 点击 "Authorize" 按钮启动 OAuth 流程

#### 授权流程

当你点击 "Authorize" 时，将打开一个浏览器窗口显示 Salesforce 登录页面：

1. **登录**：输入你的 Salesforce 凭证并登录
2. **强制重新授权**（可选）：如果你已经登录到 Salesforce，你可能看不到授权屏幕。要强制 Salesforce 再次显示授权屏幕，请在授权 URL 中添加 `&amp;prompt=login`
3. **授予访问权限**：点击 "Allow" 授权应用程序
4. **提取授权码**：
   - 授权后，Salesforce 将重定向到你的 callback URL
   - 浏览器将显示一个错误（这是预期的，因为该 URL 不存在）
   - **从浏览器的地址栏复制完整 URL** - 它看起来像：`https://localhost:8080/oauth/callback?code=aPrxh7N`
   - 授权码是 URL 中 `code=` 之后的值
5. **用代码换取令牌**：
   - 返回 Hop 并将授权码粘贴到 "Authorization Code" 字段中（如果提示）
   - 点击 "Exchange Code" 或 "Get Tokens" 按钮
   - Hop 将自动用授权码换取访问和刷新令牌
   - Access Token 和 Refresh Token 字段将自动填充

6. 点击 "Test Connection" 验证 OAuth 连接是否正常
7. 如果成功，点击 "OK" 保存连接

> **📝 注意:** 访问令牌用于 API 调用，会在一段时间后过期（通常几个小时）。刷新令牌用于在旧令牌过期时自动获取新的访问令牌，无需用户交互。

#### 令牌管理

- **Access Token**：过期后由 Hop 自动刷新
- **Refresh Token**：安全存储，用于获取新的访问令牌
- **令牌加密**：两个令牌在保存时都使用 Hop 的密码加密进行加密
- **令牌生命周期**：访问令牌通常在 1-2 小时后过期，但刷新令牌在被撤销之前保持有效

### 创建 OAuth JWT Bearer 连接

OAuth JWT Bearer 认证非常适合服务器到服务器集成、CI/CD 流水线和无法进行交互式浏览器认证的自动化流程。

#### 前提条件

===== 步骤 1：生成 RSA 密钥对

在你的本地机器上生成 RSA 密钥对：

```bash
# Generate private key
openssl genrsa -out salesforce_private.key 2048

# Generate certificate (valid for 1 year)
openssl req -new -x509 -key salesforce_private.key -out salesforce_cert.crt -days 365

# Convert private key to PKCS8 format (required for Hop)
openssl pkcs8 -topk8 -inform PEM -outform PEM -in salesforce_private.key -out salesforce_private_pkcs8.key -nocrypt
```

> **❗ 重要:** 妥善保管你的私钥文件（`salesforce_private_pkcs8.key`），切勿将其提交到版本控制。

===== 步骤 2：配置 Salesforce Connected App

1. 登录 Salesforce
2. 转到 Setup → App Manager → New Connected App
3. 填写基本信息：
   - Connected App Name：例如 "Hop JWT Integration"
   - API Name：（自动填充）
   - Contact Email：你的电子邮件
4. 配置 OAuth 设置：
   - 勾选 "Enable OAuth Settings"
   - Callback URL：`https://login.salesforce.com/`（必需但 JWT 不使用）
   - 勾选 "Use digital signatures"
   - 上传证书文件（`salesforce_cert.crt`）
   - 选择 OAuth Scopes：
     ** Full access (full)
     ** Perform requests at any time (refresh_token, offline_access)
     ** Access and manage your data (api)
5. 保存 Connected App

===== 步骤 3：配置策略

1. 创建 Connected App 后，点击 "Manage"
2. 点击 "Edit Policies"
3. 将 "Permitted Users" 设置为以下之一：
   - **Admin approved users are pre-authorized**（生产环境推荐）
   - **All users may self-authorize**（更便于测试）
4. 将 "IP Relaxation" 设置为 "Relax IP restrictions"
5. 保存

===== 步骤 4：预授权用户（如果使用管理员批准）

如果你选择了 "Admin approved users are pre-authorized"：

1. 在 Connected App 管理界面，点击 "Manage Profiles"
2. 添加应使用此集成的配置文件（例如 "System Administrator"）
3. 或者点击 "Manage Permission Sets" 并添加适当的权限集
4. 保存

===== 步骤 5：获取 Consumer Key

1. 转到你的 Connected App
2. 点击 "View" 或 "Manage Consumer Details"
3. 复制 **Consumer Key**（你将在 Hop 中需要它）

#### 在 Hop 中创建连接

1. 在 Hop GUI 中打开 Metadata Perspective
2. 在元数据树中导航到 Salesforce Connection
3. 右键点击并选择 "New Salesforce Connection"
4. 为你的连接输入一个描述性名称
5. 选择 "OAuth JWT Bearer" 作为 Authentication Type
6. 填写 JWT 设置：
   - **Username**：你的 Salesforce 用户名（例如 `user@company.com` 或使用 `{openvar}SF_USER{closevar}`）
   - **Consumer Key**：粘贴 Salesforce Connected App 的 Consumer Key（或使用 `{openvar}SF_CONSUMER_KEY{closevar}`）
   - **Private Key**：
     ** 点击 "Browse..." 按钮加载你的私钥文件（`salesforce_private_pkcs8.key`）
     ** 或者使用变量引用：`{openvar}SF_JWT_PRIVATE_KEY{closevar}`（生产环境推荐）
   - **Token Endpoint**：
     ** 生产环境使用 `https://login.salesforce.com`
     ** 沙箱环境使用 `https://test.salesforce.com`
7. 点击 "Test Connection" 验证你的设置
8. 如果成功，点击 "OK" 保存连接

#### JWT 安全最佳实践

> **❗ 重要:** 私钥是 JWT 认证中最敏感的组件。请遵循以下安全实践：

- **切勿将私钥提交到版本控制**
- 在生产环境中**使用环境变量或外部密钥管理器**：
```bash
# Set environment variable
export SF_JWT_PRIVATE_KEY="$(cat /secure/path/salesforce_private_pkcs8.key)"

# Or use Hop's variable system
SF_JWT_PRIVATE_KEY = ${SECRET:azure-keyvault:salesforce-jwt-key}
```

- 生产部署**使用 Azure Key Vault、AWS Secrets Manager 或 HashiCorp Vault**
- **私钥在保存到 Hop 元数据文件时使用 AES 加密**
- **重新打开连接时**，私钥显示为遮罩点（••••••）以确保安全 - 保存后你无法查看实际的密钥
- **要更新密钥**，使用 Browse 按钮加载新的私钥文件

#### JWT 令牌生成

当你使用 JWT Bearer 连接时：

1. Hop 使用你的 RSA 私钥生成 JWT 断言（RS256 算法）
2. JWT 包含以下声明：
   - `iss`（签发者）：你的 Consumer Key
   - `sub`（主体）：你的 Salesforce 用户名
   - `aud`（受众）：令牌端点 URL
   - `exp`（过期时间）：生成后 5 分钟
3. Hop 用 JWT 向 Salesforce 换取访问令牌
4. 访问令牌用于所有 Salesforce API 调用
5. 新的 JWT 按需生成（无需刷新令牌）

> **📝 注意:** 与 OAuth 2.0 授权码流程不同，JWT Bearer 认证按需生成新的访问令牌，无需刷新令牌或用户交互。

## 用法

### 在 Salesforce Transform 中使用

1. 打开任何 Salesforce transform 对话框
2. 在 Connection 部分，从下拉菜单中选择你的 Salesforce Connection
3. transform 将自动使用连接设置
4. 选择元数据连接时，各个连接字段（URL、用户名、密码）将被禁用

### 变量支持

所有连接字段都支持 Hop 变量：

```properties
Username: ${SF_USER}
Password: ${SF_PASS}
Target URL: ${rSF_URL}
```

这允许你在不更改 Pipeline 的情况下为不同环境使用不同的凭证。

## 最佳实践

### 安全

- 对密码和令牌等敏感信息使用环境变量
- 使用 Hop 的密码加密安全存储 OAuth 令牌
- 定期轮换访问令牌和刷新令牌

### 组织

- 为连接使用描述性名称（例如 "Salesforce-Production"、"Salesforce-Sandbox"）
- 按环境或目的对相关连接进行分组
- 在连接名称或描述中记录连接用途

### 性能

- 在生产环境中使用 OAuth 2.0，因为它提供更好的安全性和自动令牌刷新
- 定期测试连接以确保它们仍然有效
- 根据你的网络环境使用适当的超时设置

## 示例

### 基本用户名/密码连接

```properties
Name: Salesforce-Production
Authentication Type: Username/Password
Username: ${SF_USER}
Password: ${SF_PASS}
Target URL: https://login.salesforce.com/services/Soap/u/64.0
```

### OAuth 连接

```properties
Name: Salesforce-OAuth-Production
Authentication Type: OAuth
Client ID: ${SF_CLIENT_ID}
Client Secret: ${SF_CLIENT_SECRET}
Instance URL: https://yourcompany.my.salesforce.com
```

### OAuth JWT Bearer 连接

```properties
Name: Salesforce-JWT-Production
Authentication Type: OAuth JWT Bearer
Username: integration.user@company.com
Consumer Key: 3MVG9rZjd7MXFdLhRZwW8nz6y4HzBapVD3GeaSU_Ukf4HxwFls4LiQIJGJISQKQf8OO96mjyvT5CPI0EFiWhH
Private Key: ${SF_JWT_PRIVATE_KEY}
Token Endpoint: https://login.salesforce.com
```

> **📝 注意:** 生产环境中私钥应引用环境变量或外部密钥管理器。切勿在元数据文件中硬编码私钥。

## 从内联连接迁移

使用内联连接设置的现有 Salesforce transform 将继续工作，无需更改。要迁移到元数据连接：

1. 使用当前设置创建一个 Salesforce Connection 元数据项
2. 打开每个 Salesforce transform 对话框
3. 从下拉菜单中选择新的元数据连接
4. 保存 transform

选择元数据连接时，内联连接字段将自动禁用。

## 故障排除

### 连接测试失败

- 验证你的用户名和密码是否正确
- 检查你的组织是否需要安全令牌
- 确保你的 IP 地址已列入白名单（如果启用了 IP 限制）
- 验证 Target URL 对你的 Salesforce 实例是否正确

### OAuth 问题

- 确保你的 Connected App 在 Salesforce 中已正确配置
- 验证 Redirect URI 与你的 Connected App 设置匹配
- 检查你的 Connected App 是否具有必要的 OAuth 范围
- 确保访问和刷新令牌有效且未过期

### OAuth JWT Bearer 问题

- **"user hasn't approved this consumer"**：Salesforce 用户需要在 Connected App 策略中预授权。转到 Setup → App Manager → 你的 Connected App → Manage → Manage Profiles/Permission Sets
- **"invalid_grant" 或 "authentication failure"**：验证私钥与上传到 Salesforce 的证书匹配
- **"Destination URL not reset"**：这通常是内部错误 - 确保你使用的是最新版本的 plugin
- **证书/密钥不匹配**：确保上传到 Salesforce 的证书是使用你在 Hop 中使用的相同私钥生成的
- **令牌端点不正确**：生产环境使用 `https://login.salesforce.com`，沙箱使用 `https://test.salesforce.com`
- **私钥格式**：确保私钥为 PKCS8 PEM 格式（如果需要，使用 `openssl pkcs8` 转换）

### 变量解析

- 验证变量已在你的环境中正确定义
- 检查变量名称拼写是否正确
- 确保变量在当前上下文中可访问
