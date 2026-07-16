---
name: performance-tuning
description: >-
  Use when optimizing pipeline performance. Covers parallelism,
  database write tuning, memory optimization, and benchmarking.
hop_category: pipeline
hop_enabled: false
hop_trigger: manual
hop_priority: 10
hop_source: builtin
hop_version: "1.0.0"
hop_usage_count: 0
---

# Pipeline 性能调优指南

## 数据库写入优化

### 批量提交
| Transform | 推荐设置 |
|---|---|
| Table Output | `Use batch insert` → 1000-5000 行/批 |
| Insert/Update | 仅小数据量使用，大数据量改为 Delete+Output |
| Bulk Loader | 大数据量首选（PG/MySQL/Oracle 均有专用插件） |

### 连接池配置
- 设置 `Initial pool size = Max pool size`（避免动态扩容开销）
- 写密集型：池大小 = 并发 Transform 数 × 2

## 并行化策略

### Transform 级别
- **Copy data to next steps**: 一个 Transform 实例输出多路
- **Launch next copies in parallel**: 并行执行多个副本
- 并行副本数建议 ≤ CPU 核心数

### Pipeline 级别
- 使用 **Cartesian Product** 避免无意义全连接
- 利用 **Sorted Merge** 替代 Blocking Transform 实现有序合并

## 内存优化
- 使用 **Stream Lookup** 替代 **Database Lookup**（大数据量）
- 使用 **Memory Group By** 替代 **Group By**（内存足够时）
- 设置 Transform 的 `Memory threshold` 限制缓存大小

## 性能分析
- 使用 **Execution Results** 面板查看每个 Transform 的吞吐量
- 关注: `Read`, `Written`, `Input`, `Output`, `Rejected` 行数
- 吞吐量瓶颈通常在读 IO 或写 IO，非计算
