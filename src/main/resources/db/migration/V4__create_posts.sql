CREATE TABLE posts
(
    id                BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    activity_status   VARCHAR(255) NOT NULL,
    moderation_status VARCHAR(255) NOT NULL,
    moderator_id      BIGINT(20)   NULL REFERENCES users (id),
    user_id           BIGINT(20)   NOT NULL REFERENCES users (id),
    time              DATETIME     NOT NULL,
    title             VARCHAR(255) NOT NULL,
    text              TEXT         NOT NULL,
    view_count        INT          NOT NULL
);