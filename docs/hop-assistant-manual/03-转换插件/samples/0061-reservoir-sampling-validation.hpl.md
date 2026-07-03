# Pipeline: 0061-reservoir-sampling-validation

## Basic Information

- **Pipeline Name:** 0061-reservoir-sampling-validation
- **Source File:** `03-转换插件/samples/0061-reservoir-sampling-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Reservoir sampling | ReservoirSampling |
| Verify | Dummy |
| input | DataGrid |

## Hops

| From | To |
|------|----|
| input | Reservoir sampling |
| Reservoir sampling | Verify |
