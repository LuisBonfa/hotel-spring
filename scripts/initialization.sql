create user hotel with PASSWORD 'hotel@alten';
create DATABASE hotel;
grant all privileges on DATABASE hotel TO hotel;
\c hotel
create EXTENSION "uuid-ossp";
create EXTENSION "pgcrypto";
alter database hotel set timezone to "UTC";
grant all privileges on ALL TABLES IN SCHEMA public TO hotel;
grant all privileges on ALL SEQUENCES IN SCHEMA public TO hotel;




