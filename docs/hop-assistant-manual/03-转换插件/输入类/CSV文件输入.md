# CSV 文件输入

## 功能概述


CSV 文件输入转换用于从带分隔符的文件中读取数据。你可以选择使用"架构定义（Schema Definition）"，也可以手动定义所需字段的布局。
`ignore manual fields`（忽略手动字段）选项会忽略在转换字段布局中手动定义的所有字段，仅使用架构定义中指定的布局。
虽然名称为 "CSV"，但实际上你可以定义任意分隔符，例如管道符（|）、制表符和分号，并不局限于逗号。内部处理机制使该转换能够快速处理数据。该转换的选项是"文本文件输入"转换的子集。
与通用的"文本文件输入"转换相比，该转换的整体选项更少，但具有以下优势：
- **NIO（原生 I/O）**：使用原生系统调用读取文件，性能更快，但目前仅限于本地文件，不支持 VFS。
- **并行运行**：若将该转换配置为多副本或集群模式运行，并启用并行运行，每个副本将读取单个文件的独立数据块，从而将文件读取工作分配到多个线程甚至集群中的多个从节点。
- **惰性转换（Lazy conversion）**：如果从文件中读取大量字段，且这些字段大多不会被处理，只是经过管道最终写入其他文本文件或数据库，惰性转换可以避免 Hop 对这些字段执行不必要的对象转换工作（如转换为字符串、日期或数字对象）。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 文件名（或文件名字段） | 指定要读取的 CSV 文件名；或选择包含文件名的字段 |
| 分隔符（Delimiter） | 目标文件中使用的分隔符字符。特殊字符可用 `$[value]` 格式设置，例如 `$[01]` |
| 封闭符（Enclosure） | 目标文件中使用的封闭符字符 |
| NIO 缓冲区大小 | 读取缓冲区的大小，表示每次从磁盘读取的字节数 |
| 惰性转换（Lazy conversion） | 惰性转换算法尽量避免不必要的数据类型转换，可显著提升性能 |
| 是否包含表头行 | 若目标文件第一行包含列名的表头行，则启用此项 |
| 将文件名加入结果 | 将读取的 CSV 文件名加入管道结果 |
| 行号字段名（可选） | 输出中包含行号的 Integer 字段名 |
| 是否并行运行 | 若有多个转换副本运行，且希望每个副本读取 CSV 文件的独立部分，则勾选此项 |
| 文件编码 | 指定所读文件的编码 |
| 架构定义（Schema Definition） | 要引用的架构定义名称 |
| 字段表 | 包含从目标文件读取的字段有序列表 |

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

- 并行读取 CSV 文件仅支持字段中不包含换行符或回车符的文件。
- 若误对没有列名的文件勾选了"包含表头行"，Hop 会将特定位置的列值设为列名；若该值为空，则列名设为 `EmptyField_<n>`（n 为列的位置序号）。
- 建议在文件分析后检查 Hop 猜测的数据类型和列说明，因为基于样本数据集的猜测可能不准确。
