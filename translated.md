# Hop 翻译（i18n）模块调用关系

> 本文档基于对 `core/src/main/java/org/apache/hop/i18n/` 与 `core/src/main/java/org/apache/hop/laf/`
> 源码的实际核对整理而成。所有结论均可在对应文件中验证。

## 1. 核心类层次结构

```
IMessageHandler (接口)
    ↑
    | extends
    |
AbstractMessageHandler (抽象类)
    ↑
    | extends
    |
GlobalMessages (核心实现类)
    ↑
    | extends
    |
LafMessageHandler (支持 Look And Feel 替换)
```

### 1.1 核心类说明

| 类名 | 文件位置 | 职责 |
|------|----------|------|
| `IMessageHandler` | `core/src/main/java/org/apache/hop/i18n/IMessageHandler.java` | 消息处理器接口，定义获取翻译字符串的方法 |
| `AbstractMessageHandler` | `core/src/main/java/org/apache/hop/i18n/AbstractMessageHandler.java` | 消息处理器的抽象基类，持有当前 `Locale` 并定义 `getString` 方法签名 |
| `GlobalMessages` | `core/src/main/java/org/apache/hop/i18n/GlobalMessages.java` | 全局消息类的核心实现，注册可用语言、构建 `ResourceBundle` 名称 |
| `LafMessageHandler` | `core/src/main/java/org/apache/hop/i18n/LafMessageHandler.java` | 继承 `GlobalMessages`，支持通过 LAF (Look And Feel) 动态替换消息处理器 |
| `BaseMessages` | `core/src/main/java/org/apache/hop/i18n/BaseMessages.java` | 供外部类调用的**静态入口类**（单例模式），通过 `LafFactory` 委托给 `IMessageHandler` |
| `PackageMessages` | `core/src/main/java/org/apache/hop/i18n/PackageMessages.java` | 按包名维度缓存/查找消息的辅助类 |

## 2. 支持类

| 类名 | 文件位置 | 职责 |
|------|----------|------|
| `GlobalMessageUtil` | `core/src/main/java/org/apache/hop/i18n/GlobalMessageUtil.java` | 工具类，处理实际的字符串格式化、locale 回退、`ResourceBundle` 加载（UTF-8） |
| `GlobalMessageControl` | `GlobalMessageUtil` 的内部类（`ResourceBundle.Control` 子类） | 自定义 `ResourceBundle.Control`，禁用 JDK 默认回退、按需控制 ROOT 回退、以 UTF-8 读取属性文件 |
| `LanguageChoice` | `core/src/main/java/org/apache/hop/i18n/LanguageChoice.java` | 语言选择管理（单例），将默认语言持久化到 `HopConfig`（键 `LocaleDefault`） |
| `LafFactory` | `core/src/main/java/org/apache/hop/laf/LafFactory.java` | LAF 工厂类，用于动态获取消息处理器实现 |
| `IHandler` | `core/src/main/java/org/apache/hop/laf/IHandler.java` | LAF Handler 的基础接口 |
| `BasePropertyHandler` | `core/src/main/java/org/apache/hop/laf/BasePropertyHandler.java` | LAF 属性处理器基类 |
| `RemoveAltKeyMessageHandler` | `core/src/main/java/org/apache/hop/laf/RemoveAltKeyMessageHandler.java` | macOS 专用处理器，用于去除菜单文本中的 `Alt` 修饰键 |
| `LafDelegate` | `core/src/main/java/org/apache/hop/laf/LafDelegate.java` | LAF 委托实现 |

## 3. 调用流程

```
┌─────────────────────────────────────────────────────────────────────┐
│                          外部调用者                                   │
│   BaseMessages.getString( PKG, key, params... )                      │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                        BaseMessages (单例)                           │
│   - getInstanceHandler() 通过 LafFactory.getHandler() 获取处理器      │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      LafFactory (LAF 工厂)                           │
│   - 返回 LafMessageHandler，或 RemoveAltKeyMessageHandler (macOS)     │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                    LafMessageHandler / GlobalMessages                │
│   - calculateString() 方法处理翻译逻辑                                │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                        GlobalMessageUtil                             │
│   - getActiveLocales(): 获取活跃的 locale 列表（有序）                 │
│   - calculateString(): 遍历 locale，用 MessageFormat 格式化           │
│   - getBundle(): 通过 GlobalMessageControl 加载 ResourceBundle        │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                    ResourceBundle (Java 原生)                        │
│   - 从 messages/messages_xx_XX.properties 文件加载翻译（UTF-8）       │
└─────────────────────────────────────────────────────────────────────┘
```

## 4. 资源文件结构

### 4.1 命名约定

`GlobalMessages` 中定义：
```java
protected static final String BUNDLE_NAME = "messages.messages";
```

`buildBundleName(packageName)` 返回 `packageName + "." + BUNDLE_NAME`，即
`<包名>.messages.messages`。

因此实际文件**位于包目录下的 `messages/` 子目录中**，命名格式为：
`messages/messages_语言_国家.properties`

示例（以 `org.apache.hop.ui.core.widget` 包为例）：
```
ui/src/main/resources/org/apache/hop/ui/core/widget/messages/
├── messages_en_US.properties    # 英文（美国）
└── messages_zh_CN.properties    # 简体中文
```

> 注意：并不存在包根目录下的 `messages.properties`，所有翻译文件都集中在 `messages/`
> 子目录里。`Locale.ROOT` 回退时查找的是 `messages/messages.properties`（如存在）。

### 4.2 典型分布位置

各模块的 `src/main/resources` 目录下按包结构组织：
- `core/src/main/resources/org/apache/hop/i18n/messages/messages_xx_XX.properties`
- `ui/src/main/resources/org/apache/hop/ui/hopgui/dialog/messages/messages_xx_XX.properties`
- `plugins/transforms/<name>/src/main/resources/.../messages/messages_xx_XX.properties`

## 5. 支持的语言

在 [GlobalMessages.java](core/src/main/java/org/apache/hop/i18n/GlobalMessages.java) 中**实际注册**的语言数组：

```java
public static final String[] localeCodes = {"en_US", "zh_CN"};

public static final String[] localeDesc = {
  "English (US)",
  "简体中文 (Simplified Chinese)",
};

public static final String[] localeBetaStatus = {
  "N", // "English (US)"
  "N", // "Simplified Chinese"
};
```

| Locale Code | 语言名称 | Beta 状态 |
|-------------|----------|-----------|
| `en_US` | English (US) | N |
| `zh_CN` | 简体中文 (Simplified Chinese) | N |

> **重要**：`LanguageChoice` 在初始化时，若 JVM 默认 locale 不在 `localeCodes` 列表中
> （或属于 Beta 语言），会自动回退为 `en-US`。因此即使代码库中存在其它语言的
> `messages_xx_XX.properties` 文件，它们也不会被 `LanguageChoice` 选中，除非将对应的
> locale code 加入 `localeCodes` 数组。

## 6. Locale 回退机制

`GlobalMessageUtil.getActiveLocales()` 返回一个**有序**的 `LinkedHashSet<Locale>`，
查找时按顺序遍历，首个命中即返回：

```java
public static final Locale FAILOVER_LOCALE = Locale.US;

public static LinkedHashSet<Locale> getActiveLocales() {
  final LinkedHashSet<Locale> activeLocales = new LinkedHashSet<>();
  // 1. 用户首选语言
  activeLocales.add(LanguageChoice.getInstance().getDefaultLocale());
  // 2. 故障转移语言
  activeLocales.add(FAILOVER_LOCALE);          // Locale.US
  // 3. 根语言
  activeLocales.add(Locale.ROOT);              // messages.properties
  return activeLocales;
}
```

回退顺序：
1. **用户首选语言** — `LanguageChoice.getInstance().getDefaultLocale()`（例如 `zh_CN`）
2. **故障转移语言** — `Locale.US`（`messages_en_US.properties`）
3. **根语言** — `Locale.ROOT`（`messages.properties`）

回退过程示例（查找 `TableView.menu.MoveUp` 键，中文环境）：
1. 尝试 `messages/messages_zh_CN.properties` 中的 `TableView.menu.MoveUp`
2. 若找不到，尝试 `messages/messages_en_US.properties` 中的同名键
3. 若仍找不到，尝试 `messages/messages.properties` 中的同名键
4. 全部未命中时返回 `!TableView.menu.MoveUp!`（缺失键标记，由 `decorateMissingKey()` 生成）

> 此外，`GlobalMessageControl` 禁用了 JDK `ResourceBundle` 的默认 `getFallbackLocale`，
> 回退完全由上述 `getActiveLocales()` 控制。属性文件统一以 **UTF-8** 编码读取
> （见 `GlobalMessageControl.newBundle` 中的 `InputStreamReader(stream, UTF_8)`）。

## 7. 使用示例

### 7.1 在类中使用翻译（推荐模式）

每个 UI/核心类遵循两步约定：声明 `PKG` 常量作为资源包定位符，再调用 `getString`：

```java
import org.apache.hop.i18n.BaseMessages;

public class TableView extends Composite {
  // 第 1 步：声明 PKG，指向当前类（用于定位同包下的 messages/ 目录）
  private static final Class<?> PKG = TableView.class;

  void buildMenu() {
    // 第 2 步：通过 PKG + key 获取翻译
    String text = BaseMessages.getString(PKG, "TableView.menu.MoveUp");

    // 带参数的消息：使用 {0}, {1} 占位符
    String undo = BaseMessages.getString(PKG, "TableView.menu.Undo", "上一步");
  }
}
```

`BaseMessages` 提供的重载方法：
```java
getString(Class<?> packageClass, String key)
getString(Class<?> packageClass, String key, String... parameters)
getString(Class<?> packageClass, String key, Object... parameters)   // 自动 toString
getString(String packageName, String key, String... parameters)      // 按包名字符串
```

### 7.2 属性文件示例

```properties
# messages/messages_en_US.properties
TableView.menu.MoveUp=Move up\tCtrl-UP
TableView.menu.Undo=Undo : {0} \tCtrl-Z
```

```properties
# messages/messages_zh_CN.properties
TableView.menu.MoveUp=上移\tCtrl-UP
TableView.menu.Undo=撤销 : {0} \tCtrl-Z
```

## 8. 相关配置

| 配置项 | 位置 | 说明 |
|--------|------|------|
| `LocaleDefault` | `hop-config.json`（由 `HopConfig` 管理） | 用户语言偏好，由 `LanguageChoice` 读写。键名定义在 `LanguageChoice.STRING_DEFAULT_LOCALE` |
| `LAF package` 属性 | 系统属性 / LAF 配置 | 指定消息处理器的包路径，用于替换默认 `LafMessageHandler` |

## 9. 关键文件路径汇总

```
core/src/main/java/org/apache/hop/i18n/
├── IMessageHandler.java           # 消息处理器接口
├── AbstractMessageHandler.java    # 抽象基类（持有当前 Locale）
├── GlobalMessages.java            # 核心实现（注册 localeCodes、构建 bundle 名）
├── LafMessageHandler.java         # LAF 支持的消息处理器
├── BaseMessages.java              # 静态入口类（外部调用入口）
├── GlobalMessageUtil.java         # 工具类（含 GlobalMessageControl 内部类）
├── LanguageChoice.java            # 语言选择管理（单例，持久化到 HopConfig）
└── PackageMessages.java           # 按包名查找消息的辅助类

core/src/main/java/org/apache/hop/laf/
├── IHandler.java                  # Handler 接口
├── IPropertyHandler.java          # 属性 Handler 接口
├── ILafChangeListener.java        # LAF 变更监听器接口
├── LafFactory.java                # LAF 工厂（获取 Handler 实现）
├── LafDelegate.java               # LAF 委托实现
├── BasePropertyHandler.java       # 属性处理器基类
├── OverlayPropertyHandler.java    # 覆盖式属性处理器
├── OverlayProperties.java         # 覆盖属性工具
└── RemoveAltKeyMessageHandler.java # macOS 专用消息处理器
```

## 10. 国际化修复工作实践总结

### 10.1 问题发现方法论

#### 硬编码字符串检测

通过 `grep` 命令扫描源代码中的 `setText("...")` 模式，快速定位未国际化的文本：

```bash
# 检测 UI 模块中的硬编码
grep -rn 'setText("' ui/src/main/java --include="*.java" | grep -v 'BaseMessages.getString'

# 检测 Plugin 模块中的硬编码
grep -rn 'setText("' plugins/tech --include="*.java" | grep -v 'BaseMessages.getString'
```

**检测结果统计：**
- UI 模块：27 处硬编码
- Plugins 模块：194 处硬编码（分布在 Neo4j、Azure、Salesforce、MongoDB、Cassandra 等插件）

#### 缺失键检测

通过对比 `en_US` 和 `zh_CN` 属性文件，发现翻译键的缺失情况：

```bash
# 提取两个文件的键集合并对比
comm -23 messages_en_US.properties messages_zh_CN.properties > missing_in_zh.txt
```

**重要发现：** 部分键差异是由于 `=` 周围空格格式不同导致的误报（如 `Key=value` vs `Key = value`），实际缺失键为 0 个。

### 10.2 修复策略与步骤

#### 阶段划分

| 阶段 | 内容 | 状态 |
|------|------|------|
| 阶段1 | UI 模块硬编码修复（27处） | ✅ 完成 |
| 阶段2 | UI 缺失 zh_CN 键确认 | ✅ 完成（0个真实缺失） |
| 阶段3a | Neo4j 插件硬编码修复（91处） | ✅ 完成 |
| 阶段3b | Azure + Salesforce 插件修复 | ✅ 完成 |
| 阶段3c | MongoDB/Cassandra/Google/Elastic/JSON 插件 | ✅ 完成 |
| 阶段3d | Transforms 插件修复 | ✅ 完成 |
| 阶段4 | 缺失键补全 | ✅ 完成（0个真实缺失） |

#### 修复步骤

1. **确认 PKG 常量存在**
   - 检查文件头部是否已定义 `private static final Class<?> PKG = Xxx.class;`
   - 如缺失，需添加

2. **确认 BaseMessages 导入**
   - 检查是否已导入 `import org.apache.hop.i18n.BaseMessages;`
   - 如缺失，需添加

3. **替换硬编码字符串**
   - 将 `setText("硬编码文本")` 替换为 `setText(BaseMessages.getString(PKG, "键名"))`

4. **在消息文件中添加翻译键**
   - 在 `messages_en_US.properties` 添加英文键值对
   - 在 `messages_zh_CN.properties` 添加中文键值对（Unicode 转义）

### 10.3 修改文件清单

#### Neo4j 插件

**代码文件：**
| 文件 | 修复内容 |
|------|----------|
| `CypherBuilderDialog.java` | BatchSize、UnwindAlias 标签 |
| `CypherDialog.java` | Unwind 标签 |
| `GraphOutputDialog.java` | OutOfOrderAllowed、FieldToPropertiesMappings、FieldToRelationshipMappings、GetFields 等 |
| `NeoConnectionEditor.java` | URLs、Advanced 标签页标题 |
| `GraphModelEditor.java` | CopyRelationship、RelationshipsList、RelSource、RelTarget、Graph 等 |
| `HopNeo4jPerspective.java` | 表格列标题 Hash、ID、Name、Errors、Date、Duration |
| `NeoExecutionViewerErrorTab.java` | 表格列标题 Hash、ID、Type |
| `NeoExecutionViewerLineageTab.java` | 表格列标题 ID、Name、Failed |
| `GenerateCsvDialog.java` | FilesPrefix 标签 |
| `ImporterDialog.java` | Neo4j 版本选择（5.x、4.x） |

**消息文件：**
| 文件 | 新增键 |
|------|--------|
| `perspective/messages/messages_en_US.properties` | `Neo4jPerspectiveDialog.Column.Hash.Name=#` |
| `perspective/messages/messages_zh_CN.properties` | `Neo4jPerspectiveDialog.Column.Hash.Name=#` |
| `shared/messages/messages_en_US.properties` | `NeoConnectionEditor.Advanced.Tab=Advanced` |
| `shared/messages/messages_zh_CN.properties` | `NeoConnectionEditor.Advanced.Tab=高级` |
| `gencsv/messages/messages_en_US.properties` | `GenerateCsvDialog.FilesPrefix.Label=CSV files prefix` |
| `gencsv/messages/messages_zh_CN.properties` | `GenerateCsvDialog.FilesPrefix.Label=CSV 文件前缀` |
| `importer/messages/messages_en_US.properties` | `ImporterDialog.Neo4jVersion.4x=4.x`、`ImporterDialog.Neo4jVersion.5x=5.x` |
| `importer/messages/messages_zh_CN.properties` | `ImporterDialog.Neo4jVersion.4x=4.x`、`ImporterDialog.Neo4jVersion.5x=5.x` |

#### Salesforce 插件

**代码文件：**
| 文件 | 修复内容 |
|------|----------|
| `SalesforceConnectionEditor.java` | OAuth 配置错误消息（`OAuth Configuration Error`） |

### 10.4 修复中的问题与解决方案

#### 问题1：文件损坏

在修复 `GraphModelEditor.java` 时，由于替换顺序不当导致部分代码损坏（`yRelationship.addSelectionListener`）。

**解决方案：** 逐步重新修复受损代码段，使用精确的 old_string 匹配。

#### 问题2：消息键不存在

部分代码使用的消息键在属性文件中不存在。

**解决方案：** 先检查属性文件中是否有相似的键，如有则复用，如无则创建新键。

#### 问题3：URL/数值类文本

检测到的 `setText("https://...")` 和 `setText("50")` 属于配置值而非用户可见文本。

**解决方案：** 这类文本不需要国际化，保持原样。

### 10.5 工具栏 View/Tool 国际化部分实现的原因分析

经过分析，工具栏国际化部分实现的原因包括：

1. **历史遗留代码**：部分工具栏代码创建于国际化框架完善之前，后续未完全迁移

2. **动态文本生成**：部分工具栏文本由代码动态生成（如包含运行时变量），难以静态国际化

3. **上下文相关性**：某些工具栏项的文本根据当前选择动态变化（如 "Copy" vs "Cut"），更适合运行时决定

4. **优先级差异**：核心对话框和表单标签优先处理，工具栏等辅助界面次之

**建议：** 对于工具栏国际化，应采用以下策略：
- 静态文本（如 "Save"、"Open"）应完全国际化
- 动态文本应提取到消息文件中，通过参数传递
- 确实需要运行时决定的文本，使用条件逻辑选择已国际化的键

### 10.6 验证方法

修复完成后，通过以下方式验证：

1. **编译检查**：确保代码无语法错误
   ```bash
   ./mvnw compile -pl plugins/tech/neo4j -am
   ```

2. **Spotless 格式化**：确保代码格式规范
   ```bash
   ./mvnw spotless:apply
   ```

3. **运行时验证**：切换语言设置，确认翻译正确加载

### 10.7 经验总结

1. **检测先行**：使用 `grep` 快速定位问题，避免手动逐行检查

2. **批量处理**：对于模式一致的修复，可以利用 `Edit` 工具的 `replace_all` 参数

3. **消息文件同步**：修改代码后必须同步更新 `en_US` 和 `zh_CN` 两个文件

4. **键命名规范**：采用 `类名.元素.类型.名称` 的四级命名法（如 `GraphOutputDialog.MapFields.Button`）

5. **区分文本类型**：
   - 用户可见标签 → 必须国际化
   - URL/数值配置 → 通常不需要国际化
   - 工具提示 → 应国际化
