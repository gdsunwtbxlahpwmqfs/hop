# Hop Server

## 核心功能

Hop Server 是一个轻量级 Web 服务器，承载 Hop 运行时环境用于远程执行，提供 Web 界面和一组 Servlet 用于检查执行状态等。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Server name（服务器名称） | 用于此服务器定义的名称 |
| Hostname or IP address（主机名或 IP 地址） | 服务器的主机名或 IP 地址 |
| Port（端口） | 端口号，留空则默认为 80 |
| Web app name（Web 应用名称，可选） | Web 应用名称 |
| Username（用户名） | 登录用户名 |
| Password（密码） | 登录密码 |
| Use https protocol（是否使用 HTTPS） | 默认：false |

## 说明

Hop Server 用于将管道和工作流部署到远程服务器执行，支持集群和集中化管理。详细文档请参考 Hop Server 专章。
