FROM postgres:latest

COPY v1.2/00_create.sql /docker-entrypoint-initdb.d/00_create.sql

COPY v1.2/data/*.sql /docker-entrypoint-initdb.d/