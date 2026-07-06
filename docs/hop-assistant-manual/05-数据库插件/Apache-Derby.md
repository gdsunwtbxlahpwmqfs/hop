# Apache Derby（Apache Derby）

## 概述

Apache Derby 是一种轻量级的关系型数据库，支持嵌入式和网络（客户端/服务器）两种部署模式。其 JDBC 驱动已内置在 Qi 数据治理平台 中，无需额外下载。

Derby JDBC 驱动有两种不同的模式：如果您想使用 Derby 嵌入式（Embedded）模式，请将 *derbyclient.jar* 文件替换为 *derby.jar*。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | 内置（Included） |
| 内置版本 | 10.17.1.0 |
| Hop 依赖 | 无 |
| 文档 | https://db.apache.org/derby/derby_downloads.html |
| JDBC 连接串 | jdbc:derby:&lt;host&gt;[:&lt;port&gt;]/&lt;database&gt; |
| JDBC 嵌入式连接串 | jdbc:derby:&lt;database&gt; |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
