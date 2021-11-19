DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS oauth_code;
DROP TABLE IF EXISTS oauth_refresh_token;

-- 导出  表 db02.oauth_access_token 结构
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` VARCHAR(255) DEFAULT NULL,
  `token` BLOB,
  `authentication_id` VARCHAR(255) DEFAULT NULL,
  `user_name` VARCHAR(255) DEFAULT NULL,
  `client_id` VARCHAR(255) DEFAULT NULL,
  `authentication` BLOB,
  `refresh_token` VARCHAR(255) DEFAULT NULL,
  KEY `token_id_index` (`token_id`) USING BTREE,
  KEY `authentication_id_index` (`authentication_id`) USING BTREE,
  KEY `user_name_index` (`user_name`) USING BTREE,
  KEY `client_id_index` (`client_id`) USING BTREE,
  KEY `refresh_token_index` (`refresh_token`) USING BTREE
) ENGINE=MYISAM DEFAULT CHARSET=utf8mb4;

-- 导出  表 db02.oauth_client_details 结构
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
  `client_id` VARCHAR(250) NOT NULL,
  `resource_ids` VARCHAR(256) DEFAULT NULL,
  `client_secret` VARCHAR(256) DEFAULT NULL,
  `scope` VARCHAR(256) DEFAULT NULL,
  `authorized_grant_types` VARCHAR(256) DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256) DEFAULT NULL,
  `authorities` VARCHAR(256) DEFAULT NULL,
  `access_token_validity` INT(11) DEFAULT NULL,
  `refresh_token_validity` INT(11) DEFAULT NULL,
  `additional_information` VARCHAR(4096) DEFAULT NULL,
  `autoapprove` VARCHAR(256) DEFAULT NULL,
  `create_time` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=MYISAM DEFAULT CHARSET=utf8mb4;


-- 导出  表 db02.oauth_code 结构
CREATE TABLE IF NOT EXISTS `oauth_code` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `code` VARCHAR(255) DEFAULT NULL,
  `authentication` BLOB,
  KEY `code_index` (`code`) USING BTREE
) ENGINE=MYISAM DEFAULT CHARSET=utf8mb4;



-- 导出  表 db02.oauth_refresh_token 结构
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `token_id` VARCHAR(255) DEFAULT NULL,
  `token` BLOB,
  `authentication` BLOB,
  KEY `token_id_index` (`token_id`) USING BTREE
) ENGINE=MYISAM DEFAULT CHARSET=utf8mb4;

