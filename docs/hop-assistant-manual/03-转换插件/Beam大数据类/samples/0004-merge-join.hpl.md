# Pipeline: 0004-merge-join

## Basic Information

- **Pipeline Name:** 0004-merge-join
- **Source File:** `03-转换插件/Beam大数据类/samples/0004-merge-join.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp//0004/0004-full-outer-join | BeamOutput |
| /tmp//0004/0004-left-outer-join | BeamOutput |
| /tmp/0004/0004-inner-join | BeamOutput |
| /tmp/0004/0004-right-outer-join | BeamOutput |
| Left outer join | MergeJoin |
| customers | BeamInput |
| full outer join | MergeJoin |
| inner join | MergeJoin |
| right outer join | MergeJoin |
| state-population | BeamInput |
| uppercase state | StringOperations |

## Hops

| From | To |
|------|----|
| Left outer join | /tmp//0004/0004-left-outer-join |
| customers | Left outer join |
| customers | full outer join |
| customers | inner join |
| customers | right outer join |
| full outer join | /tmp//0004/0004-full-outer-join |
| inner join | /tmp/0004/0004-inner-join |
| right outer join | /tmp/0004/0004-right-outer-join |
| state-population | uppercase state |
| uppercase state | Left outer join |
| uppercase state | full outer join |
| uppercase state | inner join |
| uppercase state | right outer join |
