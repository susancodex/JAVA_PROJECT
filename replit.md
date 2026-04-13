# Workspace

## Overview

pnpm workspace monorepo using TypeScript. Each package manages its own dependencies.

## Stack

- **Monorepo tool**: pnpm workspaces
- **Node.js version**: 24
- **Package manager**: pnpm
- **TypeScript version**: 5.9
- **API framework**: Express 5
- **Database**: PostgreSQL + Drizzle ORM
- **Validation**: Zod (`zod/v4`), `drizzle-zod`
- **API codegen**: Orval (from OpenAPI spec)
- **Build**: esbuild (CJS bundle)

## Key Commands

- `pnpm run typecheck` — full typecheck across all packages
- `pnpm run build` — typecheck + build all packages
- `pnpm --filter @workspace/api-spec run codegen` — regenerate API hooks and Zod schemas from OpenAPI spec
- `pnpm --filter @workspace/db run push` — push DB schema changes (dev only)
- `pnpm --filter @workspace/api-server run dev` — run API server locally

See the `pnpm-workspace` skill for workspace structure, TypeScript setup, and package details.

## Student Management System (Java Swing)

A standalone Java Swing desktop application located in `student-management-system/`.

### Stack
- **Language**: Java (GraalVM 22.3)
- **GUI**: Java Swing
- **Database**: SQLite (via JDBC + sqlite-jdbc.jar)
- **Architecture**: MVC-like pattern (Student model, StudentService DAO, StudentManagementUI view)

### Files
- `Main.java` — Entry point
- `Student.java` — Model/POJO class
- `DatabaseConnection.java` — JDBC SQLite connection
- `StudentService.java` — CRUD + filter/sort operations
- `StudentManagementUI.java` — Swing GUI with 5 event listener types

### Commands (run from `student-management-system/` directory)
```
javac -cp .:sqlite-jdbc.jar *.java
java -cp .:sqlite-jdbc.jar Main
```
