FROM postgres:latest

COPY v1.3/00_create.sql /docker-entrypoint-initdb.d/00_create.sql

COPY v1.3/data/*.sql /docker-entrypoint-initdb.d/