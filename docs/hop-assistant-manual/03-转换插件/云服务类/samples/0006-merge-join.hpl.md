# Pipeline: 0006-merge-join

## Basic Information

- **Pipeline Name:** 0006-merge-join
- **Source File:** `03-转换插件/云服务类/samples/0006-merge-join.hpl`

## Transforms

| Name | Type |
|------|------|
| Left outer join | MergeJoin |
| customers | BeamInput |
| full outer join | MergeJoin |
| full-outer-join | BeamOutput |
| inner join | MergeJoin |
| inner-join | BeamOutput |
| left-outer-join | BeamOutput |
| right outer join | MergeJoin |
| right-outer-join | BeamOutput |
| state-population | BeamInput |
| uppercase state | StringOperations |

## Hops

| From | To |
|------|----|
| customers | Left outer join |
| customers | full outer join |
| customers | inner join |
| customers | right outer join |
| Left outer join | left-outer-join |
| state-population | uppercase state |
| full outer join | full-outer-join |
| inner join | inner-join |
| right outer join | right-outer-join |
| uppercase state | Left outer join |
| uppercase state | full outer join |
| uppercase state | inner join |
| uppercase state | right outer join |
