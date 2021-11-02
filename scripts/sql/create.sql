GRANT ALL PRIVILEGES ON DATABASE sportsevents TO sportsevents;

create table event (
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

CREATE SEQUENCE event_id_seq;

ALTER TABLE event ALTER id SET DEFAULT NEXTVAL('event_id_seq');