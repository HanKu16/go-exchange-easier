FROM postgres:latest

COPY v1.4/00_create.sql /docker-entrypoint-initdb.d/00_create.sql

COPY v1.4/data/*.sql /docker-entrypoint-initdb.d/