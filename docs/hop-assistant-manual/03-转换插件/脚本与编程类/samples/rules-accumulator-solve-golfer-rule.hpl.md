# Pipeline: rules-accumulator-solve-golfer-rule

## Basic Information

- **Pipeline Name:** rules-accumulator-solve-golfer-rule
- **Source File:** `03-转换插件/脚本与编程类/samples/rules-accumulator-solve-golfer-rule.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate Color | DataGrid |
| Generate Golfers | DataGrid |
| Generate Position | DataGrid |
| Insert Color | JoinRows |
| Insert Position | JoinRows |
| Rule Accumulator | RuleAccumulator |
| Sort rows | SortRows |

## Hops

| From | To |
|------|----|
| Generate Golfers | Insert Position |
| Generate Position | Insert Position |
| Insert Position | Insert Color |
| Generate Color | Insert Color |
| Insert Color | Rule Accumulator |
| Rule Accumulator | Sort rows |

## Notes

Puzzle rules:

- A foursome of golfers is standing at a tee, in a line from left to right.

- Each golfer wears different colored pants; one is wearing red pants.

- The golfer to Fred’s immediate right is wearing blue pants.

- Joe is second in line.

- Bob is wearing plaid pants.

- Tom isn’t in position one or four, and he isn’t wearing the hideous orange pants.

- In what order will the four golfers tee off, and what color are each golfer’s pants?”

---
