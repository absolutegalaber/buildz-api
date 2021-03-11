DELIMITER $$

CREATE PROCEDURE createServerBasedEnvs()
BEGIN
    DECLARE done INT default FALSE;
    DECLARE server INT;
    DECLARE curServers CURSOR FOR SELECT DISTINCT server_id FROM deploy;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN curServers;

    read_loop: LOOP
        FETCH curServers INTO server;

        IF done THEN
            LEAVE read_loop;
        END IF;

        INSERT INTO environment (name, internal)
        SELECT CONCAT(name, ' Environment'), TRUE
        FROM server
        WHERE id = server;

        SET @env_id = (SELECT LAST_INSERT_ID());

        INSERT INTO artifact (branch, project, environment_id)
        SELECT b.branch, b.project, @env_id
        FROM build b
            INNER JOIN deploy d
                ON d.build_id = b.id
        WHERE server_id = 1
        ORDER BY deployed_at DESC
        LIMIT 1;
    END LOOP;

    CLOSE curServers;

END $$

DELIMITER ;

CALL createServerBasedEnvs();

DROP PROCEDURE createServerBasedEnvs;
