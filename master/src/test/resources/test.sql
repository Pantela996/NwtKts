alter table user modify documentid BLOB;

insert into authority (name) values ('ADMIN_ROLE');
insert into authority (name) values ('PASSENGER_ROLE');
insert into authority (name) values ('VALIDATOR_ROLE');

insert into user (id,username, password,dtype) values (1,'Existing user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq','LocationEventAdmin');

insert into user (id,username, password,dtype) values (2,'User 1', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq','LocationEventAdmin');


insert into kts.category (id, name, required_rows, required_columns, category_type) values (1, 'Sport', 30, 40, 0);

insert into kts.event (id) values(5);

insert into kts.event (id) values(6);

insert into kts.event (id,description) values(7, 'Check this description');

insert into kts.event (id,user_id) values(8, 1);

insert into kts.event (id,user_id) values(9,2);