# ![Calculator transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/calculator.svg) Calculator

| == 支持的引擎 |  |
|---|---|
| Hop Engine | ![Supported,24](../../assets/images/check_mark.svg) |
| Spark | ![Supported,24](../../assets/images/check_mark.svg) |
| Flink | ![Supported,24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported,24](../../assets/images/check_mark.svg) |

## 选项

| 列 | 描述 |
|---|---|
| New field | 计算结果的输出字段。对于仅用于计算后续字段但最终结果中不需要的临时字段，将 `Remove` 设置为 `Y`。 |
| Calculation | 此新字段所需的计算。 |
| Field A | 计算中使用的第一个字段。 |
| Field B | 计算中使用的第二个字段。 |
| Field C | 计算中使用的第三个字段。 |
| Value Type | 此计算结果使用的数据类型。 |
| Length | 此计算结果设置的长度。 |
| Precision | 此计算结果设置的精度。 |
| Remove | 一个布尔标志。对于仅用于中间计算且不需要包含在输出中的临时字段，设置为 `Y`。 |
| Conversion mask | 应用于日期或数值字段的转换掩码。 |
| Decimal symbol | 输出字段中设置的小数符号。 |
| Grouping symbol | 输出字段中设置的分组符号。 |
| Currency symbol | 输出字段中设置的货币符号。 |

下表列出了 `Calculator` transform 中支持的计算：

| 功能（Hop GUI 视图） | 功能（Metadata Injection 视图） | 描述 |
|---|---|---|
| Set field to constant A | CONSTANT |  |
| Create a copy of field A | COPY_OF_FIELD |  |
| A + B | ADD | A 加 B。 |
| A - B | SUBTRACT | A 减 B。 |
| A * B | MULTIPLY | A 乘以 B。 |
| A / B | DIVIDE | A 除以 B。 |
| A * A | SQUARE | A 的平方。 |
| SQRT( A ) | SQUARE_ROOT | A 的平方根。 |
| 100 * A / B | PERCENT_1 | A 在 B 中的百分比。 |
| A - ( A * B / 100 ) | PERCENT_2 | 从 A 中减去 A 的 B%。 |
| A + ( A * B / 100 ) | PERCENT_3 | 给 A 加上 A 的 B%。 |
| A + B *C | COMBINATION_1 | A 加上 B 乘以 C。 |
| SQRT( A*A ` B*B ) | COMBINATION_2 | 计算 ?(A2`B2)。 |
| ROUND( A ) | ROUND_1 | 返回最接近参数的整数。 |
| ROUND( A, B ) | ROUND_2 | 将 A 舍入到 B 位小数的最近偶数。 |
| STDROUND( A ) | ROUND_STD_1 | 将 A 舍入到最近的整数。 |
| STDROUND( A, B ) | ROUND_STD_2 | 与 STDROUND (A) 使用相同的舍入方法，但保留 B 位小数。 |
| CEIL( A ) | CEIL | 向上取整函数，将数字映射到大于等于它的最小整数。 |
| FLOOR( A ) | FLOOR | 向下取整函数，将数字映射到小于等于它的最大整数。 |
| NVL( A, B ) | NVL | 如果 A 不为 NULL，返回 A，否则返回 B。请注意，有时您的变量不是 null 而是空字符串。 |
| Date A + B days | ADD_DAYS | 在日期字段 A 上添加 B 天。 |
| Year of date A | YEAR_OF_DATE | 计算日期 A 的年份。 |
| Month of date A | MONTH_OF_DATE | 计算日期 A 的月份数字。 |
| Day of year of date A | DAY_OF_YEAR | 计算一年中的第几天（1-365）。 |
| Day of month of date A | DAY_OF_MONTH | 计算一月中的第几天（1-31）。 |
| Day of week of date A | DAY_OF_WEEK | 计算一周中的第几天（1-7）。1 是星期日，2 是星期一，以此类推。 |
| Week of year of date A | WEEK_OF_YEAR | 计算一年中的第几周（1-54）。 |
| ISO8601 Week of year of date A | WEEK_OF_YEAR_ISO8601 | 以 ISO8601 方式计算一年中的第几周（1-53）。 |
| ISO8601 Year of date A | YEAR_OF_DATE_ISO8601 | 以 ISO8601 方式计算年份。 |
| Byte to hex encode of string A | BYTE_TO_HEX_ENCODE | 将字符串中的字节编码为十六进制表示。 |
| Hex to byte encode of string A | HEX_TO_BYTE_DECODE | 将字符串从其自身的十六进制表示编码。 |
| Char to hex encode of string A | CHAR_TO_HEX_ENCODE | 将字符串中的字符编码为十六进制表示。 |
| Hex to char decode of string A | HEX_TO_CHAR_DECODE | 从十六进制表示解码字符串（当 A 长度为奇数时添加前导 0）。 |
| Checksum of a file A using CRC-32 | CRC32 | 使用 CRC-32 计算文件的校验和。 |
| Checksum of a file A using Adler-32 | ADLER32 | 使用 Adler-32 计算文件的校验和。 |
| Checksum of a file A using MD5 | MD5 | 使用 MD5 计算文件的校验和。 |
| Checksum of a file A using SHA-1 | SHA1 | 使用 SHA-1 计算文件的校验和。 |
| Checksum of a file A using SHA-256 | SHA256 | 使用 SHA-256 计算文件的校验和。 |
| Checksum of a file A using SHA-384 | SHA384 | 使用 SHA-384 计算文件的校验和。 |
| Checksum of a file A using SHA-512 | SHA512 | 使用 SHA-512 计算文件的校验和。 |
| Levenshtein Distance (Source A and Target B) | LEVENSHTEIN_DISTANCE | 计算 Levenshtein 距离：http://en.wikipedia.org/wiki/Levenshtein_distance |
| Metaphone of A (Phonetics) | METAPHONE | 计算 A 的 metaphone：http://en.wikipedia.org/wiki/Metaphone |
| Double metaphone of A | DOUBLE_METAPHONE | 计算 A 的双重 metaphone：http://en.wikipedia.org/wiki/Double_Metaphone |
| Absolute value ABS(A) | ABS | 计算 A 的绝对值。 |
| Remove time from a date A | REMOVE_TIME_FROM_DATE | 移除 A 的时间值。注意：圣保罗和巴西其他一些地区在午夜 0:00 更改夏令时（DST）。这使得在夏令时从 0:00 变为 1:00 am 的特定日期无法将时间设置为 0:00。 |
| Date A - Date B (in days) | DATE_DIFF | 计算日期字段 A 和日期字段 B 之间相差的天数。 |
| A ` B ` C | ADD3 | A 加 B 加 C。 |
| First letter of each word of a string A in capital | INIT_CAP | 将字符串中每个单词的首字母大写。 |
| UpperCase of a string A | UPPER_CASE | 将字符串转换为大写。 |
| LowerCase of a string A | LOWER_CASE | 将字符串转换为小写。 |
| Mask XML content from string A | MASK_XML | 转义 XML 内容；将字符替换为 &值。 |
| Protect (CDATA) XML content from string A | USE_CDATA | 指示 XML 字符串是通用字符数据，而非非字符数据或具有更具体、有限结构的字符数据。 |
| Remove CR from a string A | REMOVE_CR | 从字符串中移除回车符。 |
| Remove LF from a string A | REMOVE_LF | 从字符串中移除换行符。 |
| Remove CRLF from a string A | REMOVE_CRLF | 从字符串中移除回车符/换行符。 |
| Remove TAB from a string A | REMOVE_TAB | 从字符串中移除制表符。 |
| Return only digits from string A | GET_ONLY_DIGITS | 仅输出字符串中的数字（0-9）。 |
| Remove digits from string A | REMOVE_DIGITS | 移除字符串中的所有数字（0-9）。 |
| Return the length of a string A | STRING_LEN | 返回字符串的长度。 |
| Load file content in binary | LOAD_FILE_CONTENT_BINARY | 将给定文件（在字段 A 中）的内容加载为二进制数据类型（例如图片）。 |
| Add time B to date A | ADD_TIME_TO_DATE | 将时间添加到日期，返回日期和时间作为一个值。 |
| Quarter of date A | QUARTER_OF_DATE | 返回日期的季度（1 到 4）。 |
| variable substitution in string A | SUBSTITUTE_VARIABLE | 替换字符串中的变量。 |
| Unescape XML content | UNESCAPE_XML | 从字符串中反转义 XML 内容。 |
| Escape HTML content | ESCAPE_HTML | 转义字符串中的 HTML 内容。 |
| Unescape HTML content | UNESCAPE_HTML | 反转义字符串中的 HTML 内容。 |
| Escape SQL content | ESCAPE_SQL | 转义字符串中的字符，使其适合传递给 SQL 查询。 |
| Date A - Date B (working days) | DATE_WORKING_DIFF | 计算日期字段 A 和日期字段 B 之间的差值（仅工作日周一至周五）。 |
| Date A + B Months | ADD_MONTHS a | 在日期字段 A 上添加 B 个月。 |
| Check if an XML file A is well-formed | CHECK_XML_FILE_WELL_FORMED | 验证 XML 文件输入。 |
| Check if an XML string A is well-formed | CHECK_XML_WELL_FORMED | 验证 XML 字符串输入。 |
| Get encoding of file A | GET_FILE_ENCODING | 猜测给定文件的最佳编码（UTF-8）。 |
| Dameraulevenshtein distance between String A and String B | DAMERAU_LEVENSHTEIN | 计算字符串之间的 Damerau-Levenshtein 距离：http://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance |
| NeedlemanWunsch distance between String A and String B | NEEDLEMAN_WUNSH | 计算字符串之间的 Needleman-Wunsch 距离：http://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm |
| Jaro similitude between String A and String B | JARO | 返回两个字符串之间的 Jaro 相似系数。 |
| JaroWinkler similitude between String A and String B | JARO_WINKLER | 返回两个字符串之间的 Jaro 相似系数：http://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance |
| SoundEx of String A | SOUNDEX | 将字符串编码为 Soundex 值。 |
| RefinedSoundEx of String A | REFINED_SOUNDEX | 检索给定字符串对象的 Refined Soundex 代码 |
| Date A + B Hours | ADD_HOURS |  |
| Date A ` B Minutes | ADD_MINUTES a | 在日期字段上添加 B 分钟。` |
| Date A - Date B (milliseconds) | DATE_DIFF_MSEC | 从日期字段 A 中减去 B 毫秒 |
| Date A - Date B (seconds) a | DATE_DIFF_SEC a | 从日期字段 A 中减去 B 秒。 |
| Date A - Date B (minutes) | DATE_DIFF_MN a | 从日期字段 A 中减去 B 分钟。 |
| Date A - Date B (hours) | DATE_DIFF_HR a | 从日期字段 A 中减去 B 小时。 |
| Hour of Day of Date A | HOUR_OF_DAY | 提取给定日期的小时部分 |
| Minute of Hour of Date A | MINUTE_OF_HOUR | 提取给定日期的分钟部分 |
| Second of Hour of Date A | SECOND_OF_MINUTE | 提取给定日期的秒部分 |
| ROUND_CUSTOM( A , B ) | ROUND_CUSTOM_1 | （...未实现...？） |
| ROUND_CUSTOM( A , B , C ) | ROUND_CUSTOM_2 | （...未实现...？） |
| Date A + B seconds | ADD_SECONDS | 在日期字段 A 上添加 B 秒 |
| Remainder of A / B | REMAINDER | A 和 B 之间整数除法的余数（A 模 B） |
| Base64 Encode | BASE64_ENCODE | 以 Base64 编码编码字符串，末尾不加填充 |
| Base64 Encode (padded) | BASE64_ENCODE_PADDED | 以 Base64 编码编码字符串，末尾加填充，参见 [RFC 4648 第 3.2 节](https://doi.org/10.17487/RFC4648) |
| Base64 Decode | BASE64_DECODE | 解码 Base64 编码的字符串。支持填充和非填充输入。 |
| First day of month (A) date | FIRST_DAY_OF_MONTH | 将字段 (A) 的日期设置为 1。 |
| Last day of month (A) date | LAST_DAY_OF_MONTH | 将字段 (A) 的日期设置为给定月份的最后一天。 |
| Encode a URL | URL_ENCODE | 对 URL 进行编码，使浏览器/Web 服务能正确处理 URL 调用。 |
| Decode a URL | URL_DECODE | 解码 URL。 |

## Metadata Injection 支持

此 transform 的所有字段均支持 metadata injection。
您可以将此 transform 与 ETL Metadata Injection 配合使用，在运行时向 pipeline 传递 metadata。

使用上表中"功能（Metadata Injection 视图）"列中的值来指定应用于字段的操作（计算类型）。

## 关于长度、精度和数据类型影响结果的常见问题

*问*：我使用 Calculator transform 中的 A/B 制作了一个 pipeline，结果舍入错误：两个输入字段是整数，但我的结果类型是 Number(6, 4)，所以我期望整数在执行除法之前被转换为 Number。

例如，如果我想执行 28/222，我得到的是 0.0 而不是我期望的 0.1261。
所以结果类型似乎被忽略了。
如果我将两个输入类型都改为 Number(6, 4)，我得到的结果是 0.12612612612612611，仍然忽略了结果类型（小数点后 4 位）。

这是为什么？

*答*：Length 和 Precision 只是 metadata 信息。

如果您想舍入到指定精度，应该在另一个 transform 中执行此操作。
但请记住，对双精度浮点值进行舍入本来就是徒劳的。
浮点数以近似值存储（它是浮动的），所以 0.1261（您期望的输出）可能（很可能）最终被存储为 0.126099999999 或 0.1261000000001（注意：BigNumbers 不是这种情况）

所以最终，当我们将数字存储到输出表中时，我们使用 BigDecimal 进行舍入，但在 pipeline 执行期间不会这样做。
Text File Output transform 也是如此。
如果您指定 Integer 作为结果类型，内部数字格式将被保留，您点击"Get Fields"时会自动填入所需的 Integer 类型。
所需的转换将在那时那地完成。

简而言之：当我们将数据落地到某处时才转换为所需的 metadata 类型，而不是在此之前。

*问*：数据类型在内部是如何工作的？
*答*：您可能会注意到，如果将一个 Integer 和一个 Number 相乘，结果总是会被舍入。
这是因为 Calculator 取乘法左侧（A）的数据类型作为计算的驱动类型。
因此，如果您想要更高的精度，应该将字段 B 放在左侧，或将数据类型更改为 Number，一切就会正常。
