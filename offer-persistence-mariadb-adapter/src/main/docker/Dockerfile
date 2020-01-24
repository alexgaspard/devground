FROM postgres:12-alpine

HEALTHCHECK CMD psql --host localhost --username postgres --quiet --no-align --tuples-only --list

CMD ["postgres"]