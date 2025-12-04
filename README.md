# Chimera

Yet another throwaway app that retrieves weather for exploring some concepts related to Spring Boot.

Used for experimenting with setting up a modular Spring Boot application with a reusable core that can be extended by
multiple applications.

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
chimera/
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
└── chimera-localhost/        # local development environment (via docker-compose)
```

## How to use this

This project uses Java 25. Building also requires a proper **Docker** environment as building will spawn docker
containers via `testcontainers` library.

1. To start containers for local usage.
   ```bash
   cd chimera-localhost/
   docker-compose up -d
   ```
2. To format code use `spotlessApply` task.
   ```bash
   ./gradlew spotlessApply
   ```
3. To build project use `build` task.
   ```bash
   ./gradlew build
   ```
4. For doing all-in-one, declare all tasks at once.
   ```bash
   ./gradlew clean spotlessApply build
   ```
5. By default, tests that use `testcontainers` are skipped. To enabled add `-Pcontainers.enabled=true`
   ```bash
   ./gradlew clean test -Pcontainers.enabled=true
   ```
   For running in IDE, set `containers.enabled=true` in `gradle.properties` file.
