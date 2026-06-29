# Beam 文件输出（Beam File Output）

Beam 文件输出转换使用文件定义（file definition）配合 Beam 执行引擎来写入文件。该转换专为在 Beam 引擎上运行的管道设计，通过元存储（metastore）中保存的文件定义来组织输出文件的结构。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ❌ 不支持 |
| Spark | ✅ 支持 |
| Flink | ✅ 支持 |
| Dataflow | ✅ 支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| 输出位置（Output location） | 输出位置。 |
| 文件前缀（File prefix） | 文件名前需要添加的文本。 |
| 文件后缀（File suffix） | 文件名后需要追加的文本。 |
| 是否窗口写入？（Windowed writes?） | 每个窗口写入一个文件。建议与 Beam 窗口（Beam Window）转换配合使用。 |
| 使用的文件定义（File definition to use） | 保存在元存储中的文件定义。 |
| 编辑（Edit） | 编辑已有的文件定义。 |
| 新建（New） | 创建新的文件定义。 |
| 管理（Manage） | 打开元存储浏览器（MetaStore Explorer）。 |

### 文件定义（File Definition）

文件定义用于定义文件的结构。

| 选项 | 说明 |
|------|------|
| 名称（Name） | 文件定义名称。 |
| 描述（Description） | 文件定义描述。 |
| 字段分隔符（Field separator） | 分隔字段的字符。 |
| 字段封闭符（Field enclosure） | 封闭字段的字符。 |
| 字段定义（Field definitions） | 字段列表。 |
| 字段名称（Field name） | 字段名称。 |
| 类型（Type） | 字段数据类型。 |
| 格式（Format） | 字段格式。 |
| 长度（Length） | 字段长度。 |
| 精度（Precision） | 字段精度。 |
