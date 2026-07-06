# Neo4j 图输出（Neo4j Graph Output）

## 功能概述


Graph Output 转换允许您将输入字段自动映射到图模型。
该转换用于更新图数据的算法会考虑映射到节点主属性的空值字段。对于这些节点，不会执行合并操作，也不会创建或更新指向这些节点的关系。这一机制有助于保证图数据的完整性，避免因空值导致的不一致。

## 主要选项

| 选项 | 说明 |
|------|------|
| 连接 | 指定 Neo4j 数据库连接 |
| 图模型 | 选择目标图模型（包含节点和关系定义） |
| 字段映射 | 配置输入字段到图模型节点属性和关系属性的映射关系 |
| 批处理大小 | 控制每次提交到数据库的批处理记录数量 |


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