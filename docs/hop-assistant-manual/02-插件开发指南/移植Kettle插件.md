# 移植 Kettle 插件

将 Kettle/PDI 插件移植到 Hop 非常直接。以下描述了开发者需要注意的几个要点，以使移植过程尽可能顺利。

## 更改 Maven 依赖

你不再需要 Pentaho 特定的 `$HOME/.m2/settings.xml`。

- 将 group `pentaho-kettle` 改为 `org.apache.hop`
- 将依赖 `kettle-core` 改为 `hop-core`
- 将依赖 `kettle-engine` 改为 `hop-engine`
- 将依赖 `kettle-ui-swt` 改为 `hop-ui-swt`

Hop（尚未）将构件发布到 Maven Central。你需要在 `pom.xml` 中包含 Apache 仓库：

```xml
<repositories>
  <repository>
    <id>Apache</id>
    <url>https://repository.apache.org/snapshots/</url>
    <name>Apache Repository</name>
    <snapshots><enabled>true</enabled></snapshots>
    <releases><enabled>false</enabled></releases>
  </repository>
</repositories>
```

## Jandex 索引

为加速插件注解的搜索，hfxt data process 使用 Java 注解索引：Jandex。除非你的插件 JAR 文件中有这样的索引，否则 Hop 不会加载它。要添加索引，只需在 maven pom.xml 中添加插件：

```xml
<properties>
  <jandex.version>3.1.6</jandex.version>
</properties>
<build>
  <pluginManagement>
    <plugins>
      <plugin>
        <groupId>io.smallrye</groupId>
        <artifactId>jandex-maven-plugin</artifactId>
        <version>${jandex.version}</version>
      </plugin>
    </plugins>
  </pluginManagement>
  <plugins>
    <plugin>
      <groupId>io.smallrye</groupId>
      <artifactId>jandex-maven-plugin</artifactId>
      <executions>
        <execution>
          <id>make-index</id>
          <goals><goal>jandex</goal></goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

构建并生成 Java 注解索引后，你应该会在插件 JAR 文件中看到一个名为 `META-INF/jandex.idx` 的文件。

## API 变更

### 接口命名

所有接口类已从 `<Name>Interface` 改为 `I<Name>`。例如：`RowMetaInterface` → `IRowMeta`。

### 转换（Transformations）→ 管道（Pipelines）

Transformations 现在称为 Pipelines。对应类名已更改：`TransMeta` → `PipelineMeta`，`Trans` → `Pipeline`。

### 步骤（Steps）→ 转换（Transforms）

Steps 现在称为 Transforms。例如：
- `StepMeta` → `TransformMeta`
- `StepMetaInterface` → `ITransformMeta`
- `BaseStep` → `BaseTransform`
- `StepDataInterface` → `ITransformData`

### 转换泛型

`ITransform` 不再要求你在 `processRow()`、`init()`、`dispose()`、`stopRunning()` 方法中传递 `ITransformMeta` 和 `ITransformData` 类。这大大简化了代码。你需要让 Hop 知道你的实现之间的关系：

```java
public class Sample extends BaseTransform<SampleMeta, SampleData>
    implements ITransform<SampleMeta, SampleData> { ... }
```

`ITransformMeta` 的实现也是如此：

```java
public class SampleMeta extends BaseTransformMeta
    implements ITransformMeta<Sample, SampleData> { ... }
```

### 仓库（Repository）

对 Repository 的所有引用（包括参数以及转换和动作中的 `saveRep()` 和 `loadRep()` 方法）可以安全地移除。

### VariableSpace → IVariables

`VariableSpace` 重命名为 `IVariables`，API 也经过了清理和简化。例如 `environmentSubstitute()` 现在称为 `resolve()`。请注意，元数据对象（TransMeta、JobMeta、DatabaseMeta 等）不再实现 `VariableSpace`（或 `IVariables`）。只有运行时对象（如 Pipeline、Workflow、ITransform 等）在 Hop 中拥有状态。

### 扩展点插件

移植实现 `ExtensionPointInterface` 的扩展点插件类时，请注意你将收到一个额外参数 `IVariables`，它旨在包含 XP 上下文中父对象的变量。接口名称已更改为 `IExtensionPoint`。你也可以使用泛型指定接收主体的预期类。

### Slave Server 和数据库连接

这些对象不再存储在 Pipeline 或 Workflow 中，它们现在是完全共享的对象，因此转换和动作接口方法中对它们的任何引用都可以安全地移除：`List<DatabaseMeta> databases`、`List<SlaveServer> slaveServers`。

### MetaStore → HopMetadata

MetaStore 代码因 LGPL 许可证原因已从项目中移除，由通用的 `IHopMetadataProvider` 替代 `IMetaStore` 引用。你可以随时要求当前的 `IHopMetadataProvider` 为你提供元数据类的序列化器。然后可以使用 `IHopMetadataSerializer` 对对象进行 CRUD 操作以及列表等。

在转换对话框中，你可以使用组件 `MetaSelectionLine<T extends IHopMetadata>` 来管理 MetaStore 元素，它会自动添加标签、工具提示、下拉框和几个用于管理 metastore 元素的按钮。
