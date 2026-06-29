# Vectorwise（Actian Vector / VectorWise）

## 概述

Vectorwise（现为 Actian Vector）是一个高性能的列式分析型数据库，基于 Actian Ingres 技术。使用此数据库需要单独下载 JDBC 驱动程序。

## 主要连接参数

| 参数 | 信息 |
|------|------|
| 类型 | 关系型（Relational） |
| 驱动 | https://esd.actian.com/product/drivers/JDBC/java/JDBC |
| 内置版本 | 无 |
| Hop 依赖 | 无 |
| 文档 | https://docs.actian.com/ingres/11.0/index.html#page/Connectivity%2FJDBC_Driver_and_Data_Source_Classes.htm%23 |
| JDBC 连接串 | jdbc:ingres://host:port{,port}{;host:port{,port}}/db{;attr=value} |
| 驱动文件夹 | &lt;Hop 安装目录&gt;/lib/jdbc |
