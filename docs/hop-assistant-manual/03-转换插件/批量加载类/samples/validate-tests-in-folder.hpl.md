# Pipeline: validate-tests-in-folder

## Basic Information

- **Pipeline Name:** validate-tests-in-folder
- **Source File:** `03-转换插件/批量加载类/samples/validate-tests-in-folder.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PARENT_FOLDER |  | The folder in which the parent workflow was executed. |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Errors only | FilterRows |
| Execute Unit Tests | ExecuteTests |
| Get all tests | JsonInput |
| Log failing tests | WriteToLog |
| count | GroupBy |
| count>0 | FilterRows |
| folder | ReplaceString |
| limit paths | FilterRows |
| message | ScriptValueMod |
| parentFolder | GetVariable |
| with data set | FilterRows |

## Hops

| From | To |
|------|----|
| limit paths | Execute Unit Tests |
| Get all tests | parentFolder |
| with data set | message |
| message | Errors only |
| Errors only | count |
| Log failing tests | Abort |
| count | count>0 |
| count>0 | Log failing tests |
| parentFolder | folder |
| folder | limit paths |
| Execute Unit Tests | with data set |

## Notes

Find all tests and limit the pipelines to the ones in ${TEST_FOLDER}

Then execute all the tests and get the results.

If any of the tests fails, log a report and abort.

---
