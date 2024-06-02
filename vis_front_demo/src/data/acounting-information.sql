/*
 Navicat Premium Data Transfer

 Source Server         : mysqllink
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : acounting-information

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 02/06/2024 14:29:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for companies
-- ----------------------------
DROP TABLE IF EXISTS `companies`;
CREATE TABLE `companies`  (
  `Company_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Company_Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Company_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of companies
-- ----------------------------
INSERT INTO `companies` VALUES (1, '蔚来');
INSERT INTO `companies` VALUES (2, '理想');
INSERT INTO `companies` VALUES (3, '比亚迪');
INSERT INTO `companies` VALUES (4, '零跑');
INSERT INTO `companies` VALUES (5, '小鹏汽车');

-- ----------------------------
-- Table structure for company_financial_data
-- ----------------------------
DROP TABLE IF EXISTS `company_financial_data`;
CREATE TABLE `company_financial_data`  (
  `Data_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Company_ID` int(11) NULL DEFAULT NULL,
  `Year` int(11) NOT NULL,
  `Indicator_ID` int(11) NULL DEFAULT NULL,
  `Financial_Data_Value` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`Data_ID`) USING BTREE,
  INDEX `Company_ID`(`Company_ID`) USING BTREE,
  INDEX `Year`(`Year`) USING BTREE,
  INDEX `Indicator_ID`(`Indicator_ID`) USING BTREE,
  CONSTRAINT `company_financial_data_ibfk_1` FOREIGN KEY (`Company_ID`) REFERENCES `companies` (`Company_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `company_financial_data_ibfk_3` FOREIGN KEY (`Indicator_ID`) REFERENCES `financial_indicators` (`Indicator_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 402 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_financial_data
-- ----------------------------
INSERT INTO `company_financial_data` VALUES (1, 1, 2018, 1, 1.42);
INSERT INTO `company_financial_data` VALUES (2, 1, 2019, 1, 0.52);
INSERT INTO `company_financial_data` VALUES (3, 1, 2020, 1, 3.31);
INSERT INTO `company_financial_data` VALUES (4, 1, 2021, 1, 2.18);
INSERT INTO `company_financial_data` VALUES (5, 1, 2022, 1, 1.29);
INSERT INTO `company_financial_data` VALUES (6, 2, 2018, 1, 1.31);
INSERT INTO `company_financial_data` VALUES (7, 2, 2019, 1, 1.08);
INSERT INTO `company_financial_data` VALUES (8, 2, 2020, 1, 7.28);
INSERT INTO `company_financial_data` VALUES (9, 2, 2021, 1, 4.33);
INSERT INTO `company_financial_data` VALUES (10, 2, 2022, 1, 2.45);
INSERT INTO `company_financial_data` VALUES (11, 2, 2023, 1, 1.57);
INSERT INTO `company_financial_data` VALUES (12, 3, 2018, 1, 0.99);
INSERT INTO `company_financial_data` VALUES (13, 3, 2019, 1, 0.99);
INSERT INTO `company_financial_data` VALUES (14, 3, 2020, 1, 1.05);
INSERT INTO `company_financial_data` VALUES (15, 3, 2021, 1, 0.97);
INSERT INTO `company_financial_data` VALUES (16, 3, 2022, 1, 0.72);
INSERT INTO `company_financial_data` VALUES (17, 4, 2019, 1, 1.00);
INSERT INTO `company_financial_data` VALUES (18, 4, 2020, 1, 1.60);
INSERT INTO `company_financial_data` VALUES (19, 4, 2021, 1, 2.07);
INSERT INTO `company_financial_data` VALUES (20, 4, 2022, 1, 1.47);
INSERT INTO `company_financial_data` VALUES (21, 5, 2018, 1, 6.28);
INSERT INTO `company_financial_data` VALUES (22, 5, 2019, 1, 1.50);
INSERT INTO `company_financial_data` VALUES (23, 5, 2020, 1, 5.06);
INSERT INTO `company_financial_data` VALUES (24, 5, 2021, 1, 2.71);
INSERT INTO `company_financial_data` VALUES (25, 5, 2022, 1, 1.81);
INSERT INTO `company_financial_data` VALUES (26, 1, 2018, 2, 1.25);
INSERT INTO `company_financial_data` VALUES (27, 1, 2019, 2, 0.43);
INSERT INTO `company_financial_data` VALUES (28, 1, 2020, 2, 3.23);
INSERT INTO `company_financial_data` VALUES (29, 1, 2021, 2, 2.11);
INSERT INTO `company_financial_data` VALUES (30, 1, 2022, 2, 1.11);
INSERT INTO `company_financial_data` VALUES (31, 2, 2018, 2, 1.26);
INSERT INTO `company_financial_data` VALUES (32, 2, 2019, 2, 0.87);
INSERT INTO `company_financial_data` VALUES (33, 2, 2020, 2, 6.72);
INSERT INTO `company_financial_data` VALUES (34, 2, 2021, 2, 3.93);
INSERT INTO `company_financial_data` VALUES (35, 2, 2022, 2, 2.07);
INSERT INTO `company_financial_data` VALUES (36, 2, 2023, 2, 1.42);
INSERT INTO `company_financial_data` VALUES (37, 3, 2018, 2, 0.76);
INSERT INTO `company_financial_data` VALUES (38, 3, 2019, 2, 0.75);
INSERT INTO `company_financial_data` VALUES (39, 3, 2020, 2, 0.75);
INSERT INTO `company_financial_data` VALUES (40, 3, 2021, 2, 0.72);
INSERT INTO `company_financial_data` VALUES (41, 3, 2022, 2, 0.49);
INSERT INTO `company_financial_data` VALUES (42, 4, 2019, 2, 0.81);
INSERT INTO `company_financial_data` VALUES (43, 4, 2020, 2, 0.53);
INSERT INTO `company_financial_data` VALUES (44, 4, 2021, 2, 1.90);
INSERT INTO `company_financial_data` VALUES (45, 4, 2022, 2, 1.28);
INSERT INTO `company_financial_data` VALUES (46, 5, 2018, 2, 6.10);
INSERT INTO `company_financial_data` VALUES (47, 5, 2019, 2, 1.37);
INSERT INTO `company_financial_data` VALUES (48, 5, 2020, 2, 4.89);
INSERT INTO `company_financial_data` VALUES (49, 5, 2021, 2, 2.56);
INSERT INTO `company_financial_data` VALUES (50, 5, 2022, 2, 1.62);
INSERT INTO `company_financial_data` VALUES (51, 1, 2018, 3, 1.76);
INSERT INTO `company_financial_data` VALUES (52, 1, 2019, 3, 1.31);
INSERT INTO `company_financial_data` VALUES (53, 1, 2020, 3, 0.41);
INSERT INTO `company_financial_data` VALUES (54, 1, 2021, 3, 0.54);
INSERT INTO `company_financial_data` VALUES (55, 1, 2022, 3, 0.71);
INSERT INTO `company_financial_data` VALUES (56, 2, 2018, 3, -1.00);
INSERT INTO `company_financial_data` VALUES (57, 2, 2019, 3, 0.52);
INSERT INTO `company_financial_data` VALUES (58, 2, 2020, 3, 0.18);
INSERT INTO `company_financial_data` VALUES (59, 2, 2021, 3, 0.34);
INSERT INTO `company_financial_data` VALUES (60, 2, 2022, 3, 0.48);
INSERT INTO `company_financial_data` VALUES (61, 2, 2023, 3, 0.58);
INSERT INTO `company_financial_data` VALUES (62, 3, 2018, 3, 0.69);
INSERT INTO `company_financial_data` VALUES (63, 3, 2019, 3, 0.68);
INSERT INTO `company_financial_data` VALUES (64, 3, 2020, 3, 0.68);
INSERT INTO `company_financial_data` VALUES (65, 3, 2021, 3, 0.65);
INSERT INTO `company_financial_data` VALUES (66, 3, 2022, 3, 0.75);
INSERT INTO `company_financial_data` VALUES (67, 4, 2019, 3, 1.50);
INSERT INTO `company_financial_data` VALUES (68, 4, 2020, 3, 1.17);
INSERT INTO `company_financial_data` VALUES (69, 4, 2021, 3, 0.42);
INSERT INTO `company_financial_data` VALUES (70, 4, 2022, 3, 0.57);
INSERT INTO `company_financial_data` VALUES (71, 5, 2018, 3, 1.28);
INSERT INTO `company_financial_data` VALUES (72, 5, 2019, 3, 1.74);
INSERT INTO `company_financial_data` VALUES (73, 5, 2020, 3, 0.23);
INSERT INTO `company_financial_data` VALUES (74, 5, 2021, 3, 0.36);
INSERT INTO `company_financial_data` VALUES (75, 5, 2022, 3, 0.48);
INSERT INTO `company_financial_data` VALUES (76, 1, 2018, 4, 0.33);
INSERT INTO `company_financial_data` VALUES (77, 1, 2019, 4, 0.44);
INSERT INTO `company_financial_data` VALUES (78, 1, 2020, 4, 0.44);
INSERT INTO `company_financial_data` VALUES (79, 1, 2021, 4, 0.48);
INSERT INTO `company_financial_data` VALUES (80, 1, 2022, 4, 0.51);
INSERT INTO `company_financial_data` VALUES (81, 2, 2019, 4, 0.04);
INSERT INTO `company_financial_data` VALUES (82, 2, 2020, 4, 0.41);
INSERT INTO `company_financial_data` VALUES (83, 2, 2021, 4, 0.55);
INSERT INTO `company_financial_data` VALUES (84, 2, 2022, 4, 0.61);
INSERT INTO `company_financial_data` VALUES (85, 2, 2023, 4, 1.08);
INSERT INTO `company_financial_data` VALUES (86, 2, 2018, 4, -1.00);
INSERT INTO `company_financial_data` VALUES (87, 3, 2018, 4, 0.68);
INSERT INTO `company_financial_data` VALUES (88, 3, 2019, 4, 0.65);
INSERT INTO `company_financial_data` VALUES (89, 3, 2020, 4, 0.81);
INSERT INTO `company_financial_data` VALUES (90, 3, 2021, 4, 0.88);
INSERT INTO `company_financial_data` VALUES (91, 3, 2022, 4, 1.04);
INSERT INTO `company_financial_data` VALUES (92, 4, 2019, 4, 0.05);
INSERT INTO `company_financial_data` VALUES (93, 4, 2020, 4, 0.23);
INSERT INTO `company_financial_data` VALUES (94, 4, 2021, 4, 0.40);
INSERT INTO `company_financial_data` VALUES (95, 4, 2022, 4, 0.75);
INSERT INTO `company_financial_data` VALUES (96, 5, 2018, 4, 0.00);
INSERT INTO `company_financial_data` VALUES (97, 5, 2019, 4, 0.27);
INSERT INTO `company_financial_data` VALUES (98, 5, 2020, 4, 0.22);
INSERT INTO `company_financial_data` VALUES (99, 5, 2021, 4, 0.39);
INSERT INTO `company_financial_data` VALUES (100, 5, 2022, 4, 0.37);
INSERT INTO `company_financial_data` VALUES (101, 1, 2018, 5, 6.70);
INSERT INTO `company_financial_data` VALUES (102, 1, 2019, 5, 7.66);
INSERT INTO `company_financial_data` VALUES (103, 1, 2020, 5, 14.60);
INSERT INTO `company_financial_data` VALUES (104, 1, 2021, 5, 18.68);
INSERT INTO `company_financial_data` VALUES (105, 1, 2022, 5, 8.61);
INSERT INTO `company_financial_data` VALUES (106, 2, 2018, 5, -1.00);
INSERT INTO `company_financial_data` VALUES (107, 2, 2019, 5, 1.10);
INSERT INTO `company_financial_data` VALUES (108, 2, 2020, 5, 10.10);
INSERT INTO `company_financial_data` VALUES (109, 2, 2021, 5, 15.94);
INSERT INTO `company_financial_data` VALUES (110, 2, 2022, 5, 8.67);
INSERT INTO `company_financial_data` VALUES (111, 2, 2023, 5, 14.09);
INSERT INTO `company_financial_data` VALUES (112, 3, 2018, 5, 4.62);
INSERT INTO `company_financial_data` VALUES (113, 3, 2019, 5, 4.08);
INSERT INTO `company_financial_data` VALUES (114, 3, 2020, 5, 4.56);
INSERT INTO `company_financial_data` VALUES (115, 3, 2021, 5, 5.10);
INSERT INTO `company_financial_data` VALUES (116, 3, 2022, 5, 5.56);
INSERT INTO `company_financial_data` VALUES (117, 4, 2019, 5, 1.39);
INSERT INTO `company_financial_data` VALUES (118, 4, 2020, 5, 5.64);
INSERT INTO `company_financial_data` VALUES (119, 4, 2021, 5, 9.76);
INSERT INTO `company_financial_data` VALUES (120, 4, 2022, 5, 11.12);
INSERT INTO `company_financial_data` VALUES (121, 5, 2018, 5, 0.07);
INSERT INTO `company_financial_data` VALUES (122, 5, 2019, 5, 9.19);
INSERT INTO `company_financial_data` VALUES (123, 5, 2020, 5, 6.31);
INSERT INTO `company_financial_data` VALUES (124, 5, 2021, 5, 9.27);
INSERT INTO `company_financial_data` VALUES (125, 5, 2022, 5, 6.39);
INSERT INTO `company_financial_data` VALUES (126, 1, 2018, 6, 6.41);
INSERT INTO `company_financial_data` VALUES (127, 1, 2019, 6, 6.99);
INSERT INTO `company_financial_data` VALUES (128, 1, 2020, 6, 12.26);
INSERT INTO `company_financial_data` VALUES (129, 1, 2021, 6, 16.81);
INSERT INTO `company_financial_data` VALUES (130, 1, 2022, 6, 11.50);
INSERT INTO `company_financial_data` VALUES (131, 2, 2018, 6, -1.00);
INSERT INTO `company_financial_data` VALUES (132, 2, 2019, 6, 10.51);
INSERT INTO `company_financial_data` VALUES (133, 2, 2020, 6, 2.36);
INSERT INTO `company_financial_data` VALUES (134, 2, 2021, 6, 1.57);
INSERT INTO `company_financial_data` VALUES (135, 2, 2022, 6, 0.67);
INSERT INTO `company_financial_data` VALUES (136, 2, 2023, 6, 0.28);
INSERT INTO `company_financial_data` VALUES (137, 3, 2018, 6, 2.57);
INSERT INTO `company_financial_data` VALUES (138, 3, 2019, 6, 2.74);
INSERT INTO `company_financial_data` VALUES (139, 3, 2020, 6, 3.68);
INSERT INTO `company_financial_data` VALUES (140, 3, 2021, 6, 5.58);
INSERT INTO `company_financial_data` VALUES (141, 3, 2022, 6, 11.29);
INSERT INTO `company_financial_data` VALUES (142, 4, 2019, 6, -1.00);
INSERT INTO `company_financial_data` VALUES (143, 4, 2020, 6, -1.00);
INSERT INTO `company_financial_data` VALUES (144, 4, 2021, 6, -1.00);
INSERT INTO `company_financial_data` VALUES (145, 4, 2022, 6, -1.00);
INSERT INTO `company_financial_data` VALUES (146, 5, 2018, 6, 0.25);
INSERT INTO `company_financial_data` VALUES (147, 5, 2019, 6, 8.04);
INSERT INTO `company_financial_data` VALUES (148, 5, 2020, 6, 7.01);
INSERT INTO `company_financial_data` VALUES (149, 5, 2021, 6, 11.04);
INSERT INTO `company_financial_data` VALUES (150, 5, 2022, 6, 8.20);
INSERT INTO `company_financial_data` VALUES (151, 1, 2018, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (152, 1, 2019, 7, 0.34);
INSERT INTO `company_financial_data` VALUES (153, 1, 2020, 7, 0.51);
INSERT INTO `company_financial_data` VALUES (154, 1, 2021, 7, 0.54);
INSERT INTO `company_financial_data` VALUES (155, 1, 2022, 7, 0.27);
INSERT INTO `company_financial_data` VALUES (156, 2, 2018, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (157, 2, 2019, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (158, 2, 2020, 7, 34.43);
INSERT INTO `company_financial_data` VALUES (159, 2, 2021, 7, 1.95);
INSERT INTO `company_financial_data` VALUES (160, 2, 2022, 7, 0.53);
INSERT INTO `company_financial_data` VALUES (161, 2, 2023, 7, 1.68);
INSERT INTO `company_financial_data` VALUES (162, 3, 2018, 7, 0.18);
INSERT INTO `company_financial_data` VALUES (163, 3, 2019, 7, -0.04);
INSERT INTO `company_financial_data` VALUES (164, 3, 2020, 7, 0.31);
INSERT INTO `company_financial_data` VALUES (165, 3, 2021, 7, 0.43);
INSERT INTO `company_financial_data` VALUES (166, 3, 2022, 7, 0.79);
INSERT INTO `company_financial_data` VALUES (167, 4, 2019, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (168, 4, 2020, 7, 0.83);
INSERT INTO `company_financial_data` VALUES (169, 4, 2021, 7, 0.80);
INSERT INTO `company_financial_data` VALUES (170, 4, 2022, 7, 0.72);
INSERT INTO `company_financial_data` VALUES (171, 5, 2018, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (172, 5, 2019, 7, -1.00);
INSERT INTO `company_financial_data` VALUES (173, 5, 2020, 7, 0.83);
INSERT INTO `company_financial_data` VALUES (174, 5, 2021, 7, 0.80);
INSERT INTO `company_financial_data` VALUES (175, 5, 2022, 7, 0.72);
INSERT INTO `company_financial_data` VALUES (176, 1, 2018, 8, -1.00);
INSERT INTO `company_financial_data` VALUES (177, 1, 2019, 8, -0.18);
INSERT INTO `company_financial_data` VALUES (178, 1, 2020, 8, 0.51);
INSERT INTO `company_financial_data` VALUES (179, 1, 2021, 8, -1.02);
INSERT INTO `company_financial_data` VALUES (180, 1, 2022, 8, -0.32);
INSERT INTO `company_financial_data` VALUES (181, 2, 2018, 8, -1.00);
INSERT INTO `company_financial_data` VALUES (182, 2, 2019, 8, -0.36);
INSERT INTO `company_financial_data` VALUES (183, 2, 2020, 8, 14.10);
INSERT INTO `company_financial_data` VALUES (184, 2, 2021, 8, -0.54);
INSERT INTO `company_financial_data` VALUES (185, 2, 2022, 8, -0.82);
INSERT INTO `company_financial_data` VALUES (186, 2, 2023, 8, 1.18);
INSERT INTO `company_financial_data` VALUES (187, 3, 2018, 8, -0.34);
INSERT INTO `company_financial_data` VALUES (188, 3, 2019, 8, -0.43);
INSERT INTO `company_financial_data` VALUES (189, 3, 2020, 8, 1.79);
INSERT INTO `company_financial_data` VALUES (190, 3, 2021, 8, -0.26);
INSERT INTO `company_financial_data` VALUES (191, 3, 2022, 8, 3.98);
INSERT INTO `company_financial_data` VALUES (192, 4, 2019, 8, -1.00);
INSERT INTO `company_financial_data` VALUES (193, 4, 2020, 8, -0.67);
INSERT INTO `company_financial_data` VALUES (194, 4, 2021, 8, -0.78);
INSERT INTO `company_financial_data` VALUES (195, 4, 2022, 8, -0.20);
INSERT INTO `company_financial_data` VALUES (196, 5, 2018, 8, -1.00);
INSERT INTO `company_financial_data` VALUES (197, 5, 2019, 8, -1.00);
INSERT INTO `company_financial_data` VALUES (198, 5, 2020, 8, -0.67);
INSERT INTO `company_financial_data` VALUES (199, 5, 2021, 8, -0.78);
INSERT INTO `company_financial_data` VALUES (200, 5, 2022, 8, -0.20);
INSERT INTO `company_financial_data` VALUES (201, 1, 2018, 9, -1.00);
INSERT INTO `company_financial_data` VALUES (202, 1, 2019, 9, -0.24);
INSERT INTO `company_financial_data` VALUES (203, 1, 2020, 9, 2.99);
INSERT INTO `company_financial_data` VALUES (204, 1, 2021, 9, 0.56);
INSERT INTO `company_financial_data` VALUES (205, 1, 2022, 9, 0.07);
INSERT INTO `company_financial_data` VALUES (206, 2, 2018, 9, -1.00);
INSERT INTO `company_financial_data` VALUES (207, 2, 2019, 9, 0.61);
INSERT INTO `company_financial_data` VALUES (208, 2, 2020, 9, 3.07);
INSERT INTO `company_financial_data` VALUES (209, 2, 2021, 9, 0.76);
INSERT INTO `company_financial_data` VALUES (210, 2, 2022, 9, 0.28);
INSERT INTO `company_financial_data` VALUES (211, 2, 2023, 9, 0.62);
INSERT INTO `company_financial_data` VALUES (212, 3, 2018, 9, 0.05);
INSERT INTO `company_financial_data` VALUES (213, 3, 2019, 9, -0.02);
INSERT INTO `company_financial_data` VALUES (214, 3, 2020, 9, 0.09);
INSERT INTO `company_financial_data` VALUES (215, 3, 2021, 9, 0.52);
INSERT INTO `company_financial_data` VALUES (216, 3, 2022, 9, 0.52);
INSERT INTO `company_financial_data` VALUES (217, 4, 2019, 9, -1.00);
INSERT INTO `company_financial_data` VALUES (218, 4, 2020, 9, 0.30);
INSERT INTO `company_financial_data` VALUES (219, 4, 2021, 9, 0.75);
INSERT INTO `company_financial_data` VALUES (220, 4, 2022, 9, 0.29);
INSERT INTO `company_financial_data` VALUES (221, 5, 2018, 9, -1.00);
INSERT INTO `company_financial_data` VALUES (222, 5, 2019, 9, 0.21);
INSERT INTO `company_financial_data` VALUES (223, 5, 2020, 9, 3.83);
INSERT INTO `company_financial_data` VALUES (224, 5, 2021, 9, 0.47);
INSERT INTO `company_financial_data` VALUES (225, 5, 2022, 9, 0.09);
INSERT INTO `company_financial_data` VALUES (226, 1, 2018, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (227, 1, 2019, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (228, 1, 2020, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (229, 1, 2021, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (230, 1, 2022, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (231, 2, 2018, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (232, 2, 2019, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (233, 2, 2020, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (234, 2, 2021, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (235, 2, 2022, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (236, 2, 2023, 10, 24.07);
INSERT INTO `company_financial_data` VALUES (237, 3, 2018, 10, 54.84);
INSERT INTO `company_financial_data` VALUES (238, 3, 2019, 10, 95.34);
INSERT INTO `company_financial_data` VALUES (239, 3, 2020, 10, 132.18);
INSERT INTO `company_financial_data` VALUES (240, 3, 2021, 10, 252.94);
INSERT INTO `company_financial_data` VALUES (241, 3, 2022, 10, 45.00);
INSERT INTO `company_financial_data` VALUES (242, 4, 2019, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (243, 4, 2020, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (244, 4, 2021, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (245, 4, 2022, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (246, 4, 2023, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (247, 5, 2018, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (248, 5, 2019, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (249, 5, 2020, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (250, 5, 2021, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (251, 5, 2022, 10, -1.00);
INSERT INTO `company_financial_data` VALUES (252, 1, 2018, 11, 6.74);
INSERT INTO `company_financial_data` VALUES (253, 1, 2019, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (254, 1, 2020, 11, 17.91);
INSERT INTO `company_financial_data` VALUES (255, 1, 2021, 11, 9.84);
INSERT INTO `company_financial_data` VALUES (256, 1, 2022, 11, 4.90);
INSERT INTO `company_financial_data` VALUES (257, 2, 2018, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (258, 2, 2019, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (259, 2, 2020, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (260, 2, 2021, 11, 4.70);
INSERT INTO `company_financial_data` VALUES (261, 2, 2022, 11, 3.00);
INSERT INTO `company_financial_data` VALUES (262, 2, 2023, 11, 4.48);
INSERT INTO `company_financial_data` VALUES (263, 3, 2018, 11, 2.17);
INSERT INTO `company_financial_data` VALUES (264, 3, 2019, 11, 1.68);
INSERT INTO `company_financial_data` VALUES (265, 3, 2020, 11, 8.22);
INSERT INTO `company_financial_data` VALUES (266, 3, 2021, 11, 6.67);
INSERT INTO `company_financial_data` VALUES (267, 3, 2022, 11, 4.52);
INSERT INTO `company_financial_data` VALUES (268, 4, 2019, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (269, 4, 2020, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (270, 4, 2021, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (271, 4, 2022, 11, 3.74);
INSERT INTO `company_financial_data` VALUES (272, 5, 2018, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (273, 5, 2019, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (274, 5, 2020, 11, -1.00);
INSERT INTO `company_financial_data` VALUES (275, 5, 2021, 11, 6.14);
INSERT INTO `company_financial_data` VALUES (276, 5, 2022, 11, 1.60);
INSERT INTO `company_financial_data` VALUES (277, 1, 2018, 12, 2.94);
INSERT INTO `company_financial_data` VALUES (278, 1, 2019, 12, 3.71);
INSERT INTO `company_financial_data` VALUES (279, 1, 2020, 12, 23.19);
INSERT INTO `company_financial_data` VALUES (280, 1, 2021, 12, 8.78);
INSERT INTO `company_financial_data` VALUES (281, 1, 2022, 12, 2.26);
INSERT INTO `company_financial_data` VALUES (282, 2, 2018, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (283, 2, 2019, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (284, 2, 2020, 12, 8.67);
INSERT INTO `company_financial_data` VALUES (285, 2, 2021, 12, 7.01);
INSERT INTO `company_financial_data` VALUES (286, 2, 2022, 12, 3.05);
INSERT INTO `company_financial_data` VALUES (287, 2, 2023, 12, 2.28);
INSERT INTO `company_financial_data` VALUES (288, 3, 2018, 12, 1.17);
INSERT INTO `company_financial_data` VALUES (289, 3, 2019, 12, 1.20);
INSERT INTO `company_financial_data` VALUES (290, 3, 2020, 12, 3.57);
INSERT INTO `company_financial_data` VALUES (291, 3, 2021, 12, 3.56);
INSERT INTO `company_financial_data` VALUES (292, 3, 2022, 12, 1.76);
INSERT INTO `company_financial_data` VALUES (293, 4, 2019, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (294, 4, 2020, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (295, 4, 2021, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (296, 4, 2022, 12, 2.28);
INSERT INTO `company_financial_data` VALUES (297, 5, 2018, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (298, 5, 2019, 12, -1.00);
INSERT INTO `company_financial_data` VALUES (299, 5, 2020, 12, 18.07);
INSERT INTO `company_financial_data` VALUES (300, 5, 2021, 12, 12.55);
INSERT INTO `company_financial_data` VALUES (301, 5, 2022, 12, 2.21);
INSERT INTO `company_financial_data` VALUES (302, 1, 2018, 13, 1.20);
INSERT INTO `company_financial_data` VALUES (303, 1, 2019, 13, 1.34);
INSERT INTO `company_financial_data` VALUES (304, 1, 2020, 13, 0.69);
INSERT INTO `company_financial_data` VALUES (305, 1, 2021, 13, 0.60);
INSERT INTO `company_financial_data` VALUES (306, 1, 2022, 13, 0.65);
INSERT INTO `company_financial_data` VALUES (307, 2, 2018, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (308, 2, 2019, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (309, 2, 2020, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (310, 2, 2021, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (311, 2, 2022, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (312, 2, 2023, 13, 0.00);
INSERT INTO `company_financial_data` VALUES (313, 3, 2018, 13, 0.35);
INSERT INTO `company_financial_data` VALUES (314, 3, 2019, 13, 0.60);
INSERT INTO `company_financial_data` VALUES (315, 3, 2020, 13, 0.03);
INSERT INTO `company_financial_data` VALUES (316, 3, 2021, 13, 0.07);
INSERT INTO `company_financial_data` VALUES (317, 3, 2022, 13, 0.06);
INSERT INTO `company_financial_data` VALUES (318, 4, 2019, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (319, 4, 2020, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (320, 4, 2021, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (321, 4, 2022, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (322, 5, 2018, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (323, 5, 2019, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (324, 5, 2020, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (325, 5, 2021, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (326, 5, 2022, 13, -1.00);
INSERT INTO `company_financial_data` VALUES (327, 1, 2018, 14, -195.12);
INSERT INTO `company_financial_data` VALUES (328, 1, 2019, 14, -145.86);
INSERT INTO `company_financial_data` VALUES (329, 1, 2020, 14, -34.51);
INSERT INTO `company_financial_data` VALUES (330, 1, 2021, 14, -29.26);
INSERT INTO `company_financial_data` VALUES (331, 1, 2022, 14, -29.55);
INSERT INTO `company_financial_data` VALUES (332, 2, 2018, 14, -1.00);
INSERT INTO `company_financial_data` VALUES (333, 2, 2019, 14, -857.53);
INSERT INTO `company_financial_data` VALUES (334, 2, 2020, 14, -1.60);
INSERT INTO `company_financial_data` VALUES (335, 2, 2021, 14, -1.19);
INSERT INTO `company_financial_data` VALUES (336, 2, 2022, 14, -4.44);
INSERT INTO `company_financial_data` VALUES (337, 2, 2023, 14, 9.45);
INSERT INTO `company_financial_data` VALUES (338, 3, 2018, 14, 2.14);
INSERT INTO `company_financial_data` VALUES (339, 3, 2019, 14, 1.26);
INSERT INTO `company_financial_data` VALUES (340, 3, 2020, 14, 2.70);
INSERT INTO `company_financial_data` VALUES (341, 3, 2021, 14, 1.41);
INSERT INTO `company_financial_data` VALUES (342, 3, 2022, 14, 3.92);
INSERT INTO `company_financial_data` VALUES (343, 4, 2019, 14, -770.44);
INSERT INTO `company_financial_data` VALUES (344, 4, 2020, 14, -174.26);
INSERT INTO `company_financial_data` VALUES (345, 4, 2021, 14, -90.86);
INSERT INTO `company_financial_data` VALUES (346, 4, 2022, 14, -41.25);
INSERT INTO `company_financial_data` VALUES (347, 5, 2018, 14, -14411.94);
INSERT INTO `company_financial_data` VALUES (348, 5, 2019, 14, -159.04);
INSERT INTO `company_financial_data` VALUES (349, 5, 2020, 14, -46.75);
INSERT INTO `company_financial_data` VALUES (350, 5, 2021, 14, -23.17);
INSERT INTO `company_financial_data` VALUES (351, 5, 2022, 14, -34.03);
INSERT INTO `company_financial_data` VALUES (352, 1, 2018, 15, -64.90);
INSERT INTO `company_financial_data` VALUES (353, 1, 2019, 15, -67.57);
INSERT INTO `company_financial_data` VALUES (354, 1, 2020, 15, -16.44);
INSERT INTO `company_financial_data` VALUES (355, 1, 2021, 15, -15.54);
INSERT INTO `company_financial_data` VALUES (356, 1, 2022, 15, -15.57);
INSERT INTO `company_financial_data` VALUES (357, 2, 2018, 15, -26.51);
INSERT INTO `company_financial_data` VALUES (358, 2, 2019, 15, -31.64);
INSERT INTO `company_financial_data` VALUES (359, 2, 2020, 15, -0.64);
INSERT INTO `company_financial_data` VALUES (360, 2, 2021, 15, -0.66);
INSERT INTO `company_financial_data` VALUES (361, 2, 2022, 15, -2.61);
INSERT INTO `company_financial_data` VALUES (362, 2, 2023, 15, 10.10);
INSERT INTO `company_financial_data` VALUES (363, 3, 2018, 15, 1.46);
INSERT INTO `company_financial_data` VALUES (364, 3, 2019, 15, 0.82);
INSERT INTO `company_financial_data` VALUES (365, 3, 2020, 15, 2.20);
INSERT INTO `company_financial_data` VALUES (366, 3, 2021, 15, 1.24);
INSERT INTO `company_financial_data` VALUES (367, 3, 2022, 15, 4.06);
INSERT INTO `company_financial_data` VALUES (368, 4, 2019, 15, -36.98);
INSERT INTO `company_financial_data` VALUES (369, 4, 2020, 15, -39.58);
INSERT INTO `company_financial_data` VALUES (370, 4, 2021, 15, -36.27);
INSERT INTO `company_financial_data` VALUES (371, 4, 2022, 15, -30.96);
INSERT INTO `company_financial_data` VALUES (372, 5, 2018, 15, -18.23);
INSERT INTO `company_financial_data` VALUES (373, 5, 2019, 15, -43.22);
INSERT INTO `company_financial_data` VALUES (374, 5, 2020, 15, -10.23);
INSERT INTO `company_financial_data` VALUES (375, 5, 2021, 15, -8.93);
INSERT INTO `company_financial_data` VALUES (376, 5, 2022, 15, -12.74);
INSERT INTO `company_financial_data` VALUES (377, 1, 2018, 16, -165.40);
INSERT INTO `company_financial_data` VALUES (378, 1, 2019, 16, -102.69);
INSERT INTO `company_financial_data` VALUES (379, 1, 2020, 16, -38.95);
INSERT INTO `company_financial_data` VALUES (380, 1, 2021, 16, -22.04);
INSERT INTO `company_financial_data` VALUES (381, 1, 2022, 16, -40.12);
INSERT INTO `company_financial_data` VALUES (382, 2, 2018, 16, -31.13);
INSERT INTO `company_financial_data` VALUES (383, 2, 2019, 16, -32.34);
INSERT INTO `company_financial_data` VALUES (384, 2, 2020, 16, -9.28);
INSERT INTO `company_financial_data` VALUES (385, 2, 2021, 16, -27.38);
INSERT INTO `company_financial_data` VALUES (386, 2, 2022, 16, -21.13);
INSERT INTO `company_financial_data` VALUES (387, 2, 2023, 16, 27.77);
INSERT INTO `company_financial_data` VALUES (388, 3, 2018, 16, 4.82);
INSERT INTO `company_financial_data` VALUES (389, 3, 2019, 16, 4.14);
INSERT INTO `company_financial_data` VALUES (390, 3, 2020, 16, 8.50);
INSERT INTO `company_financial_data` VALUES (391, 3, 2021, 16, 4.52);
INSERT INTO `company_financial_data` VALUES (392, 3, 2022, 16, 7.85);
INSERT INTO `company_financial_data` VALUES (393, 4, 2019, 16, -40.05);
INSERT INTO `company_financial_data` VALUES (394, 4, 2020, 16, -36.06);
INSERT INTO `company_financial_data` VALUES (395, 4, 2021, 16, -73.97);
INSERT INTO `company_financial_data` VALUES (396, 4, 2022, 16, -79.46);
INSERT INTO `company_financial_data` VALUES (397, 5, 2018, 16, -64.41);
INSERT INTO `company_financial_data` VALUES (398, 5, 2019, 16, -94.33);
INSERT INTO `company_financial_data` VALUES (399, 5, 2020, 16, -75.50);
INSERT INTO `company_financial_data` VALUES (400, 5, 2021, 16, -51.60);
INSERT INTO `company_financial_data` VALUES (401, 5, 2022, 16, -31.49);

-- ----------------------------
-- Table structure for financial_indicators
-- ----------------------------
DROP TABLE IF EXISTS `financial_indicators`;
CREATE TABLE `financial_indicators`  (
  `Indicator_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Indicator_Name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`Indicator_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of financial_indicators
-- ----------------------------
INSERT INTO `financial_indicators` VALUES (1, '流动比率');
INSERT INTO `financial_indicators` VALUES (2, '速动比率');
INSERT INTO `financial_indicators` VALUES (3, '资产负债率');
INSERT INTO `financial_indicators` VALUES (4, '总资产周转率');
INSERT INTO `financial_indicators` VALUES (5, '存货周转率');
INSERT INTO `financial_indicators` VALUES (6, '应收账款周转率');
INSERT INTO `financial_indicators` VALUES (7, '销售增长率');
INSERT INTO `financial_indicators` VALUES (8, '净利润增长率');
INSERT INTO `financial_indicators` VALUES (9, '总资产增长率');
INSERT INTO `financial_indicators` VALUES (10, '市盈率');
INSERT INTO `financial_indicators` VALUES (11, '市净率');
INSERT INTO `financial_indicators` VALUES (12, '市销率');
INSERT INTO `financial_indicators` VALUES (13, '股息率');
INSERT INTO `financial_indicators` VALUES (14, '净利率');
INSERT INTO `financial_indicators` VALUES (15, '净资产收益率');
INSERT INTO `financial_indicators` VALUES (16, '总资产报酬率');

SET FOREIGN_KEY_CHECKS = 1;
