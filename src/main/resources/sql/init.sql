DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
  `id`        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_code` varchar(36) COMMENT '用户编号',
  `username`  varchar(20) COMMENT '用户名称',
  `password`  varchar(100) COMMENT '密码',
  `email`     varchar(100) COMMENT '邮箱',
  `phone`     varchar(11) COMMENT '手机号',
  `role`      int(10) COMMENT '角色',
  `image`     varchar(255) COMMENT '头像',
  `last_ip`   varchar(50) COMMENT '最后登录IP',
  `last_time` varchar(50) COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- username: root password: 123456
INSERT INTO `USER`
VALUES (1, 'd242ae49-4734-411e-8c8d-d2b09e87c3c8', 'root',
        '$2a$04$REdYt1gsbANkWtfhqjc9C.EqJM/k8qcQv2McNv/YGROZtOaFzzP4.', 'admin@163.com', '16666666666', 1, 'image',
        '127.0.0.1', '2019-10-21 11:26:27');