# JSON 输入

## 功能概述


JSON 输入转换使用 JSONPath 表达式从 JSON 结构、文件或传入字段中提取数据并输出行。
> **提示**：当每一列应来自其*自身的 JsonPath*（或你不将列表"展开"为多行）时，使用 JSON 输入。对于*记录数组*（每个元素成为一行且*嵌套对象被扁平化*为带点号列名）的场景，请改用 JSON Normalize Input。

## 使用技巧

- JSONPath 表达式可使用点表示法或方括号表示法。
- 如有需要，你可以在不选择"字段"选项卡中任何字段的情况下，将 REST 请求结果字段传递下去。

## 示例

- 例如，将 REST 客户端连接到 JSON 输入转换，将 JSON 读入数据行。
- 要读取嵌套的 JSON 元素，可使用一系列 JSON 输入转换：第一个 JSON 输入转换将嵌套元素读取为字符串字段；在第二个 JSON 输入转换中，使用"源来自先前转换"选项选择第一个转换中使用的字符串字段，以读取嵌套元素中的信息。

可在 Samples 项目中查看示例：`json-input-nested-elements.hpl`、`json-input-basic.hpl`。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 源来自先前转换的字段 | 是否从先前转换的字段获取 JSON 源 |
| 源是文件名 | 源是否为文件名（否则为目录） |
| JSONPath 表达式（字段） | 使用 JSONPath 提取数据的字段定义 |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>rest-canada-public-holidays</name>
    <name_sync_with_filename>Y</name_sync_with_filename>
    <description/>
    <extended_description/>
    <pipeline_version/>
    <pipeline_type>Normal</pipeline_type>
    <pipeline_status>0</pipeline_status>
    <parameters>
      <parameter>
        <name>PRM_COUNTRY</name>
        <default_value>BE</default_value>
        <description/>
      </parameter>
    </parameters>
    <capture_transform_performance>N</capture_transform_performance>
    <transform_performance_capturing_delay>1000</transform_performance_capturing_delay>
    <transform_performance_capturing_size_limit>100</transform_performance_capturing_size_limit>
    <created_user>-</created_user>
    <created_date>2023/09/10 10:26:36.788</created_date>
    <modified_user>-</modified_user>
    <modified_date>2023/09/10 10:26:36.788</modified_date>
  </info>
  <notepads>
    <notepad>
      <backgroundcolorblue>251</backgroundcolorblue>
      <backgroundcolorgreen>232</backgroundcolorgreen>
      <backgroundcolorred>201</backgroundcolorred>
      <bordercolorblue>90</bordercolorblue>
      <bordercolorgreen>58</bordercolorgreen>
      <bordercolorred>14</bordercolorred>
      <fontbold>N</fontbold>
      <fontcolorblue>90</fontcolorblue>
      <fontcolorgreen>58</fontcolorgreen>
      <fontcolorred>14</fontcolorred>
      <fontitalic>N</fontitalic>
      <fontname>Noto Sans</fontname>
      <fontsize>10</fontsize>
      <height>78</height>
      <xloc>112</xloc>
      <yloc>48</yloc>
      <note>This sample pipeline reads the public holidays for ${PRM_COUNTRY} in 2023 and parses the result. 

The default value for ${PRM_COUNTRY} is set to 'BE' (Belgium). 
Check your 2-character country code to run this sample pipeline for your country: </note>
      <width>556</width>
    </notepad>
  </notepads>
  <order>
    <hop>
      <from>generate 1 row</from>
      <to>get ${PRM_COUNTRY} public holidays</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>get ${PRM_COUNTRY} public holidays</from>
      <to>read fields</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>read fields</from>
      <to>read countries</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>read countries</from>
      <to>read types</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>read types</from>
      <to>clean</to>
      <enabled>Y</enabled>
    </hop>
  </order>
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
        <name>date</name>
      </field>
      <field>
        <name>local_name</name>
      </field>
      <field>
        <name>name</name>
      </field>
      <field>
        <name>country_code</name>
      </field>
      <field>
        <name>fixed</name>
      </field>
      <field>
        <name>global</name>
      </field>
      <field>
        <name>launch_year</name>
      </field>
      <field>
        <name>county</name>
      </field>
      <field>
        <name>type</name>
      </field>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>1263</xloc>
      <yloc>160</yloc>
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
</fields>
    <interval_in_ms>5000</interval_in_ms>
    <last_time_field>FiveSecondsAgo</last_time_field>
    <limit>1</limit>
    <never_ending>N</never_ending>
    <row_time_field>now</row_time_field>
    <attributes/>
    <GUI>
      <xloc>128</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>get ${PRM_COUNTRY} public holidays</name>
    <type>Rest</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <applicationType>TEXT PLAIN</applicationType>
    <method>GET</method>
    <url>https://date.nager.at/api/v3/publicholidays/2023/${PRM_COUNTRY}</url>
    <urlInField>N</urlInField>
    <dynamicMethod>N</dynamicMethod>
    <methodFieldName/>
    <urlField/>
    <bodyField/>
    <httpLogin/>
    <httpPassword>Encrypted </httpPassword>
    <proxyHost/>
    <proxyPort/>
    <preemptive>N</preemptive>
    <trustStoreFile/>
    <trustStorePassword>Encrypted </trustStorePassword>
    <ignoreSsl>N</ignoreSsl>
    <headers>
      </headers>
    <parameters>
      </parameters>
    <matrixParameters>
      </matrixParameters>
    <result>
      <name>result</name>
      <code/>
      <response_time/>
      <response_header/>
    </result>
    <attributes/>
    <GUI>
      <xloc>355</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>read countries</name>
    <type>JsonInput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <addresultfile>N</addresultfile>
    <readurl>N</readurl>
    <removeSourceField>N</removeSourceField>
    <IsIgnoreEmptyFile>N</IsIgnoreEmptyFile>
    <doNotFailIfNoFile>Y</doNotFailIfNoFile>
    <ignoreMissingPath>Y</ignoreMissingPath>
    <defaultPathLeafToNull>Y</defaultPathLeafToNull>
    <rownum_field/>
    <file>
      <name/>
      <filemask/>
      <exclude_filemask/>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
    </file>
    <fields>
      <field>
        <name>county</name>
        <path>.*</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <IsInFields>Y</IsInFields>
    <IsAFile>N</IsAFile>
    <valueField>counties</valueField>
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
      <xloc>809</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>read fields</name>
    <type>JsonInput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <addresultfile>N</addresultfile>
    <readurl>N</readurl>
    <removeSourceField>N</removeSourceField>
    <IsIgnoreEmptyFile>N</IsIgnoreEmptyFile>
    <doNotFailIfNoFile>Y</doNotFailIfNoFile>
    <ignoreMissingPath>Y</ignoreMissingPath>
    <defaultPathLeafToNull>Y</defaultPathLeafToNull>
    <rownum_field/>
    <file>
      <name/>
      <filemask/>
      <exclude_filemask/>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
    </file>
    <fields>
      <field>
        <name>date</name>
        <path>$.*.date</path>
        <type>Date</type>
        <format>YYYY-mm-dd</format>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>local_name</name>
        <path>$.*.localName</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>name</name>
        <path>$.*.name</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>country_code</name>
        <path>$.*.countryCode</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>fixed</name>
        <path>$.*.fixed</path>
        <type>Boolean</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>global</name>
        <path>$.*.global</path>
        <type>Boolean</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>counties</name>
        <path>$.*.counties</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>launch_year</name>
        <path>$.*.launchYear</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
      <field>
        <name>types</name>
        <path>$.*.types</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <IsInFields>Y</IsInFields>
    <IsAFile>N</IsAFile>
    <valueField>result</valueField>
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
      <xloc>582</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>read types</name>
    <type>JsonInput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <addresultfile>N</addresultfile>
    <readurl>N</readurl>
    <removeSourceField>N</removeSourceField>
    <IsIgnoreEmptyFile>N</IsIgnoreEmptyFile>
    <doNotFailIfNoFile>Y</doNotFailIfNoFile>
    <ignoreMissingPath>Y</ignoreMissingPath>
    <defaultPathLeafToNull>Y</defaultPathLeafToNull>
    <rownum_field/>
    <file>
      <name/>
      <filemask/>
      <exclude_filemask/>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
    </file>
    <fields>
      <field>
        <name>type</name>
        <path>$.*</path>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <IsInFields>Y</IsInFields>
    <IsAFile>N</IsAFile>
    <valueField>types</valueField>
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
      <xloc>1036</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 当需要将数组展开为多行时，应考虑使用 JSON Normalize Input 而非 JSON 输入。
- 嵌套 JSON 可通过多个 JSON 输入转换串联来逐层解析。
