# Pipeline: 0036-text-file-input-validate

## Basic Information

- **Pipeline Name:** 0036-text-file-input-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-text-file-input-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Row denormaliser | Denormaliser |
| Validate | Dummy |
| Validate fields | Dummy |
| Validate files | Dummy |
| text-file-input-mdi.hpl file/ | XMLInputStream |
| fileNr | ScriptValueMod |
| files/textfile/text-file-input.xml - general | getXMLData |
| filter file elements | FilterRows |
| text-file-input-mdi.hpl - fields | getXMLData |
| text-file-input-mdi.hpl files/file | XMLInputStream |
| filter files/file elements | FilterRows |
| Row denormaliser 2 | Denormaliser |
| Select values | SelectValues |
| Select values 2 | SelectValues |

## Hops

| From | To |
|------|----|
| files/textfile/text-file-input.xml - general | Validate |
| text-file-input-mdi.hpl file/ | filter file elements |
| filter file elements | fileNr |
| fileNr | Row denormaliser |
| text-file-input-mdi.hpl - fields | Validate fields |
| text-file-input-mdi.hpl files/file | filter files/file elements |
| filter files/file elements | Row denormaliser 2 |
| Row denormaliser | Select values |
| Select values | Validate files |
| Row denormaliser 2 | Select values 2 |
| Select values 2 | Validate files |

## Notes

### Note 1

TODO: add exclude_filemask

---

### Note 2

TODO: add accept_transform_name

---
