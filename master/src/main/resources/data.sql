insert into authority (name) values ('ADMIN_ROLE');
insert into authority (name) values ('REGULAR_USER_ROLE');
insert into authority (name) values ('LOCATION_AND_EVENT_ADMIN_ROLE');

insert into kts.user (username,password, dtype) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'LocationEventAdmin');

insert into kts.user_authority (user_id, authority_id) values (1,3);

insert into kts.event_location (id, name, location_city) values  (1, 'Kombank Arena', 'Beograd');

insert into kts.category (id, name, required_rows, required_columns, category_type) values (1, 'Sport', 30, 40, 0);

insert into hall (id, category_id, total_rows, total_columns, location_id) values (1, 1, 25, 35, 1);