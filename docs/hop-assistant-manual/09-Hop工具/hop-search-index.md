# Hop Search

## 描述

Hop Search 是一个命令行工具，允许你在项目中进行搜索。
当你通过 SSH 连接在远程服务器上工作，需要查找某个值、设置、Transform、Action 等内容时，它特别有用。

搜索将匹配名称、键、值、描述等。

## 选项

| 选项 | 描述 | 默认值 | 可用 plugin |
|---|---|---|---|
| `-j` 或 `--project` |  |  |  |
| 要搜索的项目名称 |  |  |  |
| 无，或未指定时使用配置的默认项目 |  |  |  |
| Projects |  |  |  |
| `-e` 或 `--environment` |  |  |  |
| 要搜索的环境 |  |  |  |
| 无，或未指定时使用配置的默认环境 |  |  |  |
| Projects |  |  |  |
| `-h` 或 `--help` |  |  |  |
| 打印帮助选项 |  |  |  |
| 无 |  |  |  |
| `-i` 或 `--case-insensitive` |  |  |  |
| 执行不区分大小写的搜索 |  |  |  |
| false |  |  |  |
| `-x` 或 `--regular-expression` |  |  |  |
| 指定的搜索字符串是一个（Java）[正则表达式](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern) |  |  |  |
| false |  |  |  |
| `-l` 或 `--print-locations` |  |  |  |
| 打印正在查看的位置 |  |  |  |
| false |  |  |  |
| `-v` 或 `--version` |  |  |  |
| 打印版本信息 |  |  |  |
| false |  |  |  |
