CREATE DATABASE music_linker CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'emanresu'@'%' IDENTIFIED BY 'drowssap';
GRANT INSERT, DELETE, UPDATE, SELECT ON music_linker.* TO 'emanresu'@'%';
FLUSH PRIVILEGES;

USE music_linker;

CREATE TABLE country
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'row id',
    country_code       CHAR(2)      NOT NULL COMMENT 'country code',
    en_name            VARCHAR(128) NOT NULL COMMENT 'name of english',
    local_name         VARCHAR(128) NOT NULL COMMENT 'name of native language',
    phone_code         VARCHAR(8)   NOT NULL COMMENT 'phone code',
    phone_pattern      VARCHAR(16)  NOT NULL COMMENT 'telephone number regex pattern',
    created_date       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row created date',
    last_modified_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row last modified date',
    PRIMARY KEY pk_i (id) COMMENT 'row primary key',
    UNIQUE KEY uk_cc (country_code) COMMENT 'unique of country code',
    UNIQUE KEY uk_pc (phone_code) COMMENT 'unique of phone code'
)
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = 'country code and phone code';

CREATE TABLE user
(
    id                 BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    country_id         BIGINT      NOT NULL COMMENT 'country code reference',
    tel_no             VARCHAR(15) NOT NULL COMMENT 'user telephone number',
    password           CHAR(60)    NOT NULL COMMENT 'user login password with bCrypt',
    enable             BOOLEAN     NOT NULL DEFAULT TRUE COMMENT 'user account is enable or not',
    created_date       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row created date',
    last_modified_date TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row last modified date',
    PRIMARY KEY pk_i (id) COMMENT 'row primary key',
    FOREIGN KEY fk_ui_ci (country_id) REFERENCES country (id),
    UNIQUE KEY uk_ui_ci_tn (country_id, tel_no) COMMENT 'unique of telephone number'
)
    AUTO_INCREMENT = 10001
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = 'user info';

CREATE TABLE role
(
    id                 BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'row id',
    role_name          VARCHAR(32) NOT NULL COMMENT 'role name',
    created_date       TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row created date',
    last_modified_date TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row last modified date',
    PRIMARY KEY pk_i (id) COMMENT 'row primary key'
)
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = 'role';

CREATE TABLE user_role
(
    id                 BIGINT    NOT NULL AUTO_INCREMENT COMMENT 'row id',
    user_id            BIGINT    NOT NULL COMMENT 'user id',
    role_id            BIGINT    NOT NULL COMMENT 'role id',
    created_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row created date',
    last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'row last modified date',
    PRIMARY KEY pk_i (id) COMMENT 'row primary key',
    FOREIGN KEY fk_ur_ui (user_id) REFERENCES user (id),
    FOREIGN KEY fk_ur_ri (role_id) REFERENCES role (id),
    UNIQUE KEY uk_ur_ui_ri (user_id, role_id) COMMENT 'unique of user id and role id'
)
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_unicode_ci
    COMMENT = 'user role';
