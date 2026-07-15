# ![Notify via AWS SNS, role="image-doc-icon"](../../assets/images/transforms/icons/aws-sns.svg) AWS SNS Notify

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/question_mark.svg) |

## 前置条件

在首次执行之前，你需要创建一个 IAM-Role（例如用于 EC2/ECS）或一个带有 AWS Key 和 Secret 的 IAM-User，并附加通过 SNS 推送通知所需的策略。

你还需要创建一个或多个订阅主题，用于推送消息。

## 选项

### AWS 设置选项卡

| option | description |
|---|---|
| Use AWS Credentials chain | Qi Hop 尝试从主机环境获取 AWS 凭据。更多信息请查看 [Credentials](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials) 文档。 |
| AWS Access Key | 你的 AWS Access Key（`AWS_ACCESS_KEY_ID`） |
| AWS Secret Access Key | 你的 AWS Access Key 的密钥（`AWS_SECRET_ACCESS_KEY`） |
| AWS Region | 服务运行的 AWS 区域。 |

### 通知

在通知选项卡上，你可以定义发送通知时的行为、topicARN、内容以及用于 MessageID 的字段。

| Option | Description |
|---|---|
| Event type |  |
| Message ID | 每条通知都会从 SNS 获取一个 Message-ID。可以将其写入此处定义的输出字段。 |
| SNS-Fields table |  |
