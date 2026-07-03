# Pipeline: 0009-stream-lookup

## Basic Information

- **Pipeline Name:** 0009-stream-lookup
- **Source File:** `03-转换插件/Beam大数据类/samples/0009-stream-lookup.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0009/stream-lookup.csv | BeamOutput |
| Lookup count per state | StreamLookup |
| Merge join | MergeJoin |
| countPerState | MemoryGroupBy |
| input/customers-noheader-1k.txt | BeamInput |
| input/state-population.txt | BeamInput |
| uppercase state | StringOperations |

## Hops

| From | To |
|------|----|
| Merge join | Lookup count per state |
| input/state-population.txt | uppercase state |
| countPerState | Lookup count per state |
| uppercase state | Merge join |
| input/customers-noheader-1k.txt | countPerState |
| input/customers-noheader-1k.txt | Merge join |
| Lookup count per state | /tmp/0009/stream-lookup.csv |
