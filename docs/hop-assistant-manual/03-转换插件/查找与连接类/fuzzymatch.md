# ![Fuzzy match transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/fuzzymatch.svg) Fuzzy match

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

### General 选项卡

| Option | Description |
|---|---|
| Transform name | 此 Transform 在 Pipeline 工作区中显示的名称 |
| Lookup transform | 标识包含要匹配字段的 Transform |
| Lookup field | 标识要匹配的字段 |
| Main stream field | 标识要与 Lookup field 匹配的主数据流 |
| Algorithm | 标识要使用的字符串匹配算法——选项包括 Levenshtein、Damerau-Levenshtein、Needleman Wunsch、Jaro、Jaro Winkler、Pair letters similarity、Metaphone、Double Metaphone、SoundEx 或 Refined SoundEx |
| Case sensitive | 标识数据流是否可以基于大小写字母的差异进行区分——仅适用于 Levenshtein 算法 |
| Get closer value | 勾选时，返回相似度得分最高的单个结果——不勾选时，返回满足最小值和最大值设置的所有匹配项作为分隔列表，由值分隔符分隔 |
| Minimum value | 标识最低可能的相似度得分 |
| Maximal value | 标识最高可能的相似度得分 |
| Values separator | 标识分隔匹配项的字符串。 |

*算法定义*

在 Algorithm 字段中，有多个选项可用于比较和匹配字符串。

- [Levenshtein](https://en.wikipedia.org/wiki/Levenshtein_distance) 和 [Damerau-Levenshtein](https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance)：通过查看从一个字符串转换到另一个字符串需要多少次编辑操作来计算两个字符串之间的距离。
前者仅考虑插入、删除和替换。
后者增加了转置操作。
得分表示所需的最小更改次数。
例如，John 和 Jan 之间的差异为二；要将 John 变为 Jan，需要一个操作将 O 替换为 A，另一个操作删除 H。
- [Needleman Wunsch](https://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm)：计算两个序列的相似度，主要用于生物信息学。
该算法计算间隙罚分。
上述示例的得分为负二。
- [Jaro 和 Jaro Winkler](https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance)：计算两个字符串之间的相似度指数。
结果为 0 到 1 之间的分数，0 表示完全不相似，1 表示完全匹配。
- Pair letters similarity：将两个字符串拆分成对，通过用共同对数除以两个字符串所有对的总和来计算两个字符串的相似度。
- Metaphone、Double Metaphone、SoundEx 和 Refined SoundEx：是[语音算法](https://en.wikipedia.org/wiki/Phonetic_algorithm#:~:text=A%20phonetic%20algorithm%20is%20an,indexing%20words%20in%20other%20languages.)，尝试根据字符串的发音来匹配。
每种算法都基于英语，不适用于比较其他语言。
** [Metaphone 算法](https://en.wikipedia.org/wiki/Metaphone) 返回基于给定单词英语发音的编码值。
John 和 Jan 的编码值都返回 JN。
** [Double Metaphone](https://en.wikipedia.org/wiki/Metaphone#Double_Metaphone) 算法对其前身有根本性的设计改进，并使用更复杂的规则集进行编码。
它可以为字符串返回主要和次要编码值。
John 和 Jan 分别返回 metaphone 键值 JN 和 AN。
** [Soundex 算法](https://en.wikipedia.org/wiki/Soundex) 为名称返回一个编码值，由一个字母后跟三个数字组成。
字母是名称的首字母，数字编码其余的辅音。
** Refined SoundEx 算法是对其前身的改进。
此算法的编码值为六位数字长度，首字符被编码，单个名称可以返回多种可能的编码。
使用此算法，John 返回值 160000 和 460000，Jan 也是如此。

### Fields 选项卡

Fields 选项卡允许您定义如何返回比较结果。

| Option | Description |
|---|---|
| Match field | 定义包含比较值的列名称 |
| Value field | 定义要返回值的相似度得分 |

您还可以指定要从查找流中检索的附加字段列表。
