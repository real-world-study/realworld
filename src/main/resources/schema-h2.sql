CREATE TABLE user
(
    id              bigint          NOT NULL AUTO_INCREMENT,
    email           varchar(50)     NOT NULL,
    username        varchar(20)     NOT NULL,
    password        varchar(255)    NOT NULL,
    bio             varchar         DEFAULT NULL,
    image           varchar         DEFAULT NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at      DATETIME        DEFAULT NULL,
    deleted_at      DATETIME        DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE follow
(
    user_id         bigint          NOT NULL,
    follower_id     bigint          NOT NULL,
    PRIMARY KEY(user_id, follower_id),
    CONSTRAINT fk_follow_to_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_follow_to_follower_id FOREIGN KEY (follower_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE article
(
    id              bigint          NOT NULL AUTO_INCREMENT,
    user_id         bigint          NOT NULL,
    slug            varchar(50)     NOT NULL,
    title           varchar(50)     NOT NULL,
    description     varchar(255)    NOT NULL,
    body            text            NOT NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at      DATETIME        DEFAULT NULL,
    deleted_at      DATETIME        DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_article_to_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT unique_user_id_slug UNIQUE (user_id, slug)
);

CREATE TABLE comment
(
    id              bigint          NOT NULL AUTO_INCREMENT,
    user_id         bigint          NOT NULL,
    article_id     bigint          NOT NULL,
    body            text            NOT NULL,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at      DATETIME        DEFAULT NULL,
    deleted_at      DATETIME        DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_comment_to_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_comment_to_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE favorite
(
    user_id         bigint          NOT NULL,
    article_id     bigint          NOT NULL,
    PRIMARY KEY (user_id, article_id),
    CONSTRAINT fk_favorite_to_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ,
    CONSTRAINT fk_favorite_to_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
);

CREATE TABLE tag
(
    id              bigint          NOT NULL AUTO_INCREMENT,
    name            varchar(20)     NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE article_tag
(
    article_id      bigint          NOT NULL,
    tag_id          bigint          NOT NULL,
    PRIMARY KEY (article_id, tag_id),
    CONSTRAINT fk_articletag_article_id FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
    CONSTRAINT fk_articletag_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

