# 检查Web服务可用性（Check Webservice Availability）

## 功能概述


`Check webservice availability` 动作检查给定的 URL（例如 Web 服务 URL）是否有效、是否可以连接以及是否可以从中读取数据。
如果在给定的超时时间内连接并且可以读取数据，则返回 'true'，否则返回 'false'。
有关失败原因的更多信息可以在日志中作为错误日志操作找到。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| URL | 指定要验证的 URL。 |
| 连接超时（毫秒）（Connect timeout (ms)） | 连接超时时间（毫秒）。该值取决于此 URL 的服务质量和经验。 |
| 读取超时（毫秒）（Read timeout (ms)） | 连接后，工作流动作尝试读取数据。 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0009-build-documentation-check</name>
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
    <created_date>2025/12/11 10:30:25.056</created_date>
    <modified_user>-</modified_user>
    <modified_date>2025/12/11 10:30:25.056</modified_date>
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
      <fontname>Cantarell</fontname>
      <fontsize>11</fontsize>
      <height>32</height>
      <xloc>432</xloc>
      <yloc>208</yloc>
      <note>We expect one svg file per pipeline or workflow in this project:
We expect 2 extra md files for the index and metadata.</note>
      <width>32</width>
    </notepad>
  </notepads>
  <order>
    <hop>
      <from>get md files</from>
      <to>count md files</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>get svg files</from>
      <to>count svg files</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>get pipelines/workflows</from>
      <to>count hop files</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>count md files</from>
      <to>X</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>count svg files</from>
      <to>X</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>count hop files</from>
      <to>X</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>X</from>
      <to>Check Hop Doc files</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Check Hop Doc files</name>
    <type>ScriptValueMod</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <optimizationLevel>9</optimizationLevel>
    <jsScripts>
      <jsScript>
        <jsScript_type>0</jsScript_type>
        <jsScript_name>Script 1</jsScript_name>
        <jsScript_script>/*
    We expect one svg file per pipeline or workflow in this project:
    We expect 2 extra md files for the index and metadata.
*/

if (nrMd-nrHop != 2) {
  throw new Error("We expected to find "+(nrHop+2)+" .md files, but only found "+nrMd);
}

if (nrSvg-nrHop != 0) {
  throw new Error("We expected to find "+nrHop+" .svg files, but only found "+nrSvg);
}
</jsScript_script>
      </jsScript>
    </jsScripts>
    <fields>    </fields>
    <attributes/>
    <GUI>
      <xloc>512</xloc>
      <yloc>144</yloc>
    </GUI>
  </transform>
  <transform>
    <name>X</name>
    <type>JoinRows</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <cache_size>500</cache_size>
    <compare>
      <condition>
        <conditions>
</conditions>
        <function>=</function>
        <negated>N</negated>
        <operator>-</operator>
      </condition>
    </compare>
    <directory>%%java.io.tmpdir%%</directory>
    <prefix>out</prefix>
    <attributes/>
    <GUI>
      <xloc>368</xloc>
      <yloc>144</yloc>
    </GUI>
  </transform>
  <transform>
    <name>count hop files</name>
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
        <aggregate>nrHop</aggregate>
        <subject>filename</subject>
        <type>COUNT_ALL</type>
      </field>
    </fields>
    <give_back_row>Y</give_back_row>
    <group>
</group>
    <ignore_aggregate>N</ignore_aggregate>
    <prefix>grp</prefix>
    <attributes/>
    <GUI>
      <xloc>240</xloc>
      <yloc>224</yloc>
    </GUI>
  </transform>
  <transform>
    <name>count md files</name>
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
        <aggregate>nrMd</aggregate>
        <subject>filename</subject>
        <type>COUNT_ALL</type>
      </field>
    </fields>
    <give_back_row>Y</give_back_row>
    <group>
</group>
    <ignore_aggregate>N</ignore_aggregate>
    <prefix>grp</prefix>
    <attributes/>
    <GUI>
      <xloc>240</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform>
    <name>count svg files</name>
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
        <aggregate>nrSvg</aggregate>
        <subject>filename</subject>
        <type>COUNT_ALL</type>
      </field>
    </fields>
    <give_back_row>Y</give_back_row>
    <group>
</group>
    <ignore_aggregate>N</ignore_aggregate>
    <prefix>grp</prefix>
    <attributes/>
    <GUI>
      <xloc>240</xloc>
      <yloc>144</yloc>
    </GUI>
  </transform>
  <transform>
    <name>get md files</name>
    <type>GetFileNames</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <doNotFailIfNoFile>N</doNotFailIfNoFile>
    <dynamic_include_subfolders>N</dynamic_include_subfolders>
    <file>
      <file_required>Y</file_required>
      <filemask>.*\.md</filemask>
      <include_subfolders>Y</include_subfolders>
      <name>${java.io.tmpdir}/hop-doc/</name>
    </file>
    <filefield>N</filefield>
    <filter>
      <filterfiletype>all_files</filterfiletype>
    </filter>
    <isaddresult>Y</isaddresult>
    <limit>0</limit>
    <raiseAnExceptionIfNoFile>N</raiseAnExceptionIfNoFile>
    <rownum>N</rownum>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform>
    <name>get pipelines/workflows</name>
    <type>GetFileNames</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <doNotFailIfNoFile>N</doNotFailIfNoFile>
    <dynamic_include_subfolders>N</dynamic_include_subfolders>
    <file>
      <file_required>Y</file_required>
      <filemask>.*\.hpl</filemask>
      <include_subfolders>Y</include_subfolders>
      <name>${PROJECT_HOME}/</name>
    </file>
    <file>
      <file_required>Y</file_required>
      <filemask>.*\.hwf</filemask>
      <include_subfolders>Y</include_subfolders>
      <name>${PROJECT_HOME}/</name>
    </file>
    <file>
      <file_required>N</file_required>
      <include_subfolders>N</include_subfolders>
    </file>
    <filefield>N</filefield>
    <filter>
      <filterfiletype>all_files</filterfiletype>
    </filter>
    <isaddresult>Y</isaddresult>
    <limit>0</limit>
    <raiseAnExceptionIfNoFile>N</raiseAnExceptionIfNoFile>
    <rownum>N</rownum>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>224</yloc>
    </GUI>
  </transform>
  <transform>
    <name>get svg files</name>
    <type>GetFileNames</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <doNotFailIfNoFile>N</doNotFailIfNoFile>
    <dynamic_include_subfolders>N</dynamic_include_subfolders>
    <file>
      <file_required>Y</file_required>
      <filemask>.*\.svg</filemask>
      <include_subfolders>Y</include_subfolders>
      <name>${java.io.tmpdir}/hop-doc/</name>
    </file>
    <filefield>N</filefield>
    <filter>
      <filterfiletype>all_files</filterfiletype>
    </filter>
    <isaddresult>Y</isaddresult>
    <limit>0</limit>
    <raiseAnExceptionIfNoFile>N</raiseAnExceptionIfNoFile>
    <rownum>N</rownum>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>144</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```