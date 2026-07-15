一些 Transform 共享一个通用的字段信息面板。该面板允许用户修改关于特定画布上 Transform 存储或处理信息方式的元数据，同时也会设置其他 Transform 呈现或输出数据的预期方式。

New Field - 从步骤输出的字段名称。
Type - 从步骤输出的字段类型（String、Integer、Number 等）。
Length - 通常用于 String，但如果为整数指定长度，会产生前置补 0 的填充效果，直到整数达到指定长度。
Precision - 通常用于 Number 类型步骤中关心小数点后有效数字的情况。
Format - 经常用于为日期、数字甚至货币指定格式。遵循 https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html 并支持常见的数字格式。（查找该标准的链接）。
Group - 用于指定输出中千位分隔符使用的字符，例如美国使用逗号（1,000），德国使用句点（1.000）。
Decimal - 用于分隔整数部分和小数部分的字符，例如美国为 1,000.10，德国为 1.000,10。
Currency - 保留使用，但很少被 Transform 使用。需要了解哪个作为示例。
Nullif - 如果字段返回 <null>，应该写入什么值。
Default - 如果字段为空，应该赋予什么默认值。
