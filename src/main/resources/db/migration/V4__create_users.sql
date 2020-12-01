CREATE TABLE users
(
    id           BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    is_moderator TINYINT      NOT NULL,
    reg_time     DATETIME     NOT NULL,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    code         VARCHAR(255),
    photo        TEXT
);

