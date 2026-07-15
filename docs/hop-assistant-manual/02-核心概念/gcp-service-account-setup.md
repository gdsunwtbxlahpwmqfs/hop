## Google Cloud Platform 服务账号设置

要连接到 Google Cloud 服务，您需要在 Google Cloud Platform (GCP) 项目中设置服务账号并下载身份验证凭据。

### 创建服务账号

1. **进入 Google Cloud Console**：
   - 访问 https://console.cloud.google.com/
   - 选择您的项目或创建新项目

2. **创建服务账号**：
   - 导航到 "IAM & Admin" > "Service Accounts"
   - 点击 "Create Service Account"
   - 为您的服务账号提供名称和描述
   - 点击 "Create and Continue"

3. **分配角色**：
   - 根据您要连接的服务，为服务账号分配适当的角色：
     - 对于 BigQuery：
       ** `BigQuery Data Viewer` (`roles/bigquery.dataViewer`) - 用于读取访问
       ** `BigQuery Job User` (`roles/bigquery.jobUser`) - 用于运行查询（必需）
       ** `BigQuery Data Editor` (`roles/bigquery.dataEditor`) - 如果需要写入访问
       ** `BigQuery Data Owner` (`roles/bigquery.dataOwner`) - 如果需要完全控制
     - 对于 Cloud Storage：`Storage Object Viewer`、`Storage Object Admin`（如果需要写入访问）
     - 对于其他服务：请查阅相应服务的文档了解所需角色
   - 点击 "Continue" 然后点击 "Done"

4. **生成并下载密钥**：
   - 点击创建的服务账号
   - 进入 "Keys" 标签页
   - 点击 "Add Key" > "Create new key"
   - 选择 "JSON" 格式
   - 点击 "Create" 下载密钥文件

5. **保护密钥文件**：
   - 将下载的 JSON 密钥文件存储在安全的位置
   - 记下此文件的完整路径 - 身份验证配置时需要使用
   - 确保文件具有适当的权限（仅运行 Hop 的用户可读）

### 替代方案：使用应用默认凭据

如果您在 Google Cloud Platform 上运行 Hop 或已在本地配置了 Google Cloud SDK，Qi Hop 也可以使用 Google Cloud 的应用默认凭据 (ADC)。

使用 ADC：
1. 安装并配置 Google Cloud SDK
2. 运行 `gcloud auth application-default login` 设置默认凭据
3. 在您的 Hop 连接中，可以省略服务账号密钥文件参数

此方法特别适用于开发环境或在 Google Cloud Platform 服务上运行 Hop 时。

### 安全最佳实践

- **最小权限原则**：仅为您的用例分配最低限度的必要角色
- **密钥轮换**：定期轮换服务账号密钥（建议每 90 天）
- **环境变量**：考虑使用环境变量存储密钥文件路径，而不是硬编码
- **访问控制**：使用适当的文件系统权限限制对服务账号密钥文件的访问
- **监控**：启用审计日志以监控服务账号的使用情况
