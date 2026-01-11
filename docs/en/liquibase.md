# Liquibase Database Version Management

## What

Liquibase is an open-source database change management tool. This project integrates Liquibase for database version control and automatic migration.

## Why

- Version control: Manage database changes like code
- Automatic migration: Execute pending change scripts on application startup
- Multi-environment support: Same scripts work for development, testing, and production
- Rollback capability: Support change rollback, reduce release risk

## How

### 1. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  liquibase:
    enabled: true # Switch
```

### 2. Create Changelog Files

Create change files in `src/main/resources/db/changelog`:

```
db/
└── changelog/
    ├── db.changelog-master.yaml    # Master file
    ├── db.changelog-1.0.yaml       # Version 1.0 changes
    └── db.changelog-1.1.yaml       # Version 1.1 changes
```

### 3. Master File Configuration

`db.changelog-master.yaml`:

```yaml
databaseChangeLog:
  - include:
      file: db/changelog/db.changelog-1.0.yaml
  - include:
      file: db/changelog/db.changelog-1.1.yaml
```

### 4. Change Script Example

`db.changelog-1.0.yaml`:

```yaml
databaseChangeLog:
  - changeSet:
      id: 1
      author: developer
      changes:
        - createTable:
            tableName: sys_user
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    primaryKey: true
              - column:
                  name: login
                  type: varchar(50)
                  constraints:
                    nullable: false
              - column:
                  name: email
                  type: varchar(100)
              - column:
                  name: created_date
                  type: datetime
```

## Cases

### Case 1: Add New Table

```yaml
databaseChangeLog:
  - changeSet:
      id: 2
      author: developer
      changes:
        - createTable:
            tableName: sys_role
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
                  constraints:
                    primaryKey: true
              - column:
                  name: code
                  type: varchar(50)
              - column:
                  name: name
                  type: varchar(100)
```

### Case 2: Add Index

```yaml
databaseChangeLog:
  - changeSet:
      id: 3
      author: developer
      changes:
        - createIndex:
            indexName: idx_user_login
            tableName: sys_user
            columns:
              - column:
                  name: login
```

## Pitfalls

1. **Unique changeSet ID**: changeSet ID must be unique per author
2. **Don't modify executed changes**: Modifying executed changeSets will cause validation failure
3. **ShardingSphere compatibility**: ShardingSphere 5.x has compatibility issues with Liquibase
4. **Production caution**: Disable auto-execution in production, use manual migration

## Best Practices

1. Use separate changelog files for each version
2. Keep changeSet granularity small, one changeSet does one thing
3. Add meaningful comments to changeSets
4. Use `runOnChange: true` for repeatable scripts (views, stored procedures)
