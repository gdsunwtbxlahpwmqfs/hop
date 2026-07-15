### 舍入类型

Number 和 BigNumber 数据类型字段的舍入基于 [Java Rounding Mode](https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode)

默认情况下使用 `Half Even` 舍入模式，该模式会向"最近的邻居"舍入，除非两个邻居距离相等，此时向偶数邻居舍入。

示例：从 1 位小数舍入到 0 位小数
5.5 -> 6
2.5 -> 2
-2.5 -> -2
-5.5 -> -6

#### Unnecessary
断言请求的操作具有精确结果的舍入模式，因此不需要舍入。当您尝试降低数字精度时，此模式会抛出错误。

#### Ceiling
向正无穷方向舍入的模式。

#### Down
向零方向舍入的模式。

#### Floor
向负无穷方向舍入的模式。

#### Half Down
向"最近的邻居"舍入的模式，除非两个邻居距离相等，此时向下舍入。

#### Half Even
向"最近的邻居"舍入的模式，除非两个邻居距离相等，此时向偶数邻居舍入。

#### Half Up
向"最近的邻居"舍入的模式，除非两个邻居距离相等，此时向上舍入。

#### Up
远离零方向舍入的模式。

#### 示例

| 输入数字 | Up | Down | Ceiling | Floor | Half Up | Half Down | Half Even | Unnecessary |
|---|---|---|---|---|---|---|---|---|
| 5.5 | 6 | 5 | 6 | 5 | 6 | 5 | 6 | 抛出 ArithmeticException |
| 2.5 | 3 | 2 | 3 | 2 | 3 | 2 | 2 | 抛出 ArithmeticException |
| 1.6 | 2 | 1 | 2 | 1 | 2 | 2 | 2 | 抛出 ArithmeticException |
| 1.1 | 2 | 1 | 2 | 1 | 1 | 1 | 1 | 抛出 ArithmeticException |
| 1.0 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 |
| -1.0 | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
| -1.1 | -2 | -1 | -1 | -2 | -1 | -1 | -1 | 抛出 ArithmeticException |
| -1.6 | -2 | -1 | -1 | -2 | -2 | -2 | -2 | 抛出 ArithmeticException |
| -2.5 | -3 | -2 | -3 | -3 | -3 | -2 | -2 | 抛出 ArithmeticException |
| -5.5 | -6 | -5 | -6 | -6 | -6 | -5 | -6 | 抛出 ArithmeticException |
