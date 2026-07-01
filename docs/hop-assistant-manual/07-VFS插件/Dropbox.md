# Dropbox（Dropbox VFS）

## 核心功能

hfxt data process 通过 Apache VFS 支持在平台几乎任何位置读取和写入 Dropbox。

## 方案（Scheme）

访问 Dropbox 中文件所使用的方案为：

`**dropbox://**`

## 配置

您需要通过存储的访问令牌为 Dropbox 设置 OAuth 2.0 访问。

1. 在您的 OAuth 2.0 提供商 Dropbox 处创建一个应用（参见 Dropbox 文档）：
   - 打开浏览器并访问 https://www.dropbox.com/developers
   - 选择 My Apps 并点击 Create app
   - 选择 API（dropbox API），选择您需要的访问类型
   - 为您的应用提供一个唯一的名称
   - 然后，点击 Create App
2. 获取访问令牌并将其配置到 Hop 中
