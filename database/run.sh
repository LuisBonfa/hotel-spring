docker cp ./initialization.sql microservices_postgresql_1:/initialization.sql
PGPASSWORD=123456 docker exec microservices_postgresql_1 psql -U postgres -f /initialization.sql
