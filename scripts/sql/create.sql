GRANT ALL PRIVILEGES ON DATABASE sportsevents TO sportsevents;

CREATE DATABASE test_db;

create table chess_event (
        id int8 not null,
        closedBy varchar(255),
        closedDate timestamp,
        duration int4 not null,
        facilitator varchar(255),
        location varchar(255),
        maxPlayers int4 not null,
        name varchar(255),
        outdoors boolean not null,
        players bytea,
        scheduledDate timestamp,
        status varchar(255),
        type varchar(255),
        winner varchar(255),
        primary key (id)
    );

 create table pingpong_event (
        id int8 not null,
        closedBy varchar(255),
        closedDate timestamp,
        duration int4 not null,
        facilitator varchar(255),
        location varchar(255),
        maxPlayers int4 not null,
        name varchar(255),
        outdoors boolean not null,
        players bytea,
        scheduledDate timestamp,
        status varchar(255),
        type varchar(255),
        winner varchar(255),
        primary key (id)
    );

 create table swimming_event (
        id int8 not null,
        closedBy varchar(255),
        closedDate timestamp,
        duration int4 not null,
        facilitator varchar(255),
        location varchar(255),
        maxPlayers int4 not null,
        name varchar(255),
        outdoors boolean not null,
        players bytea,
        scheduledDate timestamp,
        status varchar(255),
        type varchar(255),
        winner varchar(255),
        primary key (id)
    );

 create table tennis_event (
        id int8 not null,
        closedBy varchar(255),
        closedDate timestamp,
        duration int4 not null,
        facilitator varchar(255),
        location varchar(255),
        maxPlayers int4 not null,
        name varchar(255),
        outdoors boolean not null,
        players bytea,
        scheduledDate timestamp,
        status varchar(255),
        type varchar(255),
        winner varchar(255),
        primary key (id)
    );