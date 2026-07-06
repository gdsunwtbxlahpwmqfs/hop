# HTTP Post（HTTP Post）

## 功能概述


HTTP Post 转换使用 HTTP POST 命令通过 URL 提交表单数据。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 常规（General）选项卡

常规选项卡定义您要使用的 Web 服务 URL，以及（可选）包含 URL 的字段。

| 选项 | 说明 |
|------|------|
| URL | 要提交到的 Web 服务 URL。 |
| 是否从字段接受 URL？（Accept URL from field?） | 勾选后，必须指定从哪个字段获取 URL。 |
| URL 字段名（URL field name） | 若上一选项勾选，在此指定 URL 字段。 |
| 编码（Encoding） | 所访问文件的编码标准。 |
| 请求实体字段（Request entity field） | 包含 POST 请求的字段名称。启用"提交文件"选项时，将检索此字段中命名的文件并提交其内容。 |
| 提交文件（Post a file） | 若在请求实体字段中定义了文件，勾选此选项将提交其内容。 |
| 使用分段上传（Use MultiPart Upload） | 以分段请求的形式将数据发送到服务器，提交文件时很有用。 |
| 连接超时（Connection timeout） | 连接尝试出错前的超时时间（毫秒，默认 10000）。 |
| 套接字超时（Socket timeout） | 套接字出错前的超时时间（毫秒，默认 10000）。 |
| 连接关闭等待时间（Connection close wait time） | 连接关闭后的等待时间（毫秒），默认 -1 表示使用操作系统默认等待时间（通常为 2 分钟）。 |
| 结果字段名（Result fieldname） | 您要将结果输出到的字段。 |
| HTTP 状态码字段名（HTTP status code fieldname） | 您要将状态码输出到的字段。 |
| 响应时间（毫秒）字段名（Response time fieldname） | 您要将响应时间（毫秒）输出到的字段。 |
| HTTP 登录（HTTP login） | 若表单需要身份验证，此字段应包含用户名。 |
| HTTP 密码（HTTP password） | 若表单需要身份验证，此字段应包含与用户名对应的密码。 |
| 代理主机（Proxy host） | 代理服务器的主机名或 IP 地址（若使用）。 |
| 代理端口（Proxy port） | 代理服务器的端口号（若使用）。 |

### 字段选项卡：正文（标头）参数

字段选项卡定义 HTTP 请求标头和正文的参数。正文参数用于 POST 和 PUT 操作。


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0018-mts-pkc12</name>
    <name_sync_with_filename>Y</name_sync_with_filename>
    <description/>
    <extended_description/>
    <pipeline_version/>
    <pipeline_type>Normal</pipeline_type>
    <parameters>
    </parameters>
    <capture_transform_performance>N</capture_transform_performance>
    <transform_performance_capturing_delay>1000</transform_performance_capturing_delay>
    <transform_performance_capturing_size_limit>100</transform_performance_capturing_size_limit>
    <created_user>-</created_user>
    <created_date>2025/11/11 16:32:26.027</created_date>
    <modified_user>-</modified_user>
    <modified_date>2025/11/11 16:32:26.027</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>generate 1 row</from>
      <to>GET /api/whoami</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>GET /api/whoami</from>
      <to>200?</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>200?</from>
      <to>parse result</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>200?</from>
      <to>Abort</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>parse result</from>
      <to>clean</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>clean</from>
      <to>preview</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>200?</name>
    <type>FilterRows</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <compare>
      <condition>
        <conditions>
</conditions>
        <function>=</function>
        <leftvalue>result_code</leftvalue>
        <negated>N</negated>
        <operator>-</operator>
        <value>
          <isnull>N</isnull>
          <length>-1</length>
          <mask>####0;-####0</mask>
          <name>constant</name>
          <precision>0</precision>
          <text>200</text>
          <type>Integer</type>
        </value>
      </condition>
    </compare>
    <send_false_to>Abort</send_false_to>
    <send_true_to>parse result</send_true_to>
    <attributes/>
    <GUI>
      <xloc>512</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Abort</name>
    <type>Abort</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <abort_option>ABORT_WITH_ERROR</abort_option>
    <always_log_rows>Y</always_log_rows>
    <row_threshold>0</row_threshold>
    <attributes/>
    <GUI>
      <xloc>512</xloc>
      <yloc>288</yloc>
    </GUI>
  </transform>
  <transform>
    <name>GET /api/whoami</name>
    <type>Rest</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <applicationType>JSON</applicationType>
    <bodyField/>
    <connectionTimeout>10000</connectionTimeout>
    <connection_name>REST-mTLS-pkcs12</connection_name>
    <dynamicMethod>N</dynamicMethod>
    <headers>
</headers>
    <httpLogin/>
    <httpPassword/>
    <ignoreSsl>N</ignoreSsl>
    <matrixParameters>
</matrixParameters>
    <method>GET</method>
    <methodFieldName/>
    <parameters>
</parameters>
    <preemptive>N</preemptive>
    <proxyHost/>
    <proxyPort/>
    <readTimeout>10000</readTimeout>
    <result>
      <code>result_code</code>
      <name>result</name>
      <response_header/>
      <response_time/>
    </result>
    <trustStoreFile/>
    <trustStorePassword>Encrypted </trustStorePassword>
    <url>/api/whoami</url>
    <urlField/>
    <urlInField>N</urlInField>
    <attributes/>
    <GUI>
      <xloc>371</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>clean</name>
    <type>SelectValues</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <fields>
      <field>
        <length>-2</length>
        <name>authenticated</name>
        <precision>-2</precision>
      </field>
      <field>
        <length>-2</length>
        <name>client_dn</name>
        <precision>-2</precision>
      </field>
      <field>
        <length>-2</length>
        <name>verified</name>
        <precision>-2</precision>
      </field>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>812</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>generate 1 row</name>
    <type>RowGenerator</type>
    <description/>
    <distribute>N</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <fields>
</fields>
    <interval_in_ms>5000</interval_in_ms>
    <last_time_field>FiveSecondsAgo</last_time_field>
    <limit>1</limit>
    <never_ending>N</never_ending>
    <row_time_field>now</row_time_field>
    <attributes/>
    <GUI>
      <xloc>224</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>parse result</name>
    <type>JsonInput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <addresultfile>N</addresultfile>
    <readurl>N</readurl>
    <removeSourceField>N</removeSourceField>
    <IsIgnoreEmptyFile>N</IsIgnoreEmptyFile>
    <doNotFailIfNoFile>Y</doNotFailIfNoFile>
    <ignoreMissingPath>Y</ignoreMissingPath>
    <defaultPathLeafToNull>Y</defaultPathLeafToNull>
    <rownum_field/>
    <file>
      <name/>
      <filemask/>
      <exclude_filemask/>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
    </file>
    <fields>
      <field>
        <name>authenticated</name>
        <path>$.authenticated</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>client_dn</name>
        <path>$.client_dn</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>verified</name>
        <path>$.verified</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <IsInFields>Y</IsInFields>
    <IsAFile>N</IsAFile>
    <valueField>result</valueField>
    <shortFileFieldName/>
    <pathFieldName/>
    <hiddenFieldName/>
    <lastModificationTimeFieldName/>
    <uriNameFieldName/>
    <rootUriNameFieldName/>
    <extensionFieldName/>
    <sizeFieldName/>
    <attributes/>
    <GUI>
      <xloc>665</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>preview</name>
    <type>Dummy</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <attributes/>
    <GUI>
      <xloc>959</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```