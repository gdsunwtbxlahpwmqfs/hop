# Neo4j-Cypher脚本（Neo4j Cypher script）

## 功能概述


`Neo4j Cypher script` 动作在工作流中执行 Cypher 语句。
与所有动作一样，目标不是检索数据，而是执行各种语句，如创建或删除索引、调用 APOC 过程、添加或删除一些静态数据等。
此动作的结果是 `true` 或 `false` 退出代码，用于确定选择成功还是失败跳转到工作流中的下一个动作。

## 重要说明

**为了允许您在命令、字符串等中输入分号（;），我们要求您在单独的行上用分号分隔命令。**


## XML代码模板

```xml
<pipeline>
  <info>
    <name>neo4j-action-cypher-script-validate</name>
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
    <created_date>2022/12/01 10:07:25.145</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/12/01 10:07:25.145</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>read (p:Project)</from>
      <to>Verify</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Verify</name>
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
      <xloc>368</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform>
    <name>read (p:Project)</name>
    <type>Neo4jCypherOutput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <connection>Test</connection>
    <cypher>MATCH(p:Project) 
RETURN p.id as id, p.name as name</cypher>
    <cypher_from_field>N</cypher_from_field>
    <mappings>
</mappings>
    <read_only>Y</read_only>
    <retry>Y</retry>
    <returning_graph>N</returning_graph>
    <returns>
      <return>
        <name>id</name>
        <source_type>Integer</source_type>
        <type>Integer</type>
      </return>
      <return>
        <name>name</name>
        <source_type>String</source_type>
        <type>String</type>
      </return>
    </returns>
    <unwind>N</unwind>
    <attributes/>
    <GUI>
      <xloc>128</xloc>
      <yloc>80</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```