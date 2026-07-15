# ![File Metadata transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/filemetadata.svg) File Metadata

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | 此 Transform 的名称 |
| Filename | 要扫描 metadata 的文件名 |
| Filename in a field? | 启用后可在输入流的字段中指定文件名 |
| Filename field | 当启用上一选项时，可指定运行时包含文件名的字段。 |
| Limit scanned rows | 限制扫描的行数（默认 10,000）。 |
| Fallback charset | 扫描文件时使用的字符集 |
| Delimiter candidates | 检测文件布局时要尝试的分隔符列表。 |
| Enclosure candidates | 检测文件布局时要尝试的封闭符列表。 |

## 输出字段

此 Transform 返回的字段为

- charset
- delimiter
- field_count
- skip_header_lines
- skip_footer_lines
- header_line_present
- name
- type
- length
- precision
- mask
- decimal_symbol
- grouping_symbol
