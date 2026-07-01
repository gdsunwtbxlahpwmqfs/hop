# AWS SNS 通知（AWS SNS Notify）

AWS SNS 通知转换使您能够通过 Amazon Web Services 简单通知服务（Simple Notification Service）从 hfxt data process 管道向已订阅的用户发送通知。

## 前置条件

在首次执行之前，您需要创建一个 IAM 角色（例如用于 EC2/ECS）或一个带有 AWS 密钥和密钥对的 IAM 用户，并附加通过 SNS 推送通知所需的策略。您还需要创建一个或多个用于推送消息的订阅主题。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 支持 |
| Flink | ❓ 支持 |
| Dataflow | ❓ 支持 |

## 主要选项

### AWS 设置（AWS Settings）选项卡

| 选项 | 说明 |
|------|------|
| 使用 AWS 凭证链（Use AWS Credentials chain） | hfxt data process 尝试从主机环境获取 AWS 凭证。 |
| AWS 访问密钥（AWS Access Key） | 您的 AWS 访问密钥（`AWS_ACCESS_KEY_ID`）。 |
| AWS 秘密访问密钥（AWS Secret Access Key） | 您的 AWS 访问密钥对应的密钥（`AWS_SECRET_ACCESS_KEY`）。 |
| AWS 区域（AWS Region） | 服务运行的 AWS 区域。 |

### 通知（Notifications）选项卡

在通知选项卡中，您可以定义发送通知时的行为、主题 ARN（topicARN）、内容以及用于消息 ID 的字段。事件类型可选择在第一行（推荐）或最后一行发送通知。
