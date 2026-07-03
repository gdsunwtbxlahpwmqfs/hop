# Pipeline: 0007-group-by

## Basic Information

- **Pipeline Name:** 0007-group-by
- **Source File:** `03-转换插件/Beam大数据类/samples/0007-group-by.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0007/group-by.csv | BeamOutput |
| group by state | MemoryGroupBy |
| input/customers-noheader-1k.txt | BeamInput |

## Hops

| From | To |
|------|----|
| input/customers-noheader-1k.txt | group by state |
| group by state | /tmp/0007/group-by.csv |
