# Sample Code of Dependency Injection

This repository is a beginner-friendly sample that implements a **minimal dependency-injection (DI)
container** using `jakarta.inject` annotations (`@Named` / `@Inject`).  
It is designed so you can follow where objects are created and how dependencies are injected.

## What This Sample Shows

- Automatically scanning classes annotated with `@Named`
- Creating instances with a no-arg constructor
- Injecting dependencies into fields annotated with `@Inject`
- A simple Controller → Service → Repository flow

## Prerequisites

- Java 25 (aligned with `pom.xml`)
- Maven

Check your Java version:

```bash
java -version
```

Check your Maven version:

```bash
mvn -version
```

## Quick Start

1. Resolve dependencies and build

```bash
mvn -DskipTests package
```

2. Run the sample

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=dev.sobue.sample.di.field.DependencyInjectionSampleApplication
```

If you see logs starting with `start app` and a `result = ...` line, the run succeeded.

## Run Tests

```bash
mvn test
```

## Key Files

- `src/main/java/dev/sobue/sample/di/field/DependencyInjectionSampleApplication.java`  
  Entry point. Initializes the DI container and invokes the controller.
- `src/main/java/dev/sobue/sample/di/field/container/Context.java`  
  The DI container implementation: scanning, instantiation, and injection.
- `src/main/java/dev/sobue/sample/di/field/controller` / `service` / `repository`  
  Sample layer implementations.
- `src/test/java/dev/sobue/sample/di/field`  
  Unit tests.

## How the Container Works (High-Level)

- `Context.initialize(...)` scans the target package for `@Named` classes and registers instances.
- It then injects dependencies into fields annotated with `@Inject`, resolved by type.
- If multiple candidates match the same type, an exception is thrown (this is a simple container).

## Common Errors

- `no default constructor`  
  The `@Named` class must have a **no-arg constructor**.
- `not single object`  
  Multiple implementations for the same type were registered. Adjust the resolution logic in
  `Context.getBean(...)`.

## References (Official Docs)

- JDK 25 Documentation: https://docs.oracle.com/en/java/javase/25/
- Jakarta Dependency Injection: https://jakarta.ee/specifications/dependency-injection/
- Jakarta EE Specifications: https://jakarta.ee/specifications/

## CI Status

- SonarCloud [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ssobue_dependency-injection&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ssobue_dependency-injection)

## Update Properties

```bash
mvn versions:update-properties -DgenerateBackupPoms=false -Dproperties=jakarta.inject-api.version,commons-codec.version,logback.version,lombok.version,junit.version,mockit.version,maven-clean-plugin.version,maven-enforcer-plugin.version,maven-checkstyle-plugin.version,maven-resources-plugin.version,maven-compiler-plugin.version,maven-surefire-plugin.version,maven-source-plugin.version,maven-site-plugin.version,maven-jxr-plugin.version,maven-project-info-reports-plugin.version,maven-pmd-plugin.version,maven-javadoc-plugin.version,jacoco-maven-plugin.version,clover-maven-plugin.version
```
