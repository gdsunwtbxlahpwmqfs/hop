# ![Rules executor transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/rules_exec.svg) Rules executor

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 详情
对于 Rules Executor transform 处理的每一行，每个列都被转换为 _Rules.Column_，然后注入规则引擎并针对规则集执行。_Rules.Column_ 包含四个属性：

_externalSource_：布尔属性，指示此 Rules.Column 是否从外部源注入（来自已处理行的字段）

_name_：此字段的名称

_type_：存储在 payload 中的数据类型（ValueMetaInterface.typeCodes 之一——Number、String、Integer、Date、Boolean 等）

_payload_：此字段的值

结果通过创建 Rules.Column 对象来生成。通过名称配置 transform 来提取这些生成的对象。

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
所有 Rule 定义应包含 "import org.apache.hop.pipeline.transforms.drools.Rules.Column;" 以访问 Rules.Column 类。

对于输入行：fica_seed（Integer）、hi（String）、fica（Integer）；将在 Rules Engine 中创建三个 Rules.Column 对象：

| Rules.Row->row (Map) | Type | Payload |
|---|---|---|
| fica_seed | Integer | -3561151667 |
| hi | String | test |
| fica | Integer | bad |

可以定义和应用规则：
```drools
rule "Decline"
    dialect "mvel"
    when
        $fica : Column(name == "fica", payload < 550)

    then
        Column approvalStatus = new Column();
        approvalStatus.name = "approvalStatus"
        approvalStatus.type = String.class
        approvalStatus.payload = "declined"

        Column approvalClassification = new Column();
        approvalClassification.name = "approvalClass"
        approvalClassification.type = String.class
        approvalClassification.payload = "Declined: fica too low"

        insert(approvalStatus);
        insert(approvalClassification);

        System.out.println("Declined");
end
```
可以如上例左侧所示检查 Rules.Column 属性。

可以如上例右侧所示创建 Rules.Column 对象以供提取。

通过在 transform 的 Results 选项卡中定义 Rules.Column 名称，可以告知 transform 提取生成的 Rules.Column 对象。也可以通过设置 Result column type 来应用类型转换。
