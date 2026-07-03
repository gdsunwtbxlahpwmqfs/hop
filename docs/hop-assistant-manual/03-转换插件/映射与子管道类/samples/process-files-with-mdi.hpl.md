# Pipeline: process-files-with-mdi

## Basic Information

- **Pipeline Name:** process-files-with-mdi
- **Source File:** `03-转换插件/映射与子管道类/samples/process-files-with-mdi.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| PRM_TYPE | 1 |  |

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| File Metadata | FileMetadataPlugin |
| Get file names | GetFileNames |
| field position | ValueMapper |
| keep fields | FilterRows |
| keep filename | SelectValues |
| rename fields | ValueMapper |
| sort fields | SortRows |
| sort unique filename | SortRows |

## Hops

| From | To |
|------|----|
| Get file names | File Metadata |
| File Metadata | ETL metadata injection |
| File Metadata | keep filename |
| keep filename | sort unique filename |
| sort unique filename | ETL metadata injection |
| File Metadata | rename fields |
| rename fields | keep fields |
| keep fields | field position |
| field position | sort fields |
| sort fields | ETL metadata injection |

## Notes

This sample pipelines reads person information from 2 files with different layouts.

The data is written to a unified file format through metadata injection.

To run this sample, run this pipeline with the default PRM_TYPE value of 1, then run it again with 2 as the parameter value.

This will read ${PROJECT_HOME}/files/person-info-1.csv and ${PROJECT_HOME}/files/person-info-2.csv respectively.

The unified output file is written to ${PROJECT_HOME}/output/unified-person-data.csv.

To learn more about metadata injection: https://hop.apache.org//manual/latest/pipeline/metadata-injection.html

---
