# Azure

来自 [Wikipedia](https://en.wikipedia.org/wiki/Microsoft_Azure)：

Microsoft Azure，通常简称为 Azure（/ˈæʒər/），是 Microsoft 创建的云计算服务，用于通过 Microsoft 管理的数据中心构建、测试、部署和管理应用程序和服务。
它提供软件即服务（SaaS）、平台即服务（PaaS）和基础设施即服务（IaaS），并支持许多不同的编程语言、工具和框架，包括 Microsoft 专有的和第三方软件及系统。

Hop 通过多种 metadata 类型支持 Azure：

## Pipeline Transform

- [Azure Event Hubs Listener](../03-转换插件/其他转换/azure-event-hubs-listener.md)：无限期监听 Microsoft Azure 云平台上的 Event Hub。
- [Azure Event Hubs Writer](../03-转换插件/其他转换/azure-event-hubs-writer.md)：允许您将消息（事件）写入 Microsoft Azure 云平台上名为 Event Hubs 的流式服务总线。

## VFS

Hop 中的 Apache [VFS 支持](../14-虚拟文件系统/vfs.md)允许您直接从多种文件系统和协议读取数据，包括 Azure：

- [Azure Blob Storage](../14-虚拟文件系统/azure-blob-storage-vfs.md)：直接在 Azure Blob Storage 中读取和写入数据
