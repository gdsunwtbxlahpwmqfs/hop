# AWS SQS 读取器（AWS SQS Reader）

## 功能概述


AWS SQS 读取器转换使您能够在 Qi 数据治理平台 管道中接收来自 Amazon Web Services 简单队列服务（Simple Queue Service）的消息。

## 前置条件

在首次执行之前，您需要创建一个 IAM 角色（例如用于 EC2/ECS）或一个带有 AWS 密钥和密钥对的 IAM 用户，并附加所需策略。您还需要创建一个或多个订阅主题。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Qi 数据治理平台 Engine | ✅ 支持 |
| Spark | ❓ 支持 |
| Flink | ❓ 支持 |
| Dataflow | ❓ 支持 |

## 主要选项

### AWS 设置（AWS Settings）选项卡

| 选项 | 说明 |
|------|------|
| 使用 AWS 凭证链（Use AWS Credentials chain） | Qi 数据治理平台 尝试从主机环境获取 AWS 凭证。 |
| AWS 访问密钥（AWS Access Key） | 您的 AWS 访问密钥（`AWS_ACCESS_KEY_ID`）。 |
| AWS 秘密访问密钥（AWS Secret Access Key） | 您的 AWS 访问密钥对应的密钥（`AWS_SECRET_ACCESS_KEY`）。 |
| AWS 区域（AWS Region） | 服务运行的 AWS 区域。 |
| SQS 队列 URL（SQS Queue URL） | SQS 队列的 URL（以 https:// 开头，不是 ARN！）。 |

### 输出定义（Output Definition）选项卡

在输出定义选项卡中，您可以定义从 SQS 消息读取信息的输出字段，以及接收消息的一些初始设置。可设置是否在接收后从队列中删除消息。


## XML代码模板

```xml
<pipeline>
  <info>
    <name>0007-get-file-names</name>
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
    <created_date>2024/08/27 15:45:30.287</created_date>
    <modified_user>-</modified_user>
    <modified_date>2024/08/27 15:45:30.287</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Sparkle</from>
      <to>Get file names</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Get file names</from>
      <to>Select values</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Select values</from>
      <to>Dummy (do nothing)</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Dummy (do nothing)</name>
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
      <xloc>928</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Get file names</name>
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
      <include_subfolders>N</include_subfolders>
      <name>azit://mycontainer/copy/staging</name>
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
      <xloc>528</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Select values</name>
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
        <name>short_filename</name>
      </field>
      <select_unspecified>N</select_unspecified>
    </fields>
    <attributes/>
    <GUI>
      <xloc>736</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Sparkle</name>
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
      <xloc>272</xloc>
      <yloc>128</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```