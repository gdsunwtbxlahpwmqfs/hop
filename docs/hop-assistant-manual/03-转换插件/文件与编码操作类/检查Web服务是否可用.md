# 检查Web服务是否可用（Check if webservice is available）

## 功能概述


检查Web服务是否可用转换用于检查数据流中的 Web 服务 URL 是否有效、是否可以连接以及是否可以读取。
如果在给定时间内成功连接并能够读取数据，它将返回布尔值 `true`，否则返回 `false`。启用调试日志（debug logging）时，可以在日志中找到失败原因的更多信息。

## 主要选项

| 选项 | 说明 |
|------|------|
| URL 字段（URL field） | 包含要检查的 Web 服务 URL 的输入字段 |
| 结果字段名（Result fieldname） | 输出布尔结果（true/false）的字段名称 |
| 连接超时（Connect timeout） | 连接超时时间（毫秒） |
| 读取超时（Read timeout） | 读取超时时间（毫秒） |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0056-result-files-add</name>
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
    <created_date>2022/11/28 13:01:39.434</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/11/28 13:01:39.434</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Get file names</from>
      <to>Set files in result</to>
      <enabled>Y</enabled>
    </hop>
  </order>
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
      <filemask>sample-file..txt</filemask>
      <include_subfolders>N</include_subfolders>
      <name>${PROJECT_HOME}/files/</name>
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
      <xloc>160</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Set files in result</name>
    <type>FilesToResult</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <filename_field>filename</filename_field>
    <file_type>GENERAL</file_type>
    <attributes/>
    <GUI>
      <xloc>368</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
- 适用于在调用 Web 服务前验证其可用性，避免因服务不可用导致管道失败。
