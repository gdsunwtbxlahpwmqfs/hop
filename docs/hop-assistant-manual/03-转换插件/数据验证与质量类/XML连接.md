# XML 连接（XML Join）

## 功能概述


XML Join 转换将一个数据流中的 XML 标签添加到第二个数据流的主 XML 结构中。
目标数据流必须只有一行，因为它代表一个 XML 文档。另一个数据流可以包含多行，连接操作会将所有行的标签添加到目标文档中。连接后只产生一行输出，该行包含目标转换的字段以及连接的结果字段。

## 主要选项

| 选项 | 说明 |
|------|------|
| 目标 XML 字段 | 指定主 XML 结构所在的目标字段（代表一个 XML 文档，仅一行） |
| 源 XML 字段 | 指定要添加的 XML 标签来源字段（可包含多行） |
| 目标 XPath | 指定源标签插入到目标 XML 文档中的位置 |
| 结果字段 | 指定存放连接后生成的 XML 结果的字段名称 |
| 连接比较字段 | 配置用于匹配两个数据流的键字段 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>merge_empty_stream</name>
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
    <created_date>2021/11/11 16:31:19.403</created_date>
    <modified_user>-</modified_user>
    <modified_date>2021/11/11 16:31:19.403</modified_date>
    <key_for_session_key>H4sIAAAAAAAAAAMAAAAAAAAAAAA=</key_for_session_key>
    <is_key_private>N</is_key_private>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>A</from>
      <to>new_field_A</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Switch / case</from>
      <to>A</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Switch / case</from>
      <to>Default</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>new_field_A</from>
      <to>Stream Schema Merge</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>new_field_B</from>
      <to>Stream Schema Merge</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Switch / case</from>
      <to>B</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>B</from>
      <to>new_field_B</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Default</from>
      <to>Stream Schema Merge</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Data grid</from>
      <to>Switch / case</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>A</name>
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
      <xloc>352</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform>
    <name>B</name>
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
      <xloc>352</xloc>
      <yloc>304</yloc>
    </GUI>
  </transform>
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
    <fields>
      <field>
        <set_empty_string>N</set_empty_string>
        <length>-1</length>
        <name>first</name>
        <precision>-1</precision>
        <type>String</type>
      </field>
      <field>
        <set_empty_string>N</set_empty_string>
        <length>-1</length>
        <name>second</name>
        <precision>-1</precision>
        <type>Integer</type>
      </field>
      <field>
        <set_empty_string>N</set_empty_string>
        <length>-1</length>
        <name>third</name>
        <precision>-1</precision>
        <type>Number</type>
      </field>
    </fields>
    <data>
      <line>
        <item>A</item>
        <item>1</item>
        <item>111</item>
      </line>
      <line>
        <item>C</item>
        <item>2</item>
        <item>123</item>
      </line>
      <line>
        <item>C</item>
        <item>3</item>
        <item>321</item>
      </line>
    </data>
    <attributes/>
    <GUI>
      <xloc>96</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Default</name>
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
      <xloc>352</xloc>
      <yloc>416</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Stream Schema Merge</name>
    <type>StreamSchema</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <transforms>
      <transform>
        <name>new_field_A</name>
      </transform>
      <transform>
        <name>new_field_B</name>
      </transform>
      <transform>
        <name>Default</name>
      </transform>
    </transforms>
    <attributes/>
    <GUI>
      <xloc>656</xloc>
      <yloc>416</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Switch / case</name>
    <type>SwitchCase</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <cases>
      <case>
        <target_transform>A</target_transform>
        <value>A</value>
      </case>
      <case>
        <target_transform>B</target_transform>
        <value>B</value>
      </case>
    </cases>
    <case_value_type>String</case_value_type>
    <default_target_transform>Default</default_target_transform>
    <fieldname>first</fieldname>
    <use_contains>N</use_contains>
    <attributes/>
    <GUI>
      <xloc>208</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform>
    <name>new_field_A</name>
    <type>Constant</type>
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
        <set_empty_string>N</set_empty_string>
        <length>-1</length>
        <name>new_field_A</name>
        <precision>-1</precision>
        <type>Number</type>
        <nullif>666</nullif>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>464</xloc>
      <yloc>192</yloc>
    </GUI>
  </transform>
  <transform>
    <name>new_field_B</name>
    <type>Constant</type>
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
        <set_empty_string>N</set_empty_string>
        <length>-1</length>
        <name>new_field_B</name>
        <precision>-1</precision>
        <type>String</type>
        <nullif>BBB</nullif>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>464</xloc>
      <yloc>304</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```