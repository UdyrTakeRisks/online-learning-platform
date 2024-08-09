
FROM mariadb:latest

LABEL description="Java EE Database"

COPY ./platformDB.sql /docker-entrypoint-initdb.d/

ENV DB_SERVER_PORT=3306

EXPOSE $DB_SERVER_PORT


