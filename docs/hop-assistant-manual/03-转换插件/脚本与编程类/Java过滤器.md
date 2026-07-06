# Java过滤器（Java Filter）

## 功能概述


Java过滤器转换允许使用用户自定义的 Java 表达式对流进行过滤。
来自一个或多个转换的输入流，可以根据所编写表达式的评估结果，被重定向到两个不同的转换。
换言之，用户可以使用纯 Java 表达式执行 if 语句来过滤数据流：
```java
if (Condition)
{matching-transform}
else
{non-matching transform}
```

## 主要选项

| 选项 | 说明 |
|------|------|
| Java 表达式（Condition） | 用于过滤的 Java 条件表达式，返回布尔值 |
| 匹配的目标转换（Send true data to transform） | 满足条件的行将被发送到的目标转换 |
| 不匹配的目标转换（Send false data to transform） | 不满足条件的行将被发送到的目标转换 |

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

- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
