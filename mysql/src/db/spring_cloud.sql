

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
                        `age` int DEFAULT NULL COMMENT '年龄',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
