# Pipeline: sample_pipeline_resolver_usage

## Basic Information

- **Pipeline Name:** sample_pipeline_resolver_usage
- **Source File:** `06-元数据类型/samples/sample_pipeline_resolver_usage.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| INPUT_NUMBER | 100 |  |

## Transforms

| Name | Type |
|------|------|
| Get some numbers | GetVariable |

## Notes

This Sample uses the sample_pipeline_resolver.hpl to add 5 to a given number.

The syntax is: #{sampleResolver:<NUMBER>:output_value}

The first part sampleResolver is the name of the resolver to use.

The second part is the value given to the resolving pipeline

The third part is the output field from the resolving pipeline

It is possible to combine variable resolvers or use parameters using the ${MY_PARAMETER} syntax inside the variable

---
