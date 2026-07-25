CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`          bigint(19) NOT NULL AUTO_INCREMENT,
    `deleted`     tinyint    NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `roles`       tinyint    NOT NULL DEFAULT 0 COMMENT '角色',
    `type`        tinyint COMMENT '类型，保留',
    `gender`      tinyint COMMENT '性别，保留',

    `token`       varchar(64) COMMENT "token",

    `name`        varchar(128) COMMENT "名字",
    `nickname`    varchar(128) COMMENT "昵称",
    `avatar`      varchar(256) COMMENT "头像",
    `wx_union_id` varchar(32) COMMENT "微信unionId",
    `open_id`     varchar(32) COMMENT "微信open_id",
    `phone`       varchar(32) COMMENT "电话号码",

    `login_time`  datetime COMMENT "登录时间",
    `create_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY WX (`wx_union_id`),
    KEY WX_OPEN (`open_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;