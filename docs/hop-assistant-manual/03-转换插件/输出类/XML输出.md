# XML 输出

## 功能概述


XML 输出（XML Output）转换允许你将来自任何源的行写入一个或多个 XML 文件。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 文件名 | 输出 XML 文件名 |
| 扩展名 | 输出文件扩展名 |
| 编码 | XML 文件编码 |
| 命名空间 | XML 命名空间定义 |
| 字段 | 输出 XML 元素字段定义 |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0101-create-ods-file</name>
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
    <created_date>2026/05/29 12:00:00.000</created_date>
    <modified_user>-</modified_user>
    <modified_date>2026/05/29 12:00:00.000</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Data grid</from>
      <to>Write test ODS file</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Data grid</name>
    <type>DataGrid</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <data>
      <line>
        <item>A</item>
      </line>
    </data>
    <fields>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>f1</name>
        <type>String</type>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>256</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Write test ODS file</name>
    <type>TypeExitExcelWriterTransform</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <add_to_result_filenames>Y</add_to_result_filenames>
    <appendEmpty>0</appendEmpty>
    <appendLines>N</appendLines>
    <appendOffset>0</appendOffset>
    <appendOmitHeader>N</appendOmitHeader>
    <fields>
      <field>
        <commentAuthorField/>
        <commentField/>
        <format/>
        <formula>N</formula>
        <hyperlinkField/>
        <name>f1</name>
        <styleCell/>
        <title>f1</title>
        <titleStyleCell/>
        <type>String</type>
      </field>
    </fields>
    <file>
      <SpecifyFormat>N</SpecifyFormat>
      <add_date>N</add_date>
      <add_time>N</add_time>
      <autosizecolums>N</autosizecolums>
      <createParentFolder>Y</createParentFolder>
      <date_time_format/>
      <do_not_open_newfile_init>Y</do_not_open_newfile_init>
      <extension>ods</extension>
      <filename_field/>
      <filename_in_field>N</filename_in_field>
      <if_file_exists>new</if_file_exists>
      <if_sheet_exists>new</if_sheet_exists>
      <name>${PROJECT_HOME}/files/excel/temp-ods-output</name>
      <password/>
      <protect_sheet>N</protect_sheet>
      <protected_by/>
      <sheetname>Sheet1</sheetname>
      <split>N</split>
      <splitevery>0</splitevery>
      <stream_data>N</stream_data>
    </file>
    <footer>N</footer>
    <forceFormulaRecalculation>N</forceFormulaRecalculation>
    <header>Y</header>
    <leaveExistingStylesUnchanged>N</leaveExistingStylesUnchanged>
    <makeSheetActive>Y</makeSheetActive>
    <rowWritingMethod>overwrite</rowWritingMethod>
    <startingCell>A1</startingCell>
    <template>
      <enabled>N</enabled>
      <filename>template.xls</filename>
      <hidden>N</hidden>
      <sheet_enabled>N</sheet_enabled>
      <sheetname/>
    </template>
    <attributes/>
    <GUI>
      <xloc>448</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 该转换支持 Qi 数据治理平台 Engine、Spark、Flink、Dataflow 等多种引擎。
- 适用于将数据导出为扁平的、重复行的 XML 文档。
- 如需更深层、自定义形状的 XML 结构，请使用 XML 输出（高级）转换。
