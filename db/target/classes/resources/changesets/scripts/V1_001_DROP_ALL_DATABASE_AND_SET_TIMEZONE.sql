--liquibase formatted sql logicalFilePath:V1_001_DROP_ALL_DATABASE_AND_SET_TIMEZONE.sql
--changeset DShablov:1.1 runOnChange:true context:prod

DROP DATABASE IF EXISTS `hospitallapp`;
SET TIME_ZONE = '+00:00';

