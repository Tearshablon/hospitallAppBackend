--liquibase formatted sql logicalFilePath:V1_003_TB_CONFIG.sql
--changeset DShablov:3.1 runOnChange:true context:prod
CREATE TABLE `hospitallapp`.`config` (
  `property_name` VARCHAR(200) NOT NULL,
  `property_value` VARCHAR(400) NOT NULL,
  PRIMARY KEY (`property_name`));