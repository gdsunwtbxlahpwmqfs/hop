# Pipeline: beers-wikipedia-scrub

## Basic Information

- **Pipeline Name:** beers-wikipedia-scrub
- **Source File:** `03-转换插件/Neo4j图数据库类/samples/beers-wikipedia-scrub.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/beers-wikipedia.hop | CubeOutput |
| 1 row | RowGenerator |
| https://nl.wikipedia.org/wiki/Lijst_van_Belgische_bieren | Http |
| HTML to lines | SplitFieldToRows3 |
| cleanup brand | ReplaceString |
| cleanup brewery | ReplaceString |
| cleanup percentage | ReplaceString |
| cleanup period | ReplaceString |
| cleanup types | ReplaceString |
| evaluate percentage | NumberRange |
| fieldnr | GroupBy |
| flatten | Denormaliser |
| format percentage | ScriptValueMod |
| keep | FilterRows |
| keep line? | ScriptValueMod |
| keep table data | ScriptValueMod |
| limit fields | SelectValues |
| line | SelectValues |
| trim/lowercase type | StringOperations |
| translate type | ValueMapper |
| types -> type | SplitFieldToRows3 |

## Hops

| From | To |
|------|----|
| 1 row | https://nl.wikipedia.org/wiki/Lijst_van_Belgische_bieren |
| https://nl.wikipedia.org/wiki/Lijst_van_Belgische_bieren | HTML to lines |
| HTML to lines | line |
| line | keep line? |
| keep line? | keep |
| keep | keep table data |
| keep table data | keep |
| keep | fieldnr |
| fieldnr | flatten |
| flatten | cleanup brand |
| cleanup brand | cleanup types |
| cleanup types | cleanup percentage |
| cleanup percentage | cleanup brewery |
| cleanup brewery | cleanup period |
| cleanup period | types -> type |
| types -> type | trim/lowercase type |
| trim/lowercase type | translate type |
| translate type | limit fields |
| format percentage | evaluate percentage |
| limit fields | format percentage |
| evaluate percentage | /tmp/beers-wikipedia.hop |

## Notes

The result of this pipeline is a binary file which contains

all the interesting beers data from the Wikipedia page.

---
