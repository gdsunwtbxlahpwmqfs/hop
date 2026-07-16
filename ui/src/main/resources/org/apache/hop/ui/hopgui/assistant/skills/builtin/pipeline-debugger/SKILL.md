---
name: pipeline-debugger
description: >-
  Use when debugging pipeline execution failures, analyzing error logs,
  or diagnosing data quality issues. Includes common error patterns and fixes.
hop_category: pipeline
hop_enabled: false
hop_trigger: manual
hop_priority: 8
hop_source: builtin
hop_version: "1.0.0"
hop_usage_count: 0
---

# Pipeline 调试排错指南

## 常见错误诊断

### 1. 数据类型不匹配
- **症状**: `ClassCastException` 或 `NumberFormatException`
- **排查**: 检查上游 Transform 输出字段类型与下游期望类型
- **修复**: 添加 **Select Values** 做类型转换

### 2. 内存溢出 (OOM)
- **症状**: `OutOfMemoryError`
- **排查**: 查看是否有无限制缓存（如 Sorted Merge 数据量过大）
- **修复**:
  - 增大 JVM 堆内存
  - 使用 **Sorted Merge** 替代内存排序（需输入已排序）
  - 分批处理：增加 **Blocking Transform** 控制行数

### 3. 数据库连接超时
- **症状**: `Connection timed out` 或连接池耗尽
- **排查**: 检查连接池大小、网络、数据库负载
- **修复**: 调整 `HOP_DATABASE_POOL_*` 参数

### 4. Transform 卡住不动
- **排查**: 检查是否有 **Blocking Transform** 未触发、单线程瓶颈
- **修复**: 检查 Copy/Distribute 设置，调整并发数

## 日志分析技巧
- 设置日志级别为 `Detailed` 或 `Debug` 查看数据流
- 查看 `Sample rows` 输出定位数据问题
- 使用 `Write to Log` Transform 输出中间数据
