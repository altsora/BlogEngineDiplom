CREATE TABLE comments
(
    id        BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT(20) NULL REFERENCES comments (id),
    post_id   BIGINT(20) NOT NULL REFERENCES posts (id),
    user_id   BIGINT(20) NOT NULL REFERENCES users (id),
    time      DATETIME   NOT NULL,
    text      TEXT       NOT NULL
);