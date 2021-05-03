ALTER TABLE `server`
    ADD COLUMN `reserved_by`       varchar(255),
    ADD COLUMN `reservation_note`  varchar(255);