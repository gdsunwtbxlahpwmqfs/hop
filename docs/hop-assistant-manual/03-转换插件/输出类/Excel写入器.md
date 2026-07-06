# Excel 写入器

## 功能概述


Excel 写入器（Excel Writer）转换将传入的行写入 Microsoft Excel（.xls、.xlsx）或 OpenDocument 电子表格（.ods）文件。
该转换支持三种输出格式：
- `.xls` — 传统的 Excel 二进制格式（Apache POI）
- `.xlsx` — 现代的 Excel Open XML 格式（Apache POI）
- `.ods` — OpenDocument 电子表格格式（LibreOffice Calc、Apache OpenOffice；通过 ODFDOM 写入）
`.xls` 和 `.xlsx` 后端共享相同的 POI 代码路径。`.ods` 后端是独立的实现，在 ODF 格式允许的范围内镜像相同的转换选项。
`.xls` 文件使用二进制格式，更适合简单内容；`.xlsx` 文件使用 Open XML 格式，与模板配合良好，因为它能更好地保留图表和杂项对象。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 输出文件 | 输出的 Excel 文件名 |
| 扩展名 | 输出格式（xls、xlsx 或 ods） |
| 模板文件 | 用作模板的 Excel 文件（可选） |
| 工作表 | 要写入的工作表名称 |
| 是否追加 | 是否追加到已有文件 |

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

- `.xlsx` 格式更适合使用模板，因为它能更好地保留图表和杂项对象。
- 支持基于模板写入，保留模板中的格式、图表等内容。
