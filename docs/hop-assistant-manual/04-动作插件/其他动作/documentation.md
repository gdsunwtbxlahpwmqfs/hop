# 构建文档

## 描述

此 action 用于构建 Hop 文件或项目的文档。

## 选项

- 您希望项目文档输出到的目标文件夹。
- 是否包含备注？
- 是否包含参数？
- 是否包含 metadata？
- 是否生成 HTML？
- 是否在生成 HTML 后删除 MarkDown？

## 从 MarkDown 生成 HTML

索引中的链接当前设置为 .md.html 文件。
我在目标文件夹中运行了以下命令来生成 HTML 文件：

```bash
for i in $(find . -name '*.md') ; do echo "$i" && pandoc -s $i -c assets/styles.css -o $i.html ; done
```
