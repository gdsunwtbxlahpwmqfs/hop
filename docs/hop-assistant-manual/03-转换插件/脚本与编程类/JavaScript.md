# JavaScript

## 功能概述


JavaScript 转换提供了一个用户界面，用于构建可修改数据的 JavaScript 表达式。您在脚本区域中输入的代码会对每个输入行执行一次。
可以将管道中的可用字段作为变量在脚本中使用。

## 主要选项

| 选项 | 说明 |
|------|------|
| 脚本（Script） | JavaScript 脚本代码，对每个输入行执行一次 |
| 兼容模式（Compatibility mode） | 是否启用与旧版本的兼容模式 |
| 字段（Fields） | 脚本中使用的字段及其输出类型 |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0066-script-groovy-generate-rows</name>
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
    <created_date>2023/01/31 09:06:02.137</created_date>
    <modified_user>-</modified_user>
    <modified_date>2023/01/31 09:06:02.137</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Generate 10 rows with Groovy</from>
      <to>Verify</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Generate 10 rows with Groovy</name>
    <type>SuperScript</type>
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
        <length>-1</length>
        <name>id</name>
        <precision>-1</precision>
        <rename>id</rename>
        <replace>N</replace>
        <scriptResult>N</scriptResult>
        <type>Integer</type>
      </field>
      <field>
        <length>-1</length>
        <name>name</name>
        <precision>-1</precision>
        <rename>name</rename>
        <replace>N</replace>
        <scriptResult>N</scriptResult>
        <type>String</type>
      </field>
    </fields>
    <scriptLanguage>Groovy</scriptLanguage>
    <scripts>
      <script>
        <scriptBody>
def COUNT=10;

id = 100000L;
(1..COUNT).each {
 outputRow = RowDataUtil.allocateRowData(rowMeta.size());
 outputRow[0] = id;
 outputRow[1] = "Apache Hop "+id
 transform.putRow(outputRowMeta, outputRow);

 id++;
}

pipeline_status=SKIP_PIPELINE;</scriptBody>
        <scriptName>script1</scriptName>
        <scriptType>0</scriptType>
      </script>
    </scripts>
    <attributes/>
    <GUI>
      <xloc>144</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Verify</name>
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
      <xloc>400</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 提示：在创建字段之前，变量无法使用或测试。点击"获取变量（Get variables）"按钮可将脚本变量转换为字段。
- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
