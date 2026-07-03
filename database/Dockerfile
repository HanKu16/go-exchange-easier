FROM postgres:latest

COPY current-database/core/core.00_create.sql /docker-entrypoint-initdb.d/core.00_create.sql

COPY current-database/core/data/*.sql /docker-entrypoint-initdb.d/

COPY current-database/chat/chat.00_create.sql /docker-entrypoint-initdb.d/chat.00_create.sql

COPY current-database/chat/data/*.sql /docker-entrypoint-initdb.d/

