# hfxt data process 知识库系统设计方案

> 基于 `docs/hop-assistant-manual` 语料，为现有 LLM 助理提供 RAG 增强能力。

## 1. 目标与范围

### 1.1 目标
为 Hop UI 中的 `LlmAssistantDialog` 聊天面板增加 RAG（检索增强生成）能力：用户提问时，系统先从 `docs/hop-assistant-manual` 知识库中检索相关文档片段，再将其注入 system prompt，让 LLM 基于文档给出准确回答。

### 1.2 范围
- **包含**：文档切分与索引、Qdrant 向量存储、LiteLLM Embedding 客户端、检索服务、REST API（管理/查询）、LlmAssistantDialog RAG 集成、Docker 部署。
- **不包含**：多租户、权限控制、增量更新（首版全量重建）、非中文语料优化。

## 2. 技术选型

| 组件 | 选型 | 理由 |
|------|------|------|
| 向量数据库 | **Qdrant** | 单 Docker 容器，Java SDK 官方维护，与现有 docker-compose 无缝集成 |
| Embedding | **LiteLLM `/v1/embeddings`** | 复用已配置的 LiteLLM 代理，无需额外 AI 网关 |
| Chat | **复用现有 `LlmClient`** | 已支持 OpenAI 兼容协议与流式响应 |
| HTTP | **JDK 21 `java.net.http.HttpClient`** | 与 `LlmClient` 风格一致，无第三方依赖 |
| JSON | **Jackson + `HopJson.newMapper()`** | 全项目统一 |
| REST | **Jersey / JAX-RS**（rest 模块扩展） | 现有成熟范式 |
| 配置 | **环境变量 + 系统属性** | 沿用 `LlmAssistantConfig` 的 `HOP_LLM_*` 风格 |

## 3. 系统架构

```
┌─────────────────────────────────────────────────────────────┐
│                        HopGui (UI)                          │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  LlmAssistantDialog (RAG 增强)                       │   │
│  │   用户提问 → KnowledgeBaseClient.search(query)       │   │
│  │            → 组装 system prompt → LlmClient.stream   │   │
│  └──────────────┬───────────────────────────────────────┘   │
└─────────────────┼───────────────────────────────────────────┘
                  │ HTTP (内嵌 REST 或直连服务)
                  ▼
┌─────────────────────────────────────────────────────────────┐
│              rest/ 模块: KnowledgeBaseResource              │
│   POST /api/v1/knowledgebase/search   ← 检索                │
│   POST /api/v1/knowledgebase/index     ← 索引构建           │
│   GET  /api/v1/knowledgebase/status    ← 状态查询           │
│   DELETE /api/v1/knowledgebase         ← 清空重建           │
└────────┬──────────────────────────────┬─────────────────────┘
         │                              │
         ▼                              ▼
┌─────────────────────┐     ┌─────────────────────────────────┐
│  KnowledgeBaseService│     │  EmbeddingClient                │
│  (检索 + prompt 组装)│     │  POST LiteLLM /v1/embeddings    │
│  - topK 检索         │     └─────────────────────────────────┘
│  - 结果过滤          │
└────────┬─────────────┘
         │
         ▼
┌─────────────────────┐     ┌─────────────────────────────────┐
│  Qdrant Vector Store│◄────┤  DocumentIndexer                │
│  collection: hop_kb │     │  - Markdown 切分                │
│  ~380 docs, ~5000   │     │  - 批量 embedding               │
│  chunks             │     │  - 写入 Qdrant                  │
└─────────────────────┘     └─────────────────────────────────┘
```

## 4. 模块设计

### 4.1 新增代码位置

新增一个独立 Maven 模块 `plugins/tech/knowledge-base/`，作为知识库核心逻辑的承载。REST 端点扩展在现有 `rest/` 模块中。

```
plugins/tech/knowledge-base/
├── pom.xml
└── src/main/java/org/apache/hop/knowledgebase/
    ├── config/
    │   └── KnowledgeBaseConfig.java        # 环境变量配置
    ├── model/
    │   ├── KnowledgeChunk.java             # 文档片段 record
    │   ├── SearchResult.java               # 检索结果 record
    │   └── IndexStatus.java                # 索引状态 record
    ├── embedding/
    │   ├── EmbeddingClient.java            # LiteLLM embedding 客户端
    │   └── EmbeddingException.java
    ├── store/
    │   ├── VectorStore.java                # 向量存储接口
    │   └── qdrant/
    │       ├── QdrantVectorStore.java      # Qdrant 实现
    │       └── QdrantClient.java           # Qdrant HTTP 客户端
    ├── indexer/
    │   ├── DocumentSplitter.java           # Markdown 切分器
    │   └── DocumentIndexer.java            # 索引构建器
    └── service/
        └── KnowledgeBaseService.java       # 检索 + prompt 组装门面
```

### 4.2 核心组件职责

#### 4.2.1 `KnowledgeBaseConfig`
沿用 `LlmAssistantConfig` 模式，从环境变量读取配置：
- `HOP_KB_ENABLED`（默认 false）— 是否启用 RAG
- `HOP_KB_QDRANT_URL`（默认 `http://localhost:6333`）— Qdrant 地址
- `HOP_KB_QDRANT_COLLECTION`（默认 `hop_kb`）— 集合名
- `HOP_KB_EMBEDDING_MODEL`（默认复用 LiteLLM，模型名如 `text-embedding-3-small`）
- `HOP_KB_DOCS_PATH`（默认 `docs/hop-assistant-manual`）— 语料目录
- `HOP_KB_CHUNK_SIZE`（默认 800）— 切分字符数
- `HOP_KB_CHUNK_OVERLAP`（默认 150）— 重叠字符数
- `HOP_KB_TOP_K`（默认 5）— 检索结果数

复用 `HOP_LLM_API_URL`、`HOP_LLM_API_KEY`（LiteLLM 代理地址和 key），embedding 端点为 `${HOP_LLM_API_URL}/embeddings`。

#### 4.2.2 `KnowledgeChunk`（record）
```java
record KnowledgeChunk(
    String id,            // 文件路径 + "#" + 段落序号 的 hash
    String filePath,      // 相对路径，如 "03-转换插件/输入类/表输入.md"
    String section,       // 所属标题，如 "表输入（Table Input）"
    int chunkIndex,       // 该文件内片段序号
    String content,       // 片段文本
    Map<String,String> metadata  // 分类、类别等
) {}
```

#### 4.2.3 `EmbeddingClient`
- 调用 `${HOP_LLM_API_URL}/embeddings`（OpenAI 兼容格式）
- 支持单条与批量（`List<String> → float[][]`）
- 使用 `java.net.http.HttpClient` + Jackson
- 复用 `HOP_LLM_API_KEY`

#### 4.2.4 `VectorStore` 接口 + `QdrantVectorStore`
```java
interface VectorStore {
    void ensureCollection(int vectorSize);
    void upsert(List<KnowledgeChunk> chunks, List<float[]> vectors);
    List<SearchResult> search(float[] queryVector, int topK);
    void deleteAll();
    long count();
}
```
`QdrantVectorStore` 通过 Qdrant REST API（`/collections`、`/points`）实现，无需额外 SDK 依赖（Qdrant 的 HTTP API 完整支持所有操作）。

#### 4.2.5 `DocumentSplitter`
针对 Markdown 优化：
1. 按 `##` / `###` 标题切分为段落
2. 超长段落按 `chunkSize`（默认 800 字）二次切分，保留 `chunkOverlap`（默认 150 字）重叠
3. 保留标题层级作为 `section` 字段
4. 提取文件路径中的分类信息（如 `03-转换插件/输入类`）作为 metadata

#### 4.2.6 `DocumentIndexer`
- 扫描 `HOP_KB_DOCS_PATH` 下所有 `.md` 文件
- 调用 `DocumentSplitter` 切分
- 批量调用 `EmbeddingClient`（每批 64 条）
- 调用 `VectorStore.upsert` 写入

#### 4.2.7 `KnowledgeBaseService`
门面类，提供：
- `List<SearchResult> search(String query)` — 检索
- `String buildContextPrompt(String query)` — 检索并组装为 context 文本，供注入 system prompt
- `IndexStatus index()` — 触发全量索引
- `IndexStatus status()` — 查询状态
- `void clear()` — 清空重建

### 4.3 REST API 接口

在 `rest/src/main/java/org/apache/hop/rest/v1/resources/` 下新增 `KnowledgeBaseResource.java`：

| 方法 | 路径 | 请求体 | 响应 |
|------|------|--------|------|
| POST | `/api/v1/knowledgebase/search` | `{"query": "...", "topK": 5}` | `{"results": [SearchResult...]}` |
| POST | `/api/v1/knowledgebase/index` | `{"force": false}` | `{"status": "completed", "chunks": 5000, "duration": 120}` |
| GET | `/api/v1/knowledgebase/status` | — | `{"enabled": true, "indexed": true, "chunks": 5000}` |
| DELETE | `/api/v1/knowledgebase` | — | `{"deleted": true}` |

### 4.4 LlmAssistantDialog RAG 集成

修改 `LlmAssistantDialog.java` 的 `sendMessage` 流程：
1. 检查 `KnowledgeBaseConfig.isEnabled()`
2. 若启用，在调用 `LlmClient.streamChat` 前，先调用 `KnowledgeBaseService.search(query)`
3. 将检索结果格式化为 context 文本，追加到 system prompt 末尾
4. 格式：`"以下是与用户问题相关的参考文档：\n\n[1] {filePath}\n{content}\n\n[2] ..."`

## 5. 数据处理流程

### 5.1 索引构建流程
```
docs/hop-assistant-manual/*.md
    │
    ▼ DocumentSplitter
    │  → 按标题切分
    │  → 超长段落按 800 字 + 150 字重叠切分
    │  → 提取 metadata（分类、标题）
    │
    ▼ EmbeddingClient (批量，每批 64 条)
    │  → POST LiteLLM /v1/embeddings
    │
    ▼ QdrantVectorStore.upsert
    │  → PUT /collections/hop_kb
    │  → POST /collections/hop_kb/points
```

### 5.2 检索流程
```
用户提问
    │
    ▼ EmbeddingClient.embed(query) → float[]
    │
    ▼ QdrantVectorStore.search(vector, topK=5)
    │  → POST /collections/hop_kb/points/search
    │
    ▼ 过滤 score < 0.3 的低质量结果
    │
    ▼ 格式化为 context prompt
    │
    ▼ 注入 system prompt → LlmClient.streamChat
```

## 6. 部署方案

### 6.1 Docker Compose 扩展

在 `docker-compose.dev.yml` 中新增 `qdrant` 服务：

```yaml
services:
  qdrant:
    image: qdrant/qdrant:v1.12.4
    ports:
      - "6333:6333"
      - "6334:6334"
    volumes:
      - qdrant-data:/qdrant/storage
    environment:
      - QDRANT__LOG_LEVEL=INFO

volumes:
  qdrant-data:
```

### 6.2 环境变量配置

在 `docker/web-local.Dockerfile` 和 `docker-compose.dev.yml` 中追加：

```env
HOP_KB_ENABLED=true
HOP_KB_QDRANT_URL=http://qdrant:6333
HOP_KB_DOCS_PATH=/opt/hop/docs/hop-assistant-manual
HOP_KB_EMBEDDING_MODEL=text-embedding-3-small
```

## 7. 错误处理

- **Qdrant 不可达**：检索降级为空（不注入 context），记录 warning，聊天继续
- **Embedding API 失败**：索引构建中断并返回错误；检索时降级为空
- **配置未启用**：`KnowledgeBaseService` 所有方法静默返回空结果，不影响现有行为

## 8. 测试策略

- **单元测试**：`DocumentSplitter`（切分正确性）、`EmbeddingClient`（mock HTTP）、`QdrantVectorStore`（mock HTTP）
- **集成测试**：使用 Testcontainers 启动 Qdrant，端到端验证索引与检索
- **手动验证**：构建索引后，在 LlmAssistantDialog 中提问验证 RAG 效果

## 9. 开发步骤

1. 创建 `plugins/tech/knowledge-base/` Maven 模块骨架
2. 实现 `KnowledgeBaseConfig`
3. 实现 `EmbeddingClient`
4. 实现 `DocumentSplitter` + 单元测试
5. 实现 `VectorStore` 接口 + `QdrantVectorStore`
6. 实现 `DocumentIndexer`
7. 实现 `KnowledgeBaseService`
8. 在 `rest/` 模块新增 `KnowledgeBaseResource`
9. 修改 `LlmAssistantDialog` 集成 RAG
10. 扩展 `docker-compose.dev.yml` 和 Dockerfile
11. 端到端测试与验证
