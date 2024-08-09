
FROM mariadb:latest

LABEL description="MicroService Database"

COPY ./testcenterDB.sql /docker-entrypoint-initdb.d/

ENV DB_SERVER_PORT=3306

EXPOSE $DB_SERVER_PORT


