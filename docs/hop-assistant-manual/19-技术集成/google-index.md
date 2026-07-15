# Google 技术

## 简介

Hop 通过多个 plugin 支持 Google 技术栈。
下面我们简要介绍它们。

## Pipeline Transform

- [BigQuery Input (Beam)](../03-转换插件/其他转换/beambigqueryinput.md)
- [BigQuery Output (Beam)](../03-转换插件/其他转换/beambigqueryoutput.md)
- [Bigtable Input (Beam)](../03-转换插件/其他转换/beambigtableinput.md)
- [Bigtable Output (Beam)](../03-转换插件/其他转换/beambigtableinput.md)
- [GCP Pub/Sub Publisher (Beam)](../03-转换插件/其他转换/beamgcppublisher.md)
- [GCP Pub/Sub Subscriber (Beam)](../03-转换插件/其他转换/beamgcpsubscriber.md)

## VFS

Hop 中的 Apache [VFS 支持](../14-虚拟文件系统/vfs.md)允许您直接从多种文件系统和协议读取数据，包括 Google：

- [Google Drive](../14-虚拟文件系统/google-drive-vfs.md)：直接从/向 Google Drive 文件和文件夹读取和写入数据。
- [Google Cloud Storage](../14-虚拟文件系统/google-cloud-storage-vfs.md)：直接从/向 Google Cloud Storage 存储桶中的文件和文件夹读取和写入数据。

## Beam 与 Google Cloud

当您使用非 DataFlow 的 Beam runner 执行 pipeline 时，请确保通过运行以下命令传递默认的 Google Cloud 项目 ID：

`gcloud config set project <project-id>`

这会影响 Google Cloud 特定的 API，如 BigQuery、Pub/Sub 等。
