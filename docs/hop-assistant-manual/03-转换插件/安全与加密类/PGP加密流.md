# PGP 加密流（PGP encrypt stream）

## 功能概述


PGP 加密流转换使用 PGP（Pretty Good Privacy）对文本进行加密。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0084-string-to-timestamp-6306</name>
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
    <created_date>2026/01/05 15:49:38.689</created_date>
    <modified_user>-</modified_user>
    <modified_date>2026/01/05 15:49:38.689</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>input data</from>
      <to>str -> timestamp</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>str -> timestamp</from>
      <to>ok</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>str -> timestamp</from>
      <to>error</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>error</name>
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
      <xloc>416</xloc>
      <yloc>352</yloc>
    </GUI>
  </transform>
  <transform>
    <name>input data</name>
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
        <item>01-01-2000 00:00:00</item>
      </line>
      <line>
        <item>01-01-2000 00:01:01</item>
      </line>
      <line>
        <item>I am not a timestamp</item>
      </line>
      <line>
        <item>null</item>
      </line>
      <line>
        <item/>
      </line>
    </data>
    <fields>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>timestamp_string</name>
        <type>String</type>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>128</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>ok</name>
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
      <xloc>640</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>str -> timestamp</name>
    <type>SelectValues</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <fields>
      <meta>
        <conversion_mask>MM-dd-yyyy HH:mm:ss</conversion_mask>
        <date_format_lenient>N</date_format_lenient>
        <length>-2</length>
        <lenient_string_to_number>N</lenient_string_to_number>
        <name>timestamp_string</name>
        <precision>-2</precision>
        <rename>timestamp_string</rename>
        <roundingType>half_even</roundingType>
        <storage_type/>
        <type>Timestamp</type>
      </meta>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>416</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
    <error>
      <source_transform>str -> timestamp</source_transform>
      <target_transform>error</target_transform>
      <is_enabled>Y</is_enabled>
      <nr_valuename>nb_errors</nr_valuename>
      <descriptions_valuename>error_description</descriptions_valuename>
      <fields_valuename>error_field</fields_valuename>
      <codes_valuename>error_codes</codes_valuename>
      <max_errors/>
      <max_pct_errors/>
      <min_pct_rows/>
    </error>
  </transform_error_handling>
  <attributes/>
</pipeline>
```