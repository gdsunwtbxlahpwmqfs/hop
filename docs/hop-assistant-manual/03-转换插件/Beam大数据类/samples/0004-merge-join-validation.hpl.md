# Pipeline: 0004-merge-join-validation

## Basic Information

- **Pipeline Name:** 0004-merge-join-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0004-merge-join-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0004-full-outer-join*.csv | TextFileInput2 |
| /tmp/0004-inner-join*.csv | TextFileInput2 |
| /tmp/0004-left-outer-join*.csv | TextFileInput2 |
| /tmp/0004-right-outer-join*.csv | TextFileInput2 |
| Sort by id | SortRows |
| Sort by id 2 | SortRows |
| Sort by id 3 | SortRows |
| Sort by id 4 | SortRows |
| full-outer-join-data | Dummy |
| inner-join-data | Dummy |
| left-outer-join-data | Dummy |
| right-outer-join-data | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0004-inner-join*.csv | Sort by id |
| Sort by id | inner-join-data |
| /tmp/0004-left-outer-join*.csv | Sort by id 2 |
| Sort by id 2 | left-outer-join-data |
| /tmp/0004-right-outer-join*.csv | Sort by id 3 |
| Sort by id 3 | right-outer-join-data |
| /tmp/0004-full-outer-join*.csv | Sort by id 4 |
| Sort by id 4 | full-outer-join-data |
