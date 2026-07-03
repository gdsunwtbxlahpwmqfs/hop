# Pipeline: detectlanguage

## Basic Information

- **Pipeline Name:** detectlanguage
- **Source File:** `03-转换插件/字符串与文本处理类/samples/detectlanguage.hpl`

## Transforms

| Name | Type |
|------|------|
| Corpus | DataGrid |
| Detect Language | DetectLanguage |
| Match | FilterRows |
| Select values | SelectValues |
| Text output (Fail) | TextFileOutput |
| Text output (Pass) | TextFileOutput |

## Hops

| From | To |
|------|----|
| Corpus | Detect Language |
| Detect Language | Select values |
| Select values | Match |
| Match | Text output (Fail) |
| Match | Text output (Pass) |

## Notes

The Detect Language plugin uses Lingua to identify the language of documents, with support for 75 languages.

While generally accurate, occasional misclassifications may still occur, particularly with less-resourced languages.

Other reasons could be due to:

- Shared vocabulary or similar structures

- Short or ambiguous text

- Language overlaps.

- Code switching (e.g. text contains one language and then switches to another).

This sample demostrates 74 successful detections and 1 misclassification: a Latin phrase incorrectly identified as Catalan.

English: Seize the day, because life is short and time flies.

Latin: Carpe diem, quia vita brevis est et tempus fugit.

Catalan: Aprofita el dia, perquè la vida és curta i el temps passa volant.

(Note: Google translate was used to verify misclassification)

Reference:

https://github.com/pemistahl/lingua

---
