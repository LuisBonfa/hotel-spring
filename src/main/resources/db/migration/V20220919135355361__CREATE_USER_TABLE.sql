create table "user" (
  id uuid primary key,
  password varchar(256) not null,
  name varchar(256) unique not null,
  username varchar(50) not null,
  alias varchar(256) not null,
  tries integer not null default 0,
  email varchar(100) unique not null,
  phone varchar(15) unique not null,
  document varchar(20) unique not null,
  status varchar(10) not null,
  created_at timestamp with time zone not null default now(),
  updated_at timestamp with time zone null default now()
);

