# Stanford NLP（自然语言处理）

Stanford NLP 转换允许解析文本，并为更高层次的文本理解提供基础构建模块。Stanford CoreNLP 提供了一套自然语言分析工具，可以接受原始英文文本输入并完成以下工作：

- 给出单词的基本形态
- 标注词性（parts of speech）
- 识别公司名、人名等命名实体
- 规范化日期、时间和数字数量
- 以短语和词语依赖关系标注句子结构
- 指示哪些名词短语引用相同实体

它为更高层次的文本理解应用提供基础构建模块。

## 注意事项

- 该转换的依赖项未包含在应用程序中，需将以下文件放入 `/plugins/transforms/stanfordnlp/lib` 文件夹：
  - [stanford-corenlp 4.5.7](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7.jar)
  - 对应语言的模型文件，例如：
    - [阿拉伯语模型（Arabic）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-arabic.jar)
    - [中文模型（Chinese）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-chinese.jar)
    - [英语模型（English）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-english.jar)
    - [法语模型（French）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-french.jar)
    - [德语模型（German）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-german.jar)
    - [匈牙利语模型（Hungarian）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-hungarian.jar)
    - [意大利语模型（Italian）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-italian.jar)
    - [西班牙语模型（Spanish）](https://repo1.maven.org/maven2/edu/stanford/nlp/stanford-corenlp/4.5.7/stanford-corenlp-4.5.7-models-spanish.jar)
- 在 Spark、Flink、Dataflow 引擎上可能受支持（Maybe Supported），Hop Engine 完全支持。
