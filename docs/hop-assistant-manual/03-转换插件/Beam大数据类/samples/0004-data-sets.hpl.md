# Pipeline: 0004-data-sets

## Basic Information

- **Pipeline Name:** 0004-data-sets
- **Source File:** `03-转换插件/Beam大数据类/samples/0004-data-sets.hpl`

## Transforms

| Name | Type |
|------|------|
| Left outer join | MergeJoin |
| customers | TextFileInput2 |
| customers-by-state | SortRows |
| full outer join | MergeJoin |
| full-outer-join-output | Dummy |
| inner join | MergeJoin |
| inner-join-output | Dummy |
| left-outer-join-output | Dummy |
| population-by-state | SortRows |
| right outer join | MergeJoin |
| right-outer-join-output | Dummy |
| state-population | TextFileInput2 |
| uppercase state | StringOperations |

## Hops

| From | To |
|------|----|
| customers | customers-by-state |
| customers-by-state | inner join |
| customers-by-state | Left outer join |
| customers-by-state | right outer join |
| customers-by-state | full outer join |
| inner join | inner-join-output |
| Left outer join | left-outer-join-output |
| right outer join | right-outer-join-output |
| full outer join | full-outer-join-output |
| state-population | uppercase state |
| uppercase state | population-by-state |
| population-by-state | full outer join |
| population-by-state | inner join |
| population-by-state | Left outer join |
| population-by-state | right outer join |

## Notes

This pipeline is used to create the reference data sets.

We want to make sure that the Beam "merge join" output

is identical in functionality on a row and field level

to what we produce in the Hop local engine.

We don't run this pipeline as part of the integration tests.

It is only used for the setup of the test.

---
