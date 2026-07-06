# Beam BigQuery 输出（BigQuery Output）

## 功能概述


BigQuery 输出转换将数据写入 Google Cloud BigQuery 表。在本地 Hop 引擎上，它使用 BigQuery Java 客户端的流式 `insertAll` API（每批最多 500 行）；在 Beam 引擎上，它使用 Beam 原生的 BigQuery 接收器（sink）。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ✅ 支持 |
| Flink | ✅ 支持 |
| Dataflow | ✅ 支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一。 |
| 项目 ID（Project ID） | Google Cloud Platform 项目。留空则使用 `GOOGLE_APPLICATION_CREDENTIALS` 中的应用默认项目。 |
| 数据集 ID（Data set ID） | BigQuery 数据集 ID，必须已存在。 |
| 表 ID（Table ID） | BigQuery 表 ID。 |
| 需要时创建表（Create table if needed） | 当表尚不存在时创建它。架构由输入行元数据派生（Hop 类型 → BigQuery `STANDARD_SQL` 类型）。默认：true。 |
| 清空表（Truncate table） | 写入前清空表。在 Hop 引擎上会执行 `TRUNCATE TABLE` DDL——无需成本，不占用 DML 配额，并保留架构/分区/聚簇。 |
| 表非空时失败（Fail if the table is not empty） | 当目标表已有数据行时拒绝运行。与"清空表"选项配合可实现幂等加载。 |

> **注意：** BigQuery 流式插入有其独立的配额（每次 `insertAll` 请求最多 10,000 行 / 10 MB，项目默认每秒 100,000 行）——与 DML 配额分开计算。数据行几乎立即可供 `SELECT` 查询，但在迁移到托管存储之前会在流式缓冲区中停留最多约 90 分钟；在此之前对这些行执行 DML 会被拒绝，但 DDL（包括由"清空表"选项执行的 `TRUNCATE TABLE`）则没有问题。


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