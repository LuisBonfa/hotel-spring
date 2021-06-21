create table "user" (
  id uuid primary key default uuid_generate_v4(),
  password varchar(256) not null,
  name varchar(256) unique not null,
  alias varchar(256) not null,
  tries integer not null,
  email varchar(100) unique not null,
  phone varchar(15) unique not null,
  document varchar(20) unique not null,
  status varchar(10) not null,
  created_at timestamp with time zone not null default now(),
  updated_at timestamp with time zone null default now()
);

create table role (
    id uuid primary key default uuid_generate_v4(),
    name varchar(20) not null,
    status varchar(10) not null,
    created_at timestamp with time zone not null default now()
);

create table user_role (
    id uuid primary key default uuid_generate_v4(),
    user_id uuid not null references "user"(id),
    role_id uuid not null references "role"(id),
    created_at timestamp with time zone not null default now(),
    unique(user_id, role_id)
);

insert into role (name, status) values ('admin', 'enabled');
insert into role (name, status) values ('client', 'enabled');