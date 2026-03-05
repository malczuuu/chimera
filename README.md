# Chimera Project

[![Gradle Build](https://github.com/malczuuu/chimera-project/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/malczuuu/chimera-project/actions/workflows/gradle-build.yml)

Yet another throwaway app that retrieves weather from OpenWeatherAPI, used for exploring some concepts related to Spring
Boot.

Used for experimenting with setting up a modular Spring Boot application with a reusable core that can be extended by
multiple applications. Leverages `@AutoConfiguration` and `@ConditionalOnMissingBean` annotations for creating
extendable components.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [How to use this](#how-to-use-this)

## Overview

This project demonstrates the architectural pattern of creating a **base (core) application** with all business logic
extracted into an auto-configurable core module. This allows multiple applications to:

- Include the core functionality via dependency management.
- Override or extend specific components as needed.
- Share common business logic while maintaining application-specific customizations.

## Architecture

```
./
├── chimera-app/              # base application
│   ├── chimera-app-flyway/   # flyway runner for base migrations
│   ├── chimera-app-service/  # runnable base application
│   ├── chimera-bom/          # dependency managementn
│   ├── chimera-core/         # core business logic and auto-configuration
│   ├── chimera-migration/    # core database migrations
│   └── chimera-testkit/      # testing utilities
├── chimera-ext-app/                # extended application
│   ├── chimera-ext-app-flyway/     # flyway runner for extended migrations
│   ├── chimera-ext-app-service/    # runnable extended application
│   └── chimera-ext-migration/      # extension database migrations
└── localhost/                # local development environment (via docker-compose)
```

## How to use this

### Prerequisites

- JDK 17+ (Gradle 9 requires Java 17 or higher, but code is compiled with Java 25 Toolchain)
- Docker (required for Testcontainers)

### Build & Test

Default Gradle tasks are `spotlessApply` and `build`. To run both formatting and tests, simply execute:

```bash
./gradlew
```

To run tests, execute:

```bash
./gradlew test
```

To run tests with Testcontainers, ensure Docker is running and add `-Pcontainers.enabled`:

```bash
./gradlew test -Pcontainers.enabled
```

To run individual tests via IntelliJ IDEA, set `containers.enabled` in `gradle.properties` for convenience.
