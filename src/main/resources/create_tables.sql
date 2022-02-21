create schema mono;

create extension if not exists "uuid-ossp";

create table mono.users
(
	full_name varchar(50),
	email varchar(50),
	phone_number varchar(15),
	primary key (email)
)
;

create table mono.addresses
(
	id uuid,
	address varchar(100),
	user_email varchar(50),
	primary key (id),
	foreign key (user_email)
    	references mono.users (email)
    	on update cascade
    	on delete no action
)
;

create table mono.templates
(
	id uuid,
	template_name varchar(100),
	address_id uuid,
	payment_purpose varchar(200),
	iban varchar(29),
	primary key (id),
	foreign key (address_id)
    	references mono.addresses (id)
    	on update cascade
    	on delete no action
)
;

create table mono.payments
(
	id uuid not null,
    template_id uuid not null,
    card_number bigint,
    payment_amount numeric(9,2) check(payment_amount > 0),
    payment_status varchar(10),
    created_date_time timestamp(0),
    etl_date_time timestamp(0),
    primary key (id),
    foreign key (template_id)
    	references mono.templates (id)
    	on update cascade
    	on delete no action
)
;