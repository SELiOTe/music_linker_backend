USE music_linker;

INSERT INTO country(country_code, en_name, local_name, phone_code, phone_pattern)
VALUES ('US', 'United States', 'United States', '1', '^\\d{10}$');
INSERT INTO country(country_code, en_name, local_name, phone_code, phone_pattern)
VALUES ('CN', 'China', '中国大陆', '86', '^\\d{11}$');
INSERT INTO country(country_code, en_name, local_name, phone_code, phone_pattern)
VALUES ('HK', 'Hong Kong(China)', '中國香港', '852', '^\\d{8}$');
INSERT INTO country(country_code, en_name, local_name, phone_code, phone_pattern)
VALUES ('MC', 'Macao(China)', '中國澳門', '853', '^\\d{8}$');
INSERT INTO country(country_code, en_name, local_name, phone_code, phone_pattern)
VALUES ('TW', 'Taiwan(China)', '中國臺灣', '886', '^\\d{9}$');

INSERT INTO role(role_name)
VALUES ('admin'),
       ('user');
