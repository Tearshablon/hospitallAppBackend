--liquibase formatted sql logicalFilePath:V1_002_SCHEMA_ADMIN_DASHBOARD.sql
--changeset DShablov:2.1 runOnChange:true context:prod

CREATE SCHEMA `hospitallapp` DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci;