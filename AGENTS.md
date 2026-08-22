# AGENTS.md

## Overview
This is a Java/Maven sample project that demonstrates a simple dependency-injection container using `jakarta.inject` annotations. The main flow scans for `@Named` classes, instantiates them, and injects `@Inject` fields by type.

## Key Paths

- `src/main/java/dev/sobue/sample/di/field/FieldInjectionApplication.java` (field injection entry
  point)
- `src/main/java/dev/sobue/sample/di/field/container/Context.java` (field injection DI container)
- `src/main/java/dev/sobue/sample/di/field/controller` / `service` / `repository` (sample layers)
- `src/main/java/dev/sobue/sample/di/constructor/ConstructorInjectionApplication.java` (constructor
  injection entry point)
- `src/main/java/dev/sobue/sample/di/constructor/container/Context.java` (constructor injection DI
  container)
- `src/main/java/dev/sobue/sample/di/constructor/controller` / `service` / `repository` (sample
  layers)
- `src/test/java/dev/sobue/sample/di/field` (unit tests)
- `src/main/resources/logback.xml` (logging)
- `pom.xml` (Maven build, Java 25, plugins and dependency versions)
- `.github/workflows/maven.yaml` (GitHub Actions CI workflow)
- `.github/dependabot.yml` (Dependabot configuration)

## Build and Test
Use Maven. Java 25 is the minimum JDK version required by `pom.xml`.
The GitHub Actions matrix verifies the build on JDK 25 and JDK 26.

```bash
mvn test
```

If you only need to compile without tests:

```bash
mvn -DskipTests package
```

## DI Container Notes
- `Context.initialize(...)` scans the classpath for `@Named` classes and registers instances.
- Only classes with a **no-arg constructor** are instantiable by the container.
- `@Inject` is field-based injection; dependencies are resolved by type and must be unique.

## Conventions
- Keep new DI-managed components in the `dev.sobue.sample.di.field` package (or subpackages) so the scanner can find them.
- Keep constructor-injection sample components in the `dev.sobue.sample.di.constructor` package (or
  subpackages) so its scanner can find them.
- Avoid introducing multiple implementations of the same interface unless you also update resolution logic in `Context.getBean(...)`.
- Add Javadoc to constants (including `private static final` fields).

## GitHub Actions

- Workflow: `.github/workflows/maven.yaml`
- Triggers: `push` and `pull_request` to `master`
- Job: Maven `verify` on JDK 25 and JDK 26 (Temurin), with Maven cache enabled
- The JDK 25 build uploads `target/` for the SonarQube scan job.
- `sonarqube-scan` runs the Maven SonarScanner only when `SONAR_TOKEN` is configured and the event is not a Dependabot pull request.

## Dependabot
- Config: `.github/dependabot.yml`
- Maven updates: weekly, labels PRs with `dependencies`
- GitHub Actions updates: weekly (Monday 04:30 Asia/Tokyo), max 5 open PRs, labels `dependencies`
