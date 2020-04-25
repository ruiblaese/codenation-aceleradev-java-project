create table token(
	id serial not null,
	users integer references users(id),
	parent integer references token(id),
	description varchar(50) not null,
	token varchar(32) not null,
	active boolean not null,
	primary key (id)
);

create table log(
    id serial not null,
    date timestamp not null,
    title varchar(100),
    details text,
    environment integer,
    level integer,
    token integer references token(id),
    primary key (id)
)