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


CREATE TABLE IF NOT EXISTS `t_routine`
(
    `id`           bigint(19) NOT NULL AUTO_INCREMENT,
    `deleted`      tinyint    NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `status`       tinyint    NOT NULL DEFAULT 0 COMMENT '任务状态',
    `category`     int        NOT NULL DEFAULT 0 COMMENT '任务类型',
    `subcategory`  int        NOT NULL DEFAULT 0 COMMENT '任务子类型',

    `user_id`      bigint(19) NOT NULL COMMENT '归属用户',
    `date`         datetime   NOT NULL COMMENT '任务日期',
    `transaction`  varchar(32) COMMENT '防重提交',
    `name`         text COMMENT '名字',
    `detail`       text COMMENT '详情',
    `medias`       text COMMENT '图片或者视频详情',
    `remark`       text COMMENT '反馈',
    `media_remark` text COMMENT '图片或者视频反馈',
    `extra`        text COMMENT '保留扩展',

    `finish_time`  datetime COMMENT '完成时间',
    `create_time`  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY USER_DATE (`user_id`, `date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;