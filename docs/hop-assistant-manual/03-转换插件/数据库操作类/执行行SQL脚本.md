# 执行行 SQL 脚本

## 功能概述


执行行 SQL 脚本（Execute Row SQL Script）转换为转换接收的每个输入行执行 SQL 脚本。要执行的 SQL 可作为管道字段传递或从文件读取。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 用于执行脚本的数据库连接 |
| SQL 字段 | 包含要执行 SQL 的字段 |
| 提交记录数 | 每次提交的记录数量 |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0025-table-compare-validation</name>
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
    <created_date>2023/03/07 11:23:18.711</created_date>
    <modified_user>-</modified_user>
    <modified_date>2023/03/07 11:23:18.711</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Data grid</from>
      <to>Table compare</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Table compare</from>
      <to>ERRORS</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Table compare</from>
      <to>Table results</to>
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
        <item>public</item>
        <item>reference_table</item>
        <item/>
        <item>compare_table</item>
        <item>id</item>
        <item/>
        <item>ID</item>
        <item>ref_value</item>
        <item>compare_value</item>
      </line>
    </data>
    <fields>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>ref_schema</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>ref_table</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>cmp_schema</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>cmp_table</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>id_fields</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>excl_fields</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>key_description</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>ref_value</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>compare_value</name>
        <type>String</type>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>ERRORS</name>
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
      <xloc>512</xloc>
      <yloc>176</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Table compare</name>
    <type>TableCompare</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <compare_connection>h2-db</compare_connection>
    <compare_schema_field>cmp_schema</compare_schema_field>
    <compare_table_field>cmp_table</compare_table_field>
    <exclude_fields_field>excl_fields</exclude_fields_field>
    <key_description_field>key_description</key_description_field>
    <key_fields_field>id_fields</key_fields_field>
    <nr_errors_field>nrErrors</nr_errors_field>
    <nr_errors_inner_join_field>nrErrorsInnerJoin</nr_errors_inner_join_field>
    <nr_errors_left_join_field>nrErrorsLeftJoin</nr_errors_left_join_field>
    <nr_errors_right_join_field>nrErrorsRightJoin</nr_errors_right_join_field>
    <nr_records_compare_field>nrRecordsCompareTable</nr_records_compare_field>
    <nr_records_reference_field>nrRecordsReferenceTable</nr_records_reference_field>
    <reference_connection>unit-test-db</reference_connection>
    <reference_schema_field>ref_schema</reference_schema_field>
    <reference_table_field>ref_table</reference_table_field>
    <value_compare_field>compare_value</value_compare_field>
    <value_reference_field>ref_value</value_reference_field>
    <attributes/>
    <GUI>
      <xloc>288</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Table results</name>
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
      <xloc>512</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
    <error>
      <source_transform>Table compare</source_transform>
      <target_transform>ERRORS</target_transform>
      <is_enabled>Y</is_enabled>
      <nr_valuename/>
      <descriptions_valuename/>
      <fields_valuename/>
      <codes_valuename/>
      <max_errors/>
      <max_pct_errors/>
      <min_pct_rows/>
    </error>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 该转换为每个输入行执行一条 SQL 语句，SQL 内容来自字段或文件。
- 适用于需要为每行执行不同 SQL 语句的动态场景。
