# Azure-Key-Vault 变量解析器（Azure Key Vault Variable Resolver）

## 核心功能

Azure Key Vault 变量解析器允许您从 Microsoft Azure Key Vault 检索机密，并将其作为变量在 Qi 数据治理平台 管道和工作流中使用。

此集成能够安全管理敏感信息，如数据库凭据、API 密钥和其他机密，通过直接从 Azure Key Vault 获取它们。

## 配置选项

### Azure Key Vault URI（Azure Key Vault URI）

您的 Azure Key Vault 的完整 URI。格式应为 `https://your-vault-name.vault.azure.net/`。

示例：`https://my-company-vault.vault.azure.net/`

### Azure Tenant ID（Azure 租户 ID）

您的 Azure Active Directory 的目录（租户）ID。这是一个标识您的 Azure AD 租户的 GUID。

## 变量表达式格式

```
#{name:secret-name:value-key}
```

- name：要使用的变量解析器元数据元素的名称
- secret-name：要检索的机密名称
- value-key：如果值是 JSON 格式，要检索的值的键（可选）
