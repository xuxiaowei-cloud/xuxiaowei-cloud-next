/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.5.4-docker
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 192.168.5.4:3306
 Source Schema         : xuxiaowei_cloud_next

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 14/06/2022 23:02:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (7, 'xuxiaowei', 'user_authorities', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (8, 'xuxiaowei', 'user_details', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (9, 'xuxiaowei', 'user_info', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (10, 'xuxiaowei', 'user_oauth2_oauth2Request', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (11, 'xuxiaowei', 'user_oauth2_userAuthentication', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (12, 'xuxiaowei', 'manage_user_add', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (13, 'xuxiaowei', 'manage_user_delete', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (14, 'xuxiaowei', 'manage_user_edit', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (15, 'xuxiaowei', 'manage_user_read', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (16, 'xuxiaowei', 'manage_user_authority', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (17, 'xuxiaowei', 'manage_client_add', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (18, 'xuxiaowei', 'manage_client_delete', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (19, 'xuxiaowei', 'manage_client_edit', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (20, 'xuxiaowei', 'manage_client_read', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (21, 'xuxiaowei', 'clientId_token_delete', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (22, 'xuxiaowei', 'username_token_delete', '2022-06-14 23:01:27', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (23, 'xuxiaowei', 'audit_authorization_read', '2022-06-18 15:14:09', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (24, 'xuxiaowei', 'audit_authorization_delete', '2022-06-18 15:25:36', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (25, 'xuxiaowei', 'audit_authorization_consent_delete', '2022-06-18 15:59:06', NULL);
INSERT INTO `authorities` (`authorities_id`, `username`, `authority`, `create_date`, `update_date`) VALUES (26, 'xuxiaowei', 'audit_authorization_consent_read', '2022-06-18 15:59:16', NULL);

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` (`authority`, `explain`) VALUES ('audit_authorization_consent_delete', '?????????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('audit_authorization_consent_read', '?????????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('audit_authorization_delete', '??????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('audit_authorization_read', '??????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('clientId_token_delete', '??????Token ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_client_add', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_client_delete', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_client_edit', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_client_read', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_user_add', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_user_authority', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_user_delete', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_user_edit', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('manage_user_read', '???????????? ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('username_token_delete', '??????Token ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('user_authorities', '??????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('user_details', '??????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('user_info', '??????????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('user_oauth2_oauth2Request', '?????? oauth2 ????????????');
INSERT INTO `authority` (`authority`, `explain`) VALUES ('user_oauth2_userAuthentication', '?????? oauth2 ??????????????????');


-- ----------------------------
-- Records of oauth2_authorization
-- ----------------------------

-- ----------------------------
-- Records of oauth2_authorization_consent
-- ----------------------------

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` (`id`, `client_id`, `client_id_issued_at`, `client_secret`, `client_secret_expires_at`, `client_name`, `client_authentication_methods`, `authorization_grant_types`, `redirect_uris`, `scopes`, `client_settings`, `token_settings`) VALUES ('1', 'xuxiaowei_client_id', '2022-05-30 17:17:41', '{bcrypt}$2a$10$s/3CEiHunH9wo2qr7JfeD.SRa8kK2Y8lOriHWrWhidQX3hyhuORlO', NULL, '2bf94b1e-7b69-4370-86b0-73f402ddbdb7', 'client_secret_post,client_secret_basic', 'refresh_token,implicit,client_credentials,authorization_code', 'http://127.0.0.1:1401/code,http://gateway.example.next.xuxiaowei.cloud:1101/passport/code,https://gateway.example.next.xuxiaowei.cloud/passport/code', 'snsapi_base,snsapi_info', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",43200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",2592000.000000000]}');
INSERT INTO `oauth2_registered_client` (`id`, `client_id`, `client_id_issued_at`, `client_secret`, `client_secret_expires_at`, `client_name`, `client_authentication_methods`, `authorization_grant_types`, `redirect_uris`, `scopes`, `client_settings`, `token_settings`) VALUES ('2', 'xuxiaowei_client_wechat_miniprogram_id', '2022-05-30 17:17:41', '{bcrypt}$2a$10$ILU5dLDEwYoRS64PtFttCOZFxAq8lO6Awkikx6stne0wwtDf4d0jS', NULL, '86b0-4370-7b69-73f402ddbdb7-2bf94b1e', 'client_secret_post,client_secret_basic', 'refresh_token,wechat_miniprogram', NULL, 'snsapi_base,snsapi_info', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":false}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",43200.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.core.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",2592000.000000000]}');

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` (`users_id`, `username`, `email`, `email_valid`, `nickname`, `password`, `enabled`, `account_non_expired`, `credentials_non_expired`, `account_non_locked`, `create_date`, `update_date`, `deleted`) VALUES (1, 'xuxiaowei', 'xuxiaowei@xuxiaowei.com.cn', 1, '?????????', '{bcrypt}$2a$10$UEX4P9awppGO0DACKpGbpOmcViKZqbG5ObTOr8viJJvAh1AFOGHkK', 1, 1, 1, 1, '2022-04-06 09:32:43', '2022-06-18 10:47:00', 0);

SET FOREIGN_KEY_CHECKS = 1;
