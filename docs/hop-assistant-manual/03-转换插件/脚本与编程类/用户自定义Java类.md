# 用户自定义Java类（User Defined Java Class）

用户自定义Java类转换允许您输入用户自定义的 Java 类来驱动一个完整转换的功能。本质上，此转换允许您在转换中编写自己的插件。

此转换的目标并非让用户在转换内进行大规模的 Java 开发（Hop 有完整的插件系统来支持这部分工作），而是允许用户以尽可能少的代码定义方法和逻辑，并以最快的速度执行。

为此，本转换使用 [Janino](https://janino-compiler.github.io/janino/) 项目库在运行时以类的形式编译 Java 代码。

## 主要选项

| 选项 | 说明 |
|------|------|
| 类名（Class name） | 用户自定义 Java 类的名称 |
| 代码（Code） | Java 类的源代码，需实现必要的转换接口方法（如 processRow） |
| 字段（Fields） | 定义输入和输出字段 |

## 注意事项

- 使用 Janino 编译器在运行时编译 Java 代码，执行速度优于脚本类转换。
- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
