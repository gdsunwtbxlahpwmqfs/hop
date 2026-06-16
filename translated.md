# Hop 翻译（i18n）模块调用关系

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
| `AbstractMessageHandler` | `core/src/main/java/org/apache/hop/i18n/AbstractMessageHandler.java` | 消息处理器的抽象基类，定义 getString 方法签名 |
| `GlobalMessages` | `core/src/main/java/org/apache/hop/i18n/GlobalMessages.java` | 全局消息类的核心实现，管理 ResourceBundle 和 locale 回退逻辑 |
| `LafMessageHandler` | `core/src/main/java/org/apache/hop/i18n/LafMessageHandler.java` | 继承 GlobalMessages，支持通过 LAF (Look And Feel) 动态替换消息处理器 |
| `BaseMessages` | `core/src/main/java/org/apache/hop/i18n/BaseMessages.java` | 供外部类调用的静态入口类（单例模式） |

## 2. 支持类

| 类名 | 文件位置 | 职责 |
|------|----------|------|
| `GlobalMessageUtil` | `core/src/main/java/org/apache/hop/i18n/GlobalMessageUtil.java` | 工具类，处理实际的字符串格式化、locale 回退、ResourceBundle 加载 |
| `GlobalMessageControl` | `GlobalMessageUtil` 的内部类 | `ResourceBundle.Control` 的自定义实现，控制资源包加载和回退逻辑 |
| `LanguageChoice` | `core/src/main/java/org/apache/hop/i18n/LanguageChoice.java` | 语言选择管理，单例模式，管理默认语言设置 |
| `LafFactory` | `core/src/main/java/org/apache/hop/laf/LafFactory.java` | LAF 工厂类，用于动态获取消息处理器实现 |
| `IHandler` | `core/src/main/java/org/apache/hop/laf/IHandler.java` | LAF Handler 的基础接口 |
| `BasePropertyHandler` | `core/src/main/java/org/apache/hop/laf/BasePropertyHandler.java` | LAF 属性处理器基类 |

## 3. 调用流程

```
┌─────────────────────────────────────────────────────────────────────┐
│                          外部调用者                                   │
│   BaseMessages.getString( packageName, key, params... )              │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                        BaseMessages (单例)                           │
│   - 通过 LafFactory.getHandler(IMessageHandler.class) 获取处理器       │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      LafFactory (LAF 工厂)                           │
│   - 返回 LafMessageHandler 或 RemoveAltKeyMessageHandler (macOS)      │
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
│   - getActiveLocales(): 获取活跃的 locale 列表                        │
│   - calculateString(): 使用 MessageFormat 格式化                     │
│   - getBundle(): 加载 ResourceBundle                                 │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                                  ↓
┌─────────────────────────────────────────────────────────────────────┐
│                    ResourceBundle (Java 原生)                        │
│   - 从 messages_xx_XX.properties 文件加载翻译                        │
└─────────────────────────────────────────────────────────────────────┘
```

## 4. 资源文件结构

翻译资源文件命名格式：`messages_语言_国家.properties`

示例：
- `messages.properties` - 默认（英文）
- `messages_zh_CN.properties` - 简体中文
- `messages_fr_FR.properties` - 法语（法国）

资源文件位置：各模块的 `src/main/resources` 目录下，例如：
- `core/src/main/resources/org/apache/hop/i18n/messages_zh_CN.properties`
- `ui/src/main/resources/org/apache/hop/ui/hopgui/dialog/messages_zh_CN.properties`
- `plugins/transforms/*/src/main/resources/.../messages_xx_XX.properties`

## 5. 支持的语言

| Locale Code | 语言名称 | Beta 状态 |
|-------------|----------|-----------|
| en_US | English (US) | N |
| nl_NL | Nederlands (Beta) | Y |
| zh_CN | 简体中文 (Simplified Chinese) | N |
| es_ES | Español (Spain) (Beta) | Y |
| fr_FR | Français | N |
| de_DE | Deutsch (Beta) | Y |
| pt_BR | Portuguese (Brazil) | N |
| pt_PT | Portuguese (Portugal) (Beta) | Y |
| es_AR | Español (Argentina) (Beta) | Y |
| no_NO | Norwegian (Norway) (Beta) | Y |
| it_IT | Italian (Italy) | N |
| ja_JP | Japanese (Japan) (Beta) | Y |
| ko_KR | Korean (Korea) (Beta) | Y |
| pl_PL | Polski (Beta) | Y |

## 6. Locale 回退机制

GlobalMessageUtil 的回退顺序：

1. **用户首选语言** - `LanguageChoice.getInstance().getDefaultLocale()`
2. **故障转移语言** - `Locale.US` (FAILOVER_LOCALE)
3. **根语言** - `Locale.ROOT` (messages.properties)

回退过程示例（查找 "dialog.ok" 键，中文环境）：
1. 尝试 `messages_zh_CN.properties` 中的 `dialog.ok`
2. 如果找不到，尝试 `messages_en_US.properties` 中的 `dialog.ok`
3. 如果仍然找不到，尝试 `messages.properties` 中的 `dialog.ok`
4. 如果都不存在，返回 `!dialog.ok!`（标记缺失的键）

## 7. 使用示例

### 7.1 在类中使用翻译

```java
import static org.apache.hop.i18n.BaseMessages.getString;

public class MyClass {
  // 方式1: 通过包名获取
  private static final String MSG = getString("org.apache.hop.my.package", "key");

  // 方式2: 通过 Class 获取
  private static final String MSG = getString(MyClass.class, "key");

  // 方式3: 带参数的消息
  private static final String MSG = getString(MyClass.class, "param.message", "value1", "value2");
}
```

### 7.2 属性文件示例

```properties
# messages.properties
welcome=Welcome to Hop
param.message=The value is {0} and {1}
error.code=Error occurred: {0}
```

```properties
# messages_zh_CN.properties
welcome=欢迎使用 Hop
param.message=值为 {0} 和 {1}
error.code=发生错误：{0}
```

## 8. 相关配置文件

| 配置文件 | 位置 | 说明 |
|----------|------|------|
| LAF package 属性 | `hop-config.json` 或系统属性 | 指定消息处理器的包路径 |
| HopConfig | Hop 配置类 | 存储用户语言偏好设置 |

## 9. 关键文件路径汇总

```
core/src/main/java/org/apache/hop/i18n/
├── IMessageHandler.java           # 消息处理器接口
├── AbstractMessageHandler.java    # 抽象基类
├── GlobalMessages.java            # 核心实现
├── LafMessageHandler.java        # LAF 支持的消息处理器
├── BaseMessages.java             # 静态入口类
├── GlobalMessageUtil.java        # 工具类
├── LanguageChoice.java           # 语言选择管理
├── LafMessageHandler.java        # LAF 消息处理

core/src/main/java/org/apache/hop/laf/
├── IHandler.java                 # Handler 接口
├── LafFactory.java               # LAF 工厂
├── BasePropertyHandler.java      # 属性处理器基类
├── LafDelegate.java              # LAF 委托
└── RemoveAltKeyMessageHandler.java # macOS 专用处理器
```
