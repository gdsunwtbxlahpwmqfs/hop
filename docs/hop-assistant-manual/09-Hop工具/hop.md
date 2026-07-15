# Hop

`hop` 命令行工具是对项目附带的众多脚本（如 `hop-run`、`hop-search`、`hop-gui` 等）的简化。
其主要目标是使用单一包装脚本来简化开发者的维护工作，同时允许 plugin 贡献新命令。

.Usage

=====

## 用法

要查看 `hop` 工具的用法，只需使用 `help` 命令查看可用的命令：

```bash
$ ./hop help
Usage: hop [-hV] [-s=<systemProperties>[,<systemProperties>...]]... [COMMAND]
  -h, --help      Show this help message and exit.
  -s, --system-properties=<systemProperties>[,<systemProperties>...]
                  A comma separated list of KEY=VALUE pairs
  -V, --version   Print version information and exit.
Commands:
  help     Display help information about the specified command.
  conf     Configure Hop
  doc      Generate documentation
  encrypt  Encrypt secrets
  gui      The Hop GUI
  import   Import metadata
  run      Run a pipeline or workflow
  search   Search in Hop metadata
  server   Run a Hop server
```

=====

## 选项

| 选项 | 描述 |
|---|---|
| -s | 一个或多个系统参数。这些参数将在执行给定命令之前设置。 |
| -h | 显示此帮助消息并退出 |
| -V | 显示当前 Hop 版本 |
