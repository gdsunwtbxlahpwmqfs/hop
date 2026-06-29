# Hashicorp-Vault 变量解析器（Hashicorp Vault Variable Resolver）

## 核心功能

此变量解析器可以从 Hashicorp Vault 检索机密。

## 配置选项

- **Vault address（Vault 地址）**：Vault 服务器的基础地址和端口（例如：https://vault-server:8200）
- **Vault token（Vault 令牌）**：用于认证的令牌
- **Path prefix（路径前缀）**：一个可选的路径前缀，会被添加到解析器表达式中的键路径之前。例如，如果您在此处填入 `kv-other/data`，则表达式

```
#{vault:db:password}
```

将在内部解析为：

```
kv-other/data/db/password
```

## 变量表达式格式

```
#{name:key-path:value-key}
```

- name：要使用的变量解析器元数据元素的名称
- key-path：要检索的机密的键路径
- value-key：如果值是 JSON 格式，要检索的值的键（可选）
