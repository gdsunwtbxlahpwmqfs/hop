# ![Beam Window Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-window.svg) Beam Window

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Window type a |  |
| Window size (duration in seconds) | 设置窗口持续时间大小（秒），默认 60。 |
| Every x seconds (Sliding windows) | 设置滑动窗口持续时间（秒）。 |
| Window start field | 包含窗口开始时间的字段。 |
| Window end field | 包含窗口结束时间的字段。 |
| Window max field | 包含事件之间最大持续时间的字段。 |

## 窗口类型

### 固定窗口

固定或翻滚窗口用于将数据重复分割为不同的时间段，并且不重叠。
事件不能属于多个窗口。

### 滑动窗口

滑动窗口仅在事件发生时产生输出，并持续向前移动。
每个窗口至少包含一个事件，且可以重叠。
事件可以属于多个窗口。

### 会话窗口

会话窗口将大致同时到达的事件分组，并过滤掉没有数据的时段。

窗口在第一个事件发生时开始，并在指定超时范围内扩展以包含新事件。
如果事件持续发生，窗口将不断扩展直到达到最大持续时间。

### 全局窗口

全局窗口是 Beam 中的默认窗口，忽略事件时间（跨越所有事件时间），并使用触发器提供该窗口的快照。
