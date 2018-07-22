--liquibase formatted sql logicalFilePath:V1_005_TB_DEPARTMENT.sql
--changeset DShablov:5.1 runOnChange:true context:prod

CREATE TABLE `hospitallapp`.`department` (
  id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR (255) NOT NULL,
  description VARCHAR (255),
  deleted        TINYINT(4) DEFAULT '0'  NOT NULL,
  create_date    DATETIME  DEFAULT CURRENT_TIMESTAMP,
  modify_date    DATETIME ON UPDATE CURRENT_TIMESTAMP
);