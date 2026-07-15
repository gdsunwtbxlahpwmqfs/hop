# 解压文件

## 描述

`Unzip file` action 将一个或多个文件解压到指定目标位置。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Get args from previous | 如果要使用（来自先前 workflow action 的）结果文件列表作为要解压的文件列表，请勾选此选项。 |
| Zip file name | zip 文件名或文件夹（如果要使用通配符）。 |
| Source wildcard a | 如果前一个选项是文件夹，您可以在此输入正则表达式通配符。 |
| Use zipfile name as root directory | 如果要为每个 zip 文件名创建一个单独的目录（与文件同名），请勾选此项 |
| Target directory | 要解压到的目标目录 |
| Create folder | 如果要创建目标文件夹，请勾选此项 |
| Include wildcard | 使用此正则表达式选择 zip 存档中要提取的文件 |
| Exclude wildcard | 使用此正则表达式选择 zip 存档中要排除的文件 |
| Include date in filename | 在解压的文件名中包含当前日期（格式 yyyyMMdd） |
| Include time in filename | 包含时间（格式 HHmmss） |
| Specify the date time format yourself | 允许您自行指定日期时间格式（默认为：yyyyMMdd'_'HHmmss） |
| If files exists | 选择目标（解压的）文件已存在时要采取的操作：跳过、覆盖等。 |
| After extraction a | 选择 zip 文件提取后要采取的操作： |
| Move files to | 如果前一个选项是 "Move files"，您可以在此选择目标目录。 |

### Advanced 选项卡

| 选项 | 描述 |
|---|---|
| Add extracted file to result | 将提取的文件名添加到此 workflow action 的结果文件列表中，以供后续 workflow action 使用。 |
| Success on a | 允许您指定此 workflow action 的成功因素： |
| Limit files | 解压的文件数或错误数，取决于上方选择的 `Success on` 选项。 |
