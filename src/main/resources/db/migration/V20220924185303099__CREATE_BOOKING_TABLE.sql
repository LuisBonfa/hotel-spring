create table booking (
  id uuid primary key,
  user_id uuid not null references "user"(id),
  booking_code varchar(20) unique not null,
  booking_begin timestamp with time zone not null,
  booking_end timestamp with time zone not null,
  status varchar(10) not null,
  created_at timestamp with time zone not null default now(),
  updated_at timestamp with time zone null default now(),
  unique(booking_begin, booking_end, status)
);