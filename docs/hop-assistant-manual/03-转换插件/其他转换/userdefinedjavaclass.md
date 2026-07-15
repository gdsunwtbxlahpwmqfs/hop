# ![User Defined Java Class transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/userdefinedjavaclass.svg) User Defined Java Class

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Class code | Java 代码。 |
| Fields | 输出字段列表。 |
| Parameters | 您可以使用 Parameters 表来避免使用硬编码的字符串值，例如字段名称（例如 customer）。 |
| Info transforms | 用于读取数据的附加 transform。 |
| Target transforms | 目标 Transform。 |
| Test class | 测试该类。 |

## 用法

### 处理行

Processor 代码定义了 processRow() 方法，这是 transform 的核心。
此方法由 Pipeline 在紧密循环中调用，并将持续执行直到返回 false。

```java
String firstnameField;
String lastnameField;
String nameField;
 
public boolean processRow() throws HopException
{
    // Let's look up parameters only once for performance reason.
    //
    if (first) {
      firstnameField = getParameter("FIRSTNAME_FIELD");
      lastnameField = getParameter("LASTNAME_FIELD");
      nameField = getParameter("NAME_FIELD");
      first=false;
    }
 
    // First, get a row from the default input hop
    //
    Object[] r = getRow();
 
    // If the row object is null, we are done processing.
    //
    if (r == null) {
      setOutputDone();
      return false;
    }
 
    // It is always safest to call createOutputRow() to ensure that your output row's Object[] is large
    // enough to handle any new fields you are creating in this transform.
    //
    Object[] outputRow = createOutputRow(r, data.outputRowMeta.size());
 
    String firstname = get(Fields.In, firstnameField).getString(r);
    String lastname = get(Fields.In, lastnameField).getString(r);
 
    // Set the value in the output field
    //
    String name = firstname`" "`lastname;
    get(Fields.Out, nameField).setValue(outputRow, name);
 
    // putRow will send the row on to the default output hop.
    //
    putRow(data.outputRowMeta, outputRow);
 
    return true;
```

### 错误处理

如果您希望 Hop 处理在 Pipeline 中运行您的类时可能发生的错误，您必须实现自己的错误处理代码。
在添加任何错误处理代码之前，右键点击 Hop 客户端画布中的 User Defined Java Class transform，然后在出现的菜单中选择 Error Handling。
生成的 transform 错误处理设置对话框包含用于指定错误目标 transform 和关联字段名称的选项，您将使用这些选项在定义的代码中实现错误处理。

```java
try {

Object     numList = strsList.stream()
                        .map( new ToInteger() )
                     .sorted( new ReverseCase() )
                     .collect( Collectors.toList() );

    get( Fields.Out, "reverseOrder" ).setValue( row, numList.toString() );

} catch (NumberFormatException ex) {
    // Number List contains a value that cannot be converteds to an Integer.
    rowInError = true;
    errMsg = ex.getMessage();
    errCnt = errCnt + 1;
}

if ( !rowInError ) {
    putRow( data.outputRowMeta, row );
} else {
    // Output errors to the error hop. Right click on transform and choose "Error Handling..."
    putError(data.outputRowMeta, row, errCnt, errMsg, "Not allowed", "DEC_0");
}
```

上面代码示例中的 try 用于测试 numList 是否包含有效数字。
如果列表包含无效数字，则使用 putError 处理错误并将其定向到示例 Pipeline 中的 wlog: ErrorPath transform。
ErrorPath transform 也在 User Define Java Class transform 的 Target transforms 选项卡中指定。

### 日志记录

如果您希望 Hop 记录来自您的类的数据操作（如读取、写入、输出或更新数据），您需要在定义的 transform 中实现日志记录。
以下代码是实现日志记录的示例：

```java
putRow( data.outputMeta, r );

if ( checkFeedback( getLinesOutput() ) ) {
  if ( log.isBasic() ) {
    logBasic( "Have I got rows for you! " + getLinesOutput() );
  }
}
```

### 类和代码片段

您可以通过 Classes and Code Fragments 面板浏览您定义的类以及相关的代码片段和字段。
您可以右键点击此树中的任何项目来 Delete、Rename 或 Show Sample。

**Classes**

Classes 文件夹指示哪些类在 Class Code 面板中有对应的代码块选项卡。

**Code Snippits**

Code Snippits 文件夹显示与 User Defined Java Class transform 相关的内部 Hop 代码。
这些片段作为您类代码的参考显示。

**Input Fields**

Input fields 文件夹包含您在代码中定义的任何输入字段。
在处理定义的代码时，您将处理输入和输出字段。
处理输入字段有多种方式。
例如，首先请查看以下输入行的描述。

```java
IRowMeta inputRowMeta = getInputRowMeta();
```

inputRowMeta 对象包含输入行的 metadata。
它包括所有字段、它们的数据类型、长度、名称、格式掩码等。
您可以使用此对象来查找输入字段。
例如，如果您想查找名为 customer 的字段，您可以使用以下代码。

```java
IValueMeta customer = inputRowMeta.searchValueMeta("year");
```

因为如果需要对通过 Pipeline 的每一行都查找字段名称可能会很慢，您可以在第一段代码中提前查找字段名称，如以下示例所示：

```java
if (first) {
 yearIndex = getInputRowMeta().indexOfValue(getParameter("YEAR"));
 if (yearIndex<0) {
   throw new HopException("Year field not found in the input row, check parameter 'YEAR'\!");
 }
}
```

要获取 year 字段中包含的 Integer 值，您可以使用以下构造。

```java
Object[] r = getRow();
...
Long year = inputRowMeta().getInteger(r, yearIndex);
```

为了使此过程更简单，您可以使用以下形式的快捷方式。

```java
Long year = get(Fields.In, "year").getInteger(r);
```

此方法还考虑了上面提到的基于索引的优化。

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
