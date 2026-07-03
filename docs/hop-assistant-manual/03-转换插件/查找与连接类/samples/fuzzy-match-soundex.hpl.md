# Pipeline: fuzzy-match-soundex

## Basic Information

- **Pipeline Name:** fuzzy-match-soundex
- **Source File:** `03-转换插件/查找与连接类/samples/fuzzy-match-soundex.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Fuzzy match | FuzzyMatch |
| Master Data | DataGrid |
| Reference Data | DataGrid |

## Hops

| From | To |
|------|----|
| Fuzzy match | Output |
| Master Data | Fuzzy match |
| Reference Data | Fuzzy match |

## Notes

Fuzzy Match using Soundex function based on Similarly sounding phonetics

---
