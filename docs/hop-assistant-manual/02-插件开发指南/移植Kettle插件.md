# 移植 Kettle 插件

## 功能概述


将 Kettle/PDI 插件移植到 Qi 数据治理平台非常直接。本指南描述了开发者需要注意的几个要点，以使移植过程尽可能顺利。

## 更改 Maven 依赖

你不再需要 Pentaho 特定的 `$HOME/.m2/settings.xml`。

### 依赖替换对照表

| Kettle 依赖 | Qi 数据治理平台 依赖 |
|------------|-------------------|
| `pentaho-kettle` group | `org.apache.hop` group |
| `kettle-core` | `hop-core` |
| `kettle-engine` | `hop-engine` |
| `kettle-ui-swt` | `hop-ui-swt` |

### 添加 Apache 仓库

Qi 数据治理平台（尚未）将构件发布到 Maven Central。你需要在 `pom.xml` 中包含 Apache 仓库：

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

为加速插件注解的搜索，Qi 数据治理平台使用 Java 注解索引：Jandex。除非你的插件 JAR 文件中有这样的索引，否则 Qi 数据治理平台不会加载它。

### 添加 Jandex 插件配置

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

所有接口类已从 `<Name>Interface` 改为 `I<Name>`。

| Kettle | Qi 数据治理平台 |
|--------|---------------|
| `RowMetaInterface` | `IRowMeta` |

### 转换（Transformations）→ 管道（Pipelines）

Transformations 现在称为 Pipelines。对应类名已更改：

| Kettle | Qi 数据治理平台 |
|--------|---------------|
| `TransMeta` | `PipelineMeta` |
| `Trans` | `Pipeline` |

### 步骤（Steps）→ 转换（Transforms）

Steps 现在称为 Transforms。

| Kettle | Qi 数据治理平台 |
|--------|---------------|
| `StepMeta` | `TransformMeta` |
| `StepMetaInterface` | `ITransformMeta` |
| `BaseStep` | `BaseTransform` |
| `StepDataInterface` | `ITransformData` |

### 转换泛型

`ITransform` 不再要求你在 `processRow()`、`init()`、`dispose()`、`stopRunning()` 方法中传递 `ITransformMeta` 和 `ITransformData` 类。这大大简化了代码。

**示例代码：**

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

`VariableSpace` 重命名为 `IVariables`，API 也经过了清理和简化。

| Kettle | Qi 数据治理平台 |
|--------|---------------|
| `environmentSubstitute()` | `resolve()` |

请注意，元数据对象（TransMeta、JobMeta、DatabaseMeta 等）不再实现 `VariableSpace`（或 `IVariables`）。只有运行时对象（如 Pipeline、Workflow、ITransform 等）在 Qi 数据治理平台中拥有状态。

### 扩展点插件

移植实现 `ExtensionPointInterface` 的扩展点插件类时，请注意你将收到一个额外参数 `IVariables`，它旨在包含 XP 上下文中父对象的变量。接口名称已更改为 `IExtensionPoint`。

### Slave Server 和数据库连接

这些对象不再存储在 Pipeline 或 Workflow 中，它们现在是完全共享的对象，因此转换和动作接口方法中对它们的任何引用都可以安全地移除：
- `List<DatabaseMeta> databases`
- `List<SlaveServer> slaveServers`

### MetaStore → HopMetadata

MetaStore 代码因 LGPL 许可证原因已从项目中移除，由通用的 `IHopMetadataProvider` 替代 `IMetaStore` 引用。

## XML配置示例

完整的 `pom.xml` 配置示例：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>org.apache.hop</groupId>
    <artifactId>hop-transform-sample</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    
    <parent>
        <groupId>org.apache.hop</groupId>
        <artifactId>hop-plugins-transforms</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </parent>
    
    <properties>
        <jandex.version>3.1.6</jandex.version>
    </properties>
    
    <repositories>
        <repository>
            <id>Apache</id>
            <url>https://repository.apache.org/snapshots/</url>
            <snapshots><enabled>true</enabled></snapshots>
            <releases><enabled>false</enabled></releases>
        </repository>
    </repositories>
    
    <build>
        <plugins>
            <plugin>
                <groupId>io.smallrye</groupId>
                <artifactId>jandex-maven-plugin</artifactId>
                <version>${jandex.version}</version>
                <executions>
                    <execution>
                        <id>make-index</id>
                        <goals><goal>jandex</goal></goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## 注意事项

- 在转换对话框中，你可以使用组件 `MetaSelectionLine<T extends IHopMetadata>` 来管理元数据元素
- Jandex 索引是插件加载的必要条件
- API 变更主要涉及命名规范，核心逻辑保持不变
- 建议逐步移植，每次只迁移一个插件并进行测试