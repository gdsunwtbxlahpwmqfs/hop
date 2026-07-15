# Action Plugin

Action 是在 workflow 中执行的对象。它们是逻辑运算符，不操作您的数据，而是创建需要在您的 pipeline 周围执行的操作。

Workflow 中的 action 通常不处理您的数据，也不会将数据传递给下一个 action。当一个 action 完成运行时，它会返回一个 true 或 false 错误代码，该代码可用于通过 true/false 或无条件（忽略退出代码）的 workflow hop 来驱动 workflow 的行为。
