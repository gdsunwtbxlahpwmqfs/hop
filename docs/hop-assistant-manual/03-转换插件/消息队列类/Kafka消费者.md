# Kafka 消费者（Kafka Consumer）

## 功能概述


Kafka 消费者转换从 Kafka 持续拉取流式数据。它运行一个子管道，根据消息批大小或持续时间来执行，让您能够以近实时的方式处理连续的记录流。
该子管道必须以一个注入器（Injector）转换开始。
您可以定义要接受处理的消息数量，以及用于流式传输活动数据和系统指标的具体数据格式。您可以将此转换设置为收集受监控的事件、跟踪用户对数据流的消费情况以及监控警报。
Kafka 记录存储在主题（topic）中，由记录发布到的类别组成。主题被划分为一组称为分区（partition）的日志。Kafka 通过在消费者组（consumer group）之间分配分区来实现主题消费的扩展。消费者组是一组共享公共组标识符的消费者。
由于 Kafka 消费者转换会持续摄取流式数据，您可能希望在父管道或子管道中使用终止（Abort）转换，以便在特定工作流中停止从 Kafka 消费记录。例如，您可以按时间计划运行父管道，或者在传感器数据超出预设范围时终止子管道。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 常规（General）

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 此转换的名称。 |
| Kafka 管道（Kafka pipeline） | 通过输入路径或点击"浏览"并选择路径来指定要执行的管道。**注意：** 此管道**必须**以注入器（Injector）转换开始。 |

### 设置（Setup）

| 选项 | 说明 |
|------|------|
| 引导服务器（Bootstrap servers） | Kafka 集群中以逗号分隔的引导服务器列表。 |
| 主题（Topics） | 输入要从中消费流式数据（消息）的每个 Kafka 主题名称。 |
| 消费者组（Consumer group） | 输入您希望此消费者所属的组名称。 |


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0001-prepare-kafka-test</name>
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
    <created_date>2021/12/21 09:37:38.673</created_date>
    <modified_user>-</modified_user>
    <modified_date>2021/12/21 09:37:38.673</modified_date>
    <key_for_session_key>H4sIAAAAAAAAAAMAAAAAAAAAAAA=</key_for_session_key>
    <is_key_private>N</is_key_private>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Generate rows</from>
      <to>Kafka Producer</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Generate rows</name>
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
      <field>
        <length>-1</length>
        <name>field</name>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <type>String</type>
        <nullif>Hello Hop!</nullif>
      </field>
    </fields>
    <interval_in_ms>5000</interval_in_ms>
    <last_time_field>FiveSecondsAgo</last_time_field>
    <never_ending>N</never_ending>
    <limit>1</limit>
    <row_time_field>now</row_time_field>
    <attributes/>
    <GUI>
      <xloc>288</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Kafka Producer</name>
    <type>KafkaProducerOutput</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <directBootstrapServers>${BOOTSTRAP_SERVERS}</directBootstrapServers>
    <topic>hop-test</topic>
    <clientId/>
    <keyField/>
    <messageField>field</messageField>
    <advancedConfig>
      <option property="compression.type" value="none"/>
      <option property="ssl.key.password" value=""/>
      <option property="ssl.keystore.location" value=""/>
      <option property="ssl.keystore.password" value=""/>
      <option property="ssl.truststore.location" value=""/>
      <option property="ssl.truststore.password" value=""/>
    </advancedConfig>
    <attributes/>
    <GUI>
      <xloc>464</xloc>
      <yloc>160</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```