# Kafka 生产者（Kafka Producer）

## 功能概述


Kafka 生产者转换允许您在工作节点之间近实时地发布消息，多个已订阅的成员都可以访问这些消息。一个 Kafka 生产者转换会将记录流发布到一个 Kafka 主题。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 此转换的名称。 |
| 引导服务器（Bootstrap servers） | Kafka 集群中以逗号分隔的引导服务器列表。 |
| 客户端 ID（Client ID） | 唯一的客户端标识符，用于识别并建立到服务器的持久连接路径以发出请求，并区分不同的客户端。 |
| 主题（Topic） | 记录发布到的类别。 |
| 键字段（Key Field） | 在 Kafka 中，所有消息都可以设置键，允许消息根据其键按默认路由方案分发到分区。如果不存在键，消息将被随机分发到分区。 |
| 消息字段（Message Field） | 主题中包含的单条记录。 |

### 选项（Options）选项卡

使用此选项卡配置 Kafka 消费者代理源的属性格式。为方便使用，已包含一些最常见的属性格式。您可以输入任何所需的 Kafka 属性。有关这些输入名称的更多信息，请参阅 Apache Kafka 文档站点：https://kafka.apache.org/documentation/。


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