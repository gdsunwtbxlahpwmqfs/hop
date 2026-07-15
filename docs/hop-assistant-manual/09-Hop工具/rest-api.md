# Hop Server Web 服务概述

Hop Server 提供了一组丰富的 Web 服务，可用于查询和管理服务器状态，或管理 pipeline 和 workflow 的执行详情。

> **💡 提示:** 当指定时，workflow 或 pipeline 的 id 代表该 workflow 或 pipeline 在服务器上的一次执行。

## addExport _（已弃用）_

> **⚠️ 警告:** _自 2.18.0 起弃用。_ 请改用 `registerPackage`（`hop/registerPackage`）。
远程 pipeline 和 workflow engine 对所有导出操作调用 `registerPackage`。
此端点将在未来版本中移除。

name::
addExport

description::
上传一个资源导出文件。
将一个打包的 pipeline 或 workflow 作为二进制文件添加到请求体中。
_已弃用_ — 请改用 `registerPackage`。

endPoint::
GET `hop/addExport`

parameters::
- type: `pipeline` 或 `workflow`
- load: -

示例请求::
`http://localhost:8081/hop/addExport/?type=workflow`，以打包的 workflow 作为请求体

result::
在服务器的文件系统上创建一个包含导出内容的 zip 文件。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>OK</result>
    <message>file:///tmp/export_70eb8ef1-9721-4cf5-afa3-940cd0f771d9.zip</message>
    <id/>
</webresult>
```

## addPipeline _（已弃用）_

> **⚠️ 警告:** _自 2.18.0 起弃用。_ 请改用 registerPipeline（`hop/registerPipeline`）。
此端点不再被远程 pipeline engine 使用，将在未来版本中移除。

name::
addPipeline

description::
添加一个 pipeline 用于执行。
_已弃用_ — 请改用 `registerPipeline`。

endPoint::
GET `hop/addPipeline`
- Content-Type: text/xml;charset=UTF-8

parameters::
- xml 请求体应包含 xml，其中包含 pipeline_configuration（pipeline 和 pipeline_execution_configuration 包装在 pipeline_configuration 标签中）。

示例请求::
`http://localhost:8081/hop/addPipeline/xml=Y`，以 XML 作为请求体

result::
-

## addWorkflow _（已弃用）_

> **⚠️ 警告:** _自 2.18.0 起弃用。_ 请改用 registerWorkflow（`hop/registerWorkflow`）。
此端点不再被远程 workflow engine 使用，将在未来版本中移除。

name::
addWorkflow

description::
添加一个 workflow 用于执行。
_已弃用_ — 请改用 `registerWorkflow`。

endPoint::
GET `hop/addWorkflow`
- Content-Type: text/xml;charset=UTF-8

parameters::
- xml 请求体应包含 xml，其中包含 workflow_configuration（pipeline 和 workflow_execution_configuration 包装在 pipeline_configuration 标签中）。

示例请求::
`http://localhost:8081/hop/addWorkflow/xml=Y`，以 XML 作为请求体

result::
-

## getPipelineImage

name::
getPipelineImage

description::
生成 pipeline 的 SVG 图像

endPoint::
GET `hop/pipelineImage`

parameters::
- name: 要生成图像的 pipeline 名称
- id: 要生成图像的 pipeline id

示例请求::
GET `http://localhost:8081/hop/pipelineImage/?name=remote-pipeline&id=c1451bfb-b867-4c76-b123-c29d2b05da17`

result::
pipeline 图的 SVG 图像

## getPipelineStatus

name::
getPipelineStatus

description::
获取 pipeline 的状态

endPoint::
GET `hop/pipelineStatus`

parameters::
- name: 要获取状态的 pipeline 名称
- id: 要获取状态的 pipeline id
- xml（可选）：以 xml 格式返回信息（默认 HTML），使用 &xml=Y
- json（可选）：以 json 格式返回信息（默认 HTML），使用 &json=Y

示例请求::
GET `http://localhost:8081/hop/pipelineStatus/?name=<NAME>>&id=<ID>`

result::
包含此 pipeline 的执行状态、Transform 详情和画布预览的 HTML 响应

## Status

name::
status

description::
获取服务器的状态

parameters::
无

示例请求::
GET `http://localhost:8081/hop/status/`

result::
一个 HTML 页面，包含服务器上的 pipeline 和 workflow 概览、其执行详情以及服务器的配置详情。

## getWorkflowImage

name::
getWorkflowImage

description::
生成 workflow 的 SVG 图像

endPoint::
GET `hop/workflowImage`

parameters::
- name: 要生成图像的 workflow 名称
- id: 要生成图像的 workflow id

示例请求::
GET `http://localhost:8081/hop/workflowImage/?name=<NAME>>&id=<ID>`

result::
workflow 图的 SVG 图像

## getWorkflowStatus

name::
getWorkflowStatus

description::
获取 workflow 的状态

endPoint::
GET `hop/workflowStatus`

parameters::
- name: 要获取状态的 workflow 名称
- id: 要获取状态的 workflow id
- xml（可选）：以 xml 格式返回信息（默认 HTML），使用 &xml=Y
- json（可选）：以 json 格式返回信息（默认 HTML），使用 &json=Y

示例请求::
GET `http://localhost:8081/hop/workflowStatus/?name=<NAME>&id=<ID>`

result::
包含此 workflow 的执行状态、Action 详情和画布预览的 HTML 响应

## pausePipeline

name::
pausePipeline

description::
暂停或继续 pipeline

endPoint::
GET `/hop/pausePipeline`

parameters::
- name: 要暂停或重启的 pipeline 名称
- id: 要暂停或重启的 pipeline id

示例请求::
GET `http://localhost:8081/hop/pausePipeline/?name=<NAME>&id=<ID>`

result::
包含请求状态的 HTML 页面，例如

```html
<HTML>

<HEAD>
	<TITLE>Pause pipeline</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/pipelineStatus?name=<NAME>&id=<ID>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Pipeline [tmp] : pause requested.</H1>
	<a href="/hop/pipelineStatus?name=<NAME>&id=<ID>">Back to the pipeline status page</a>
	<p>
		<p>
</BODY>

</HTML>
```

## Prepare Execution

name::
prepareExec

description::
准备 pipeline 的执行

endPoint::
GET `/hop/prepareExec`

parameters::
- xml: 使用 xml，默认 Y
- name: 要准备执行的 pipeline 名称
- id: 要准备执行的 pipeline id

示例请求::
GET `http://localhost:8081/hop/prepareExec/?xml=Y&name=<NAME>&id=<ID>`

result::

示例结果：

```html
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>OK</result>
    <message/>
    <id/>
</webresult>
```

//=== Register Package
//
//name::
//registerPackage
//
//description::
//Upload a resources export file
//
//endPoint::
//GET `/hop/registerPackage`
//
//parameters::
//* load
//* type
//
//example request::
//-
//
//result::
//-

## Register Pipeline

name::
registerPipeline

description::
注册一个 pipeline 用于执行

endPoint::
GET `hop/registerPipeline`
- Content-Type: text/xml;charset=UTF-8

parameters::
- xml 请求体应包含 xml，其中包含 pipeline_configuration（pipeline 和 pipeline_execution_configuration 包装在 pipeline_configuration 标签中）。

示例请求::
`http://localhost:8081/hop/registerPipeline/xml=Y`

以 XML 作为请求体（示例）：
```xml
<pipeline_configuration>
<pipeline>
  <info>
    <name>generate_rows</name>
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
    <created_date>2022/02/03 13:47:49.645</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/02/03 13:47:49.645</modified_date>
    <key_for_session_key>H4sIAAAAAAAA/wMAAAAAAAAAAAA=</key_for_session_key>
    <is_key_private>N</is_key_private>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>Generate rows</from>
      <to>Delay row</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Delay row</from>
      <to>result</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Delay row</name>
    <type>Delay</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <scaletime>seconds</scaletime>
    <timeout>1</timeout>
    <attributes/>
    <GUI>
      <xloc>416</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
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
        <name>value</name>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <type>String</type>
        <nullif>test</nullif>
      </field>
    </fields>
    <interval_in_ms>5000</interval_in_ms>
    <last_time_field>FiveSecondsAgo</last_time_field>
    <never_ending>N</never_ending>
    <limit>10000</limit>
    <row_time_field>now</row_time_field>
    <attributes/>
    <GUI>
      <xloc>160</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
  <transform>
    <name>result</name>
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
      <xloc>720</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
  <pipeline_execution_configuration>
    <pass_export>N</pass_export>
    <parameters>
    </parameters>
    <variables>
    <variable><name>HOP_AUDIT_FOLDER</name><value>/Users/hans/config/audit</value></variable>
    <variable><name>HOP_AUTO_CREATE_CONFIG</name><value>Y</value></variable>
    <variable><name>HOP_CONFIG_FOLDER</name><value>/Users/hans/config</value></variable>
    <variable><name>HOP_DATASETS_FOLDER</name><value>/Users/hans/test/datasets</value></variable>
    <variable><name>HOP_ENVIRONMENT_NAME</name><value>test1</value></variable>
    <variable><name>HOP_METADATA_FOLDER</name><value>/Users/hans/test/metadata</value></variable>
    <variable><name>HOP_PIPELINE_PAN_JVM_EXIT_CODE</name><value/></variable>
    <variable><name>HOP_PROJECTS</name><value>/Users/hans/tmp/</value></variable>
    <variable><name>HOP_PROJECT_NAME</name><value>test</value></variable>
    <variable><name>HOP_UNIT_TESTS_FOLDER</name><value>/Users/hans/test</value></variable>
    <variable><name>NEO4J_CONNECTION</name><value>neo4j</value></variable>
    <variable><name>PROJECT_HOME</name><value>/Users/hans/test</value></variable>
    <variable><name>TEST</name><value>TEST</value></variable>
    <variable><name>ftp.nonProxyHosts</name><value>local|*.local|169.254/16|*.169.254/16</value></variable>
    <variable><name>http.nonProxyHosts</name><value>local|*.local|169.254/16|*.169.254/16</value></variable>
    <variable><name>jdk.debug</name><value>release</value></variable>
    <variable><name>native.encoding</name><value>UTF-8</value></variable>
    <variable><name>p1</name><value>a</value></variable>
    <variable><name>socksNonProxyHosts</name><value>local|*.local|169.254/16|*.169.254/16</value></variable>
    </variables>
    <log_level>Basic</log_level>
    <log_file>N</log_file>
    <log_filename/>
    <log_file_append>N</log_file_append>
    <create_parent_folder>N</create_parent_folder>
    <clear_log>Y</clear_log>
    <show_subcomponents>Y</show_subcomponents>
    <run_configuration>local</run_configuration>
</pipeline_execution_configuration>
<metastore_json>H4sIAAAAAAAA/`1XbVPbOBD`Kxl/upshzQsUAt/SxBzchSRNTDudpuNRZNlRkSVXkgk5hv9`K0t`
CSl3cDP37coH8Gq1evTs7rPqo6eIvCfSu/j66G3JephlU5QS78Lzjjyl2EjwmCbeBc8ZKww3IoLV
GDFFjrxMiofdXEht/TdCaW539/pn77rw0wNzhpTaChmB2edY7jJNolZ/Tc4HKMaDU4TO4j45wese
Qv3T9zgyJjyAjVzwuTnhCuIqe4QArJJGxH+gSlOewHpGpKZENUE50w1Smkh7H3uBzGId9AYGmMPK
ydZzd7mqbwCmHLhxX5jlJpb39A08aUYY5aQNW9aw+BVsOae6rYnS9lNG61QVnLq/Hr3ldPb5cjL8
wzcfEdJojRSpuc5YnlB+bUiqPY88yiPyEKA1IypDuLrHFkmyEYDPbjbhDp0QxkSpYJeBpbuXnZeS
4lUMVYgcwiUX25ihO2LKoqgY3qAVaS3pOtfE3vR2Pp8tgmUYXN/4y2B4Mw/Hw2AYBl/mcHnvC4T4
eDsL/HA4mYSX1/5kvATzFMzVzg`z2cQfTg/2Xc4WIz`8HvvT4Bp2LuCMWTiZffYXo+HSd1HmC3/p
Lz75ofs9Dj/PFuPQeZgwy4`TcDSbTv1RYO/607gA5nncS3`xgHjL0ZV/Mwynwxuz5j0deSniOWK3
kh1Ujvf0VFVaQjiRFPdhR6My5rNl8Btg/Tj5SWlkkLVEArF7JdLY8mKNvLUomMCIme83tOzZOSa9
bnR6fox6`Lwun/cnx/3nJTS3N7GY/6`ht9TQBnHVrKMsKYQIMnQXM7FtM5FY3cGQN8QjidpYcE6w
poLbFU7Eyfd2IlG2aaeg4cyaQfLbJhfUVAjIlZZwVCxk6pI2ztN01/olEi0u9AYE91dAFlNGHJJV
5xa2q1XHQFx1jAKuOqbyljbou03Gih2ERS7kPWK5kRFmFXypkc5r9XZxFUozRkKAV6KDywmuCde2
dj2UZYxiZG646nxXcM8jD1BBvUPVapmTPamWOTeUwCzLJXKsPII/lCdZ5Hy0v/TofSAoHVMJDM5d
CL/wNWuapNlEYOdbsHGx6pifeyRXnViwqKDkvgu0fE9P77Ik6nbPTjmO7rJ4IJPjaNuFfwkAD1Yd
0w1awnlAxpXIgjID6kMex0Qu6Z9OsnmerokMRRyaxMMR1hwj/TuSdc0MQWVMD0KsF0NfwjjbXAOd
ErJht1aZnxdNO2JQSkQ1R4EKBCQrcWgesgNHU6F7JH9CkhYK5GrQ5rYYk6BPRGFJM8diIYkv52NB
UqEL9smD0ZgQJFHkEpvQJtkwcktDqJFMiA5tHizYetH`3lu0WhRmgrGQ7nECVRNi9wKy`li8crKw
fDS5t0MzArQW2v0jG49VK/cOqThyPQJFXvS5c5WWgzcyNylgwx8xIdEa4btQ2Yp6b2rQgLet5qy9
pk1DqwElYZJTWJqgIm9SbBXQW/u7HTEJ0`bjUG3ENiwPraxaZAKSYKaEsyRIb4C8lID`41oIinpE
hYIZ/IX1lQVWZmqfJyBHtXaQ/lapCqpVeLJda0v1pgUwWkqDfCIZ2ZUWNE3l3bIc24wUarqnsY/V
S3pvkq4LuqR7JJ+dnJ2491pjxhfRvMYAcNdBuRYp3BOX1yxnZa6god1gLk53nEmRG0mtn8KvHuPr
qH8cRYNujAupfXZAfb5XwHwI6uQsIG+7gJbvypqTCb2H545Sow3Bd8YBwD33ecE8xD9yquiBB3S6
0kPGRuZ9Hxv1J+oA26iBICa6QubI2fevKH7O4VzAdNlZp7Vgukzg6eDsBSBVBCgCBaeflMrEn2e5
AXEuBKsVvjHyS+8yWBO2qcBU8ERE64M5nyFAVH8iteO4vT/lm3OxejlUb4m3TspKXw4V4KUWbrZp
/2/6tMT0uj4tvas+fZ0s/jvY/x3qb/Z91Y5ITDltJAAzoXJZvgWa/3U9GB/Fc2tcBXByoggUB9JC
ljWkYK7zO7toJAlk3Xw8/QVlC+SCFRAAAA==
</metastore_json>
</pipeline_configuration>
```

metastore_json 是 base64 编码的 GZip 内容

result::
-

## Register Workflow

name::
registerWorkflow

description::
在服务器上注册一个 workflow

endPoint::
GET `/hop/registerWorkflow`
- Content-Type: text/xml;charset=UTF-8

parameters::
- xml:

示例请求::
-

result::
-

## Remove Pipeline

name::
removePipeline

description::
从服务器移除一个 pipeline

endPoint::
GET `/hop/removePipeline`

parameters::
- name: 要移除的 pipeline 名称
- id: 要移除的 pipeline id

示例请求::
GET `http://localhost:8081/hop/removePipeline/?name=<NAME>&id=<ID>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>The pipeline was removed</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H3>The pipeline with name [<NAME>] and hopServer object id <ID> was removed from Hop
		Server.</H3>
	<a href="/hop/status">Back to the status page</a><br>
	<p>
</BODY>
```

## Remove Workflow

name::
removeWorkflow

description::
从服务器移除一个 workflow

endPoint::
GET `/hop/removeWorkflow`

parameters::
- name: 要移除的 workflow 名称
- id: 要移除的 workflow id

示例请求::
GET `http://localhost:8081/hop/removeWorkflow/?name=<NAME>&id=<ID>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>The workflow was removed</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H3>The workflow with name [<NAME>] and hopServer object id <ID> was removed from Hop
		Server.</H3>
	<a href="/hop/status">Back to the status page</a><br>
	<p>
</BODY>
```

## Sniff Transform

name::
sniffTransform

description::
对 pipeline Transform 进行嗅探测试

endPoint::
GET `/hop/sniffTransform`

parameters::
- xml: 使用 XML（默认：Y）
- pipeline: 要嗅探的 pipeline 名称
- id: 要嗅探的 pipeline id
- transform: 要嗅探的 Transform 名称
- type: （input/output）嗅探输入或输出
- lines: 等待的行数
- copynr: 使用多个副本时可以指定 copynr

以下参数是必需的::
- xml
- id 或 pipeline
- transform

示例请求::
GET `http://localhost:8081/hop/sniffTransform/?xml=Y&pipeline=<NAME>&id=<ID>&transform=<TRANSFORMNAME>&type=output&lines=100`

result::

示例结果（空）：

<row-buffer>
<row-meta/>
</row-buffer>

## Start Pipeline Execution

name::
startExec

description::
启动 pipeline 的执行

endPoint::
GET `/hop/startExec`

parameters::
- name: 要启动的 pipeline 名称

示例请求::
GET `http://localhost:8081/hop/startExec?name=<NAME>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>Start of pipeline</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/pipelineStatus?name=<NAME>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Pipeline [<NAME>] was started.</H1>
	<a href="/hop/pipelineStatus?name=<NAME>&id=8bea27db-de97-4bd0-a210-d9bba3aacac2">Back to the status page</a>
	<p>
		<p>
</BODY>

</HTML>
```

## Start Pipeline

name::
startPipeline

description::
准备并启动 pipeline 的执行

endPoint::
GET `/hop/startPipeline`

parameters::
- name: 要启动的 pipeline 名称

示例请求::
GET `http://localhost:8081/hop/startPipeline?name=<NAME>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>Start of pipeline</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/pipelineStatus?name=<NAME>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Pipeline [<NAME>] was started.</H1>
	<a href="/hop/pipelineStatus?name=<NAME>&id=8bea27db-de97-4bd0-a210-d9bba3aacac2">Back to the status page</a>
	<p>
		<p>
</BODY>

</HTML>
```

## Start Workflow

name::
startWorkflow

description::
准备并启动 workflow 的执行

endPoint::
GET `/hop/startPipeline`

parameters::
- name: 要启动的 workflow 名称

示例请求::
GET `http://localhost:8081/hop/startWorkflow?name=<NAME>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>Start of workflow</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/startWorkflow?name=<NAME>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Workflow [<NAME>] was started.</H1>
	<a href="/hop/workflowStatus?name=<NAME>&id=8bea27db-de97-4bd0-a210-d9bba3aacac2">Back to the status page</a>
	<p>
		<p>
</BODY>

</HTML>
```

## Execute Pipeline from File

name::
execPipeline

description::
直接从服务器文件系统上的文件路径执行 pipeline。
该 pipeline 被加载、同步执行，并立即返回结果。
此 servlet 支持文件路径中的变量解析（例如 `{openvar}PROJECT_HOME{closevar}`），并可向 pipeline 传递参数和变量。

endPoint::
GET `/hop/execPipeline`

parameters::
- pipeline（必需）：pipeline 的文件路径（.hpl 文件）。可以使用 `{openvar}PROJECT_HOME{closevar}` 等变量，服务器将进行解析。
- level（可选）：日志级别（例如 Debug、Basic、Detailed、Error、Minimal、Nothing、RowLevel）。默认为服务器的默认日志级别。
- runConfig（可选）：执行时使用的运行配置名称。如未指定，则使用 metadata 中的默认运行配置，如果未找到默认值则使用 "local"。
- *任意参数名称*：任何额外的 URL 参数将作为 pipeline 参数或变量传递。如果参数名称与 pipeline 参数匹配，则设置为参数；否则设置为变量。

示例请求::
GET `http://localhost:8081/hop/execPipeline?pipeline=/opt/hop/config/projects/samples/pipelines/pipeline-with-parameter.hpl&PRM_EXAMPLE=test_value&level=BASIC`

使用项目变量：
GET `http://localhost:8081/hop/execPipeline?pipeline=${PROJECT_HOME}/pipelines/my-pipeline.hpl&runConfig=local&level=Detailed`

result::

成功响应（HTTP 200）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>OK</result>
    <message>Pipeline executed successfully</message>
    <id/>
</webresult>
```

错误响应 - 缺少参数（HTTP 400）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Missing mandatory parameter: pipeline</message>
    <id/>
</webresult>
```

错误响应 - 文件未找到（HTTP 404）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Unable to find pipeline: /path/to/nonexistent.hpl (resolved: /path/to/nonexistent.hpl)</message>
    <id/>
</webresult>
```

错误响应 - 执行错误（HTTP 500）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Error executing pipeline: [log output]</message>
    <id/>
</webresult>
```

notes::
- pipeline 路径支持变量解析。如果服务器启动时指定了项目（使用 `-j` 标志），`{openvar}PROJECT_HOME{closevar}` 等变量将被解析。
- 执行是同步的 — servlet 会等待 pipeline 完成后再返回。
- pipeline 会自动注册到服务器的 pipeline 映射中，可使用 `getPipelineStatus` 查询。
- 除 `pipeline`、`level` 和 `runConfig` 外的所有 URL 参数都会作为参数或变量传递给 pipeline。

## Execute Workflow from File

name::
execWorkflow

description::
直接从服务器文件系统上的文件路径执行 workflow。
该 workflow 被加载、同步执行，并立即返回结果。
此 servlet 支持文件路径中的变量解析（例如 `{openvar}PROJECT_HOME{closevar}`），并可向 workflow 传递参数和变量。

endPoint::
GET `/hop/execWorkflow`

parameters::
- workflow（必需）：workflow 的文件路径（.hwf 文件）。可以使用 `{openvar}PROJECT_HOME{closevar}` 等变量，服务器将进行解析。
- level（可选）：日志级别（例如 Debug、Basic、Detailed、Error、Minimal、Nothing、RowLevel）。默认为服务器的默认日志级别。
- runConfig（可选）：执行时使用的运行配置名称。如未指定，则使用 metadata 中的默认运行配置，如果未找到默认值则使用 "local"。
- *任意参数名称*：任何额外的 URL 参数将作为 workflow 参数或变量传递。如果参数名称与 workflow 参数匹配，则设置为参数；否则设置为变量。

示例请求::
GET `http://localhost:8081/hop/execWorkflow?workflow=/opt/hop/config/projects/samples/workflows/parallel/parallel-workflow.hwf&level=BASIC`

使用项目变量：
GET `http://localhost:8081/hop/execWorkflow?workflow=${PROJECT_HOME}/workflows/my-workflow.hwf&runConfig=local&level=Detailed`

result::

成功响应（HTTP 200）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>OK</result>
    <message>Workflow executed successfully</message>
    <id/>
</webresult>
```

错误响应 - 缺少参数（HTTP 400）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Missing mandatory parameter: workflow</message>
    <id/>
</webresult>
```

错误响应 - 文件未找到（HTTP 404）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Unable to find workflow: /path/to/nonexistent.hwf (resolved: /path/to/nonexistent.hwf)</message>
    <id/>
</webresult>
```

错误响应 - 执行错误（HTTP 500）：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<webresult>
    <result>ERROR</result>
    <message>Error executing workflow: [log output]</message>
    <id/>
</webresult>
```

notes::
- workflow 路径支持变量解析。如果服务器启动时指定了项目（使用 `-j` 标志），`{openvar}PROJECT_HOME{closevar}` 等变量将被解析。
- 执行是同步的 — servlet 会等待 workflow 完成后再返回。
- workflow 会自动注册到服务器的 workflow 映射中，可使用 `getWorkflowStatus` 查询。
- 除 `workflow`、`level` 和 `runConfig` 外的所有 URL 参数都会作为参数或变量传递给 workflow。

## Stop Pipeline

name::
stopPipeline

description::
停止 pipeline

endPoint::
GET `/hop/stopPipeline`

parameters::
- name: 要停止的 pipeline 名称
- id: 要停止的 pipeline id

示例请求::
GET `http://localhost:8081/hop/stopPipeline?name=<NAME>&id=<ID>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>Stop pipeline</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/pipelineStatus?name=<NAME>>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Pipeline [<NAME>] stop requested.</H1>
	<a href="/hop/pipelineStatus?name=<NAME>&id=<ID>">Back to the pipeline status page</a>
	<p>
		<p>
</BODY>

</HTML>
```

## Stop Workflow

name::
stopWorkflow

description::
停止 workflow

endPoint::
GET `/hop/stopWorkflow`

parameters::
- name: 要停止的 workflow 名称
- id: 要停止的 workflow id

示例请求::
GET `http://localhost:8081/hop/stopWorkflow?name=<NAME>&id=<ID>`

result::

示例结果：

```html
<HTML>

<HEAD>
	<TITLE>Stop workflow</TITLE>
	<META http-equiv="Refresh" content="2;url=/hop/workflowStatus?name=<NAME>>">
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>

<BODY>
	<H1>Workflow [<NAME>] stop requested.</H1>
	<a href="/hop/workflowStatus?name=<NAME>&id=<ID>">Back to the pipeline status page</a>
	<p>
		<p>
</BODY>

</HTML>
```
