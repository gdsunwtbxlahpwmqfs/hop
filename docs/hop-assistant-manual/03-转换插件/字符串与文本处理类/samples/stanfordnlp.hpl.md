# Pipeline: stanfordnlp

## Basic Information

- **Pipeline Name:** stanfordnlp
- **Source File:** `03-转换插件/字符串与文本处理类/samples/stanfordnlp.hpl`

## Transforms

| Name | Type |
|------|------|
| Paragraphs | DataGrid |
| Select values | SelectValues |
| Analyse sentences | StanfordSimpleNlp |
| Text output | TextFileOutput |

## Hops

| From | To |
|------|----|
| Paragraphs | Analyse sentences |
| Analyse sentences | Select values |
| Select values | Text output |

## Notes

### Note 1

Example - Given the following sentence:

"She stopped at a small stall selling fresh flowers, admiring the vibrant colours of the roses and tulips."

The breakdown of the sentence using TreeBank POS tags is as follows:

She: PRP (Personal Pronoun)

stopped: VBD (Verb, Past Tense)

at: IN (Preposition)

a: DT (Determiner)

small: JJ (Adjective)

stall: NN (Noun, Singular)

selling: VBG (Verb, Gerund/Present Participle)

fresh: JJ (Adjective)

flowers: NNS (Noun, Plural)

,: , (Comma)

admiring: VBG (Verb, Gerund/Present Participle)

the: DT (Determiner)

vibrant: JJ (Adjective)

colours: NNS (Noun, Plural)

of: IN (Preposition)

the: DT (Determiner)

roses: NNS (Noun, Plural)

and: CC (Coordinating Conjunction)

tulips: NNS (Noun, Plural)

---

### Note 2

This plugin is designed to provide text analysis at the sentence level, powered by Stanford CoreNLP's Simple API.

It processes paragraphs to deliver linguistic and statistical insights.

Key Features:

-  Sentence Splitting: Automatically segments the paragraph into individual sentences.

-  Sentence Indexing: Identifies and returns the start and end indices of each sentence within the original paragraph.

-  Character and Word Count: Computes the total number of characters and words in each sentence.

-  TreeBank POS Tagging: Utilises TreeBank Part-of-Speech (POS) tagging for each word, offering detailed syntactic information.

-  POS Tag Frequency: Counts and summarises the occurrence of each POS tag (e.g., NN, VB, JJ) within each sentence, providing a thorough linguistic profile.

---
