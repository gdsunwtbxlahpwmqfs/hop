# Google BigQuery

| 选项 | 信息 |
|---|---|
| 类型 | Relational |
| 驱动 | [驱动链接](https://cloud.google.com/bigquery/docs/reference/odbc-jdbc-drivers) |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | [文档链接](https://www.simba.com/products/BigQuery/doc/JDBC_InstallGuide/content/jdbc/d-intro.htm) |
| JDBC Url | jdbc:bigquery://[Host]:[Port];ProjectId=[Project];OAuthType=[AuthValue] |
| 驱动文件夹 | <Hop Installation>/lib/jdbc |

## 驱动安装

Simba 驱动以包含多个 jar 的 .zip 包形式分发。驱动中包含的 jar 只有部分是 Qi Hop 使用 Bigquery JDBC 所必需的。此外，某些 jar 可能与 Hop 打包的库冲突，*必须*排除。

**需要排除的 SIMBA 驱动 JAR（这些 JAR 包含在 SIMBA 驱动中，但与 HOP 库冲突，必须排除）**

 grpc-alts-<VERSION>.jar
 grpc-api-<VERSION>.jar  
 grpc-core-<VERSION>.jar
 grpc-netty-shaded-<VERSION>.jar

**需要包含的 SIMBA 驱动 JAR**（仅包含这些 jar 即可实现最小 Bigquery 驱动安装）：

 api-common-<VERSION>.jar
 gax-<VERSION>.jar
 gax-grpc-<VERSION>.jar
 google-api-services-bigquery-v2-<VERSION>.jar
 GoogleBigQueryJDBC42.jar
 google-cloud-bigquerystorage-<VERSION>.jar
 grpc-google-cloud-bigquerystorage-v1-<VERSION>.jar
 grpc-google-cloud-bigquerystorage-v1beta1-<VERSION>.jar
 grpc-google-cloud-bigquerystorage-v1beta2-<VERSION>.jar
 json-<VERSION>.jar
 proto-google-cloud-bigquerystorage-v1-<VERSION>.jar
 proto-google-cloud-bigquerystorage-v1beta1-<VERSION>.jar
 proto-google-cloud-bigquerystorage-v1beta2-<VERSION>.jar
 threetenbp-<VERSION>.jar

pass:[*] 使用 Hop 2.5.0 和 Simba 1.3.3.1004 测试通过。并非所有认证方法都经过测试，因此此列表可能不完全详尽

## 连接配置

### 基本连接设置

在 Qi Hop 中创建 Google BigQuery 连接时，请配置以下基本设置：

| 设置 | 值 |
|---|---|
| 连接名称 | 您偏好的连接名称（例如"BQ"） |
| 连接类型 | Google BigQuery |
| 访问方式 | Native (JDBC) |
| 服务器主机名 | https://www.googleapis.com/bigquery/v2 |
| 数据库名称 | 您的 Google Cloud Project ID |
| 端口号 | 443 |

### 认证选项

Qi Hop 中的 Google BigQuery 连接支持多种认证方式。最常见且推荐的方式是使用带有 JSON 密钥文件的服务账户认证。

在连接对话框的 *Options* 选项卡中，配置以下参数：

| 参数 | 值 | 描述 |
|---|---|---|
| OAuthType | 0 | 服务账户认证 |
| ProjectId | your-project-id | 您的 Google Cloud Project ID |
| OAuthServiceAcctEmail | service-account@your-project.iam.gserviceaccount.com | 服务账户邮箱地址 |
| OAuthPvtKeyPath | /path/to/service-account-key.json | 服务账户 JSON 密钥文件的路径 |
| TimeOut | 3600 | 连接超时时间（秒，可选） |

### 配置示例

以下是一个可用的 BigQuery 连接配置的完整示例：

- **连接名称**：BQ
- **服务器主机名**：https://www.googleapis.com/bigquery/v2
- **数据库名称**：your-project-id
- **端口号**：443

**Options 选项卡参数**：

- **OAuthType**：0
- **ProjectId**：your-project-id
- **OAuthServiceAcctEmail**：your-service-account@your-project-id.iam.gserviceaccount.com
- **OAuthPvtKeyPath**：/path/to/your-service-account-key.json
- **TimeOut**：3600

## Google Cloud Platform 服务账户设置

要连接到 Google Cloud 服务，您需要在 Google Cloud Platform (GCP) 项目中设置服务账户并下载认证凭据。

### 创建服务账户

1. **导航到 Google Cloud Console**：
   - 前往 https://console.cloud.google.com/
   - 选择您的项目或创建新项目

2. **创建服务账户**：
   - 导航到"IAM & Admin" > "Service Accounts"
   - 点击"Create Service Account"
   - 为您的服务账户提供名称和描述
   - 点击"Create and Continue"

3. **分配角色**：
   - 根据您要连接的服务，为您的服务账户分配适当的角色：
      - 对于 BigQuery：
        ** `BigQuery Data Viewer`（`roles/bigquery.dataViewer`）- 用于读取访问
        ** `BigQuery Job User`（`roles/bigquery.jobUser`）- 用于运行查询（必需）
        ** `BigQuery Data Editor`（`roles/bigquery.dataEditor`）- 如果需要写入访问
        ** `BigQuery Data Owner`（`roles/bigquery.dataOwner`）- 如果需要完全控制
      - 对于 Cloud Storage：`Storage Object Viewer`、`Storage Object Admin`（如果需要写入访问）
      - 对于其他服务：请查阅具体服务的文档以了解所需角色
   - 点击"Continue"然后点击"Done"

4. **生成并下载密钥**：
   - 点击已创建的服务账户
   - 进入"Keys"选项卡
   - 点击"Add Key" > "Create new key"
   - 选择"JSON"格式
   - 点击"Create"下载密钥文件

5. **保护密钥文件**：
   - 将下载的 JSON 密钥文件存储在安全的位置
   - 记下此文件的完整路径 - 认证配置时需要用到
   - 确保文件具有适当的权限（仅运行 Hop 的用户可读）

### 替代方案：使用应用默认凭据

如果您在 Google Cloud Platform 上运行 Hop 或已在本地配置了 Google Cloud SDK，Qi Hop 也可以使用 Google Cloud 的应用默认凭据（ADC）。

要使用 ADC：
1. 安装并配置 Google Cloud SDK
2. 运行 `gcloud auth application-default login` 设置默认凭据
3. 在您的 Hop 连接中，可以省略服务账户密钥文件参数

此方法特别适用于开发环境或在 Google Cloud Platform 服务上运行 Hop 时。

### 安全最佳实践

- **最小权限原则**：仅为您的用例分配最低限度的必要角色
- **密钥轮换**：定期轮换服务账户密钥（建议每 90 天）
- **环境变量**：考虑使用环境变量存储密钥文件路径，而非硬编码
- **访问控制**：使用适当的文件系统权限限制对服务账户密钥文件的访问
- **监控**：启用审计日志以监控服务账户使用情况

## 测试连接

配置连接后：

1. 在连接对话框中点击"Test"按钮
2. 如果成功，您应该会看到确认消息
3. 如果测试失败，请检查：
   - 您的服务账户具有必要的 BigQuery 权限
   - JSON 密钥文件路径正确且可访问
   - 您的项目 ID 与服务账户中的匹配
   - 到 Google API 的网络连接可用

## 故障排除

### 常见问题

- **认证错误**：验证您的服务账户是否具有所需的 BigQuery 角色以及 JSON 密钥文件路径是否正确
- **找不到项目**：确保 ProjectId 参数与您的实际 Google Cloud Project ID 匹配
- **连接超时**：如果遇到慢连接，请增加 TimeOut 值
- **驱动冲突**：确保已按驱动安装部分所述排除冲突的 GRPC jar

### 性能注意事项

- 为大型查询操作使用适当的超时设置
- 考虑使用 BigQuery 的标准 SQL 方言以获得更好的性能
- 在处理大型数据集时，请在您的 workflow 中实现适当的错误处理
