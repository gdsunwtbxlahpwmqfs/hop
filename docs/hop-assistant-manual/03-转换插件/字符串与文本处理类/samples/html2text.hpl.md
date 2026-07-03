# Pipeline: html2text

## Basic Information

- **Pipeline Name:** html2text
- **Source File:** `03-转换插件/字符串与文本处理类/samples/html2text.hpl`

## Transforms

| Name | Type |
|------|------|
| Basic Html Cleaning | Html2Text |
| Extract Text | Html2Text |
| Extract Text (no pre-cleaning) | Html2Text |
| Html Documents | DataGrid |
| Text output (no pre-cleaning) | TextFileOutput |
| Text output (with pre-cleaning) | TextFileOutput |

## Hops

| From | To |
|------|----|
| Html Documents | Basic Html Cleaning |
| Basic Html Cleaning | Extract Text |
| Html Documents | Extract Text (no pre-cleaning) |
| Extract Text | Text output (with pre-cleaning) |
| Extract Text (no pre-cleaning) | Text output (no pre-cleaning) |

## Notes

### Note 1

This plugin uses JSoup to clean and extract text from HTML documents.

Cleaning creates a new, clean document, from the original dirty document, containing only elements allowed by the safelist.

Safe-lists define what HTML (elements and attributes) to allow through the cleaner. Everything else is removed.

Available safelists are:

- None

- Relaxed

- Basic

- Simple text

- Basic with Images

For more details on the safelists, see https://jsoup.org/apidocs/org/jsoup/safety/Safelist.html

---

### Note 2

In this example, there is little difference between cleaning the HTML beforehand or not, aside from a return character in one of the outputs.

However, the variation in inputs for text extraction can affect the extracted output.

Additionally, cleaning the HTML beforehand allows the inclusion of extra steps, such as applying regex rules, to improve the pre-processing of

HTML documents before text extraction.

---
