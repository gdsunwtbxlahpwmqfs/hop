# AWS S3

## 方案

你可以使用以下方案访问 Amazon Simple Storage 中的文件

`**s3://**`

## 配置

Amazon Web Services Simple Cloud Storage 的配置可以通过多种方式完成。
大多数需要你拥有 `Access Key` 和 `Secret Key`。

**最佳实践是为 Qi Hop 创建一个专用的 IAM 用户**，这样如果需要，你可以微调权限（例如设置为只读），或者在该用户不再需要时删除它。

完整列表请参见 AWS 文档中的 [Working with credentials](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials)。

以下是两种常用的配置访问方式。

### 凭证文件

你可以在主文件夹的 `.aws/credentials` 文件中创建文件，内容如下：

```properties

aws_access_key_id = yourSecretKey
aws_secret_access_key = a-long/series-of-characters-for-your-access-key
```

### 变量

你可以设置以下系统环境变量：

- `AWS_ACCESS_KEY_ID` : 设置为你的 AWS 访问密钥
- `AWS_SECRET_ACCESS_KEY` : 设置为你的秘密访问密钥

## 分片大小

你可以通过设置以下变量来设置 S3 上新文件的默认分片大小：

`HOP_S3_VFS_PART_SIZE`

这需要设置为全局 Hop 配置变量（在 hop-config.json 中）。
你可以使用 Hop GUI 中的 Tools/Edit config variables 菜单，或使用 hop-conf 命令行工具来完成。

可接受的最小值为 `5MB`，最大值为 `5GB`。

如果未设置此变量，将使用 `5MB` 作为值，并在 S3 上创建文件时在控制台上打印一条消息：

`Part size null less than minimum of 5MB, set to minimum`。

## 使用和测试

要测试配置是否有效，你可以简单地将一个小 CSV 文件上传到 S3 bucket 中，然后在 Hop GUI 中使用 File/Open。
然后输入 `s3://` 作为文件位置并按回车（或点击刷新按钮）。
浏览到你上传的 CSV 文件并打开它。
如果一切配置正确，你应该能够在 Hop GUI 中看到内容。
