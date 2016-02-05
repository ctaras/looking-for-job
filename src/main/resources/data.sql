insert into site (caption, alias, url) VALUES ('WORK.UA', 'ua_work', 'http://www.work.ua')
insert into site (caption, alias, url) VALUES ('RABOTA.UA', 'ua_rabota', 'http://rabota.ua')
insert into site (caption, alias, url) VALUES ('HH.UA', 'ua_hh', 'https://hh.ua')

insert into city (caption) VALUES ('Киев')

insert into company (caption) VALUES ('Undefined')

insert into SITES_IDS_CITIES (site_id, city_id, id_region) VALUES (1, 1, 39)
insert into SITES_IDS_CITIES (site_id, city_id, id_region) VALUES (2, 1, 1)
insert into SITES_IDS_CITIES (site_id, city_id, id_region) VALUES (3, 1, 115)

insert into currency (caption, rate) VALUES ('грн', 1)
insert into currency (caption, rate) VALUES ('usd', 27.2)
insert into currency (caption, rate) VALUES ('eur', 29.6)



