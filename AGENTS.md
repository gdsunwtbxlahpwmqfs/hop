# Qi Hop — Agent Notes

## Project Overview

Qi Hop is a data/metadata orchestration platform (ETL). Java 21 multi-module Maven project (current version: `2.19.0-SNAPSHOT`). Parent POM: `org.apache:apache:38`. License: Apache 2.0 — all source files **must** carry the ASF license header (enforced by `apache-rat-plugin`).

## Build & Verify

```bash
./mvnw clean install                          # full build (slow, runs all tests)
./mvnw clean install -Pfast-build             # skip tests, checks, spotless
./mvnw clean install -Dassemblies=false       # skip assembly packaging
```

CI enforces this order (all must pass):
1. `mvn clean apache-rat:check` — license headers
2. `mvn clean checkstyle:check` — star imports forbidden
3. `mvn spotless:check` — formatting
4. `mvn clean install -T 1C -B -C -e -fae -V -Dmaven.compiler.fork=true -Dsurefire.rerunFailingTestsCount=2 -Dassemblies=false -Djacoco.skip=true`

### Formatting

Spotless enforces **Google Java Format** + import ordering + unused import removal. Runs automatically at `compile` phase.

```bash
./mvnw spotless:apply     # auto-fix formatting
./mvnw spotless:check     # verify only
```

POM files are also sorted by Spotless (sortPom, 4-space indent).

### Checkstyle

Only rule: **no star imports** (`import foo.bar.*`). Config at `tools/maven/checkstyle.xml`.

### Maven Profiles

| Profile | Activation | Purpose |
|---------|-----------|---------|
| `fast-build` | Manual (`-Pfast-build`) | Skips checkstyle, jacoco, javadoc, rat, tests, spotless |
| `base` | Active by default | Includes core modules: core, engine, engine-beam, lib, lib-jdbc, lib-p2, plugins, rap, rcp, rest, ui |
| `assemblies` | Active by default | Adds the `assemblies` module. Pass `-Dassemblies=false` to skip packaging |
| `assembly` | Auto (if `src/assembly/assembly.xml` exists) | Runs `maven-assembly-plugin:single` at `package` phase |
| `filtered-resources` | Auto (if `src/main/resources-filtered/` exists) | Adds Maven-filtered resources via `build-helper-maven-plugin` |
| `swt-unix` / `swt-mac` / `swt-windows` | OS-detected | Selects platform-specific SWT binary |
| `uitest` | Manual (`-Puitest`) | Enables SWTBot UI tests (sets `includedGroups=uitest`) |
| `apache-release` | Release mode | Generates CycloneDX SBOM |
| `deploy-snapshots` | Manual | Uploads snapshots to Apache repo |

## Testing

- JUnit 5 (Jupiter 6.x) + Mockito 5.x + Hamcrest. Test tag: `junit5`.
- Unit tests live alongside source in `src/test/java`.
- Integration tests are in the separate `integration-tests/` directory (not run by default unit test phase).

```bash
./mvnw test -pl plugins/transforms/json -am                      # single module + deps
./mvnw test -pl plugins/transforms/json -am -Dtest=JsonReaderTest # single test class
./mvnw test -pl plugins/transforms/json -am -Dtest="JsonReaderTest#testBasic"  # single method
```

UI tests (SWTBot) require `-Puitest` profile. On Linux they need `xvfb-run`. On macOS, `-XstartOnFirstThread` is added automatically.

## Module Structure

```
core/            — core library (hop-core)
engine/          — pipeline/workflow execution engine
engine-beam/     — Apache Beam engine integration
engine-bench/    — benchmark module
lib/             — shared libraries
lib-jdbc/        — JDBC library
lib-p2/          — P2 library (includes swtbot, tm4e sub-modules)
plugins/
  transforms/    — 151 data transform plugins
  actions/       — 55 workflow action plugins
  databases/     — 46 database connection plugins
  engines/       — 1 engine plugin (beam)
  misc/          — 13 plugins (async, debug, git, mail, projects, etc.)
  tech/          — 17 technology plugins (aws, azure, google, neo4j, etc.)
  resolvers/     — 1 plugin (pipeline resolver)
  valuetypes/    — 1 plugin (uuid)
ui/              — SWT-based shared UI code
rcp/             — desktop application (Rich Client Platform)
rap/             — web application (RAP/Tomcat)
rest/            — REST API server
assemblies/      — distribution packaging (client, web, plugins, static)
dev-scripts/     — developer scripts (build, start-web-dev, etc.)
docker/          — Dockerfiles (web/rest/unified/fatjar)
docs/            — developer manuals (user/tech/dev/assistant)
helm/            — Kubernetes Helm chart
resources/       — JDBC drivers, shared jar download scripts
tools/           — build configs (checkstyle.xml, find_duplicate_jars.sh)
scripts/         — documentation pipeline scripts (adoc-to-md, url-mapping, etc.)
integration-tests/ — integration test suites (40 suites)
```

Plugin artifact naming: `hop-<type>-<name>` (e.g. `hop-transform-json`, `hop-action-ftp`).

### Plugin Categories (285 plugins total)

**transforms/** (151): addsequence, addsnowflakeid, analyticquery, append, abort, blockuntiltransformsfinish, blockingtransform, calculator, changefileencoding, checksum, clonerow, closure, coalesce, combinationlookup, concatfields, constant, cratedbbulkloader, creditcardvalidator, databaselookup, databasejoin, datagrid, datastream, dbimpact, dbproc, ddl, delay, delete, denormaliser, detectemptystream, detectlanguage, detectlastrow, dorisbulkloader, drools, dynamicsqlrow, edi2xml, execinfo, execprocess, execsqlrow, fake, fieldschangesequence, fieldsplitter, fileexists, filelocked, filemetadata, filesfromresult, filestoresult, filterrows, flattener, formula, fuzzymatch, getfilenames, getfilesrowcount, getsubfolders, gettablenames, getvariable, groupby, html2text, http, httppost, ifnull, insertupdate, janino, javascript, jdbc-metadata, joinrows, json, kafka, languagemodelchat, ldap, loadfileinput, mapping, memgroupby, mergejoin, mergerows, metainject, metadata, metastructure, monetdbbulkloader, multimerge, mysqlbulkloader, normaliser, nullif, numberrange, orabulkloader, pgbulkloader, pgp, pipelineexecutor, processfiles, propertyinput, propertyoutput, randomvalue, regexeval, repeatfields, reservoirsampling, rest, rowgenerator, rowsfromresult, rowstoresult, samplerows, sasinput, schemamapping, script, selectvalues, serverstatus, setvalueconstant, setvaluefield, setvariable, snowflake, sortedmerge, sort, splunk, splitfieldtorows, sql, sqlfileoutput, ssh, standardizephonenumber, stanfordnlp, streamlookup, streamschemamerge, stringcut, stringoperations, switchcase, synchronizeaftermerge, systemdata, tablecompare, tableexists, tableinput, tableoutput, terafast, textfile, tika, tokenreplacement, uniquerows, uniquerowsbyhashset, univariatestats, validator, valuemapper, verticabulkloader, webservices, webserviceavailable, workflowexecutor, writetolog, xml, yamlinput, zipfile

**actions/** (55): abort, addresultfilenames, as400command, checkdbconnection, checkfilelocked, columnsexist, copyfiles, copymoveresultfilenames, createfile, createfolder, delay, deletefile, deletefiles, deletefolders, deleteresultfilenames, dostounix, eval, evalfilesmetrics, evaluatetablecontent, filecompare, fileexists, filesexist, folderisempty, folderscompare, ftp, http, join, movefiles, mssqlbulkload, mysqlbulkfile, mysqlbulkload, pgpfiles, ping, pipeline, repeat, sendnagiospassivecheck, shell, simpleeval, snmptrap, snowflake, sql, success, tableexists, telnet, truncatetables, unzip, waitforfile, waitforsql, webserviceavailable, workflow, writetofile, writetolog, xml, zipfile

**databases/** (46): access, as400, cache, clickhouse, cockroachdb, cratedb, db2, derby, doris, duckdb, exasol4, firebird, generic, googlebigquery, greenplum, h2, hive, hypersonic, impala, infobright, informix, ingres, interbase, iris, kingbasees, mariadb, monetdb, mssql, mssqlnative, mysql, netezza, oracle, oraclerdb, postgresql, redshift, sapdb, singlestore, snowflake, sqlbase, sqlite, sybase, sybaseiq, teradata, universe, vectorwise, vertica

**misc/** (13): async, debug, documentation, git, import, mail, passwords, projects, py4j, reflection, rest, static-schema, testing

**tech/** (17): arrow, avro, aws, azure, cassandra, databricks, dropbox, elastic, google, minio, mongodb, neo4j, opensearch, parquet, salesforce, vault, webdav

**engines/** (1): beam | **resolvers/** (1): pipeline | **valuetypes/** (1): uuid

## Key Conventions

- **Lombok** is used project-wide (provided scope, v1.18.42). Classes use `@Getter`, `@Setter`, etc.
- **Jandex** indexing (`jandex-maven-plugin` v3.5.3) runs for plugin discovery — annotations are indexed at build time.
- Source encoding: UTF-8. Timezone: UTC. Locale: `en_US`.
- JVM needs many `--add-opens`/`--add-exports` flags (configured in `.mvn/jvm.config` and `maven-surefire-plugin.argLine`).
- `.mvn/jvm.config` also sets `-Duser.timezone=UTC`, `-Dfile.encoding=UTF-8`, `-Duser.language=en -Duser.country=US`, and suppresses JaCoCo agent logging.
- `.mvn/extensions.xml` registers `maven-buildtime-extension` for per-mojo build time reporting.
- Monaco Editor files for `rap/` are downloaded at build time (not committed).
- `src/main/resources-filtered/` is a standard pattern for Maven-filtered resources (activated by profile `filtered-resources`).

## Working with Plugins

Each plugin under `plugins/<category>/<name>/` is an independent Maven module. Supported categories: `transforms`, `actions`, `databases`, `engines`, `misc`, `tech`, `resolvers`, `valuetypes`. When adding a new plugin:

1. Create directory under the appropriate parent (e.g. `transforms/`)
2. Add `<module>` to parent `pom.xml`
3. Set parent to the aggregator POM (e.g. `hop-plugins-transforms`)
4. Include ASF license header in all files
5. Run `./mvnw spotless:apply` before committing

## Assemblies

The `assemblies/` module produces 4 distributable packages:

| Module | Output | Description |
|--------|--------|-------------|
| `static` | `hop-assemblies-static-*.zip` | Base layer: licenses, shell scripts, static resources |
| `plugins` | `hop-assemblies-plugins-*.zip` | All plugin zips aggregated and unpacked into flat structure |
| `client` | `hop-client-*.zip` | Complete desktop distribution: core + engine + UI + plugins + JDBC drivers + SWT binaries (5 platforms) |
| `web` | `hop.war` | Qi Hop Web (RAP) WAR deployable for Tomcat, includes REST API |

## Developer Scripts (`dev-scripts/`)

| Script | Purpose |
|--------|---------|
| `build-quick.sh` | Fast build: downloads monetdb-jdbc if needed, then `mvn clean install -Dfast-build -DskipTests -Dassemblies=false` |
| `build-web.sh` | Builds Web (RAP) assembly targeting Linux SWT platform; output in `assemblies/web/target/webapp/` |
| `build-kb-index.sh` | Rebuilds the RAG knowledge base index via Qi Hop Web REST API (`/knowledgebase` endpoints) |
| `start-web-dev.sh` | Starts Qi Hop Web dev server via Docker Compose; extracts WAR, swaps SWT JARs, deploys plugins |

## Documentation Pipeline (`scripts/`)

Scripts that convert AsciiDoc documentation to Chinese Markdown and build the assistant manual:

| Script | Purpose |
|--------|---------|
| `adoc-to-md-converter.py` | v1 AsciiDoc-to-Markdown converter (reads `user-url.md` mappings) |
| `adoc-to-md-v2.py` | v2 converter with full AsciiDoc syntax support (includes, admonitions, tables) |
| `collect-help-docs.py` | Phase 1: collects all plugin `documentationUrl` annotations and local MD files |
| `build-url-mapping.py` | Phase 2: scans Java annotations, matches URLs to MD files, outputs `url-mapping.json` |
| `rename-to-chinese.py` | Phase 3: reorganizes assistant-manual to Chinese numbered categories |
| `generate-hpl-md.py` | Generates companion `.md` docs for `.hpl` pipeline sample files |
| `copy-hpl-samples.sh` | Copies `.hpl` samples from plugins to `docs/hop-assistant-manual/` |
| `copy-hpl-sync-all.sh` | Syncs remaining `.hpl` samples from integration tests |
| `adoc-to-md-config.json` | Configuration: source/target dirs, term preservation, conversion rules |

## Documentation (`docs/`)

| Directory | Format | Content |
|-----------|--------|---------|
| `hop-user-manual/` | AsciiDoc (Antora) | Official user manual: transforms, actions, databases, metadata types, Qi Hop GUI, pipelines, workflows |
| `hop-tech-manual/` | AsciiDoc (Antora) | Technical manual: Qi Hop vs Kettle migration, logging, Docker, branding |
| `hop-dev-manual/` | AsciiDoc (Antora) | Developer manual: plugin development, SDK, release process, testing, i18n, lineage |
| `hop-assistant-manual/` | Markdown (Chinese) | AI assistant docs built from AsciiDoc sources; 20+ numbered categories (快速入门, 转换插件, 动作插件, etc.); contains `url-mapping.json` |

## Integration Tests

Located in `integration-tests/` (40 suites). Each subdirectory is a self-contained test suite with `.hpl`/`.hwf` files and config. Some require external services (databases, Kafka, SSH, GCP, etc.) and won't pass without them — look for `disabled.txt`.

Key suites: `database` (30+ DB operation tests), `http` (30+ HTTP/REST tests), `mdi` (~80 metadata injection tests), `transforms` (100+ transform tests), `json`, `ldap`, `neo4j`, `mongo`, `beam_directrunner`, `spark`, `gcp`, `azure`.

The `hopweb` suite contains Selenium/SWTBot UI tests against Qi Hop Web, run via the `Jenkinsfile.hop-web-selenium` pipeline.

## Docker

| File | Purpose |
|------|---------|
| `docker/Dockerfile` | Standard Qi Hop client image (multi-arch: linux/amd64, linux/arm64) |
| `docker/web.Dockerfile` | Qi Hop Web (RAP) image |
| `docker/web-fatjar.Dockerfile` | Qi Hop Web fat-jar variant (with Beam) |
| `docker/rest.Dockerfile` | Qi Hop REST API server image |
| `docker/unified.Dockerfile` | Unified image (client + web + rest) |

`docker-compose.dev.yml` provides a dev setup with 3 services:
- **hop-web** (Qi Hop Web) — Tomcat 10 + JDK 21, ports 8080 (HTTP) + 5005 (JDWP debug), mounts local build output
- **litellm** — LLM proxy (port 4000), routes assistant requests to DashScope/Qwen
- **qdrant** — Vector database (ports 6333/6334) for RAG knowledge base

## CI/CD

### GitHub Actions (`.github/workflows/`)

| Workflow | Trigger | Purpose |
|----------|---------|---------|
| `pr_build_code.yml` | push/PR to `main` (excludes `docs/**`) | Main CI: RAT → Checkstyle → Spotless → install; separate UI test job with `xvfb-run` |
| `pr_build_docs.yml` | push/PR (only `docs/**`) | Docs CI: RAT license check only |
| `pr_tagger.yml` | `pull_request_target` | Auto-labels PRs by modified paths |
| `issue_tagger.yml` | issues opened | Auto-labels issues by priority/component |
| `pr_assign_milestone.yml` | PR merged | Clears milestone from merged PRs |
| `self_assign.yml` | issue_comment | Bot commands: `.take-issue`, `.close-issue`, `.set-labels`, etc. |

### Jenkins Pipelines

| File | Schedule | Purpose |
|------|----------|---------|
| `Jenkinsfile` | On push to `main` | Full build + deploy + Docker image build (client + web + dataflow template) |
| `Jenkinsfile.daily` | Daily ~02:00-04:00 UTC | Full integration tests + SonarCloud analysis (switches to JDK 17 for Sonar) |
| `Jenkinsfile.hop-web-selenium` | Daily ~02:00-07:00 UTC | Selenium web UI tests against Qi Hop Web |

## Helm Chart (`helm/hop/`)

Kubernetes chart (v0.3.0) deploying two Qi Hop components:
- **server** (Qi Hop Server) — image `apache/hop:latest`, port 8081, supports persistence/HPA/ingress
- **web** (Qi Hop Web) — image `apache/hop-web:latest`, port 8080, supports ingress/HPA

## Debugging

For Qi Hop Web (RAP) development debugging, use `.opencode/skills/rap-ui-debug/` (loaded automatically) or refer to the standalone guide at `Rap UI极速调试方法.md`. Two approaches:

- **Fast path (~20s)**: `compile → jar:jar → cp JAR → clear Tomcat cache → restart`
- **Full path (~2min)**: `mvn install → mvn package → start-web-dev.sh` (auto-detects war changes)

Note: core modules deploy to `WEB-INF/lib/`; plugin modules need `package` for Jandex indexing (using `jar:jar` alone breaks plugin discovery).

Supports any Maven module via `${MODULE}` variable (core, ui, rap, rest, plugins/*).

## Root Documentation Files

| File | Description |
|------|-------------|
| `README.md` | Project intro, build/run instructions, community links |
| `CONTRIBUTING.md` | Contribution guide (CLA, committer process) |
| `user-url.md` | URL-to-category mapping reference (566 entries, 25 categories) for doc scripts |
| `Rap UI极速调试方法.md` | RAP UI debugging guide (Chinese) |
| `SwtTerminalWidget-design.md` | SwtTerminalWidget architecture design (Chinese) |
| `智能化ETL方案.md` | AI-powered NL-to-pipeline feature design (Chinese) |
| `离线安装指南.md` | Offline installation guide for air-gapped environments (Chinese) |
| `设计规范.md` | Visual/interaction design specification (Chinese) |
| `问题集锦.md` | Development troubleshooting collection (Chinese) |
| `resources/todo_list.md` | JDBC driver download checklist (Chinese) |

## Avoid

- Do not commit `*.json` files in `integration-tests/scripts/` (gitignored).
- Do not commit Monaco editor files under `rap/src/main/resources/`.
- Do not use star imports.
- Do not skip the ASF license header on any source file.

## REST Documentation Service

The `rest` module includes a docs endpoint that serves local markdown files for plugin help URLs, mapping `documentationUrl` annotations from plugin source to MD files in `docs/hop-assistant-manual/`.

**Endpoints** (under `/api/v1/docs/`):
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/docs/{path}` | Returns matched markdown content, `{"status":"unmatched"}` for unmatched, 404 if not found |
| GET | `/api/v1/docs/mappings` | Full URL mapping array (400 entries) |
| GET | `/api/v1/docs/stats` | Mapping statistics (total, matched, unmatched) |
| GET | `/api/v1/docs/reload` | Reload mapping from filesystem |

**Key files:**
- `docs/hop-assistant-manual/url-mapping.json` — master lookup table (244 matched / 156 unmatched)
- `rest/src/main/java/org/apache/hop/rest/v1/resources/docs/UrlMappingService.java` — singleton service
- `rest/src/main/java/org/apache/hop/rest/v1/resources/docs/DocsResource.java` — JAX-RS resource
- `core/.../Const.java:getLocalDocUrl()` — builds local URL from hop.apache.org path
- `ui/.../HelpUtils.java:resolveHelpUrl()` — redirects F1 to local service in web mode

**Regenerating the mapping:**
```bash
cd docs/hop-assistant-manual
python3 ../../scripts/build-url-mapping.py
cp url-mapping.json ../../rest/src/main/resources/org/apache/hop/rest/docs/
```

**Testing:**
```bash
./mvnw test -pl rest -Dtest=UrlMappingServiceTest
```
