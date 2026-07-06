# Avro 文件输入（Avro File Input）

## 功能概述


Avro File Input 转换用于从一个或多个文件中读取 Avro 记录。
每条记录都封装在一个 Avro 字段中，每个值都有其对应的模式（Schema）和记录。该转换是读取 Avro 格式数据的基础工具，适用于从 Avro 数据文件中提取结构化数据并转换为 Hop 数据流的场景。

## 主要选项

| 选项 | 说明 |
|------|------|
| 文件名 | 指定要读取的一个或多个 Avro 文件路径 |
| Avro 模式 | 指定 Avro 数据的模式（Schema），用于解析记录结构 |
| 字段映射 | 配置 Avro 记录字段到 Hop 输出字段的映射 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>avro-file-input</name>
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
    <created_date>2023/02/06 19:48:25.353</created_date>
    <modified_user>-</modified_user>
    <modified_date>2023/02/06 19:48:25.353</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>generate 1 row</from>
      <to>Avro File Input</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Avro File Input</from>
      <to>Avro Decode</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Avro Decode</from>
      <to>clean</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Avro Decode</name>
    <type>AvroDecode</type>
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
        <source_avro_type>String</source_avro_type>
        <source_field>birthdate</source_field>
        <target_field_name>birthdate</target_field_name>
        <target_format>M/d/yyyy</target_format>
        <target_type>Date</target_type>
      </field>
      <field>
        <source_avro_type>Union</source_avro_type>
        <source_field>cc</source_field>
        <target_field_name>cc</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>comments</source_field>
        <target_field_name>comments</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>country</source_field>
        <target_field_name>country</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>email</source_field>
        <target_field_name>email</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>first_name</source_field>
        <target_field_name>first_name</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>gender</source_field>
        <target_field_name>gender</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>Long</source_avro_type>
        <source_field>id</source_field>
        <target_field_name>id</target_field_name>
        <target_type>Integer</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>ip_address</source_field>
        <target_field_name>ip_address</target_field_name>
        <target_type>Internet Address</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>last_name</source_field>
        <target_field_name>last_name</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>registration_dttm</source_field>
        <target_field_name>registration_dttm</target_field_name>
        <target_format>yyyy-MM-dd'T'HH:mm:ssXXX</target_format>
        <target_type>Date</target_type>
      </field>
      <field>
        <source_avro_type>Union</source_avro_type>
        <source_field>salary</source_field>
        <target_field_name>salary</target_field_name>
        <target_type>String</target_type>
      </field>
      <field>
        <source_avro_type>String</source_avro_type>
        <source_field>title</source_field>
        <target_field_name>title</target_field_name>
        <target_type>String</target_type>
      </field>
    </fields>
    <ignore_missing>Y</ignore_missing>
    <remove_source_field>Y</remove_source_field>
    <source_field>avro</source_field>
    <attributes/>
    <GUI>
      <xloc>480</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Avro File Input</name>
    <type>AvroFileInput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <data_filename_field>avro_file_url</data_filename_field>
    <output_field>avro</output_field>
    <attributes/>
    <GUI>
      <xloc>304</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>generate 1 row</name>
    <type>RowGenerator</type>
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
        <name>avro_file_url</name>
        <nullif>https://github.com/apache/hop/raw/main/integration-tests/transforms/files/userdata1.avro</nullif>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <type>String</type>
      </field>
    </fields>
    <interval_in_ms>5000</interval_in_ms>
    <last_time_field>FiveSecondsAgo</last_time_field>
    <limit>10</limit>
    <never_ending>N</never_ending>
    <row_time_field>now</row_time_field>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>clean</name>
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
      <field>
        <name>avro_file_url</name>
        <rename/>
      </field>
      <field>
        <name>cc</name>
        <rename/>
      </field>
      <field>
        <name>comments</name>
        <rename/>
      </field>
      <field>
        <name>country</name>
        <rename/>
      </field>
      <field>
        <name>email</name>
        <rename/>
      </field>
      <field>
        <name>first_name</name>
        <rename/>
      </field>
      <field>
        <name>gender</name>
        <rename/>
      </field>
      <field>
        <name>id</name>
        <rename/>
      </field>
      <field>
        <name>ip_address</name>
        <rename/>
      </field>
      <field>
        <name>last_name</name>
        <rename/>
      </field>
      <field>
        <name>registration_dttm</name>
        <rename/>
      </field>
      <field>
        <name>salary</name>
        <rename/>
      </field>
      <field>
        <name>title</name>
        <rename/>
      </field>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>656</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```