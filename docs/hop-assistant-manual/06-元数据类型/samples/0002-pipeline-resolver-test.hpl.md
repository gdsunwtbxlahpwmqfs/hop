# Pipeline: 0002-pipeline-resolver-test

## Basic Information

- **Pipeline Name:** 0002-pipeline-resolver-test
- **Source File:** `06-元数据类型/samples/0002-pipeline-resolver-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Union | Dummy |
| Validate | Dummy |
| db1 | Dummy |
| db1 string substitution test | Calculator |
| db2 | Dummy |
| db2 string substitution test | Calculator |
| db3 | Dummy |
| db3 string substitution test | Calculator |
| get db1 configuration | GetVariable |
| get db2 configuration | GetVariable |
| get db3 configuration | GetVariable |

## Hops

| From | To |
|------|----|
| db1 | Union |
| db2 | Union |
| db3 | Union |
| Union | Validate |
| get db1 configuration | db1 string substitution test |
| db1 string substitution test | db1 |
| get db2 configuration | db2 string substitution test |
| db2 string substitution test | db2 |
| get db3 configuration | db3 string substitution test |
| db3 string substitution test | db3 |
