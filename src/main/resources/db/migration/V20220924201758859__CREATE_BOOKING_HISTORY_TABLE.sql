create table booking_history (
    id uuid primary key,
    booking_id uuid not null references booking(id),
    booking_begin timestamp with time zone not null,
    booking_end timestamp with time zone not null,
    created_at timestamp with time zone not null default now()
);