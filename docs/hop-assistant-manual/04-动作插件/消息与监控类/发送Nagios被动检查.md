# 发送Nagios被动检查（Send Nagios passive check）

`Send Nagios passive check` 动作向 Nagios 发送被动检查。

您可以发送监控信息，例如工作流中进程的开始和结束信息。

它需要在 Nagios 服务器上安装 NCSA 插件（NSCA 是一个 Linux/Unix 守护进程，允许您将远程机器和应用程序的被动警报和检查与 Nagios 集成。对于处理安全警报以及冗余和分布式 Nagios 设置非常有用。）

有关 Nagios NSCA 插件的更多详细信息和设置说明，请参阅 Nagios 的[被动检查文档](http://nagios.sourceforge.net/docs/3_0/passivechecks.html)。
