# Hop Doc

Hop Doc 以 Markdown 格式为 Hop 项目生成文档，支持可选的 HTML 输出。

## 用法

为 Hop 项目生成文档：

```command
./hop.bat doc -s /path/to/project -t /path/to/docs [--generate-html] [--remove-markdown]
```

或使用 Hop 项目变量：
```command
./hop.bat run -j my-project doc -t /path/to/docs --generate-html
```

## 选项

| 选项 | 描述 | 默认值 |
|---|---|---|
| -s, --source-folder | 要生成文档的源项目文件夹 | 必填（或使用 project） |
| -t, --target-folder | 目标文档文件夹 | 必填 |
| -ip, --include-parameters | 包含 pipeline/workflow 参数 | true |
| -in, --include-notes | 包含注释文本 | false |
| -im, --include-metadata | 包含 metadata 概览 | true |
| -n, --project-name | 项目名称 | "Hop"（或 HOP_PROJECT_NAME） |
| -gh, --generate-html | 生成所有文档的 HTML 版本 | false |
| -rmmd, --remove-markdown | 在 HTML 生成后删除 .md 文件 | false |

## 示例

### 基本文档

仅生成 Markdown 文档：

====
Windows::
--
```shell
./hop.bat doc -s "C:\Projects\my-hop-project" -t "C:\yourLocation\docs"
```

Linux/macOS::
--
```shell
./hop doc -s ~/projects/my-hop-project -t ~/docs
```
====

### HTML 文档

生成 HTML 并删除 Markdown 源文件：

====
Windows::
--
```shell
./hop.bat doc -s "C:\Projects\my-hop-project" -t "C:\yourLocation\docs" --generate-html --remove-markdown
```

生成结果：

```
C:\docs\
├── index.html (project TOC)
├── pipelines\
│   └── my-pipeline.html
├── workflows\
│   └── my-workflow.html
└── assets\
    ├── styles.css
    └── images\
```
Linux/macOS::
--
```shell
./hop doc -s ~/projects/my-hop-project -t ~/docs --generate-html --remove-markdown
```
====

### 使用 Hop 项目上下文

```shell
./hop.bat run -j IT-mongo doc -t "C:\yourLocation\docs" --generate-html
```

自动使用 `HOP_PROJECT_HOME` 和 `HOP_PROJECT_NAME`。

## Workflow Action 用法

将"Documentation" Action 添加到任何 Hop workflow 中：

1. 从 Utility 类别中拖拽 **Documentation** Action
2. 设置 **Target folder**（`C:\yourLocation\docs`）
3. 可选：勾选 **Generate HTML**
4. 可选：勾选 **Remove markdown**（仅在启用 Generate HTML 时有效）
5. 配置内容选项：
   - ☑ **Include parameters**（可选）
   - ☑ **Include metadata**（可选）
   - ☐ **Include notes**（可选）

## 生成结构

docs/
├── index.html (Table of Contents)
├── pipelines/ (all .hpl files)
│ ├── pipeline1.html
│ └── subfolder/
│ └── pipeline2.html
├── workflows/ (all .hwf files)
│ └── workflow1.html
├── metadata/ (metadata overview)
└── assets/
├── styles.css
└── images/

## 注意事项

- 如果 `--generate-html` 为 false，则 `--remove-markdown` **会被自动禁用**
- HTML 输出包含自定义 CSS 样式和响应式图片
- `index.html` 中的所有链接自动指向 `.html` 文件
- 支持嵌套文件夹和 metadata 文档
- 使用 CommonMark 进行 Markdown → HTML 转换
