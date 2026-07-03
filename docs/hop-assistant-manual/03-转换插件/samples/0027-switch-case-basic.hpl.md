# Pipeline: 0027-switch-case-basic

## Basic Information

- **Pipeline Name:** 0027-switch-case-basic
- **Source File:** `03-转换插件/samples/0027-switch-case-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| A | Dummy |
| AAA | Constant |
| B | Dummy |
| BBB | Constant |
| C | Dummy |
| CCC | Constant |
| D | Dummy |
| DDD | Constant |
| Rest | Dummy |
| Rest | Constant |
| Sample data | DataGrid |
| Switch / case | SwitchCase |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | Switch / case |
| A | AAA |
| B | BBB |
| C | CCC |
| D | DDD |
| Rest | Rest |
| AAA | Verify |
| BBB | Verify |
| CCC | Verify |
| DDD | Verify |
| Rest | Verify |
| Switch / case | A |
| Switch / case | B |
| Switch / case | C |
| Switch / case | D |
| Switch / case | Rest |
