CREATE TABLE IF NOT EXISTS user
(
    id         bigint       NOT NULL AUTO_INCREMENT,
    email      varchar(50)  NOT NULL,
    username   varchar(20)  NOT NULL,
    password   varchar(255) NOT NULL,
    bio        varchar               DEFAULT NULL,
    image      varchar               DEFAULT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP             DEFAULT NULL,
    deleted_at TIMESTAMP             DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_username UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS follow
(
    id          bigint NOT NULL AUTO_INCREMENT,
    follower_id bigint NOT NULL,
    followee_id bigint NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (follower_id, followee_id),
    CONSTRAINT fk_follow_to_follower_id FOREIGN KEY (follower_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_follow_to_followee_id FOREIGN KEY (followee_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS article
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    user_id     bigint       NOT NULL,
    slug        varchar(50)  NOT NULL,
    title       varchar(50)  NOT NULL,
    description varchar(255) NOT NULL,
    body        text         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at  TIMESTAMP             DEFAULT NULL,
    deleted_at  TIMESTAMP             DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_article_to_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT unique_user_id_slug UNIQUE (user_id, slug)
);

CREATE TABLE IF NOT EXISTS comment
(
    id         bigint    NOT NULL AUTO_INCREMENT,
    user_id    bigint    NOT NULL,
    article_id bigint    NOT NULL,
    body       text      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP          DEFAULT NULL,
    deleted_at TIMESTAMP          DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_comment_to_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_comment_to_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS favorite
(
    user_id    bigint NOT NULL,
    article_id bigint NOT NULL,
    PRIMARY KEY (user_id, article_id),
    CONSTRAINT fk_favorite_to_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_to_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tag
(
    id   bigint      NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS article_tag
(
    article_id bigint NOT NULL,
    tag_id     bigint NOT NULL,
    PRIMARY KEY (article_id, tag_id),
    CONSTRAINT fk_articletag_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
    CONSTRAINT fk_articletag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

