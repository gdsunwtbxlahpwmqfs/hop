# 唯一行（HashSet）

## 功能概述


唯一行（HashSet）（Unique Rows (HashSet)）转换删除重复行，仅过滤唯一的行作为转换的输入数据。
该转换与唯一行（Unique Rows）管道转换的区别在于，它在内存中跟踪重复行，不需要排序的输入来处理重复行。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 用于比较的字段 | 用于判断重复的字段（留空表示使用所有字段） |
| 重定向重复行 | 是否将重复行重定向到单独的输出 |

## XML代码模板

```xml
<pipeline>
  <info>
    <name>0034-unique-rows-hashset</name>
    <name_sync_with_filename>Y</name_sync_with_filename>
    <description/>
    <extended_description/>
    <pipeline_version/>
    <pipeline_type>Normal</pipeline_type>
    <pipeline_status>0</pipeline_status>
    <parameters>
    </parameters>
    <capture_transform_performance>N</capture_transform_performance>
    <transform_performance_capturing_delay>1000</transform_performance_capturing_delay>
    <transform_performance_capturing_size_limit>100</transform_performance_capturing_size_limit>
    <created_user>-</created_user>
    <created_date>2021/07/13 21:09:00.016</created_date>
    <modified_user>-</modified_user>
    <modified_date>2021/07/13 21:09:00.016</modified_date>
  </info>
  <notepads>
    <notepad>
      <backgroundcolorblue>251</backgroundcolorblue>
      <backgroundcolorgreen>232</backgroundcolorgreen>
      <backgroundcolorred>201</backgroundcolorred>
      <bordercolorblue>90</bordercolorblue>
      <bordercolorgreen>58</bordercolorgreen>
      <bordercolorred>14</bordercolorred>
      <fontbold>N</fontbold>
      <fontcolorblue>90</fontcolorblue>
      <fontcolorgreen>58</fontcolorgreen>
      <fontcolorred>14</fontcolorred>
      <fontitalic>N</fontitalic>
      <fontname>Segoe UI</fontname>
      <fontsize>9</fontsize>
      <height>-1</height>
      <xloc>112</xloc>
      <yloc>32</yloc>
      <note>Warning, row are sorted in case insentive order and dataset is case sensitive order</note>
      <width>437</width>
    </notepad>
  </notepads>
  <order>
    <hop>
      <from>Data grid</from>
      <to>sort</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>sort</from>
      <to>Unique rows (HashSet)</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Unique rows (HashSet)</from>
      <to>Result</to>
      <enabled>Y</enabled>
    </hop>
    <hop>
      <from>Unique rows (HashSet)</from>
      <to>Duplicate row</to>
      <enabled>Y</enabled>
    </hop>
  </order>
  <transform>
    <name>Data grid</name>
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
        <item>ABCD</item>
        <item>Z</item>
        <item>1</item>
      </line>
      <line>
        <item>ABCD</item>
        <item>Z</item>
        <item>2</item>
      </line>
      <line>
        <item>ABCD</item>
        <item>z</item>
        <item>2</item>
      </line>
      <line>
        <item>ABCD</item>
        <item>z</item>
        <item>6</item>
      </line>
      <line>
        <item>abcd</item>
        <item>X</item>
        <item>9</item>
      </line>
      <line>
        <item>abcd</item>
        <item>X</item>
        <item>9</item>
      </line>
      <line>
        <item>EFG</item>
        <item>X</item>
        <item>8</item>
      </line>
      <line>
        <item>EFG</item>
        <item>Z</item>
        <item>1</item>
      </line>
      <line>
        <item>ABCD</item>
        <item>Z</item>
        <item>2</item>
      </line>
      <line>
        <item>ABCD</item>
        <item>z</item>
        <item>2</item>
      </line>
      <line>
        <item>EFG</item>
        <item>X</item>
        <item>6</item>
      </line>
      <line>
        <item>EFG</item>
        <item>Z</item>
        <item>8</item>
      </line>
    </data>
    <fields>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>Field_A</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>Field_B</name>
        <type>String</type>
      </field>
      <field>
        <length>-1</length>
        <precision>-1</precision>
        <set_empty_string>N</set_empty_string>
        <name>Field_C</name>
        <type>Integer</type>
      </field>
    </fields>
    <attributes/>
    <GUI>
      <xloc>112</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Duplicate row</name>
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
      <xloc>384</xloc>
      <yloc>240</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Result</name>
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
      <xloc>496</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform>
    <name>Unique rows (HashSet)</name>
    <type>UniqueRowsByHashSet</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <error_description>duplicate</error_description>
    <fields>
      <field>
        <name>Field_A</name>
      </field>
      <field>
        <name>Field_B</name>
      </field>
    </fields>
    <reject_duplicate_row>Y</reject_duplicate_row>
    <store_values>Y</store_values>
    <attributes/>
    <GUI>
      <xloc>384</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform>
    <name>sort</name>
    <type>SortRows</type>
    <description/>
    <distribute>Y</distribute>
    <custom_distribution/>
    <copies>1</copies>
    <partitioning>
      <method>none</method>
      <schema_name/>
    </partitioning>
    <compress>N</compress>
    <directory>${java.io.tmpdir}</directory>
    <fields>
      <field>
        <ascending>Y</ascending>
        <case_sensitive>N</case_sensitive>
        <collator_enabled>N</collator_enabled>
        <collator_strength>0</collator_strength>
        <name>Field_A</name>
        <presorted>N</presorted>
      </field>
      <field>
        <ascending>Y</ascending>
        <case_sensitive>N</case_sensitive>
        <collator_enabled>N</collator_enabled>
        <collator_strength>0</collator_strength>
        <name>Field_B</name>
        <presorted>N</presorted>
      </field>
      <field>
        <ascending>Y</ascending>
        <case_sensitive>N</case_sensitive>
        <collator_enabled>N</collator_enabled>
        <collator_strength>0</collator_strength>
        <name>Field_C</name>
        <presorted>N</presorted>
      </field>
    </fields>
    <sort_size>1000000</sort_size>
    <unique_rows>N</unique_rows>
    <attributes/>
    <GUI>
      <xloc>240</xloc>
      <yloc>112</yloc>
    </GUI>
  </transform>
  <transform_error_handling>
    <error>
      <source_transform>Unique rows (HashSet)</source_transform>
      <target_transform>Duplicate row</target_transform>
      <is_enabled>Y</is_enabled>
      <nr_valuename/>
      <descriptions_valuename/>
      <fields_valuename/>
      <codes_valuename/>
      <max_errors/>
      <max_pct_errors/>
      <min_pct_rows/>
    </error>
  </transform_error_handling>
  <attributes/>
</pipeline>
```

## 注意事项

- 该转换仅支持 Qi 数据治理平台 Engine，不支持 Spark、Flink、Dataflow。
- 与"唯一行"不同，它不需要输入排序，因为它在内存中使用 HashSet 跟踪所有行。
- 注意内存消耗，因为所有行都需要保留在内存中用于去重。
