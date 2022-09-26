docker cp ./initialization.sql hotel_postgresql:/initialization.sql
PGPASSWORD=123456 docker exec hotel_postgresql psql -U postgres -f /initialization.sql
