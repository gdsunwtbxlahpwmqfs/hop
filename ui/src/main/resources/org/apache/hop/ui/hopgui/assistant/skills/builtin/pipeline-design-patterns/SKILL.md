---
name: pipeline-design-patterns
description: >-
  Use when designing or building a pipeline. Covers ETL/ELT patterns,
  transform ordering best practices, error handling, and data flow design.
hop_category: pipeline
hop_enabled: false
hop_trigger: manual
hop_priority: 10
hop_source: builtin
hop_version: "1.0.0"
hop_usage_count: 0
---

# Pipeline 设计模式指南

## 常见 ETL 模式

### 1. 标准提取-转换-加载
```
[Input] → [Select Values] → [Filter] → [Calculator] → [Output]
```

### 2. 增量加载 (CDC)
```
[Table Input + 变量过滤] → [Insert/Update]
```
使用 `${LAST_RUN_TIME}` 变量记录上次执行时间。

### 3. 多源合并
```
[Source A] ─┐
[Source B] ─┼→ [Merge Rows] → [Output]
[Source C] ─┘
```

### 4. 错误处理模式
```
[Input] → [Transform]
            ├── 主流 → [Output]
            └── 错误流 → [Error Output]
```
利用 Transform 的 "error handling" 配置分离错误数据。

## 最佳实践
- 大数据量优先使用 **Stream Lookup** 而非 **Database Join**
- 写入数据库使用 **Bulk Loader** 而非 **Table Output**
- 内存敏感场景使用 **Blocking Transform** 控制内存
- 使用 **变量** 参数化连接信息和文件路径
