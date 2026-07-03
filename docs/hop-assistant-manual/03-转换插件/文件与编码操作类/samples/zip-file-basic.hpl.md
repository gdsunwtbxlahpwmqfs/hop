# Pipeline: zip_file

## Basic Information

- **Pipeline Name:** zip_file
- **Source File:** `03-转换插件/文件与编码操作类/samples/zip-file-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Zip file | ZipFile |
| Get file names | GetFileNames |
| define output filename | Constant |
| create path | ConcatFields |

## Hops

| From | To |
|------|----|
| Get file names | define output filename |
| define output filename | create path |
| create path | Zip file |

## Notes

Get source filename and target file name in columns (you can use variables or get data from previous steps as well)

Pass the fields to zip file step (you can select post zip action for source file as well in step i.e. move source file or delete source file or do nothing)

---
