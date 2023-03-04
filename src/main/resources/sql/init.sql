CREATE TABLE IF NOT EXISTS `token`  (
    `id` int(11) NOT NULL COMMENT 'id',
    `access_token` varchar(512) NOT NULL COMMENT 'token',
    `expires_in` int(11) NOT NULL COMMENT '过期时间',
    `create_time` datetime ( 0 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 0 ) ON UPDATE CURRENT_TIMESTAMP ( 0 ) COMMENT '创建时间',
    PRIMARY KEY (`id`)
);