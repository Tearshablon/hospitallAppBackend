--liquibase formatted sql logicalFilePath:V1_004_TB_CONFIG_DATA.sql
--changeset DShablov:4.1 runOnChange:true context:prod
INSERT INTO `hospitallapp`.`config` (`property_name`, `property_value`) VALUES ('hospitallapp.testpropetry', 45000);
