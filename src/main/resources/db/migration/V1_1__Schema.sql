# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.23)
# Datenbank: buildz
# Erstellt am: 2016-05-20 08:44:39 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

# Export von Tabelle artifact
# ------------------------------------------------------------

DROP TABLE IF EXISTS `artifact`;

CREATE TABLE `artifact` (
  `id`             BIGINT(20) NOT NULL AUTO_INCREMENT,
  `branch`         VARCHAR(255)        DEFAULT NULL,
  `project`        VARCHAR(255)        DEFAULT NULL,
  `environment_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ENVIRONMENT` (`environment_id`),
  CONSTRAINT `FK_ENVIRONMENT` FOREIGN KEY (`environment_id`) REFERENCES `environment` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Export von Tabelle artifact_labels
# ------------------------------------------------------------

DROP TABLE IF EXISTS `artifact_labels`;

CREATE TABLE `artifact_labels` (
  `artifact_id` BIGINT(20)   NOT NULL,
  `labels`      VARCHAR(255)          DEFAULT NULL,
  `labels_key`  VARCHAR(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`artifact_id`, `labels_key`),
  CONSTRAINT `FK_ARTIFACT` FOREIGN KEY (`artifact_id`) REFERENCES `artifact` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Export von Tabelle build
# ------------------------------------------------------------

DROP TABLE IF EXISTS `build`;

CREATE TABLE `build` (
  `id`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch`       VARCHAR(255) NOT NULL,
  `build_number` BIGINT(20)   NOT NULL,
  `project`      VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_BUILD` (`project`, `branch`, `build_number`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Export von Tabelle build_count
# ------------------------------------------------------------

DROP TABLE IF EXISTS `build_count`;

CREATE TABLE `build_count` (
  `id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch`  VARCHAR(255) NOT NULL,
  `counter` BIGINT(20)   NOT NULL,
  `project` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_PROJECT_BRANCH` (`project`, `branch`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Export von Tabelle build_label
# ------------------------------------------------------------

DROP TABLE IF EXISTS `build_label`;

CREATE TABLE `build_label` (
  `id`          BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `label_key`   VARCHAR(255)  NOT NULL,
  `label_value` VARCHAR(2000) NOT NULL,
  `build_id`    BIGINT(20)    NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_BUILD` (`build_id`),
  CONSTRAINT `FK_BUILD` FOREIGN KEY (`build_id`) REFERENCES `build` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# Export von Tabelle environment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `environment`;

CREATE TABLE `environment` (
  `id`   BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_ENVIRONMENT_NAME` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
