CREATE TABLE votes
(
    id      BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT(20) NOT NULL REFERENCES users (id),
    post_id BIGINT(20) NOT NULL REFERENCES posts (id),
    time    DATETIME   NOT NULL,
    value   VARCHAR(10)
)