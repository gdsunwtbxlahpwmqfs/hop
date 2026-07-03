# Pipeline: complex

## Basic Information

- **Pipeline Name:** complex
- **Source File:** `03-转换插件/Beam大数据类/samples/complex.hpl`

## Transforms

| Name | Type |
|------|------|
| CA | Constant |
| Collect | Dummy |
| Customer data | BeamInput |
| Default | Constant |
| FL | Constant |
| Label: A-M | Constant |
| Label: N-Z | Constant |
| Lookup count per state | StreamLookup |
| Merge join | MergeJoin |
| NY | Constant |
| State data | BeamInput |
| Switch / case | SwitchCase |
| complex | BeamOutput |
| countPerState | MemoryGroupBy |
| name<n | FilterRows |
| uppercase state | StringOperations |

## Hops

| From | To |
|------|----|
| Customer data | Merge join |
| State data | uppercase state |
| uppercase state | Merge join |
| Customer data | countPerState |
| countPerState | Lookup count per state |
| Merge join | Lookup count per state |
| Lookup count per state | name<n |
| name<n | Label: A-M |
| name<n | Label: N-Z |
| Label: A-M | Switch / case |
| Label: N-Z | Switch / case |
| Switch / case | CA |
| Switch / case | FL |
| Switch / case | NY |
| Switch / case | Default |
| CA | Collect |
| FL | Collect |
| NY | Collect |
| Default | Collect |
| Collect | complex |
