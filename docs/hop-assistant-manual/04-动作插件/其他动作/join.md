## Join Action

### 描述

*Join* action 用于同步 workflow 中的并行分支。它充当一个控制点，*等待同一级别的所有前序并行分支完成*后再继续执行下一个 action。

这消除了仅为在并行任务完成后进行汇合和继续执行而创建单独子 workflow 的需要。

### 用法

传统上，在 Qi Hop 中，如果您想并行运行 workflow 的某些部分，然后继续顺序执行流程，您需要将这些并行部分隔离到子 workflow 中。这是为了防止后续 action 被过早执行。

使用新的 *Join* action，不再需要这种变通方法。

使用 Join action 的步骤：

. 配置您的 workflow，使其从单个 action 发出多个输出跳转。
. 在您希望并行运行的输出跳转上启用*并行执行*。
. 在所有并行分支汇聚处添加 *Join* action。
. 使用常规（非并行）跳转将所有并行分支连接到 *Join* action。
. 将 *Join* action 连接到您希望在*所有并行任务完成后*执行的下一个 action。

### 示例 Workflow 结构

执行时：

![Using the join action instead of a child workflow, width="65%"](../../assets/images/how-to-guides/parallel-workflows/continue-in-parallel-join.png)

- action `pipeline-1`、`pipeline-2` 和 `pipeline-3` 并行运行。
- `join` action 等待 `pipeline-1`、`pipeline-2` 和 `pipeline-3` 完成。
- 一旦它们完成，workflow 将继续执行最终 action `check-datasets`。

### 优势

- *简化并行执行：* 无需仅为协调分支而创建单独的子 workflow。
- *提高可读性：* 您的并行和顺序逻辑可以在单个 workflow 中维护。
- *增强可维护性：* 更少的组件和外部依赖（子 workflow）需要管理。
