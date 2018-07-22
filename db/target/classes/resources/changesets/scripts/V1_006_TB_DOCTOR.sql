--liquibase formatted sql logicalFilePath:V1_006_TB_DOCTOR.sql
--changeset DShablov:6.1 runOnChange:true context:prod

CREATE TABLE `hospitallapp`.`doctor` (
  id             INT(11) PRIMARY KEY     NOT NULL AUTO_INCREMENT,
  first_name     VARCHAR(255)     NOT NULL,
  last_name      VARCHAR(255)     NOT NULL,
  photo          VARCHAR(255),
  department_id  INT(11) NOT NULL,
  deleted        TINYINT(4) DEFAULT '0'  NOT NULL,
  create_date    DATETIME  DEFAULT CURRENT_TIMESTAMP,
  modify_date    DATETIME ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT `fk_doctor_department` FOREIGN KEY (department_id) REFERENCES `hospitallapp`.`department` (id)
		ON DELETE CASCADE
		ON UPDATE RESTRICT
);