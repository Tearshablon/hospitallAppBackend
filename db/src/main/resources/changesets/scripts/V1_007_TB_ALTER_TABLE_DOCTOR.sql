--liquibase formatted sql logicalFilePath:V1_007_TB_ALTER_TABLE_DOCTOR.sql
--changeset DShablov:6.1 runOnChange:true context:prod

ALTER TABLE `hospitallapp`.`doctor` MODIFY department_id INT(11);

CREATE INDEX index_department_id_deleted ON `hospitallapp`.`doctor` (department_id, deleted);