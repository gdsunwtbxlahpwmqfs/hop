# ![Rules accumulator transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/rules_acc.svg) Rules accumulator

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 详情
一旦 Rules Accumulator transform 收集了所有传入的行（例如——前一个 transform 关闭），这些行将被转换为 Rules.Row 对象，并传递到规则引擎中针对给定的规则集执行。

Rules.Row 定义为字段的键/值 Map，其中键是字段名称，值是字段的值；以及 externalSource 布尔属性，用于指示 Rules.Row 对象是在规则集中创建的还是从外部源注入的。

行的字段通过 "Row (column["<fieldname>"])" 访问。

### Rules 选项卡

_Rules_ 选项卡是您输入规则定义或规则文件引用的地方。

| 选项 | 描述 |
|---|---|
| Rules filename |  |
| Rules script to execute |  |

### Rules result 选项卡

Rules result 选项卡定义 Rules 输出字段的布局。

| 选项 | 描述 |
|---|---|
| Result column name |  |
| Result column type |  |

## 示例
所有 Rule 定义应包含 "import org.apache.hop.pipeline.transforms.drools.Rules.Row;" 以访问 Rules.Row 类。

对于具有行元数据：name（String）、position（Integer）、color（String）的输入，将为每行创建一个 Rules.Row 对象，其中包含这些字段的 Map。

| Rules.Row->row (Map) | Name | Position | Color |
|---|---|---|---|
|  | Fred | 1 | Blue |
|  | Fred | 2 | Red |
|  | Bob | 1 | Blue |
|  | Bob | 1 | Red |

可以定义和应用规则：
```drools
rule "Golfers problem"
    dialect "mvel"
    when

    # Define Fred
    $fred : Row ( externalSource == true,
        column["name"] == "Fred"
    )

    # Define Bob
    $bob : Row ( externalSource == true,
        column["name"] == "Bob",
        column["position"] != $fred.column["position"],
        column["color"] == "blue",
        column["color"] != $fred.column["color"],
    )

    then

      Row fredRow = new Row();
      Row bobRow = new Row();

      fredRow.addColumn("name", "Fred");
      fredRow.addColumn("position", $fred.column["position"]);
      fredRow.addColumn("color", $fred.column["color"]);

      bobRow.addColumn("name", "Bob");
      bobRow.addColumn("position", $bob.column["position"]);
      bobRow.addColumn("color", $bob.column["color"]);

      insert(fredRow);
      insert(bobRow);

end
```

可以如上例左侧所示检查 Rules.Row 对象。

可以如上例右侧所示为数据流创建 Rules.Row 对象。

通过在 transform 的 Results 选项卡中定义字段 Map 的名称，可以告知 transform 从生成的行对象中提取哪些字段。也可以通过设置 Result column type 来应用类型转换。
