create database exchange_db owner exchange_user;

\connect exchange_db;

create table public.conversion(
                               id serial primary key,
                               source varchar not null,
                               source_amount numeric not null,
                               target varchar not null,
                               target_amount numeric not null,
                               rate numeric not null,
                               transaction_id varchar not null,
                               transaction_date timestamp
);