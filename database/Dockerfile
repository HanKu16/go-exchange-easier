FROM postgres:latest

COPY current-database/00_create.sql /docker-entrypoint-initdb.d/00_create.sql

COPY current-database/data/*.sql /docker-entrypoint-initdb.d/