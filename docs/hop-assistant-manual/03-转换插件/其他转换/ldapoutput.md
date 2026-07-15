# ![LDAP Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/ldapoutput.svg) LDAP Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## General 选项卡

| Option | Description |
|---|---|
| Transform name | Transform 名称；此名称在单个 Pipeline 中必须唯一。 |
| Host | Host 可以是 LDAP 目录服务器的 IP 地址或其 DNS 名称。 |
| Port | LDAP 目录服务器的端口号。 |
| Referral a | 确定如何处理引用。 |
| Derefalias a | 确定如何解引用别名。 |
| Protocol a | 确定使用的协议。 |
| Use authentication | 启用 LDAP 身份验证。 |
| Username | 身份验证时传递的用户名。 |
| Password | 身份验证时传递的密码。 |
| Use certificate | 启用证书使用。 |
| Trust store path |  |
| Trust store password |  |
| Trust all certificates |  |
| Test connection | 测试配置的到 LDAP 服务器的连接。 |

## 设置

| Option | Description |
|---|---|
| Operation | Insert、Update、Upsert、Add attribute、Delete、Rename DN（distinguished name）、Remove attribute。 |
| Multi valued field seperator |  |
| Fail if not exist |  |
| Dn fieldname | 包含 distinguished name 的字段。 |
| Old DN fieldname | 包含旧 distinguished name 的字段（重命名）。 |
| New DN fieldname | 包含新 distinguished name 的字段（重命名）。 |
| Delete RDN | 是否删除 RDN（relative distinguished name） |

### 操作类型

LDAP Output Transform 支持以下操作类型：

- *Insert*：使用指定的 DN 和属性创建新的 LDAP 条目。
- *Update*：更新 LDAP 条目的现有属性。该条目必须已存在。
- *Upsert*：如果不存在则插入新条目，如果存在则更新。
- *Add attribute*：向现有 LDAP 条目添加新的属性值，不删除现有值。
- *Delete*：删除整个 LDAP 条目。
- *Rename DN*：通过更改 distinguished name 来重命名 LDAP 条目。
- *Remove attribute*：从 LDAP 条目中移除属性值。如果指定了值，则仅移除该特定值。如果未指定值（为空），则从条目中移除整个属性。

## Fields 选项卡

| Option | Description |
|---|---|
| Search base for fields | 从哪里开始查找字段 |
| Attributes |  |
| Stream field |  |
| Update | 是否更新字段（Y/N） |
| Get fields |  |
| Edit mapping |  |
