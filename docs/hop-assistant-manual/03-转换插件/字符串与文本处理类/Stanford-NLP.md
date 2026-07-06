# Stanford NLP（自然语言处理）

## 功能概述


Stanford NLP 转换允许解析文本，并为更高层次的文本理解提供基础构建模块。Stanford CoreNLP 提供了一套自然语言分析工具，可以接受原始英文文本输入并完成以下工作：
- 给出单词的基本形态
- 标注词性（parts of speech）
- 识别公司名、人名等命名实体
- 规范化日期、时间和数字数量
- 以短语和词语依赖关系标注句子结构
- 指示哪些名词短语引用相同实体
它为更高层次的文本理解应用提供基础构建模块。

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0026-replace-in-string-with-backslash</name>
    <name_sync_with_filename>Y</name_sync_with_filename>
    <description/>
    <extended_description/>
    <pipeline_version/>
    <pipeline_type>Normal</pipeline_type>
    <pipeline_status>0</pipeline_status>
    <parameters>
      <parameter>
        <name>REPLACE_PARAM</name>
        <default_value>xx\zz</default_value>
        <description>Variable with backslash</description>
      </parameter>
    </parameters>
    <capture_transform_performance>N</capture_transform_performance>
    <transform_performance_capturing_delay>1000</transform_performance_capturing_delay>
    <transform_performance_capturing_size_limit>100</transform_performance_capturing_size_limit>
    <created_user>-</created_user>
    <created_date>2022/11/27 16:28:58.618</created_date>
    <modified_user>-</modified_user>
    <modified_date>2022/11/27 16:28:58.618</modified_date>
  </info>
  <notepads>
  </notepads>
  <order>
    <hop>
      <from>sample data</from>
      <to>Replace in string</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Replace in string</from>
      <to>Verify</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Replace in string</name>
    <type>ReplaceString</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <fields>
      <field>
        <case_sensitive>N</case_sensitive>
        <in_stream_name>value_variable</in_stream_name>
        <is_unicode>N</is_unicode>
        <replace_by_string>${REPLACE_PARAM}</replace_by_string>
        <replace_string>backslash</replace_string>
        <set_empty_string>N</set_empty_string>
        <use_regex>N</use_regex>
        <whole_word>N</whole_word>
      </field>
      <field>
        <case_sensitive>N</case_sensitive>
        <in_stream_name>value_field</in_stream_name>
        <is_unicode>N</is_unicode>
        <replace_field_by_string>replaceBy</replace_field_by_string>
        <replace_string>backslash</replace_string>
        <set_empty_string>N</set_empty_string>
        <use_regex>N</use_regex>
        <whole_word>N</whole_word>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>288</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Verify</name>
    <type>Dummy</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <attributes/>
    <GUI>
      <xloc>464</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
  <transform>
    <name>sample data</name>
    <type>DataGrid</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <data>
      <line>
        <item>try to specify the backslash</item>
        <item>single \ backslash</item>
      </line>
      <line>
        <item>method to replace the backslash</item>
        <item>double \\ backslash</item>
      </line>
    </data>
    <fields>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>value_variable</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>value_field</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>replaceBy</name>
        <type>String</type>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>128</xloc>
      <yloc>96</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

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
- 在 Spark、Flink、Dataflow 引擎上可能受支持（Maybe Supported），Qi 数据治理平台 Engine 完全支持。
