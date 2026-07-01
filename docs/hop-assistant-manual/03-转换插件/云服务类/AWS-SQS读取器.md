# AWS SQS 读取器（AWS SQS Reader）

AWS SQS 读取器转换使您能够在 hfxt data process 管道中接收来自 Amazon Web Services 简单队列服务（Simple Queue Service）的消息。

## 前置条件

在首次执行之前，您需要创建一个 IAM 角色（例如用于 EC2/ECS）或一个带有 AWS 密钥和密钥对的 IAM 用户，并附加所需策略。您还需要创建一个或多个订阅主题。

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
| SQS 队列 URL（SQS Queue URL） | SQS 队列的 URL（以 https:// 开头，不是 ARN！）。 |

### 输出定义（Output Definition）选项卡

在输出定义选项卡中，您可以定义从 SQS 消息读取信息的输出字段，以及接收消息的一些初始设置。可设置是否在接收后从队列中删除消息。
