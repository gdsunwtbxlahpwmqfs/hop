# ![LDAP Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/ldapinput.svg) LDAP Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 常规选项卡

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称；此名称在单个 pipeline 中必须唯一。 |
| Host | 主机可以是 LDAP 目录服务器的 IP 地址或 DNS 名称。 |
| Port | LDAP 目录服务器的端口号。 |
| Use authentication | 启用 LDAP 认证 |
| Username | 认证时传递的用户名。 |
| Password | 认证时传递的密码。 |
| Use certificate | 启用证书的使用。 |
| Trust store path |  |
| Trust store password |  |
| Trust all certificates |  |
| Test connection | 测试与 LDAP 服务器的已配置连接。 |

## 搜索选项卡

| 选项 | 描述 |
|---|---|
| Dynamic search base | 启用搜索基准字段。 |
| Search base fieldname | 包含基准 LDAP 节点的字段。 |
| Search base | 用于搜索 LDAP 内容的基准 LDAP 节点。 |
| Dynamic filter string | 启用过滤器字符串字段。 |
| Filter string fieldname | 包含过滤器字符串的字段。 |
| Filter String | 用于搜索自定义内容的 LDAP 过滤器，当前阶段仅支持"单一过滤器格式"。 |

## 高级选项卡

| 选项 | 描述 |
|---|---|
| Include rownum in output? | 向输出添加行号。 |
| Rownum fieldname | 包含行号的字段。 |
| Limit | 限制返回的结果数量。 |
| Time limit | 限制返回结果的时间。 |
| Multi valued field separator. |  |
| Set paging |  |
| Page size |  |
| Search scope | Object scope、One level scope、Subtree scope |

## 字段选项卡

| 选项 | 描述 |
|---|---|
| Name | 导入字段的名称。 |
| Attribute |  |
| Fetch as | String、Binary |
| Is sorted Key? | Y/N |
| Type | 此字段的数据类型。 |
| Format | 格式掩码（数字类型或日期格式） |
| Length | 字段长度。 |
| Precision | 精度选项取决于字段类型，但仅支持 Number 类型；它返回小数位数。 |
| Currency | 用于表示货币的符号 |
| Decimal | 小数点；可以是点或逗号 |
| Group | 在四位或更多位数字中分隔千位的方法。 |
| Trim type | 修剪字段的位置，left、right、both、none |
| Repeat | 如果 LDAP 对某个属性未返回值，则使用上一行的值 |
| Get fields | 获取结果字段 |
