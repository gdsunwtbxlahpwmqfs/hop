# ![Google Analytics Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/google-analytics.svg) Google Analytics Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 配置

- 您需要一个具有访问权限和足够权限的 Google Analytics 账户来访问 GA4 property。
- 您需要一个带有 service account 的 Google Cloud 项目。该项目需要启用 Google Analytics API。

查看 Google Cloud 文档，了解如何[创建 service account](https://cloud.google.com/iam/docs/service-accounts-create) 和[设置项目](https://developers.google.com/analytics/devguides/reporting/data/v1/quickstart-client-libraries)以使用 Google Analytics Data API。

## 选项

### Google Analytics 连接设置

| 选项 | 描述 |
|---|---|
| Application name | 输入应用程序名称，如 "Qi Hop"，或保留默认值。 |
| OAuth service email | 您的 Google Developer Service Account 的电子邮件地址（例如 "<random characters@developer.gserviceaccount.com>"）。 |
| Key file | 与您的 OAuth Service account 关联的 P12 私钥路径。 |
| Property Id | 要读取的 GA4 property |

### 查询定义

| 选项 | 描述 |
|---|---|
| Start date (YYYY-MM-DD | 指定与查询关联的开始日期。日期必须以以下格式输入：YYYY-MM-DD。 |
| End date (YYYY-MM-DD | 指定与查询关联的结束日期。日期必须以以下格式输入：YYYY-MM-DD。 |
| Dimension | 指定要查询的维度字段。Google Analytics Data API 文档提供了有效输入和可组合 metric 的列表。 |
| Metrics | 指定要返回的 metric 字段。必须至少提供一个 metric。 |
| Sort | 指定要排序的维度字段。 |

### 字段

点击 **Get Fields** 可基于您定义的查询检索可能的字段列表。
点击 **Preview** 可基于定义的查询预览数据。

### 限制大小

将指定 GA4 property 的检索行数限制为一定数量。将限制大小设为 0 可获取所有可用行。
