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
lib/ lib-jdbc/ lib-p2/ — shared libraries
plugins/
  transforms/    — ~150 data transform plugins
  actions/       — ~55 workflow action plugins
  databases/     — database connection plugins
  engines/       — engine plugins (e.g. Beam)
  misc/ tech/ resolvers/ valuetypes/ — other plugin categories
ui/              — SWT-based shared UI code
rcp/             — desktop application (Rich Client Platform)
rap/             — web application (RAP/Tomcat)
rest/            — REST API server
assemblies/      — distribution packaging (zip, tar.gz, Docker)
integration-tests/ — integration test suites
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

Each plugin under `plugins/transforms/<name>/` or `plugins/actions/<name>/` is an independent Maven module. When adding a new plugin:

1. Create directory under the appropriate parent (`transforms/` or `actions/`)
2. Add `<module>` to parent `pom.xml`
3. Set parent to the aggregator POM (e.g. `hop-plugins-transforms`)
4. Include ASF license header in all files
5. Run `./mvnw spotless:apply` before committing

## Integration Tests

Located in `integration-tests/`. Each subdirectory is a self-contained test suite. Some require external services (databases, Kafka, SSH, etc.) and won't pass without them. The `scripts/` directory contains helper scripts.

## Docker

`docker-compose.dev.yml` provides a dev setup for hop-web (Tomcat + RAP). Requires pre-built assemblies.

## Avoid

- Do not commit `*.json` files in `integration-tests/scripts/` (gitignored).
- Do not commit Monaco editor files under `rap/src/main/resources/`.
- Do not use star imports.
- Do not skip the ASF license header on any source file.
