# 发送SNMP-Trap（Send Snmp Trap）

## 功能概述


`Send SNMP trap` 动作向远程支持 SNMP 的设备发送警报消息。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| 服务器名称/IP 地址（Server name / IP address） | 服务器主机。 |
| 服务器端口（Server port） | 服务器端口。 |
| OID | 对象标识符。 |
| 测试连接（Test connection） | 可用于测试连接。 |
| 目标类型（Target type） | 目标类型：社区（Community）或用户（User）。 |
| 社区字符串（Community string） | 允许访问路由器或其他设备统计信息的用户 ID 或密码。 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>001-check-log</name>
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
    <created_date>2023/07/11 11:55:12.982</created_date>
    <modified_user>-</modified_user>
    <modified_date>2023/07/11 11:55:12.982</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Read snmp log</from>
      <to>combine data lines</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>combine data lines</from>
      <to>check for message</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>check for message</from>
      <to>Message not found</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Message not found</name>
    <type>Abort</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <abort_option>ABORT_WITH_ERROR</abort_option>
    <always_log_rows>Y</always_log_rows>
    <row_threshold>0</row_threshold>
    <attributes/>
    <GUI>
      <xloc>784</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Read snmp log</name>
    <type>TextFileInput2</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <accept_filenames>N</accept_filenames>
    <passing_through_fields>N</passing_through_fields>
    <accept_field/>
    <accept_transform_name/>
    <separator>;</separator>
    <enclosure>"</enclosure>
    <enclosure_breaks>N</enclosure_breaks>
    <escapechar/>
    <header>N</header>
    <nr_headerlines>1</nr_headerlines>
    <footer>N</footer>
    <nr_footerlines>1</nr_footerlines>
    <line_wrapped>N</line_wrapped>
    <nr_wraps>1</nr_wraps>
    <layout_paged>N</layout_paged>
    <nr_lines_per_page>80</nr_lines_per_page>
    <nr_lines_doc_header>0</nr_lines_doc_header>
    <noempty>Y</noempty>
    <include>N</include>
    <include_field/>
    <rownum>N</rownum>
    <rownumByFile>N</rownumByFile>
    <rownum_field/>
    <format>mixed</format>
    <encoding/>
    <length>Characters</length>
    <add_to_result_filenames>Y</add_to_result_filenames>
    <file>
      <name>/tmp/snmptraps/snmptraps.log</name>
      <filemask/>
      <exclude_filemask/>
      <file_required>Y</file_required>
      <include_subfolders>N</include_subfolders>
      <type>CSV</type>
      <compression>None</compression>
    </file>
    <filters>
    </filters>
    <fields>
      <field>
        <name>data</name>
        <type>String</type>
        <format/>
        <currency/>
        <decimal/>
        <group/>
        <nullif/>
        <ifnull/>
        <position>-1</position>
        <length>-1</length>
        <precision>-1</precision>
        <trim_type>none</trim_type>
        <repeat>N</repeat>
      </field>
    </fields>
    <limit>0</limit>
    <error_ignored>N</error_ignored>
    <skip_bad_files>N</skip_bad_files>
    <file_error_field/>
    <file_error_message_field/>
    <error_line_skipped>N</error_line_skipped>
    <error_count_field/>
    <error_fields_field/>
    <error_text_field/>
    <schema_definition/>
    <bad_line_files_destination_directory/>
    <bad_line_files_extension>warning</bad_line_files_extension>
    <error_line_files_destination_directory/>
    <error_line_files_extension>error</error_line_files_extension>
    <line_number_files_destination_directory/>
    <line_number_files_extension>line</line_number_files_extension>
    <date_format_lenient>Y</date_format_lenient>
    <date_format_locale>en_US</date_format_locale>
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
      <xloc>96</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>check for message</name>
    <type>FilterRows</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <compare>
      <condition>
        <conditions>
</conditions>
        <function>CONTAINS</function>
        <leftvalue>combined_data</leftvalue>
        <negated>Y</negated>
        <operator>-</operator>
        <value>
          <isnull>N</isnull>
          <length>-1</length>
          <name>constant</name>
          <precision>-1</precision>
          <text>SNMPv2-TM::snmpUDPDomain.0 = "Trap received"</text>
          <type>String</type>
        </value>
      </condition>
    </compare>
    <attributes/>
    <GUI>
      <xloc>560</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>combine data lines</name>
    <type>GroupBy</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <add_linenr>N</add_linenr>
    <all_rows>N</all_rows>
    <directory>${java.io.tmpdir}</directory>
    <fields>
      <field>
        <aggregate>combined_data</aggregate>
        <subject>data</subject>
        <type>CONCAT_COMMA</type>
      </field>
    </fields>
    <give_back_row>N</give_back_row>
    <group>
</group>
    <ignore_aggregate>N</ignore_aggregate>
    <prefix>grp</prefix>
    <attributes/>
    <GUI>
      <xloc>352</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```