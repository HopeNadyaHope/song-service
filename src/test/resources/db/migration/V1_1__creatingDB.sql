CREATE TABLE IF NOT EXISTS song
(
    `id`               INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name`             VARCHAR(45)        NOT NULL,
    `artist`           VARCHAR(45)        NOT NULL,
    `album`            VARCHAR(45)        NOT NULL,
    `length`           VARCHAR(45)        NOT NULL,
    `resource_id`      INT                NOT NULL UNIQUE,
    `year`             INT                NOT NULL
);
