# ![Language Model Chat transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/languagemodelchat.svg) Language Model Chat

| == 支持的引擎 |  |
|---|---|
| Hop Engine | ![Supported,24](../../assets/images/check_mark.svg) |
| Spark | ![Maybe Supported,24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported,24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported,24](../../assets/images/question_mark.svg) |

## 选项

### 通用参数

以下参数适用于所有语言模型选择。

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称。在单个 Pipeline 中必须唯一。 |
| Input JSON Chat | 启用时，使用 chat completion JSON 格式（system、assistant、user 消息）。禁用时，将输入视为纯文本。详见 "Chat Completion" 部分。 |
| Output JSON Chat | 启用时，以 chat completion JSON 格式返回响应。禁用时，以纯文本形式返回响应。详见 "Chat Completion" 部分。 |
| Input field name | 包含输入文本/prompt 的字段名称 |
| Output field name prefix | 应用于所有输出字段名称的前缀。 |
| Identifier value (optional) | 用于标记输出记录的可选标识符；如果留空，输出将默认为模型名称。 |
| Parallelism | 用于 API 请求的并行线程数。**警告：请注意端点容量和相关费用。** 建议在测试和开发期间启用 `Mock` 模式或将并行度设置为 1。测试完成后，逐步增加并行度以评估消耗费用和提供商管理多线程的能力，包括速率限制和其他约束。 |
| Mock (simulate) | 启用测试模式，模拟响应而不执行真实 API 调用。 |
| Mock Output Value | 在模拟模式下返回的预定义响应，在跳过真实 API 调用时使用。 |
| Model API | 定义用于与端点通信的 API（例如通过 OpenAI、Anthropic、Ollama、Hugging Face、Mistral）。**注意：** Open AI API 选项可用于任何 Open AI 兼容的端点/服务器。例如 vLLM (Kwon et al., 2023)。 |

### 模型特定选项

下表详细说明了 Language Model Chat Transform plugin 当前支持的各个语言模型 API 的特定参数。

| 选项 | OpenAI | Anthropic | Hugging Face | Mistral | Ollama |
|---|---|---|---|---|---|
| API/Endpoint URL | ✅ | ✅ | ✅ | ✅ | ✅ |
| Temperature | ✅ | ✅ | ✅ | ✅ | ✅ |
| Max New Tokens | ✅ | ✅ | ✅ | ✅ | ✅ |
| Timeout | ✅ | ✅ | ✅ | ✅ | ✅ |
| API Key/AccessToken | ✅ | ✅ | ✅ | ✅ | ❌ |
| Model Name | ✅ | ✅ | ❌ | ✅ | ✅ |
| Top P | ✅ | ✅ | ❌ | ✅ | ✅ |
| Max Retries | ✅ | ✅ | ❌ | ✅ | ✅ |
| Response Format | ✅ | ❌ | ❌ | ✅ | ✅ |
| Seed | ✅ | ❌ | ❌ | ✅ | ✅ |
| Log Requests | ✅ | ✅ | ❌ | ✅ | ❌ |
| Log Responses | ✅ | ✅ | ❌ | ✅ | ❌ |
| Top K | ❌ | ✅ | ❌ | ❌ | ✅ |
| Presence/Repeat Penalty | ✅ | ❌ | ❌ | ❌ | ✅ |
| Organisation | ✅ | ❌ | ❌ | ❌ | ❌ |
| Frequency Penalty | ✅ | ❌ | ❌ | ❌ | ❌ |
| Safe Prompt | ❌ | ❌ | ❌ | ✅ | ❌ |
| User | ✅ | ❌ | ❌ | ❌ | ❌ |
| Return Full Text | ❌ | ❌ | ✅ | ❌ | ❌ |
| Wait for Model | ❌ | ❌ | ✅ | ❌ | ❌ |
| Use Proxy | ✅ | ❌ | ❌ | ❌ | ❌ |
| Context Window Size | ❌ | ❌ | ❌ | ❌ | ✅ |

| 选项 | 描述 |
|---|---|
| API/Endpoint URL | 指定用于访问和交互 OpenAI、Anthropic、Mistral、Ollama 或 Hugging Face 等服务提供的模型的标识符（例如 URL、模型 ID 或镜像 ID）。 |
| Temperature | 控制输出的随机性：高值产生更多样化的文本（创意/创新），低值产生更集中和确定性的响应。该值通常在 0 到 1 之间，但根据模型可以超出此范围。注意：除非有意为之，通常最好只调整一个参数；temperature 或 Top-P/K，而不是同时调整多个。 |
| Max New Tokens | 设置模型在单个响应中可生成的最大 token 数。 |
| Context Window Size | 指定模型一次可以考虑的输入 token 数量。 |
| Timeout | 确定放弃响应前等待的时间。 |
| API Key/AccessToken | 用于认证和授权访问端点服务的唯一访问值。 |
| Model Name | 标识要使用的特定模型。 |
| Top P | 也称为核采样 (nucleus sampling, Holtzman et al., 2019)，通过将模型的选择限制在词汇表的动态子集来控制输出的多样性。例如，0.2 表示仅考虑占前 20% 概率质量的 token。Top-p 和 temperature 都影响模型输出的随机性和多样性，但运作方式不同。Temperature 缩放所有 token 的概率，而 top-p 将 token 选择限制在累积概率达到阈值的子集。注意：除非有意为之，通常最好只调整一个参数；temperature 或 Top-P/K，而不是同时调整多个。 |
| Max Retries | 失败 API 请求的最大重试次数。 |
| Response Format | 定义模型响应使用的结构。注意：虽然某些 API 支持此选项，但并非所有模型都兼容。此外，API 可能需要明确指示模型生成 JSON 以确保正常功能。 |
| Seed | 尝试确定性采样，确保使用相同 seed、prompt 和参数的重复请求获得一致的结果。 |
| Top K | 通过限制考虑的 token 数量来控制可能答案的广度。与 Top P（选择 token 直到累积概率达到或超过 **p**）不同，此参数将选择限制为由 **k** 定义的固定数量的 token。注意：除非有意为之，通常最好只调整一个参数；temperature 或 Top-P/K，而不是同时调整多个。 |
| Repeat/Frequency Penalty | 此设置调整模型如何评估生成文本中的 token 频率，鼓励模型使用更少/更多重复的语言。惩罚范围从 -2.0 到 2.0，取决于端点模型。正值通过惩罚频繁使用的 token 来抑制重复，负值可以通过偏向它们来增加重复。频率惩罚的合理值通常在 0.1 到 1.0 之间。 |
| Presence Penalty | 此设置确定模型如何评估新 token，旨在抑制重用之前生成的 token。惩罚范围从 -2.0 到 2.0，取决于端点模型。正值惩罚已出现在文本中的 token，鼓励模型探索新方向。与频率惩罚（根据 token 使用频率按比例缩放）不同，presence penalty 对至少出现一次的 token 施加一次性加法惩罚。这意味着所有重复的 token 都被同等惩罚，无论它们出现两次、五次还是十次。presence penalty 的合理值通常在 0.1 到 1.0 之间。 |
| Organisation | 与 API key 关联的组织 ID，用于账户和计费。 |
| Safe Prompt | 提供基线 prompt 以引导模型生成安全输出。例如，通过在您的消息前添加系统 prompt，如：``__Always assist with care, respect, and truth. Respond with utmost utility yet securely. Avoid harmful, unethical, prejudiced, or negative content. Ensure replies promote fairness and positivity__``。 |
| User | 端用户的唯一标识符，端点提供商用于监控活动并检测潜在滥用。 |
| Return Full Text | 确保返回完整的生成文本而不截断。 |
| Wait for Model | 延迟响应直到模型准备就绪。如果模型处于冷启动状态需要加载，这可以避免重复请求，等待其变为可用。 |
| Use Proxy | 通过代理服务器路由请求。 |
| Log Requests | 用于调试请求。 |
| Log Responses | 用于调试响应。 |

## 附加信息

### Token
Token 代表一个文本单元，如单词、子词或字符，取决于模型开发期间使用的分词方法。Token 的大小因模型而异，但英语的常见估算约为每 3 个单词 4 个 token。对于更精确的计算，重要的是考虑模型采用的特定分词方法，如 Byte Pair Encoding (BPE) 或 WordPiece。有在线工具提供便捷的计算器，可以根据特定分词器和输入文本确定 token 数量。此外，Hugging Face 等框架允许用户下载分词器，在本地用于计算 token 数量。

### Chat Completion / Prompting
Chat completion 指用于通过 API 设计和控制对话式 AI 系统（模型）中交互流的 JSON 结构。此结构定义基于角色的通信（例如 "system"、"user"、"assistant"）并塑造助手的行为，使其能够捕获用户意图、根据 prompt 扩展文本，并为模型未专门微调的任务生成相关内容。

与此概念相关的其他术语包括 prompting、prompt engineering、in-context learning 和 meta-learning。虽然这些术语有时可能互换使用，但它们具有不同的技术含义。Meta-learning 指更广泛的框架，其中模型利用内循环/外循环结构来适应新任务。在此框架中，in-context learning 代表内循环，模型根据推理期间提供的示例执行任务，而无需任何梯度更新。此过程可以进一步细分为 "zero-shot"、"one-shot" 或 "few-shot" 学习，取决于推理时给出的示例数量 (Brown et al., 2020)。Prompt engineering 是设计输入（prompt）以有效引导模型行为的过程，优化其响应以满足特定目标。

示例：
```json
[
{
  "role" : "system",
  "content" : "Provides instructions, context, or guidelines to shape the assistant's behaviour and set the overall tone or purpose of the interaction."
}, {
  "role" : "user",
  "content" : "Represents the individual interacting with the system, providing queries, requests, or instructions to guide the assistant's responses."
}, {
  "role" : "assistant",
  "content" : "Responds to the user's input by generating outputs based on the system's instructions and the user's messages."
}
]
```

### 基本 Prompt 工程技术

- *Zero-Shot：* 模型仅基于给定的 prompt 和其已有知识生成输出，不提供任何示例来引导其响应 (Brown et al., 2020)。

- *One/Few-Shot：* 在 prompt 中提供少量示例（一个或多个），帮助模型泛化到新的、未见的输入 (Brown et al., 2020)。

- *Chain-of-Thought：* 模型被引导通过逻辑的、逐步推理过程，使其能够分解复杂问题并提高得出更准确结论的能力 (Wei et al., 2022)。此方法对多步推理任务特别有效。变体包括 Zero-Shot 和 Few-Shot Chain-of-Thought (Kojima et al., 2023)。

- *Self-Consistency：* 模型通过调整 temperature 或应用核采样 (top-p) 等技术引入可变性，为同一 prompt 生成多条不同的推理路径。最终答案通过选择这些输出中出现频率最高的响应来确定 (Wang et al., 2023)。此方法通过利用输出的多样性，特别有效地提高推理任务的准确性。例如，在分类任务中，模型可以为情感分析问题（例如正面、负面、中性）生成三个 chain-of-thought 输出，并通过输出之间的多数投票确定最终标签。

- *Prompt-Chaining (Sequential Chaining)：* 一种按顺序提供多个相关 prompt 的方法，每个 prompt 基于前一个的输出构建。这种逐步方法有助于引导模型的推理过程，使其能够逐步处理复杂任务。此技术需要启用 JSON chat 功能，因为它依赖此功能来链接对话。
