# Neo4j索引（Neo4j index）

## 功能概述


`Neo4j index` 动作可用于在 Neo4j 图中的节点或关系属性上创建或删除索引。
有关 Neo4j 约束的类似操作，请查看 Neo4j 约束动作。

## 主要选项

- **Neo4j 连接（Neo4j Connection）**：要更新索引的 Neo4j 连接名称。
- **要执行的索引更新（Index updates to perform）**：您可以在此指定索引更新列表
  - 更新类型：CREATE 或 DROP
  - 索引对象类型：NODE 或 RELATIONSHIP
  - 索引名称：可选但推荐的索引名称
  - 对象名称：要更新索引的节点或关系的标签
  - 属性：要索引的节点或关系属性的逗号分隔列表

## 显示 Cypher 预览


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