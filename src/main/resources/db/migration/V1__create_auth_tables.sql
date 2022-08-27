create table users (
    username varchar(255) not null primary key,
    email varchar(255) not null,
    password varchar(255) not null,
    enabled boolean not null default true
);

create table authorities (
    username varchar(255) not null,
    authority varchar(255) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

-- insert admin user
-- user: admin
-- password: pass
insert into users (username, email, password, enabled) values ('admin' ,'admin@example.com', '$2a$10$LJMHX/Ar3a2.S9YnHNusreMgOOf2OZ7DWYtMAnm2vYbkMYeK7X7xq', true);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');

-- insert common user
-- user: user
-- password: pass
insert into users (username, email, password, enabled) values ('user' ,'user@example.com', '$2a$10$LJMHX/Ar3a2.S9YnHNusreMgOOf2OZ7DWYtMAnm2vYbkMYeK7X7xq', true);
insert into authorities (username, authority) values ('user', 'ROLE_USER');