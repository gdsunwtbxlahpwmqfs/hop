# JSON 归一化输入（JSON Normalize Input）

## 功能概述


JSON Normalize Input 转换读取 JSON（文件、URL 或字段），使用 JsonPath 选择记录数组，将嵌套对象展平为列，并为每个数组元素输出一行。
该转换在数据源（文件、URL 或来自前一转换的字段）方面与 JSON Input 类似，但专为从 JSON 文档中进行表格化提取而设计：一个记录 JsonPath 选择一个对象（或标量元素）数组，Hop 为每个数组元素输出一行；每个记录下的嵌套对象会使用可配置的分隔符（默认为点，如 `customer.name`）展平为列路径；在字段选项卡中，每行的路径是展平后的键（如 `customer.name`），而不是针对原始文件的独立 JsonPath 查询。
当您的 JSON 包含明确的实体列表（如 `orders`、`events` 或根级别数组），并且希望获得宽泛的、反规范化的行集而无需串联多个 JSON Input 转换时，请使用此转换。

## 主要选项

| 选项 | 说明 |
|------|------|
| 数据来源 | 指定 JSON 数据来源（文件、URL 或输入字段） |
| 记录 JsonPath | 选择一个对象或标量元素数组，每个数组元素输出一行 |
| 分隔符 | 配置嵌套对象展平为列路径的分隔符（默认为点 `.`） |
| 字段路径 | 指定字段选项卡中的展平键路径（如 `customer.name`） |


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