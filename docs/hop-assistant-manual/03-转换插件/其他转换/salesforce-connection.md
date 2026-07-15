### 连接

Salesforce transform 可以使用预定义的 [Salesforce Connection](metadata-types/salesforce-connection.md)，或直接使用内联连接设置。
当使用 Salesforce Connection 时，将使用在 metadata 中定义的连接设置（URL、用户名、密码、OAuth 令牌）。
当不使用 Salesforce Connection 时，需要在 transform 中直接指定连接详情。

| 选项 | 描述 |
|---|---|
| Salesforce Connection | （可选）要使用的 [Salesforce Connection](metadata-types/salesforce-connection.md)。选择后，将使用来自 metadata 的连接设置，内联连接字段将被禁用。 |
| Salesforce Webservice URL a | 指定 Salesforce Webservice 的 URL。 |
| Username | 指定用于 Salesforce 认证的用户名 |
| Password | 指定用于 Salesforce 认证的密码。 |
| Test Connection | 点击以验证是否可以连接到您指定的 Salesforce Webservice URL。 |
