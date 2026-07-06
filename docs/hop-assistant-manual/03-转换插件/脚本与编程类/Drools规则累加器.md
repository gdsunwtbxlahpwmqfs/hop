# Drools规则累加器（Rules Accumulator）

## 功能概述


Drools规则累加器转换使用 [Drools](https://www.drools.org/) 规则引擎，收集传入的行并与规则集（rule set）进行匹配执行。
这对于确定某个问题的答案或以其他方式分析数据集非常有用。可参考 Drools 的[规则语言](https://docs.drools.org/7.68.0.Final/drools-docs/html_single/index.html#_droolslanguagereferencechapter)和 [Drools 文档](https://docs.drools.org/7.68.0.Final/drools-docs/html_single/index.html#_welcome)来使用此转换。
与规则执行器不同，规则累加器会收集所有传入行后再统一执行规则集，适用于需要对整个数据集进行汇总分析的场景。

## 主要选项

| 选项 | 说明 |
|------|------|
| 规则定义文件（Rule definition） | Drools 规则集的定义文件，可指定元数据或文件路径 |
| 规则文件名（Rule file） | 包含 Drools 规则的文件 |
| 输入字段（Input fields） | 传入规则引擎的字段 |
| 输出字段（Output fields） | 规则执行后产生的输出字段 |

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

- 使用 Drools 作为规则引擎实现。
- 与规则执行器不同，累加器会收集所有行后再统一执行规则集。
- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
