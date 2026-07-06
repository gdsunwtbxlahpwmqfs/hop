# Beam Kafka 生产（Beam Kafka Produce）

## 功能概述


Beam Kafka 生产转换使用 Beam 执行引擎向 Kafka 集群发布（写入）记录。该转换专为在 Beam 引擎上运行的管道设计。

## 局限性

Kafka 生产者的主要局限在于：当前仅支持以字符串（String）作为键，以字符串或 Avro 记录（Avro Record）作为值进行写入或生产。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ❌ 不支持 |
| Spark | ✅ 支持 |
| Flink | ✅ 支持 |
| Dataflow | ✅ 支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| 引导服务器（Bootstrap servers） | 以逗号分隔的"引导"Kafka 集群中 Kafka 代理（broker）主机列表。 |
| 主题（The topics） | 要发布到的主题。 |
| 用作键的字段（The field to use as key） | 记录的键。 |
| 用作消息的字段（The field to use as message） | 记录的消息内容。 |

### Avro 与模式注册表（Schema Registry）

向 Kafka 服务器发送 Avro 记录值时，Avro 值的模式不会发送到 Kafka，而是发送到模式注册表（schema registry），因此您需要准备一个可用的注册表。以下是在 Confluent Cloud Kafka 实例上使其正常工作所需的部分选项。由于软件栈中有多个部分需要身份验证，因此存在一些冗余。建议将这些选项放入环境配置文件的变量中。

| 选项 | 示例 |
|------|------|
| schema.registry.url | https://abcd-12345x.europe-west3.gcp.confluent.cloud |
| value.converter.schema.registry.url | https://abcd-12345x.europe-west3.gcp.confluent.cloud |
| auto.register.schemas | true |
| security.protocol | SASL_SSL |
| sasl.jaas.config | org.apache.kafka.common.security.plain.PlainLoginModule required username="CLUSTER_API_KEY" password="CLUSTER_API_SECRET"; |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0012-get-subfolder-names-validation</name>
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
    <created_date>2022/12/01 12:53:14.315</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/12/01 12:53:14.315</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>/tmp/0012-get-subfolder-names*.csv</from>
      <to>Verify</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>/tmp/0012-get-subfolder-names*.csv</name>
    <type>TextFileInput2</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <accept_filenames>N</accept_filenames>
    <passing_through_fields>N</passing_through_fields>
    <accept_field/>
    <accept_transform_name/>
    <separator>,</separator>
    <enclosure>"</enclosure>
    <enclosure_breaks>N</enclosure_breaks>
    <escapechar/>
    <header>N</header>
    <nr_headerlines>1</nr_headerlines>
    <footer>N</footer>
    <nr_footerlines>1</nr_footerlines>
    <line_wrapped>N</line_wrapped>
    <nr_wraps>1</nr_wraps>
    <layout_paged>N</layout_paged>
    <nr_lines_per_page>80</nr_lines_per_page>
    <nr_lines_doc_header>0</nr_lines_doc_header>
    <noempty>Y</noempty>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <rownumByFile>N</rownumByFile>
    <rownum_field/>
    <format>Unix</format>
    <encoding>UTF-8</encoding>
    <length>Characters</length>
    <add_to_result_filenames>Y</add_to_result_filenames>
    <file>
      <name>${java.io.tmpdir}/0012/</name>
      <filemask>get-subfolder-names.*\.csv$</filemask>
      <exclude_filemask/>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
      <type>CSV</type>
      <compression>None</compression>
    </file>
    <filters>
    </filters>
    <fields>
      <field>
        <name>id</name>
        <type>Integer</type>
        <format>#</format>
        <currency>$</currency>
        <decimal>.</decimal>
        <group>,</group>
        <nullif>-</nullif>
        <ifnull/>
        <position>-1</position>
        <length>15</length>
        <precision>0</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>shortFolder</name>
        <type>String</type>
        <format/>
        <currency>$</currency>
        <decimal>.</decimal>
        <group>,</group>
        <nullif>-</nullif>
        <ifnull/>
        <position>-1</position>
        <length>26</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <error_ignored>N</error_ignored>
    <skip_bad_files>N</skip_bad_files>
    <file_error_field/>
    <file_error_message_field/>
    <error_line_skipped>N</error_line_skipped>
    <error_count_field/>
    <error_fields_field/>
    <error_text_field/>
    <bad_line_files_destination_directory/>
    <bad_line_files_extension>warning</bad_line_files_extension>
    <error_line_files_destination_directory/>
    <error_line_files_extension>error</error_line_files_extension>
    <line_number_files_destination_directory/>
    <line_number_files_extension>line</line_number_files_extension>
    <date_format_lenient>Y</date_format_lenient>
    <date_format_locale>en_US</date_format_locale>
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
      <xloc>432</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```