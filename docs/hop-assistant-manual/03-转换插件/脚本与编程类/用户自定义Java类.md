# 用户自定义Java类（User Defined Java Class）

## 功能概述


用户自定义Java类转换允许您输入用户自定义的 Java 类来驱动一个完整转换的功能。本质上，此转换允许您在转换中编写自己的插件。
此转换的目标并非让用户在转换内进行大规模的 Java 开发（Hop 有完整的插件系统来支持这部分工作），而是允许用户以尽可能少的代码定义方法和逻辑，并以最快的速度执行。
为此，本转换使用 [Janino](https://janino-compiler.github.io/janino/) 项目库在运行时以类的形式编译 Java 代码。

## 主要选项

| 选项 | 说明 |
|------|------|
| 类名（Class name） | 用户自定义 Java 类的名称 |
| 代码（Code） | Java 类的源代码，需实现必要的转换接口方法（如 processRow） |
| 字段（Fields） | 定义输入和输出字段 |

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

- 使用 Janino 编译器在运行时编译 Java 代码，执行速度优于脚本类转换。
- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
