将一切参数化！[变量](variables.md)提供了一种简单的方法来避免在您的系统、环境或项目中硬编码各种内容。

- 将环境特定设置放在一个或多个[环境](../12-变量与项目管理/projects-environments.md)配置文件中。这允许您在不更改项目的情况下将项目部署到另一个环境（dev/uat/prod），您只需要配置另一组配置文件。
- 引用文件位置时，优先使用 `{openvar}PROJECT_HOME{closevar}` 而不是 `{openvar}Internal.Entry.Current.Directory{closevar}` 或 `{openvar}Internal.Pipeline.Filename.Directory{closevar}` 等表达式
- 使用变量配置 transform 副本，以便在不同规模的环境之间轻松过渡。
- 使用[环境变量](variables.md#_environment_variables.md)将您的项目和环境、审计信息等保留在 Qi Hop 安装目录之外。
