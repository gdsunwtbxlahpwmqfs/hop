# ![Get SubFolder names transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/getsubfolders.svg) Get SubFolder names

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 文件夹选项卡

| 选项 | 描述 |
|---|---|
| Transform name | 此 transform 在 pipeline 工作区中显示的名称 |
| Accept foldername from field? | 允许通过字段传递文件夹名。 |
| Foldername field | 包含文件夹名称的字段。 |
| Selected directories | 要获取子文件夹的目录。 |
| Browse | 使用本地文件浏览器获取路径。 |
| Add | 添加在 Directory 字段中定义的路径。 |
| Delete | 删除路径 |
| Edit | 更改路径 |

### 设置选项卡

| 选项 | 描述 |
|---|---|
| Include rownum in output? | 允许将行号添加到输出中。 |
| Rownum fieldname | 包含行号的字段。 |
| Limit | 限制输出行数。 |

## 输出字段

| 字段 | 类型 | 描述 |
|---|---|---|
| `folderName` | String | 子文件夹的完整路径 |
| `short_folderName` | String | 子文件夹的基名（最后一段路径） |
| `path` | String | 子文件夹父目录的完整路径 |
| `ishidden` | Boolean | 子文件夹是否隐藏 |
| `isreadable` | Boolean | 子文件夹是否可读 |
| `iswriteable` | Boolean | 子文件夹是否可写 |
| `lastmodifiedtime` | Date | 子文件夹的最后修改时间戳 |
| `uri` | String | 子文件夹的完整 URI |
| `rooturi` | String | 文件系统的根 URI |
| `children` | Integer | 子文件夹中直接子项（文件和文件夹）的数量 |

## 云存储 (VFS)
此 transform 可与 Qi Hop VFS 集成支持的任何文件系统一起使用，包括本地文件系统、S3、Azure Blob Storage、Google Cloud Storage、Google Drive、Dropbox 等。
使用云存储时，请注意以下事项：

- 输入目录必须指向实际的文件夹，而非 scheme 根路径。例如，使用 `s3://my-bucket/my-folder` 而非 `s3://` 或 `s3://my-bucket`。
- 某些 metadata 字段（`ishidden`、`isreadable`、`iswriteable`、`lastmodifiedtime`、`children`）可能并非所有云存储提供商都可用。当无法确定值时，该字段将为 `null`。例如，`lastmodifiedtime` 在 S3 或 MinIO 上的虚拟文件夹中可能不可用。
- 此 transform 会递归遍历整个文件夹树。在具有深层嵌套或非常庞大的文件夹结构的云存储上，这可能导致大量的 API 调用和较慢的性能。
