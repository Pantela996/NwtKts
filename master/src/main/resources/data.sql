insert into authority (name) values ('ADMIN_ROLE');
insert into authority (name) values ('REGULAR_USER_ROLE');
insert into authority (name) values ('LOCATION_AND_EVENT_ADMIN_ROLE');

insert into kts.user (username,password, dtype) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'Admin');

insert into kts.user_authority (user_id, authority_id) values (1,2);
