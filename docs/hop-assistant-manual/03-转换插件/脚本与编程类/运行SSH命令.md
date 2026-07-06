# 运行SSH命令（Run SSH commands）

## 功能概述


运行SSH命令转换允许您通过安全外壳（ssh）TCP/IP 协议执行远程命令。
您可以在命令中将文本传递到 stdout 或 stderr，这些信息随后可以被转换捕获，并通过字段传递给后续转换。

## 主要选项

| 选项 | 说明 |
|------|------|
| 主机名/地址（Hostname/IP address） | SSH 服务器的地址 |
| 端口（Port） | SSH 服务器的端口，默认为 22 |
| 用户名（Username） | SSH 登录用户名 |
| 密码/密钥（Password/Key） | SSH 认证方式，可使用密码或私钥 |
| 命令（Commands） | 要在远程服务器上执行的命令 |
| 输出字段名（Output field name） | 存放命令输出结果的字段名称 |

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

- 支持通过 stdout/stderr 捕获命令输出，并通过字段传递给后续转换。
- 支持 Qi 数据治理平台 Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
