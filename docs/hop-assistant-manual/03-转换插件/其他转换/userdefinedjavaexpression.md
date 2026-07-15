# ![User Defined Java Expression transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/janino.svg) User Defined Java Expression

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

如果您有一个 Java 表达式，例如：

```java
C=A+B
```

那么您只需在对话框中输入表达式的右侧：

```java
A+B
```

值将作为它们原本的 Java 对象暴露给表达式：

| 数据类型 | Java 类 |
|---|---|
| BigNumber | BigDecimal |
| Binary | byte[] |
| Date | java.util.Date |
| Integer | java.lang.Long |
| Number | java.lang.Double |
| String | java.lang.String |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 为此 transform 指定一个名称。此名称在 Pipeline 中必须唯一。 |
| New field | 数据流中包含表达式结果的新字段。 |
| Java expression | 要评估的 Java 表达式。参见下方示例。 |
| Value type | 字段的数据类型。 |
| Length | 新字段中字符串的最大长度。 |
| Precision | 用于表示数字的小数位数。 |
| Replace value | 当您想替换现有字段中的值时，将此设置为与"New field"名称相同。 |

## 示例

**两个整数 A 和 B 相加**

```java
A+B
```

**拼接两个字符串：firstname 和 name，中间加一个空格**

```java
firstname`" "`name
```

或者如果您真的很在意性能，这可能会更快：

```java
new StringBuffer(firstname).append(" ").append(name).toString()
```

**使用原生 Java 和 API 函数**

```java
System.getProperty("os.name")
```

**业务规则（If / Then / Else）**

```java
a<c?true:false
```

这可以更复杂

```java
a<c?(a==1?1:2):3
```

甚至可以使用 OR 和 AND 以及其他运算符和函数

**使用常量**

如果您使用常量，在某些表达式中可能需要定义正确的类型，否则可能会抛出错误。
例如，如果您使用"0"，它是"int"，但 Qi Hop 的"Integer"是"java.lang.Long"，因此您会得到一个异常：

Incompatible expression types "int" and "java.lang.Long"

要解决此问题，请使用：

```java
test == null ? new Long(0) : test
```

上面的表达式检查"test"是否为 null，并用零 Long 替换它。
否则，返回未更改的"test"。

**从末尾截取字符串并测试 null 和最小长度**

假设您有输入字符串"location"，例如

    Orlando FL
    New York NY

您想分离州和城市。您可以使用以下表达式：

对于州（获取最后 2 个字符）：

```java
location != null && location.length()>2 ? location.substring(location.length()-2, location.length()) : null
```

对于城市（获取开头不含最后 2 个字符并修剪）：

```java
location != null && location.length()>2 ? location.substring(0, location.length()-2).trim() : location
```

**LIKE 运算符的功能（包含字符串）**

以下示例在 abc 位于源字符串中时返回 1，否则返回 2。当源字符串为 null 时也返回 2。
如果您希望返回值为 Integer 类型，请使用"new Long(1)"和"new Long(2)"。

```java
samplestr != null && samplestr.indexOf("abc")>-1 ? 1 : 2
```

## 阻止特定代码

作为一种简单的安全措施，您可以阻止执行包含特定字符串的代码。
这可以通过在位于 <Hop Installation>/plugins/transforms/janino 的 `codeExclusions.xml` 文件中添加排除项来完成

示例：
```xml
<exclusions>
        <exclusion>System.</exclusion>
        <exclusion>HopVfs.</exclusion>
    </exclusions>
```
