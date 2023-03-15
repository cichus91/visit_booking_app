-- hasła dla każdego konta: login+"hasło"

INSERT INTO address (id, creation_timestamp, flatnumber, housenumber, modify_timestamp, place, street, version, zipcode) VALUES (1,NULL, '12','11a',NULL, 'Warszawa', 'Sucza', 0, '01-222');
INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (1, 'PATIENT', FALSE, NULL, 'pac1', NULL, '9e9913319c107bd3db9d9e1293f8854c8353170f0a24132c0298d20dc59f48f5', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (1, 'cjalis@muza.biz', 'Janusz', 'Cjaliszewski');
INSERT INTO patientdata (id, dateofbirth, address) VALUES (1, '1968-08-08 00:00:01', 1);

INSERT INTO address (id, creation_timestamp, flatnumber, housenumber, modify_timestamp, place, street, version, zipcode) VALUES (2,NULL, NULL,'34',NULL, 'Dawidy Bankowe', 'raszyńska', 0, '97-111');
INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (2, 'PATIENT', FALSE, NULL, 'pac2', NULL, 'bb1daaea17b9fedae3feeaf64a957192ee64f1ed15471071c0f5c5a44bab67a2', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (2, 'tomili@jones.pl', 'Zbigniew', 'Friedel');
INSERT INTO patientdata (id, dateofbirth, address) VALUES (2, '1991-08-08 00:00:01', 2);

INSERT INTO address (id, creation_timestamp, flatnumber, housenumber, modify_timestamp, place, street, version, zipcode) VALUES (3,NULL,'11b', '24f', NULL, 'Buczek', 'Prosta', 0, '97-000');
INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (3, 'PATIENT', FALSE, NULL, 'pac3', NULL, '4e6af1efe6fa9512d5e58f7bd2f7c97c5527e93140c66265c2ad9be29d316de8', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (3, 'bff@muza.biz', 'Filip', 'Barłoś');
INSERT INTO patientdata (id, dateofbirth, address) VALUES (3, '1991-08-08 00:00:01', 3);

INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (4, 'RECEPTIONIST', TRUE, NULL, 'rec1', NULL, '554a1b835a5e427d07195f98c47603c0caa936797fe5eefd78f3092d1c25e836', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (4, 'pazdzioch.marian@gabinet.pl', 'Marian', 'Paździoch');
insert into receptionistdata(id) values (4);

INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (5, 'RECEPTIONIST', TRUE, NULL, 'rec2', NULL, '32888f2eedbec3722505f5389a7e2e1f190aa0cabd595cec2ad44f54518cd242', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (5, 'kiepski.ferdynand@gabinet.pl', 'Ferdynand', 'Kiepski');
insert into receptionistdata(id) values (5);

INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (6, 'RECEPTIONIST', TRUE, NULL, 'rec3', NULL, '8c0d52040e16ee2a12f1724a8bc1631d94e3001f9c7a7f3717dc373c6b8901cc', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (6, 'boczek.arnold@gabinet.pl', 'Arnold', 'Boczek');
insert into receptionistdata(id) values (6);

INSERT INTO account (id, type, active, creation_timestamp, login, modify_timestamp, password, phonenumber, version) VALUES (7, 'ADMIN', TRUE, NULL, 'adm1', NULL, 'a8e924532f6cf434837dc6bb804075e35a6262575f1750c9c1fd3fc5bc03f207', 111222333, 0);
INSERT INTO personaldata (id, email, firstname, secondname) VALUES (7, 'bareja.stanislaw@gabinet.pl', 'Stanisław', 'Bareja');
insert into admindata(id) VALUES (7);

INSERT INTO schedule (id, creation_timestamp, dateuniquekey, enddate, modify_timestamp, startdate, version, createdby_id) VALUES (1, null, '2022-10-09 00:00:00', '2022-10-09 18:00:00', null, '2022-10-09 08:00:00', 0, 6);