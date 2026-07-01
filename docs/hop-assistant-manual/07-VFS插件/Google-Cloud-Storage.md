# Google Cloud Storage（Google Cloud Storage VFS）

## 核心功能

hfxt data process 通过 Apache VFS 支持在平台几乎任何位置读取和写入 Google Cloud Storage。

## 方案（Scheme）

访问 Google Cloud Storage 中文件所使用的方案为：

`**gs://**`

## 配置

您需要为服务账户生成密钥文件才能使其工作。请前往 Google Cloud 控制台执行此操作。

一旦您拥有具备访问 GCP 存储权限的服务账户密钥文件，可以通过以下方式之一指向它：

- 名为 `GOOGLE_APPLICATION_CREDENTIALS` 的系统环境变量（标准的 Google 做法）
- 在选项对话框的"Google Cloud"选项卡中配置

您也可以使用 `hop-conf`：

```shell
-gck, --google-cloud-service-account-key-file=<serviceAccountKeyFile>
                      # 配置 Google Cloud 服务账户 JSON 密钥文件的路径
```
