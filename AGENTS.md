# Apache Hop — Agent Notes

## Project Overview

Apache Hop is a data/metadata orchestration platform (ETL). Java 21 multi-module Maven project. License: Apache 2.0 — all source files **must** carry the ASF license header (enforced by `apache-rat-plugin`).

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
lib-p2/          — P2 library
plugins/
  transforms/    — 154 data transform plugins
  actions/       — 57 workflow action plugins
  databases/     — 47 database connection plugins
  engines/       — engine plugins (e.g. Beam)
  misc/ tech/ resolvers/ valuetypes/ — other plugin categories
ui/              — SWT-based shared UI code
rcp/             — desktop application (Rich Client Platform)
rap/             — web application (RAP/Tomcat)
rest/            — REST API server
assemblies/      — distribution packaging (zip, tar.gz, Docker)
dev-scripts/     — developer scripts (build, start-web-dev, etc.)
docker/          — Dockerfiles (web/rest/unified/fatjar)
docs/            — developer manuals (user/tech/dev/assistant)
helm/            — Kubernetes Helm chart
resources/       — JDBC drivers, shared jar download scripts
tools/           — build configs (checkstyle.xml, etc.)
integration-tests/ — integration test suites (39 suites)
```

Plugin artifact naming: `hop-<type>-<name>` (e.g. `hop-transform-json`, `hop-action-ftp`).

## Key Conventions

- **Lombok** is used project-wide (provided scope). Classes use `@Getter`, `@Setter`, etc.
- **Jandex** indexing (`jandex-maven-plugin`) runs for plugin discovery — annotations are indexed at build time.
- Source encoding: UTF-8. Timezone: UTC. Locale: `en_US`.
- JVM needs many `--add-opens`/`--add-exports` flags (configured in `.mvn/jvm.config` and `maven-surefire-plugin.argLine`).
- Monaco Editor files for `rap/` are downloaded at build time (not committed).
- `src/main/resources-filtered/` is a standard pattern for Maven-filtered resources (activated by profile `filtered-resources`).

## Working with Plugins

Each plugin under `plugins/<category>/<name>/` is an independent Maven module. Supported categories: `transforms`, `actions`, `databases`, `engines`, `misc`, `tech`, `resolvers`, `valuetypes`. When adding a new plugin:

1. Create directory under the appropriate parent (e.g. `transforms/`)
2. Add `<module>` to parent `pom.xml`
3. Set parent to the aggregator POM (e.g. `hop-plugins-transforms`)
4. Include ASF license header in all files
5. Run `./mvnw spotless:apply` before committing

## Integration Tests

Located in `integration-tests/` (39 suites). Each subdirectory is a self-contained test suite. Some require external services (databases, Kafka, SSH, etc.) and won't pass without them. The `scripts/` directory contains helper scripts.

## Docker

`docker-compose.dev.yml` provides a dev setup for hop-web (Tomcat + RAP). Multiple Dockerfiles in `docker/` for web/rest/unified/fatjar builds. Requires pre-built assemblies.

## Debugging

For Hop Web (RAP) development debugging, use `.opencode/skills/rap-ui-debug/` (loaded automatically) or refer to the standalone guide at `Rap UI极速调试方法.md`. Two approaches:

- **Fast path (~20s)**: `compile → jar:jar → cp JAR → clear Tomcat cache → restart`
- **Full path (~2min)**: `mvn install → mvn package → start-web-dev.sh` (auto-detects war changes)

Supports any Maven module via `${MODULE}` variable (core, ui, rap, rest, plugins/*).

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
