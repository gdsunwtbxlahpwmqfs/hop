# Qi Hop REST API 接口文档

## 概述

本文档系统地整理了 Qi Hop RAP 模式下的所有 REST API 接口信息，为后续 MCP 服务的集成与对接提供参考。

**基础路径**: `/api/v1/`
**版本**: v1
**框架**: JAX-RS (Jakarta EE)

---

## 目录

1. [元数据管理模块 (Metadata)](#1-元数据管理模块-metadata)
2. [执行模块 (Execution)](#2-执行模块-execution)
3. [执行位置模块 (Location)](#3-执行位置模块-location)
4. [插件模块 (Plugins)](#4-插件模块-plugins)
5. [文档服务模块 (Docs)](#5-文档服务模块-docs)
6. [知识库模块 (KnowledgeBase)](#6-知识库模块-knowledgebase)
7. [Carte 服务模块 (Deprecated)](#7-carte-服务模块-deprecated)

---

## 1. 元数据管理模块 (Metadata)

### 1.1 获取所有元数据类型

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取所有元数据类型 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/metadata/types` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
[
  "web-service",
  "execution-info-location",
  "database",
  "pipeline-run-configuration"
]
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (数组元素) | String | 元数据类型的 key 值 |

**适用场景**: 获取系统中注册的所有元数据类型列表，用于动态展示或筛选。

---

### 1.2 获取指定类型的元素名称列表

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取指定类型的元素名称列表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/metadata/list/{key}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 元数据类型的 key 值 |

**成功响应** (200 OK):

```json
[
  "MyService",
  "AnotherService"
]
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (数组元素) | String | 指定类型下的所有元素名称 |

**适用场景**: 获取某个元数据类型下的所有元素名称，用于下拉选择或列表展示。

---

### 1.3 获取指定元素详情

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取指定元素详情 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/metadata/{key}/{name}` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 元数据类型的 key 值 |
| name | String | 是 | 元素名称 |

**成功响应** (200 OK):

```json
{
  "name": "MyService",
  "enabled": true,
  "filename": "/path/to/pipeline.hpl",
  "transformName": "Output",
  "fieldName": "result",
  "contentType": "application/json",
  "statusCode": "200",
  "listingStatus": true,
  "bodyContentVariable": "REQUEST_BODY",
  "runConfigurationName": "local",
  "headerContentVariable": ""
}
```

**响应字段说明** (以 web-service 为例):

| 字段 | 类型 | 说明 |
|------|------|------|
| name | String | 元素名称 |
| enabled | boolean | 是否启用 |
| filename | String | 关联的 pipeline 文件路径 |
| transformName | String | 输出结果的 transform 名称 |
| fieldName | String | 输出结果的字段名称 |
| contentType | String | 响应内容类型 |
| statusCode | String | 响应状态码 |
| listingStatus | boolean | 是否列出状态 |
| bodyContentVariable | String | 请求体内容变量名 |
| runConfigurationName | String | 运行配置名称 |
| headerContentVariable | String | 请求头内容变量名 |

**失败响应** (500 Internal Server Error):

```
Error getting element...
```

**适用场景**: 获取某个元数据元素的完整配置信息，用于编辑或展示详情。

---

### 1.4 保存元数据元素

| 属性 | 值 |
|------|-----|
| **接口名称** | 保存元数据元素 |
| **请求方法** | POST |
| **URL路径** | `/api/v1/metadata/{key}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 元数据类型的 key 值 |

**请求体** (JSON):

```json
{
  "name": "MyService",
  "enabled": true,
  "filename": "/path/to/pipeline.hpl",
  "transformName": "Output",
  "fieldName": "result",
  "contentType": "application/json"
}
```

**请求体字段说明**:

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 是 | 元素名称 |
| enabled | boolean | 否 | 是否启用 |
| filename | String | 否 | 关联的 pipeline 文件路径 |
| transformName | String | 否 | 输出结果的 transform 名称 |
| fieldName | String | 否 | 输出结果的字段名称 |
| contentType | String | 否 | 响应内容类型 |
| statusCode | String | 否 | 响应状态码 |
| listingStatus | boolean | 否 | 是否列出状态 |
| bodyContentVariable | String | 否 | 请求体内容变量名 |
| runConfigurationName | String | 否 | 运行配置名称 |
| headerContentVariable | String | 否 | 请求头内容变量名 |

**成功响应** (200 OK):

```json
"MyService"
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (响应体) | String | 保存成功的元素名称 |

**失败响应** (500 Internal Server Error):

```
Error saving element of type {key}
```

**适用场景**: 创建或更新元数据元素配置。

---

### 1.5 删除元数据元素

| 属性 | 值 |
|------|-----|
| **接口名称** | 删除元数据元素 |
| **请求方法** | DELETE |
| **URL路径** | `/api/v1/metadata/{key}/{elementName}` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| key | String | 是 | 元数据类型的 key 值 |
| elementName | String | 是 | 元素名称 |

**成功响应** (200 OK):

```json
"MyService"
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (响应体) | String | 删除成功的元素名称 |

**失败响应** (500 Internal Server Error):

```
Error deleting element of type {key} with name {elementName}
```

**适用场景**: 删除指定的元数据元素。

---

## 2. 执行模块 (Execution)

### 2.1 同步执行 Web Service

| 属性 | 值 |
|------|-----|
| **接口名称** | 同步执行 Web Service |
| **请求方法** | POST |
| **URL路径** | `/api/v1/execute/sync` |
| **Content-Type** | application/json |

**请求体** (JSON):

```json
{
  "service": "MyService",
  "runConfig": "local",
  "bodyContent": "{\"input\": \"data\"}",
  "variables": {
    "param1": "value1",
    "param2": "value2"
  }
}
```

**请求体字段说明**:

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| service | String | 是 | Web Service 元数据名称 |
| runConfig | String | 否 | 运行配置名称，若未指定则使用 Web Service 中配置的值 |
| bodyContent | String | 否 | 请求体内容，将设置到 bodyContentVariable 指定的变量中 |
| variables | Map<String, String> | 否 | 额外的变量/参数键值对 |

**成功响应** (200 OK):

```
Pipeline output content...
```

**响应说明**: 返回指定 transform 的指定字段内容，Content-Type 由 Web Service 配置决定。

**失败响应** (400 Bad Request / 500 Internal Server Error):

```
Please specify the name of the service in the JSON you post: service
```

或

```
Unexpected error executing synchronous web service (pipeline) with name MyService
```

**适用场景**: 通过 Web Service 元数据定义同步执行 pipeline 并获取输出结果。

---

## 3. 执行位置模块 (Location)

### 3.1 注册执行信息

| 属性 | 值 |
|------|-----|
| **接口名称** | 注册执行信息 |
| **请求方法** | POST |
| **URL路径** | `/api/v1/location/executions/{locationName}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| locationName | String | 是 | 执行信息位置名称 |

**请求体** (JSON):

```json
{
  "name": "Pipeline Execution",
  "filename": "/path/to/pipeline.hpl",
  "id": "uuid-12345",
  "parentId": null,
  "executionType": "PIPELINE",
  "runConfigurationName": "local",
  "logLevel": "BASIC",
  "parameterValues": {},
  "environmentDetails": {},
  "registrationDate": "2024-01-01T00:00:00",
  "executionStartDate": "2024-01-01T00:00:00"
}
```

**请求体字段说明**:

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| name | String | 是 | 执行名称 |
| filename | String | 是 | 执行的文件名 |
| id | String | 是 | 唯一执行 ID |
| parentId | String | 否 | 父执行器 ID |
| executionType | String | 是 | 执行类型: PIPELINE 或 WORKFLOW |
| runConfigurationName | String | 否 | 运行配置名称 |
| logLevel | String | 否 | 日志级别 |
| parameterValues | Map<String, String> | 否 | 参数值映射 |
| environmentDetails | Map<String, String> | 否 | 环境详情 |
| registrationDate | String | 否 | 注册时间 |
| executionStartDate | String | 否 | 执行开始时间 |

**成功响应** (200 OK):

```json
"execution registered successfully"
```

**失败响应** (500 Internal Server Error):

```
Error registering execution for location {locationName}
```

**适用场景**: 将 pipeline/workflow 执行信息注册到指定的执行位置。

---

### 3.2 获取执行 ID 列表

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取执行 ID 列表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/location/executions/{locationName}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| locationName | String | 是 | 执行信息位置名称 |

**查询参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| includeChildren | boolean | 否 | false | 是否包含子执行器 |
| limit | int | 否 | 100 | 最大返回数量，<=0 返回全部 |

**成功响应** (200 OK):

```json
[
  "uuid-12345",
  "uuid-67890"
]
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (数组元素) | String | 执行 ID |

**失败响应** (500 Internal Server Error):

```
Error registering execution for location {locationName}
```

**适用场景**: 获取指定位置下的所有执行 ID 列表，用于监控或历史查询。

---

### 3.3 获取执行详情

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取执行详情 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/location/executions/{locationName}/{executionId}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| locationName | String | 是 | 执行信息位置名称 |
| executionId | String | 是 | 执行 ID |

**成功响应** (200 OK):

```json
{
  "name": "Pipeline Execution",
  "filename": "/path/to/pipeline.hpl",
  "id": "uuid-12345",
  "parentId": null,
  "executionType": "PIPELINE",
  "runConfigurationName": "local",
  "logLevel": "BASIC",
  "parameterValues": {},
  "environmentDetails": {},
  "registrationDate": "2024-01-01T00:00:00",
  "executionStartDate": "2024-01-01T00:00:00"
}
```

**响应字段说明**: 同注册执行信息的请求体字段。

**失败响应** (500 Internal Server Error):

```
Unable to find execution for ID {executionId} in location {locationName}
```

**适用场景**: 获取某个执行的详细静态信息。

---

### 3.4 获取执行状态

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取执行状态 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/location/state/{locationName}/{executionId}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| locationName | String | 是 | 执行信息位置名称 |
| executionId | String | 是 | 执行 ID |

**成功响应** (200 OK):

```json
{
  "executionType": "PIPELINE",
  "id": "uuid-12345",
  "name": "Pipeline Execution",
  "statusDescription": "Running",
  "updateTime": "2024-01-01T00:01:00",
  "failed": false,
  "executionEndDate": null,
  "childIds": [],
  "details": {},
  "metrics": [],
  "loggingText": "",
  "lastLogLineNr": 0
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| executionType | String | 执行类型: PIPELINE 或 WORKFLOW |
| id | String | 执行 ID |
| name | String | 执行名称 |
| statusDescription | String | 状态描述 |
| updateTime | String | 最后更新时间 |
| failed | boolean | 是否失败 |
| executionEndDate | String | 执行结束时间，未结束为 null |
| childIds | String[] | 子执行器 ID 列表 |
| details | Map<String, String> | 额外详情 |
| metrics | Object[] | 组件指标 |
| loggingText | String | 日志文本 |
| lastLogLineNr | int | 最后日志行号 |

**失败响应** (500 Internal Server Error):

```
Unable to find execution state for ID {executionId} in location {locationName}
```

**适用场景**: 获取执行的实时状态，用于监控和进度追踪。

---

## 4. 插件模块 (Plugins)

### 4.1 获取所有插件类型

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取所有插件类型 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/plugins/types` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
[
  "org.apache.hop.core.plugins.TransformPluginType",
  "org.apache.hop.core.plugins.ActionPluginType",
  "org.apache.hop.core.plugins.DatabasePluginType"
]
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| (数组元素) | String | 插件类型的完整类名 |

**适用场景**: 获取系统中注册的所有插件类型，用于插件管理界面。

---

### 4.2 获取指定类型的插件列表

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取指定类型的插件列表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/plugins/list/{typeClassName}/` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| typeClassName | String | 是 | 插件类型的完整类名 |

**成功响应** (200 OK):

```json
[
  {
    "id": "json",
    "name": "JSON Input",
    "description": "Reads JSON files",
    "category": "transforms"
  }
]
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| id | String | 插件 ID |
| name | String | 插件名称 |
| description | String | 插件描述 |
| category | String | 插件类别 |

**失败响应** (500 Internal Server Error):

```
Type class name is not available in the plugin registry: {typeClassName}
```

**适用场景**: 获取某个插件类型下的所有插件详情，用于插件展示和选择。

---

## 5. 文档服务模块 (Docs)

### 5.1 获取文档 (HTML 格式)

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取文档 (HTML 格式) |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/{path:.+}` |
| **Content-Type** | text/html |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| path | String | 是 | 文档路径或 URL |

**请求头**:

| 头名称 | 值 | 说明 |
|--------|-----|------|
| Accept | text/html | 指定返回 HTML 格式 |

**成功响应** (200 OK):

```html
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <title>JSON Input - Qi Hop Documentation</title>
  <style>...</style>
</head>
<body>
  <header class="hop-header">...</header>
  <main class="hop-content">
    <!-- Markdown 渲染后的 HTML 内容 -->
  </main>
  <footer class="hop-footer">...</footer>
</body>
</html>
```

**失败响应** (404 Not Found):

```html
<!DOCTYPE html>
<html>
<head>...</head>
<body>
  <div class="hop-error">Document not found</div>
</body>
</html>
```

**适用场景**: 在浏览器中查看插件文档。

---

### 5.2 获取文档 (JSON 格式)

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取文档 (JSON 格式) |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/{path:.+}` |
| **Content-Type** | application/json |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| path | String | 是 | 文档路径或 URL |

**请求头**:

| 头名称 | 值 | 说明 |
|--------|-----|------|
| Accept | application/json | 指定返回 JSON 格式 |

**成功响应** (200 OK):

```json
{
  "status": "matched",
  "title": "JSON Input",
  "content": "# JSON Input Plugin\n\n## Overview\n...",
  "format": "markdown",
  "sourcePath": "03-转换插件/json-input.md",
  "pluginId": "json",
  "documentationUrl": "/pipeline/transforms/json-input.html"
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| status | String | 匹配状态: matched / unmatched / not_found / error |
| title | String | 文档标题 |
| content | String | Markdown 格式的文档内容 |
| format | String | 内容格式: markdown |
| sourcePath | String | 源文件相对路径 |
| pluginId | String | 插件 ID |
| documentationUrl | String | 文档 URL |

**未匹配响应** (200 OK):

```json
{
  "status": "unmatched",
  "pluginId": "unknown",
  "documentationUrl": "/unknown.html",
  "message": "Documentation not available locally",
  "fallbackUrl": "https://hop.apache.org/manual/next/unknown.html"
}
```

**未找到响应** (404 Not Found):

```json
{
  "status": "not_found",
  "documentationUrl": "/missing.html",
  "message": "Document not found"
}
```

**适用场景**: API 客户端获取文档内容进行自定义渲染。

---

### 5.3 获取所有 URL 映射

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取所有 URL 映射 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/mappings` |
| **Content-Type** | application/json |

**查询参数**:

| 参数名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| status | String | 否 | (空) | 按状态过滤: matched / unmatched |

**成功响应** (200 OK):

```json
{
  "mappings": [
    {
      "documentationUrl": "/pipeline/transforms/json-input.html",
      "mdRelativePath": "03-转换插件/json-input.md",
      "status": "matched",
      "pluginId": "json"
    }
  ],
  "count": 244
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| mappings | Object[] | URL 映射数组 |
| mappings[].documentationUrl | String | 文档 URL |
| mappings[].mdRelativePath | String | 本地 MD 文件路径 |
| mappings[].status | String | 匹配状态 |
| mappings[].pluginId | String | 插件 ID |
| count | int | 映射总数 |

**适用场景**: 获取所有文档映射关系，用于管理和统计。

---

### 5.4 获取映射统计

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取映射统计 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/stats` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
{
  "total": 400,
  "matched": 244,
  "unmatched": 156
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| total | int | 总映射数 |
| matched | int | 已匹配数 |
| unmatched | int | 未匹配数 |

**适用场景**: 获取文档映射统计信息，用于监控文档覆盖率。

---

### 5.5 重新加载映射表

| 属性 | 值 |
|------|-----|
| **接口名称** | 重新加载映射表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/reload` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
{
  "reloaded": true,
  "stats": {
    "total": 400,
    "matched": 244,
    "unmatched": 156
  }
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| reloaded | boolean | 是否成功重新加载 |
| stats | Object | 重新加载后的统计信息 |

**适用场景**: 在文档文件更新后重新加载映射表。

---

### 5.6 获取静态资源

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取静态资源 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/docs/assets/{path:.+}` |
| **Content-Type** | image/svg+xml / image/png / image/jpeg / image/gif |

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| path | String | 是 | 资源文件路径 |

**成功响应** (200 OK):

返回二进制图片数据

**失败响应** (404 Not Found):

```
Not Found
```

**适用场景**: 获取文档中引用的图片资源。

---

## 6. 知识库模块 (KnowledgeBase)

### 6.1 搜索知识库

| 属性 | 值 |
|------|-----|
| **接口名称** | 搜索知识库 |
| **请求方法** | POST |
| **URL路径** | `/api/v1/knowledgebase/search` |
| **Content-Type** | application/json |

**请求体** (JSON):

```json
{
  "query": "如何使用 JSON 输入插件",
  "topK": 5
}
```

**请求体字段说明**:

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| query | String | 是 | 搜索查询词 |
| topK | Integer | 否 | 返回结果数量，默认使用服务端默认值 |

**成功响应** (200 OK):

```json
{
  "results": [
    {
      "filePath": "03-转换插件/json-input.md",
      "section": "Usage",
      "content": "To use the JSON Input transform, configure the file path...",
      "score": 0.95
    }
  ]
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| results | Object[] | 搜索结果数组 |
| results[].filePath | String | 源文件路径 |
| results[].section | String | 所属章节 |
| results[].content | String | 文本内容片段 |
| results[].score | float | 相似度分数 (0-1) |

**失败响应** (400 Bad Request):

```
Query is required
```

**适用场景**: 在知识库中搜索相关文档片段，用于 RAG 问答系统。

---

### 6.2 构建索引

| 属性 | 值 |
|------|-----|
| **接口名称** | 构建索引 |
| **请求方法** | POST |
| **URL路径** | `/api/v1/knowledgebase/index` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
{
  "enabled": true,
  "indexed": true,
  "chunks": 1500,
  "message": "Index built successfully"
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| enabled | boolean | RAG 是否启用 |
| indexed | boolean | 索引是否已构建 |
| chunks | long | 向量库中的 chunk 数量 |
| message | String | 状态消息 |

**失败响应** (500 Internal Server Error):

```
Error building knowledge base index
```

**适用场景**: 初始化或重建知识库索引。

---

### 6.3 获取索引状态

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取索引状态 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/knowledgebase/status` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
{
  "enabled": true,
  "indexed": true,
  "chunks": 1500,
  "message": "Index is up to date"
}
```

**响应字段说明**: 同构建索引响应。

**失败响应** (500 Internal Server Error):

```
Error getting knowledge base status
```

**适用场景**: 查询知识库索引的当前状态。

---

### 6.4 清空索引

| 属性 | 值 |
|------|-----|
| **接口名称** | 清空索引 |
| **请求方法** | DELETE |
| **URL路径** | `/api/v1/knowledgebase` |
| **Content-Type** | application/json |

**请求参数**: 无

**成功响应** (200 OK):

```json
{
  "deleted": true
}
```

**响应字段说明**:

| 字段 | 类型 | 说明 |
|------|------|------|
| deleted | boolean | 是否成功删除 |

**失败响应** (500 Internal Server Error):

```
Error clearing knowledge base
```

**适用场景**: 清空所有索引数据，准备重新构建。

---

## 7. Carte 服务模块 (Deprecated)

> **注意**: 此模块自 2.18.0 版本起已废弃，仅供兼容性参考。

### 7.1 获取系统信息

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取系统信息 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/systemInfo` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
{
  "statusDescription": "Online"
}
```

---

### 7.2 获取配置详情

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取配置详情 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/configDetails` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
[
  {"name": "maxLogLines", "value": "1000"},
  {"name": "maxLogLinesAge", "value": "60"},
  {"name": "maxObjectsAge", "value": "30"},
  {"name": "configFile", "value": "/path/to/config.xml"}
]
```

---

### 7.3 获取 Pipeline 列表

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取 Pipeline 列表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/pipelines` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
[
  {
    "id": "uuid-123",
    "name": "MyPipeline",
    "containerId": "server"
  }
]
```

---

### 7.4 获取 Pipeline 详细状态

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取 Pipeline 详细状态 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/pipelines/detailed` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
[
  {
    "id": "uuid-123",
    "name": "MyPipeline",
    "status": "Running",
    "transformStatuses": []
  }
]
```

---

### 7.5 获取 Workflow 列表

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取 Workflow 列表 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/workflows` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
[
  {
    "id": "uuid-456",
    "name": "MyWorkflow",
    "containerId": "server"
  }
]
```

---

### 7.6 获取 Workflow 详细状态

| 属性 | 值 |
|------|-----|
| **接口名称** | 获取 Workflow 详细状态 |
| **请求方法** | GET |
| **URL路径** | `/api/v1/carte/workflows/detailed` |
| **Content-Type** | application/json |

**成功响应** (200 OK):

```json
[
  {
    "id": "uuid-456",
    "name": "MyWorkflow",
    "status": "Finished"
  }
]
```

---

### 7.7 Pipeline 操作接口

| 操作 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 获取日志 | GET | `/api/v1/carte/pipeline/log/{id}` | 获取指定 Pipeline 的日志 |
| 获取日志 (从指定行) | GET | `/api/v1/carte/pipeline/log/{id}/{logStart}` | 从指定行号开始获取日志 |
| 获取状态 | GET | `/api/v1/carte/pipeline/status/{id}` | 获取 Pipeline 状态 |
| 启动 | GET | `/api/v1/carte/pipeline/start/{id}` | 启动 Pipeline |
| 准备 | GET | `/api/v1/carte/pipeline/prepare/{id}` | 准备执行 |
| 暂停 | GET | `/api/v1/carte/pipeline/pause/{id}` | 暂停执行 |
| 恢复 | GET | `/api/v1/carte/pipeline/resume/{id}` | 恢复执行 |
| 停止 | GET | `/api/v1/carte/pipeline/stop/{id}` | 停止执行 |
| 删除 | GET | `/api/v1/carte/pipeline/remove/{id}` | 移除 Pipeline |
| 清理 | GET | `/api/v1/carte/pipeline/cleanup/{id}` | 清理资源 |
| 添加 | PUT | `/api/v1/carte/pipeline/add` | 添加 Pipeline (XML 格式) |

---

### 7.8 Workflow 操作接口

| 操作 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 获取日志 | GET | `/api/v1/carte/workflow/log/{id}` | 获取指定 Workflow 的日志 |
| 获取日志 (从指定行) | GET | `/api/v1/carte/workflow/log/{id}/{logStart}` | 从指定行号开始获取日志 |
| 获取状态 | GET | `/api/v1/carte/workflow/status/{id}` | 获取 Workflow 状态 |
| 启动 | GET | `/api/v1/carte/workflow/start/{id}` | 启动 Workflow |
| 停止 | GET | `/api/v1/carte/workflow/stop/{id}` | 停止执行 |
| 删除 | GET | `/api/v1/carte/workflow/remove/{id}` | 移除 Workflow |
| 添加 | PUT | `/api/v1/carte/workflow/add` | 添加 Workflow (XML 格式) |

---

## 附录

### A. 状态码汇总

| 状态码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 404 | 资源未找到 |
| 500 | 服务器内部错误 |

### B. Content-Type 约定

| 类型 | 用途 |
|------|------|
| application/json | API 请求/响应主体 |
| text/plain | 错误信息、日志内容 |
| text/html | 文档渲染结果 |
| image/svg+xml / image/png / image/jpeg / image/gif | 静态图片资源 |

### C. 模块分类速查

| 模块 | 基础路径 | 核心功能 |
|------|----------|----------|
| Metadata | `/api/v1/metadata` | 元数据 CRUD |
| Execution | `/api/v1/execute` | Pipeline 同步执行 |
| Location | `/api/v1/location` | 执行信息管理 |
| Plugins | `/api/v1/plugins` | 插件信息查询 |
| Docs | `/api/v1/docs` | 文档服务 |
| KnowledgeBase | `/api/v1/knowledgebase` | RAG 知识库 |
| Carte | `/api/v1/carte` | 旧版服务 (Deprecated) |
