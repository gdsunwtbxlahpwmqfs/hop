# Pipeline: switch-case

## Basic Information

- **Pipeline Name:** switch-case
- **Source File:** `03-转换插件/Beam大数据类/samples/0002-switch-case.hpl`

## Transforms

| Name | Type |
|------|------|
| CA | Constant |
| Collect | Dummy |
| Customers | BeamInput |
| Default | Constant |
| FL | Constant |
| NY | Constant |
| Switch / case | SwitchCase |
| switch-case | BeamOutput |

## Hops

| From | To |
|------|----|
| Collect | switch-case |
| Customers | Switch / case |
| Switch / case | CA |
| Switch / case | FL |
| Switch / case | NY |
| Switch / case | Default |
| CA | Collect |
| FL | Collect |
| NY | Collect |
| Default | Collect |
