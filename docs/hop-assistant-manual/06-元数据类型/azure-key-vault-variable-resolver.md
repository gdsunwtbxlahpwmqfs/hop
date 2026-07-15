# Azure Key Vault 变量解析器

## 描述

Azure Key Vault 变量解析器允许你从 Microsoft Azure Key Vault 获取密钥，并将其作为变量用于 Qi Hop 的 Pipeline 和 Workflow 中。此集成通过直接从 Azure Key Vault 获取敏感信息（如数据库凭证、API 密钥和其他密钥），实现了安全管理。

## 配置选项

### Azure Key Vault URI

你的 Azure Key Vault 的完整 URI。格式应为 `https://your-vault-name.vault.azure.net/`。

示例：`https://my-company-vault.vault.azure.net/`

### Azure Tenant ID

你的 Azure Active Directory 的目录（租户）ID。这是一个标识你的 Azure AD 租户的 GUID。

可以在 Azure Portal 的 **Azure Active Directory → Overview → Tenant ID** 中找到。

### Azure Client ID

你的服务主体或已注册应用的应用程序（客户端）ID。这是将与 Azure Key Vault 进行认证的身份。

可以在 Azure Portal 的 **Azure Active Directory → App registrations → Your Application → Application (client) ID** 中找到。

### Azure Client Secret

你的服务主体或已注册应用的客户端密钥（密码）。用于向 Azure AD 认证你的应用程序。

可以在 Azure Portal 的 **Azure Active Directory → App registrations → Your Application → Certificates & secrets** 中创建客户端密钥。

> **❗ 重要:** 请妥善保管你的客户端密钥！切勿将其提交到源代码管理或公开分享。Azure Key Vault 变量解析器旨在帮助你避免硬编码此类密钥。

## 设置 Azure Key Vault

### 前提条件

- 一个有效的 Azure 订阅
- 创建 Azure 资源的适当权限
- 已安装 Azure CLI（可选，但推荐）

### 步骤 1：创建 Azure Key Vault

使用 Azure Portal：

1. 导航到 [Azure Portal](https://portal.azure.com)
2. 点击 **Create a resource** → 搜索 **Key Vault**
3. 点击 **Create**
4. 填写所需信息：
- **Subscription**：选择你的 Azure 订阅
- **Resource Group**：创建新的或选择已有的资源组
- **Key Vault Name**：输入唯一名称（例如 `my-company-hop-vault`）
- **Region**：选择离你的 Hop 安装最近的区域
- **Pricing Tier**：Standard（或 Premium，如果你需要 HSM 支持的密钥）
5. 查看网络和访问策略设置（大多数用例使用默认即可）
6. 点击 **Review + Create** → **Create**

使用 Azure CLI：

```bash
# Create a resource group (if you don't have one)
az group create --name hop-resources --location eastus

# Create the Key Vault
az keyvault create \
  --name my-company-hop-vault \
  --resource-group hop-resources \
  --location eastus
```

### 步骤 2：创建服务主体

服务主体是你的 Hop 应用程序用于向 Azure 认证的身份。

使用 Azure Portal：

1. 转到 **Azure Active Directory** → **App registrations**
2. 点击 **New registration**
3. 输入名称（例如 `hop-key-vault-app`）
4. 选择 **Accounts in this organizational directory only**
5. 点击 **Register**
6. 记下 **Application (client) ID** 和 **Directory (tenant) ID**
7. 转到 **Certificates & secrets** → **New client secret**
8. 添加描述和过期时间
9. 点击 **Add** 并立即复制 **V**（你将无法再次看到它！）

使用 Azure CLI：

```bash
# Create a service principal
az ad sp create-for-rbac \
  --name hop-key-vault-app \
  --skip-assignment

# Note down the output:
# - appId (this is your Client ID)
# - password (this is your Client Secret)
# - tenant (this is your Tenant ID)
```

### 步骤 3：授予 Key Vault 访问权限

你的服务主体需要从 Key Vault 读取密钥的权限。

使用 Azure Portal：

1. 导航到你的 Key Vault
2. 转到 **Access policies** → **Create**
3. 在 **Secret permissions** 下，选择：
- **Get**（必需）
- **List**（可选，但有助于调试）
4. 点击 **Next**
5. 搜索并选择你的服务主体（例如 `hop-key-vault-app`）
6. 点击 **Next** → **Next** → **Create**

使用 Azure CLI：

```bash
# Get the object ID of your service principal
SP_OBJECT_ID=$(az ad sp list --display-name hop-key-vault-app --query [0].id -o tsv)

# Grant Get and List permissions
az keyvault set-policy \
  --name my-company-hop-vault \
  --object-id $SP_OBJECT_ID \
  --secret-permissions get list
```

### 步骤 4：向 Key Vault 添加密钥

使用 Azure Portal：

1. 导航到你的 Key Vault
2. 转到 **Secrets** → **Generate/Import**
3. 输入 **Name**（例如 `database-password`）
4. 输入 **V**（实际的密钥值）
5. 点击 **Create**

使用 Azure CLI：

```bash
# Add a secret
az keyvault secret set \
  --vault-name my-company-hop-vault \
  --name database-password \
  --value "MySecureP@ssw0rd!"

# Add multiple secrets
az keyvault secret set \
  --vault-name my-company-hop-vault \
  --name api-key \
  --value "abc123xyz789"
```

> **💡 提示:** Azure Key Vault 中的密钥名称只能包含字母数字字符和连字符。长度必须在 1-127 个字符之间。

## 在 Qi Hop 中使用

### 创建变量解析器

1. 在 Hop GUI 中，打开 **Metadata perspective**（右上角图标）
2. 在元数据浏览器中右键点击 → **New** → **Variable Resolver**
3. 选择 **Azure Key Vault Variable Resolver**
4. 为你的解析器输入 **Name**（例如 `azure-kv`）
5. 填写配置：
- **Azure Key Vault URI**：`https://my-company-hop-vault.vault.azure.net/`
- **Azure Tenant ID**：你的租户 GUID
- **Azure Client ID**：你的应用程序（客户端）ID
- **Azure Client Secret**：你的客户端密钥值
6. 点击 *Save* 图标

### 变量表达式格式

要从 Azure Key Vault 获取密钥，请使用以下表达式格式：

```
#{resolver-name:secret-name}
```
其中：

- **resolver-name**：你为变量解析器元数据元素指定的名称（例如 `azure-kv`）
- **secret-name**：Azure Key Vault 中的密钥名称

### 示例

假设你创建了一个名为 `azure-kv` 的变量解析器，并且你的 Key Vault 中有以下密钥：

| Secret Name | Secret Value |
|---|---|
| database-password |  |
| MySecureP@ssw0rd! |  |
| api-key |  |
| abc123xyz789 |  |
| connection-string |  |
| Server=myserver;Database=mydb;User=admin;Password=secret; |  |

你可以在 Pipeline 和 Workflow 中使用这些表达式：

- `#{azure-kv:database-password}` 返回 `MySecureP@ssw0rd!`
- `#{azure-kv:api-key}` 返回 `abc123xyz789`
- `#{azure-kv:connection-string}` 返回完整的连接字符串

### 在数据库连接中使用

你可以在数据库连接配置中使用 Azure Key Vault 变量解析器：

1. 创建或编辑数据库连接
2. 在 **Password** 字段中输入：`#{azure-kv:database-password}`
3. 当连接被使用时，Hop 将自动从 Azure Key Vault 解析密码

### 在 Transform 字段中使用

你可以在 transform 字段中使用解析器表达式，例如 **Get Variables** transform：

1. 向 Pipeline 添加 **Get Variables** transform
2. 添加字段并将 **Variable** 设置为 `#{azure-kv:api-key}`
3. 当 Pipeline 运行时，该字段将包含实际的密钥值

## 最佳实践

### 安全

- **切勿硬编码凭证**：使用变量解析器而非在 Pipeline 中硬编码密钥
- **定期轮换密钥**：在 Azure Key Vault 中更新密钥并重启 Hop 以获取新值
- **为不同环境使用不同的 Key Vault**：为开发、测试和生产环境创建单独的 Key Vault
- **限制权限**：仅授予服务主体所需的最低权限（Get secrets）
- **启用审计日志**：使用 Azure Monitor 跟踪密钥访问

### 性能

- **缓存**：解析器在每个 Hop 会话中初始化一次并缓存连接
- **避免过度调用**：密钥按需获取，因此在紧密循环中最小化解析器表达式
- **使用特定环境的解析器**：为不同环境创建单独的变量解析器元数据

### 多个解析器

你可以创建多个具有不同名称的 Azure Key Vault 变量解析器元数据元素：

- `azure-kv-abc` → 指向 Key Vault abc
- `azure-kv-def` → 指向 Key Vault def

然后使用不同的前缀：

- `#{azure-kv-abc:database-password}`
- `#{azure-kv-def:database-password}`

## 故障排除

### 认证失败

如果你在 Hop 日志中看到认证错误：

1. **验证凭证**：仔细检查你的 Tenant ID、Client ID 和 Client Secret 是否正确
2. **检查密钥过期**：客户端密钥会过期！如果你的密钥已过期，请创建新的
3. **验证 Vault URI**：确保 URI 正确且使用 HTTPS
4. **检查网络连接**：确保 Hop 可以访问 `vault.azure.net`

### 密钥未找到

如果找不到密钥：

1. **检查密钥名称**：密钥名称区分大小写
2. **验证权限**：确保你的服务主体对密钥有 **Get** 权限
3. **检查密钥是否存在**：使用 Azure Portal 验证密钥是否存在于 Key Vault 中
4. **检查已删除的密钥**：Azure Key Vault 有软删除功能；密钥可能处于已删除状态

### 启用调试日志

要查看变量解析器的详细日志，请在你的 [Pipeline 运行配置](pipeline-run-config.md)或 [Workflow 运行配置](workflow-run-config.md)中提高日志级别。

## 限制

- **密钥名称**：Azure Key Vault 密钥名称只能包含字母数字字符和连字符
- **密钥版本**：此解析器始终获取密钥的最新版本
- **复杂密钥格式**：Azure Key Vault 将密钥存储为纯字符串，不像 HashiCorp Vault 原生支持结构化 JSON
- **初始化**：解析器在首次使用时初始化；第一次解析可能需要稍长时间

## 附加资源

- [Azure Key Vault 文档](https://learn.microsoft.com/en-us/azure/key-vault/)
- [快速入门：使用 Azure Portal 创建 Key Vault](https://learn.microsoft.com/en-us/azure/key-vault/secrets/quick-create-portal)
- [Azure Key Vault 认证](https://learn.microsoft.com/en-us/azure/key-vault/general/authentication)
- [创建 Azure AD 服务主体](https://learn.microsoft.com/en-us/azure/active-directory/develop/howto-create-service-principal-portal)
- [变量解析器概述](../index.md)

## 相关元数据类型

- [HashiCorp Vault 变量解析器](hashicorp-vault-variable-resolver.md)
- [Google Secret Manager 变量解析器](google-secret-manager-variable-resolver.md)
- [Pipeline 变量解析器](pipeline-variable-resolver.md)
