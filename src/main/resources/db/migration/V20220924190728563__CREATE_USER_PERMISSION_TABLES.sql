create table role (
    id uuid primary key,
    name varchar(20) not null,
    status varchar(10) not null,
    created_at timestamp with time zone not null default now(),
    unique(name, status)
);

create table user_role (
    id uuid primary key,
    user_id uuid not null references "user"(id),
    role_id uuid not null references role(id),
    status varchar(10) not null,
    created_at timestamp with time zone not null default now(),
    unique(user_id, role_id)
);