# REST 客户端（REST Client）

## 功能概述


REST 客户端转换使您能够使用 RESTful 服务。表述性状态转移（REST）是一种关键的设计理念，它采用无状态的客户端-服务器架构，其中 Web 服务被视为资源，并可通过其 URL 进行标识。
REST 客户端转换可以使用预定义的 REST 连接（REST connection），也可以直接使用完整 URL。使用 REST 连接时，URL（硬编码或从字段接受）将被视为相对于 REST 连接中定义的基础 URL 的路径。在转换的"标头"选项卡中指定的标头值将覆盖 REST 连接中定义的同名标头。不使用 REST 连接时，需要指定完整 URL。
**示例：** REST 客户端转换返回一个"result"字段（可更改名称），该字段通常在下一个转换中使用。例如，可由 JSON 输入转换读取，以提取"字段"选项卡上指定的字段。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 常规（General）选项卡

常规选项卡用于输入访问资源的基本连接信息。

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 此转换在管道工作区中显示的名称。 |
| REST 连接（REST Connection） | （可选）用于基础 URL 和身份验证/授权标头名称及值的 REST 连接。 |
| URL | 指向资源的路径。 |
| 从字段接受 URL（Accept URL from field） | 指定资源的路径由某个字段定义。 |
| URL 字段名（URL field name） | 指定从哪个字段定义资源的路径。 |
| HTTP 方法（HTTP method） | 指示转换与资源交互的方式——选项包括 GET、PUT、DELETE、POST、HEAD 或 OPTIONS。 |


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