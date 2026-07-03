# Pipeline: vertica-docker

## Basic Information

- **Pipeline Name:** vertica-docker
- **Source File:** `03-转换插件/批量加载类/samples/vertica-docker.hpl`

## Transforms

| Name | Type |
|------|------|
| Vertica bulk loader | VerticaBulkLoader |
| generate 10M rows | RowGenerator |
| generate book data | Fake |
| string length 150 | SelectValues |

## Hops

| From | To |
|------|----|
| generate 10M rows | generate book data |
| generate book data | string length 150 |
| string length 150 | Vertica bulk loader |

## Notes

Run this sample in a Vertica Docker container:

1) pull the docker image: "docker pull vertica/vertica-ce"

2) start the Vertica container:

docker run -p 5433:5433 -p 5444:5444 \

--mount type=volume,source=vertica-data,target=/data \

--name vertica_ce \

vertica/vertica-ce

3) create a Vertica connection:

- connection name: vertica

- host: localhost

- username: dbadmin

- port: 5433

- password: leave blank

- database name: vmart

4) create the "books" table

CREATE TABLE books (

author VARCHAR(150)

, genre VARCHAR(150)

, publisher VARCHAR(150)

, title VARCHAR(150)

)

5) run the pipeline

;

---
