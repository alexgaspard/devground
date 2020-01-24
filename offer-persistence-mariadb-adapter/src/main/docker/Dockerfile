FROM mariadb:10.4.11

HEALTHCHECK CMD mysqladmin -p$MYSQL_ROOT_PASSWORD ping

CMD ["mysqld"]