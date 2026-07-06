# Neo4j 生成 CSV（Neo4j Generate CSVs）

## 功能概述


Neo4j Generate CSV 转换将节点和关系文件写入 import 文件夹，供 Neo4j 的 neo4j-import 工具使用。
该转换生成符合 Neo4j 导入工具格式要求的 CSV 文件，是进行大规模离线数据导入 Neo4j 图数据库的前置步骤。生成的文件可直接被 `neo4j-admin import` 命令使用。

## 主要选项

| 选项 | 说明 |
|------|------|
| 输出目录 | 指定生成的 CSV 文件输出路径（通常为 Neo4j 的 import 文件夹） |
| 节点文件 | 配置节点数据 CSV 文件的生成规则及字段映射 |
| 关系文件 | 配置关系数据 CSV 文件的生成规则及字段映射 |
| 文件格式 | 指定 CSV 文件的分隔符、引用符等格式选项 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>run-all-tests</name>
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
    <created_date>2022/05/25 09:42:15.952</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/05/25 09:42:15.952</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Get main workflows</from>
      <to>run-main-workflow.hwf</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>run-main-workflow.hwf</from>
      <to>Error?</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Error?</from>
      <to>Error detected</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Error detected</name>
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
    <message>One or more errors were detected in the Neo4j IT</message>
    <row_threshold>0</row_threshold>
    <attributes/>
    <GUI>
      <xloc>656</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Error?</name>
    <type>FilterRows</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <send_true_to/>
    <send_false_to/>
    <compare>
      <condition>
        <negated>N</negated>
        <leftvalue>ExecutionResult</leftvalue>
        <function>=</function>
        <rightvalue/>
        <value>
          <name>constant</name>
          <type>Boolean</type>
          <text>N</text>
          <length>-1</length>
          <precision>-1</precision>
          <isnull>N</isnull>
          <mask/>
        </value>
      </condition>
    </compare>
    <attributes/>
    <GUI>
      <xloc>480</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Get main workflows</name>
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
      <file_required>N</file_required>
      <filemask>main-.*\.hwf</filemask>
      <include_subfolders>Y</include_subfolders>
      <name>${PROJECT_HOME}</name>
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
      <xloc>96</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform>
    <name>run-main-workflow.hwf</name>
    <type>WorkflowExecutor</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <run_configuration>local</run_configuration>
    <filename>${PROJECT_HOME}/run-main-workflow.hwf</filename>
    <group_size>1</group_size>
    <group_field/>
    <group_time/>
    <parameters>
      <variablemapping>
        <variable>WORKFLOW_FILENAME</variable>
        <field>filename</field>
        <input/>
      </variablemapping>
      <inherit_all_vars>Y</inherit_all_vars>
    </parameters>
    <execution_result_target_transform>Error?</execution_result_target_transform>
    <execution_time_field>ExecutionTime</execution_time_field>
    <execution_result_field>ExecutionResult</execution_result_field>
    <execution_errors_field>ExecutionNrErrors</execution_errors_field>
    <execution_lines_read_field>ExecutionLinesRead</execution_lines_read_field>
    <execution_lines_written_field>ExecutionLinesWritten</execution_lines_written_field>
    <execution_lines_input_field>ExecutionLinesInput</execution_lines_input_field>
    <execution_lines_output_field>ExecutionLinesOutput</execution_lines_output_field>
    <execution_lines_rejected_field>ExecutionLinesRejected</execution_lines_rejected_field>
    <execution_lines_updated_field>ExecutionLinesUpdated</execution_lines_updated_field>
    <execution_lines_deleted_field>ExecutionLinesDeleted</execution_lines_deleted_field>
    <execution_files_retrieved_field>ExecutionFilesRetrieved</execution_files_retrieved_field>
    <execution_exit_status_field>ExecutionExitStatus</execution_exit_status_field>
    <execution_log_text_field>ExecutionLogText</execution_log_text_field>
    <execution_log_channelid_field>ExecutionLogChannelId</execution_log_channelid_field>
    <result_rows_target_transform/>
    <result_files_target_transform/>
    <result_files_file_name_field>FileName</result_files_file_name_field>
    <attributes/>
    <GUI>
      <xloc>320</xloc>
      <yloc>64</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```