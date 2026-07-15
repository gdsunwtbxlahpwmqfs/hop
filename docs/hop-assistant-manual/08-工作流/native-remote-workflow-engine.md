# 远程 Workflow Engine

## Hop 远程 Workflow Engine

远程运行配置在远程 Hop Server 上运行 workflow。

此运行配置需要的配置很少，但有两个前提条件：

. 一个正在运行的 [Hop Server](hop-server/index.md)
. 项目中一个指向您正在运行的 Hop Server 的 [Hop Server metadata 定义](metadata-types/hop-server.md)

此页面描述了远程 workflow engine 特有的选项。这些选项扩展了通用的 [workflow 运行配置选项](workflow/workflow-run-configurations/workflow-run-configurations.md)。

### 选项

| 选项 | 说明 |
|---|---|
| Hop server |  |
| 运行您的 workflow 的远程 Hop server。 |  |
| Run Configuration |  |
| 在远程 Hop Server 上使用的配置。 |  |
| Server poll delay (ms) |  |
| 向远程服务器周期性轮询之间的延迟（毫秒）。 |  |
| Server poll interval (ms) |  |
| 向远程服务器周期性轮询之间的间隔（毫秒）。 |  |
| Export linked resources to server? |  |
| 如果您不仅要将当前 workflow 发送到服务器，还要发送其他引用的 workflow 和 pipeline，请启用此选项。 |  |
| Named resources reference source folder |  |
| 这是正在使用的命名资源的参考源文件夹（例如 `{openvar}PROJECT_HOME{closevar}`）。 |  |
| Named resources reference target folder |  |
| 这是您希望源文件夹在服务器上映射到的位置（例如 `/path/on/server`）。 |  |

### 导出链接资源到服务器

此选项不仅将父 workflow 发送到服务器，还会发送所有其他引用的 pipeline 和 workflow。
例如，如果您在 action 中引用了 pipeline 或 workflow，它们也会被发送到服务器。
所有使用的 pipeline 和 workflow 以及 workflow 执行配置的 XML 表示将以 ZIP 归档的形式发送到服务器。
服务器接收此归档并在不解压的情况下运行 workflow。
为使此功能正常运行，Hop 会更改引用和文件名引用。
例如 `{openvar}PROJECT_HOME{closevar}/update-dimensions.hwf` 将被更改为 `{openvar}Internal.Entry.Current.Folder{closevar}/update-dimensions.hwf`。
如您所见，Hop 将尝试使用相对于父文件的路径。

如果您使用数据文件，这些文件名也会被重命名。
例如，您可能在 `CSV File Input` transform 中读取一个名为 `{openvar}PROJECT_HOME{closevar}/files/bigfile.csv` 的文件。
在导出过程中，引用的文件名将被更改为 `{openvar}DATA_PATH_1{closevar}/bigfile.csv`。
对于每个被引用的文件夹，将在执行配置中定义和设置一个新变量。
默认情况下，为此变量设置的路径与执行（本地）机器上的路径相同。
在服务器上这可能没有太大意义。
因此，您可以指定一个参考源文件夹（如 `{openvar}PROJECT_HOME{closevar}`），结合一个目标文件夹（如 `/server/`）。
在该示例中，变量 `DATA_PATH_1` 将获得值 `/server/files/`。
这反过来允许您预先传输所需文件或将文件夹映射到 docker 容器等。
它为您在远程执行时提供灵活性，同时在客户端上保持开发的便利性。
