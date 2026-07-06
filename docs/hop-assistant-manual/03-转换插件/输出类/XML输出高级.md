# XML 输出（高级）

## 功能概述


XML 输出（高级）（XML Output (Advanced)）转换使用用户定义的分层树从输入行构建 XML。你可以*写入文件*、*将文档作为字符串字段附加*（供后续转换使用），或*两者兼做*。文件相关选项仅在写入文件时适用。
XML 树是元素、属性和文档片段节点的递归结构。树中必须恰好有一个元素被标记为行循环（row-loop）：每个输入行生成该元素及其完整子树的一个实例。可选择将循环的祖先标记为分组依据（group-by）：共享相同分组键的连续输入行在单个分组元素实例下输出。
该转换是对更简单的 `XML 输出` 转换的补充。对于扁平的重复行文档使用 `XML 输出`；当需要更深层的自定义 XML 结构（嵌套在分组内的循环、任意级别的属性、文档片段、命名空间、模式生成）时使用 `XML 输出（高级）`。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| XML 树 | 用户定义的分层 XML 节点结构 |
| 行循环元素 | 标记为每个输入行生成实例的元素 |
| 分组元素 | 标记为按分组键聚合的祖先元素 |
| 输出文件 | 输出 XML 文件（可选） |
| 作为字段输出 | 是否将 XML 文档作为字符串字段输出 |

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

- 该转换仅支持 Qi 数据治理平台 Engine，不支持 Spark、Flink、Dataflow。
- 使用前应按分组键对输入数据进行排序，以获得正确的分组效果。
- 支持生成 XSD 模式文件。
