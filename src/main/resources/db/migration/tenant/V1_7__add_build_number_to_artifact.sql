ALTER TABLE `artifact`
    -- allow null since, when build_number is null,
    -- the latest build of the project and branch is
    -- assumed.
    ADD COLUMN `build_number` BIGINT(20);
