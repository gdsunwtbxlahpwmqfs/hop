# 智能化 ETL Pipeline 生成方案

## 1. 项目背景与目标

Qi Hop data processor 是一个数据/元数据编排平台，支持通过拖拽方式在画布上构建 ETL Pipeline。目前 Qi Hop data processor 已具备：

- 150+ Transform 插件（输入、输出、转换、脚本等）
- 拖拽式画布编辑器（桌面 RCP / 网页 RAP 双端）
- LLM 对话助手（LlmAssistant）+ RAG 知识库（Qdrant）
- LanguageModelChat Transform（在 pipeline 中调用 LLM）

**核心目标**：用户通过自然语言描述 ETL 需求，AI 自动生成完整 Pipeline，并以流式逐步渲染的方式呈现构建过程，生成完成后用户可自由编辑修改。

### 关键约束

- **双端支持**：同时适配 RCP（SWT 桌面端）和 RAP（Web 端）UI 框架
- **输入方式**：自然语言对话框，用户输入中文/英文描述
- **渲染粒度**：组件级实时渲染 + AI 规划时显示进度状态
- **干预程度**：完全自动生成，生成后自由编辑修改

## 2. 用户交互流程

```
1. 用户点击「AI 生成」按钮（工具栏或画布上下文）
2. 弹出聊天对话框（AIPipelineGeneratorDialog）
3. 用户输入自然语言需求
   「从 PostgreSQL 读取订单表，过滤掉金额<=0 的记录，
    按用户ID分组汇总，结果写入 ClickHouse」
4. AI 开始生成：
   ├─ 对话框显示推理过程：「正在分析需求...需要: Table Input → Filter → Group By → Output」
   ├─ [画布] 出现 "Table Input" 组件
   ├─ [画布] 出现 "Filter Rows" 组件 + 连线
   ├─ [画布] 出现 "Group By" 组件 + 连线
   ├─ [画布] 出现 "ClickHouse Output" 组件 + 连线
   └─ 对话框提示：「Pipeline 已生成完毕，您可以拖拽调整」
5. 用户在画布上自由编辑、修改、运行
6. 支持 Ctrl+Z 逐步回退 AI 的每一步操作
```

## 3. 系统架构

### 3.1 模块划分

```
hop-ui (ui/)
├── assistant/
│   ├── AIPipelineGenerator.java              ← 核心生成器（编排整个流程）
│   ├── AIPipelineGeneratorDialog.java        ← 生成对话框（SWT 组件）
│   ├── AIPipelineGeneratorFacade.java        ← 抽象门面（RAP/RCP 双端适配）
│   ├── PipelineStreamParser.java             ← 流式结果解析器
│   ├── PipelineIncrementalBuilder.java       ← 增量构建器
│   ├── TransformCatalog.java                 ← Transform 目录索引
│   ├── GenerationProgressEvent.java          ← 进度事件
│   └── GenerationProgressListener.java       ← 进度监听器接口

hop-rap (rap/)
├── ...
└── AIPipelineGeneratorFacadeImpl.java        ← RAP 实现（ServerPush + Canvas 推送）

hop-rcp (rcp/)
├── ...
└── AIPipelineGeneratorFacadeImpl.java        ← RCP 实现（直接操作 SWT Canvas）
```

### 3.2 核心架构图

```
┌─────────────────────────────────────────────────────────────┐
│  Hop GUI (RCP / RAP)                                          │
│  ┌──────────────────────┐  ┌──────────────────────────────┐  │
│  │ AIPipelineGenerator  │  │ Pipeline Canvas               │  │
│  │ Dialog               │  │  - PipelinePainter (RCP)     │  │
│  │  - 聊天输入框          │  │  - canvas.js (RAP)          │  │
│  │  - 推理过程流式显示     │  │  - 增量渲染                  │  │
│  │  - 进度状态指示        │  │  - 高亮最新添加组件           │  │
│  └──────────┬───────────┘  └──────────────┬───────────────┘  │
│             │                              ▲                   │
└─────────────┼──────────────────────────────┼───────────────────┘
              │                              │
     ┌────────▼──────────────────────────────┴───────────┐
     │  AIPipelineGenerator (核心服务层)                    │
     │  ┌──────────────┐  ┌──────────────┐  ┌──────────┐  │
     │  │StreamParser  │  │PipelineBuilder│  │UndoRecorder│  │
     │  │ (流式解析)    │  │ (增量构建)    │  │ (操作记录)  │  │
     │  └──────┬───────┘  └──────┬───────┘  └──────────┘  │
     └─────────┼────────────────┼──────────────────────────┘
               │                │
     ┌─────────▼────────────────▼──────────────────────────┐
     │  LLM Service Layer (复用现有 LlmClient)              │
     │  - 流式 SSE + Function Calling / Tool Use            │
     │  - Transform Catalog (插件清单 => 系统提示词)          │
     │  - 支持 OpenAI / Anthropic / Ollama 等               │
     │  - 可选 RAG 增强                                     │
     └────────────────────────────────────────────────────┘
```

### 3.3 核心组件职责

#### TransformCatalog

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/TransformCatalog.java`
- **职责**：启动时从 `PluginRegistry.getPlugins(TransformPluginType.class)` 扫描所有 Transform 插件，构建结构化目录供 LLM 引用
- **数据模型**：

```json
{
  "transforms": [
    {
      "id": "CsvInput",
      "name": "CSV file input",
      "category": "Input",
      "description": "Read data from a CSV file",
      "keywords": ["csv", "file", "input", "text"],
      "supportedEngines": ["Local", "Beam"],
      "maxInputs": 0,
      "maxOutputs": 1
    }
  ]
}
```

- 支持按 `category`、`keywords` 检索
- 输出为 JSON 格式，通过 system prompt 或 RAG 提供给 LLM
- 在 Hop 启动时懒加载，缓存到内存

#### AIPipelineGenerator

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/AIPipelineGenerator.java`
- **职责**：编排整个 AI 生成流程

```java
public class AIPipelineGenerator {
    private final LlmClient llmClient;
    private final PipelineStreamParser streamParser;
    private final PipelineIncrementalBuilder builder;
    private final TransformCatalog catalog;

    /**
     * 启动 AI 生成流程
     * @param pipeline  当前编辑的 PipelineMeta（副本）
     * @param userInput 用户自然语言输入
     */
    public void generate(PipelineMeta pipeline, String userInput);

    /**
     * 暂停生成
     */
    public void pause();

    /**
     * 停止生成
     */
    public void stop();
}
```

- 流程：
  1. 从 `TransformCatalog` 获取目录 JSON
  2. 构建 system prompt（目录 + 约束 + 可用函数）
  3. 调用 `LlmClient` 发起流式对话请求
  4. 逐 chunk 交给 `PipelineStreamParser` 解析
  5. 将完整动作交给 `PipelineIncrementalBuilder` 执行
  6. 派发 `GenerationProgressEvent` 给 UI 更新

#### PipelineStreamParser

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/PipelineStreamParser.java`
- **职责**：从 LLM 流式输出中提取结构化动作

支持两种模式（主 + 备）：

**模式 A — Function Calling（首选）**：

LLM 返回 tool\_calls，每个 call 对应一个 pipeline 操作：

```
tool_call: add_transform(name="Read CSV", type="CsvInput", x=100, y=200)
tool_call: add_hop(from="Read CSV", to="Filter Rows")
tool_call: done()
```

**模式 B — 结构化文本标记（备选）**：

```
##ACTION: add_transform
name: Read CSV
type: CsvInput
x: 100
y: 200
---
```

- 解析完一个完整动作后回调 `PipelineIncrementalBuilder`
- 支持增量解析：即使 chunk 边界在动作中间也能正确处理

#### PipelineIncrementalBuilder

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/PipelineIncrementalBuilder.java`
- **职责**：对 `PipelineMeta` 执行增量操作

```java
public class PipelineIncrementalBuilder {
    private final PipelineMeta pipeline;
    private final HopGuiPipelineUndoDelegate undoDelegate;

    /**
     * 添加一个 transform 组件
     */
    public TransformMeta addTransform(String name, String pluginId, int x, int y);

    /**
     * 在两个组件之间建立连线
     */
    public PipelineHopMeta addHop(String fromName, String toName);

    /**
     * 设置组件位置
     */
    public void setPosition(String name, int x, int y);

    /**
     * 标记生成完成
     */
    public void markDone();

    /**
     * 获取当前 AI 生成的所有操作列表（用于批量撤销）
     */
    public List<GenerationAction> getActions();
}
```

- 每个操作通过 `AbstractMeta.addUndo()` 记录到 undo 栈（`TYPE_UNDO_NEW`）
- 每个操作完成后触发 `GenerationProgressEvent`

#### AIPipelineGeneratorDialog

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/AIPipelineGeneratorDialog.java`
- **SWT 对话框组件**，包含：
  - 消息列表区域（用户消息 + AI 推理过程 + 构建日志）
  - 文本输入框 + 发送按钮
  - 进度指示器（生成中 / 暂停 / 完成状态）
  - 停止生成按钮

#### AIPipelineGeneratorFacade

- **位置**：`ui/src/main/java/org/apache/hop/ui/hopgui/assistant/AIPipelineGeneratorFacade.java`
- **抽象门面**，隔离 RAP/RCP 的差异

```java
public abstract class AIPipelineGeneratorFacade {
    /**
     * 获取当前画布的 PipelineMeta
     */
    public abstract PipelineMeta getPipelineMeta();

    /**
     * 触发画布增量重绘
     */
    public abstract void requestRedraw();

    /**
     * 高亮显示指定组件
     */
    public abstract void highlightTransform(String name);

    /**
     * 打开生成对话框
     */
    public abstract void openGeneratorDialog();
}
```

- **RCP 实现**：直接操作 `HopGuiPipelineGraph`，调用 `canvas.redraw()`
- **RAP 实现**：通过 `CanvasFacade.setData()` 推送状态 + `ServerPushSession` 主动刷新

## 4. AI 集成设计

### 4.1 现有基础设施复用

| 现有组件                      | 文件位置                                       | 复用方式                                 |
| ------------------------- | ------------------------------------------ | ------------------------------------ |
| `LlmClient`               | `ui/.../assistant/LlmClient.java`          | 复用流式 SSE 请求能力，新增 function calling 支持 |
| `LlmAssistantConfig`      | `ui/.../assistant/LlmAssistantConfig.java` | 复用 LLM 配置（API URL / Key / Model）     |
| `KnowledgeBaseService`    | `ui/.../assistant/knowledgebase/`          | 可选：为 transform 文档做 RAG 检索            |
| `LanguageModelChatMeta` 等 | `plugins/transforms/languagemodelchat/`    | 参考其 LangChain4j 集成模式（如函数调用格式）        |

### 4.2 LLM Prompt 设计

#### System Prompt 模板

```
你是一个 Apache Hop ETL Pipeline 生成助手。
你的任务是根据用户的需求，生成一个包含多个 Transform 组件的 Pipeline。

可选组件目录：
{transform_catalog_json}

约束规则：
1. 每次添加一个组件或连接线，使用一个 function call
2. 严格遵循数据流方向：输入类组件 → 处理类组件 → 输出类组件
3. 为每个组件选择合适的画布位置（从左到右布局）
4. 用 add_transform 函数添加组件，x 表示水平位置，y 表示垂直位置
5. 用 add_hop 函数建立连线
6. 组件名称在 pipeline 内必须唯一
7. 所有组件添加完成后调用 done() 标记完成

可用函数：
- add_transform(name, type, x, y)
- add_hop(from, to)
- done()
```

#### Tool / Function 定义

```json
[
  {
    "name": "add_transform",
    "description": "在 pipeline 画布中添加一个 transform 组件",
    "parameters": {
      "type": "object",
      "properties": {
        "name": {"type": "string", "description": "组件实例名称，在 pipeline 内必须唯一"},
        "type": {"type": "string", "description": "组件类型 ID，来自上述组件目录"},
        "x": {"type": "integer", "description": "画布 X 坐标"},
        "y": {"type": "integer", "description": "画布 Y 坐标"}
      },
      "required": ["name", "type", "x", "y"]
    }
  },
  {
    "name": "add_hop",
    "description": "在两个组件之间建立数据连线",
    "parameters": {
      "type": "object",
      "properties": {
        "from": {"type": "string", "description": "源组件名称"},
        "to": {"type": "string", "description": "目标组件名称"}
      },
      "required": ["from", "to"]
    }
  },
  {
    "name": "done",
    "description": "标记 pipeline 生成完成，不再添加更多组件",
    "parameters": {"type": "object", "properties": {}}
  }
]
```

### 4.3 自动布局策略

LLM 生成的坐标由 `PipelineIncrementalBuilder` 的自动布局算法辅助：

```
1. 将 transforms 按 categories 分层：
   Layer 0 (输入层): x=100, categories=[Input, Lookup]
   Layer 1 (处理层): x=400, categories=[Transform, Scripting, Flow, Joins]
   Layer 2 (输出层): x=700, categories=[Output, Mapping]

2. 同层内垂直排列：
   y = 100 + index * 100

3. 特殊流（错误流、Info 流）放置在组件下方 80px 处

4. 允许 LLM 通过 add_transform 的 x/y 参数覆盖默认位置
```

布局算法封装在 `PipelineIncrementalBuilder` 中。

### 4.4 支持模型

基于现有 `LlmClient` 的架构，天然支持：

| 模型                          | 支持方式                | 备注                     |
| --------------------------- | ------------------- | ---------------------- |
| OpenAI GPT-4o / GPT-4o-mini | 原生 Function Calling | 首选                     |
| Anthropic Claude 3.5 Sonnet | 原生 Tool Use         | 首选                     |
| Ollama (本地模型)               | 兼容 OpenAI API       | Function Calling 需模型支持 |
| Mistral AI                  | 兼容 OpenAI API       | Function Calling 需模型支持 |
| 其他 LiteLLM 代理模型             | 通过 LiteLLM 代理       | 使用 OpenAI 兼容格式         |

## 5. 流式增量渲染机制

### 5.1 核心渲染流程

```
LLM Streaming Response (SSE chunks)
        │
        ▼
  PipelineStreamParser.parse(chunk)
        │
        ▼
  提取完整动作（add_transform / add_hop / done）
        │
        ▼
  PipelineIncrementalBuilder.execute(action)
        │
        ├─ pipelineMeta.addTransform(...)
        ├─ pipelineMeta.addHop(...)
        └─ undoDelegate.addUndo(...)
        │
        ▼
  触发 GenerationProgressEvent（含新增/修改的组件信息）
        │
        ▼
  UI 层响应事件:
  ├─ RCP: timerExec(batchTimeout) → canvas.redraw() → PipelinePainter 重绘
  └─ RAP: CanvasFacade.setData() → JavaScript canvas.js 重绘
        │
        ▼
  对话框更新进度: "已部署 3/5 组件"
```

### 5.2 帧率优化（去抖批处理）

AI 可能在短时间内密集输出多个 function call。避免每次操作都触发全量重绘：

```java
// 在 PipelineIncrementalBuilder 中实现去抖逻辑
private final Timer batchTimer = new Timer(30, e -> {
    // 30ms 窗口内收集的变更统一触发重绘
    facade.requestRedraw();
    facade.highlightTransform(lastAddedName);
    fireProgressEvent(pendingCount, totalCount);
});
batchTimer.setRepeats(false); // 仅触发一次
```

- 30ms 去抖窗口，窗口内到达的多个变更合并为一次重绘
- 合并后的一次重绘中包含所有新增组件和连线
- 最新添加的组件获得高亮效果（闪烁边框）

### 5.3 进度反馈

生成过程中对话框持续更新：

```
User: 从 PostgreSQL 读取订单表，过滤掉金额<=0...

[AI 推理] 正在分析需求...
→ 需要读取数据 → 过滤 → 输出
需要组件: Table Input, Filter Rows, ClickHouse Output

[构建进度] ■■■□□□ 3/5 组件已部署
[操作日志]
✓ 添加组件 "Table Input" (x=100, y=100)
✓ 添加组件 "Filter Rows" (x=400, y=100)
✓ 连接 Table Input → Filter Rows
⋯ 生成中...
```

### 5.4 RAP Web 端推送实现

RAP 端利用已有基础设施：

1. **ServerPushSession**：复用 `ServerPushSessionFacade` 建立服务端推送通道
2. **Canvas 数据更新**：每次变更后调用 `canvas.setData("nodes", updatedNodes)` + `canvas.setData("hops", updatedHops)`
3. **触发重绘**：在 UI 线程中调用 `canvas.redraw()`，RAP 框架将 Paint 事件序列化到客户端
4. **客户端渲染**：`canvas.js` 的 `handleEvent(SWT.Paint)` 读取最新的 `getData()` 并重绘整个画布

```java
// RAP AIPipelineGeneratorFacadeImpl
public void requestRedraw() {
    ServerPushSessionFacade.start(); // 确保推送通道开启
    hopGui.getDisplay().asyncExec(() -> {
        CanvasFacade.setData(canvas, pipelineMeta); // 推送完整状态
        canvas.redraw(); // 触发客户端重绘
    });
}
```

考虑极端网络延迟场景：在生成过程中将 `ServerPushSession` 保持激活，批量推送减少往返。

## 6. 与现有系统的集成

### 6.1 Pipeline 元数据模型复用

AI 生成的 Pipeline 完全复用现有 `PipelineMeta` 数据结构：

| AI 操作             | 对应 PipelineMeta 操作                         |
| ----------------- | ------------------------------------------ |
| `add_transform` → | `pipelineMeta.addTransform(transformMeta)` |
| `add_hop` →       | `pipelineMeta.addPipelineHop(hopMeta)`     |
| `set_position` →  | `transformMeta.setLocation(x, y)`          |
| `done` →          | `pipelineMeta.clearChanged()` + 校验         |

无需新增任何序列化格式。最终结果仍保存为标准 `.hpl` XML 文件。

### 6.2 Undo/Redo 集成

AI 生成的每个操作都通过 `AbstractMeta.addUndo()` 记录：

- 操作类型：`TYPE_UNDO_NEW`（新增组件 / 连线）
- 撤销粒度：**逐操作撤销**——按一次 Ctrl+Z 回退一个 function call
- 批量撤销：支持「回退到生成开始前」的一键操作
- 实现方式：`PipelineIncrementalBuilder` 生成开始时标记 `startPosition`，生成结束后用户可以回退到该位置

### 6.3 Pipeline 校验

生成完成后自动触发校验：

```java
// AIPipelineGenerator.markDone() 中
pipelineCheckDelegate.checkPipeline(pipelineMeta);
```

- 检查项：组件配置完整性、无孤立组件、数据流连续性
- 校验结果展示在对话框中，允许用户一键定位问题组件

### 6.4 持久化

- AI 生成的 Pipeline 无需特殊存储格式
- 编辑、保存、运行完全复用现有机制
- 旧版 Hop 可以直接打开 AI 生成的 `.hpl` 文件

## 7. 实施计划

### Phase 1：核心基础设施（7 人日）

| 任务                                                     | 工作量 | 产出文件                                                       |
| ------------------------------------------------------ | --- | ---------------------------------------------------------- |
| TransformCatalog 实现                                    | 1d  | `TransformCatalog.java`                                    |
| PipelineIncrementalBuilder 实现                          | 2d  | `PipelineIncrementalBuilder.java`, `GenerationAction.java` |
| PipelineStreamParser 实现                                | 2d  | `PipelineStreamParser.java`                                |
| AIPipelineGenerator 核心 + LlmClient Function Calling 增强 | 2d  | `AIPipelineGenerator.java`, `LlmClient.java` 修改            |

### Phase 2：桌面端 UI（RCP）（4.5 人日）

| 任务                               | 工作量  | 产出文件                                       |
| -------------------------------- | ---- | ------------------------------------------ |
| AIPipelineGeneratorDialog SWT 实现 | 2d   | `AIPipelineGeneratorDialog.java`           |
| 增量渲染集成                           | 1d   | `AIPipelineGeneratorFacadeImpl.java (rcp)` |
| Undo 集成                          | 1d   | `HopGuiPipelineUndoDelegate.java` 修改       |
| 工具栏入口按钮 + 快捷键                    | 0.5d | `HopGui.java` 修改                           |

### Phase 3：Web 端 UI（RAP）（5 人日）

| 任务                                  | 工作量  | 产出文件                                       |
| ----------------------------------- | ---- | ------------------------------------------ |
| AIPipelineGeneratorFacadeImpl (RAP) | 2d   | `AIPipelineGeneratorFacadeImpl.java (rap)` |
| ServerPushSession 集成                | 1d   | 复用已有 `ServerPushSessionFacade`             |
| Canvas 增量推送优化                       | 1.5d | `CanvasFacadeImpl.java` 增强                 |
| RAP 端 SWT Dialog 适配                 | 0.5d | RAP 兼容测试                                   |

### Phase 4：AI 调优与测试（7 人日）

| 任务                                | 工作量 |
| --------------------------------- | --- |
| Prompt 工程迭代优化                     | 2d  |
| 多模型兼容测试（GPT-4o / Claude / Ollama） | 2d  |
| 复杂场景测试（分支、合并、多输入输出）               | 2d  |
| 性能测试 + 帧率调优                       | 1d  |

### 总计工作量：约 23.5 人日

## 8. 技术风险与缓解

| 风险                                   | 影响             | 概率 | 缓解措施                            |
| ------------------------------------ | -------------- | -- | ------------------------------- |
| LLM 生成的 pluginId 不存在或不对应             | pipeline 含无效组件 | 中  | TransformCatalog 约束 + 生成后自动校验替换 |
| 流式解析在 Function Calling 模式下 chunk 不完整 | 解析失败或丢失参数      | 低  | 缓冲区累积 + 跨 chunk 合并              |
| 大量组件（>30）时频繁重绘导致 UI 卡顿               | 用户体验下降         | 中  | 30ms 去抖批处理 + 渐进式渲染              |
| RAP 端网络延迟导致渲染滞后                      | 用户感知卡顿         | 中  | 推理文本先行展示 + 操作批量合并减少推送次数         |
| LLM 无法正确理解复杂嵌套需求（如多分支条件）             | 生成结果不符合预期      | 低  | 多轮对话修正 + 生成后用户可拖拽修改             |

## 9. 未来扩展方向

- **多轮迭代生成**：用户可在生成完成后追加指令（"在这个 pipeline 里再加一个排序组件"）
- **组件配置 AI 填充**：AI 不仅添加组件，还自动填入关键配置（表名、SQL 语句、文件路径）
- **Pipeline 优化建议**：AI 分析现有 pipeline 提出性能优化建议（并行度、数据分区等）
- **模板匹配**：用户输入模糊需求时，AI 先匹配最相似的项目模板再定制化调整
- **AI 测试数据生成**：为 pipeline 的每个组件自动生成测试数据用于预览

