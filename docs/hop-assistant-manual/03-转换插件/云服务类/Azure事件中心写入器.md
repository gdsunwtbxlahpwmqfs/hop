# Azure 事件中心写入器（Azure Event Hubs Writer）

## 功能概述


Azure 事件中心写入器转换允许您将消息（事件）写入 Microsoft Azure 云平台上称为事件中心（Event Hubs）的流式服务总线。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❌ 不支持 |
| Flink | ❌ 不支持 |
| Dataflow | ❌ 不支持 |

## 主要选项

以下是使该转换正常工作需要填写的重要选项：

| 选项 | 说明 |
|------|------|
| Event Hubs 命名空间 | 您的 Event Hubs 命名空间名称。 |
| Event Hubs 实例名称 | Event Hub 的名称，即实例本身。 |
| SAS 策略密钥名称 | Azure 仪表板中 Event Hubs 命名空间"共享访问策略"部分中的策略名称。需要启用"发送"权限的策略。 |
| SAS 密钥连接字符串 | 可使用策略中标记为"连接字符串 – 主密钥"的值。 |
| 批大小（Batch size） | 每次调用 Azure 时以一批发送的消息（事件）数量。 |
| 消息字段（Message field） | 包含用作事件的消息的字段。可使用如 JSON 输出或"JavaScript"等转换来组装消息。 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0007-get-file-names</name>
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
    <created_date>2024/08/27 15:45:30.287</created_date>
    <modified_user>-</modified_user>
    <modified_date>2024/08/27 15:45:30.287</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Sparkle</from>
      <to>Get file names</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Get file names</from>
      <to>Select values</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Select values</from>
      <to>Dummy (do nothing)</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Dummy (do nothing)</name>
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
      <xloc>928</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Get file names</name>
    <type>GetFileNames</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <doNotFailIfNoFile>N</doNotFailIfNoFile>
    <dynamic_include_subfolders>N</dynamic_include_subfolders>
    <file>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
      <name>azit://mycontainer/copy/staging</name>
    </file>
    <filefield>N</filefield>
    <filter>
      <filterfiletype>all_files</filterfiletype>
    </filter>
    <isaddresult>Y</isaddresult>
    <limit>0</limit>
    <raiseAnExceptionIfNoFile>N</raiseAnExceptionIfNoFile>
    <rownum>N</rownum>
    <attributes/>
    <GUI>
      <xloc>528</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Select values</name>
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
        <name>short_filename</name>
      </field>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>736</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Sparkle</name>
    <type>RowGenerator</type>
    <description/>
    <distribute>Y</distribute>
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
      <xloc>272</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```