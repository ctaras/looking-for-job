merge into site (id, caption, alias, url) VALUES (1, 'WORK.UA', 'ua_work', 'http://www.work.ua')
merge into site (id, caption, alias, url) VALUES (2, 'RABOTA.UA', 'ua_rabota', 'http://rabota.ua')
merge into site (id, caption, alias, url) VALUES (3, 'HH.UA', 'ua_hh', 'https://hh.ua')

merge into city (id, caption) VALUES (1, 'Киев')

merge into company (id, caption) VALUES (1, 'Undefined')

merge into SITES_IDS_CITIES (id, site_id, city_id, id_region) VALUES (1, 1, 1, 39)
merge into SITES_IDS_CITIES (id, site_id, city_id, id_region) VALUES (2, 2, 1, 1)
merge into SITES_IDS_CITIES (id, site_id, city_id, id_region) VALUES (3, 3, 1, 115)

merge into currency (id, caption, rate) VALUES (1, 'грн', 1)
merge into currency (id, caption, rate) VALUES (2, 'usd', 27.2)
merge into currency (id, caption, rate) VALUES (3, 'eur', 29.6)



