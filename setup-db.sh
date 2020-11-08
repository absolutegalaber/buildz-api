#!/usr/bin/env bash
docker stop buildz_db | true
docker rm buildz_db | true
docker run --name buildz_db --network=buildz_net -d -p 3306:3306 -e MYSQL_DATABASE=buildz -e MYSQL_USER=buildz -e MYSQL_PASSWORD=buildz -e MYSQL_ROOT_PASSWORD=buildz mariadb:10.5
