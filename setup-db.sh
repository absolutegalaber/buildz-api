#!/usr/bin/env bash
docker stop pikilia_db | true
docker rm pikilia_db | true
docker run --name pikilia_db -d -p 3306:3306 -e MYSQL_DATABASE=buildz -e MYSQL_USER=buildz -e MYSQL_PASSWORD=buildz -e MYSQL_ROOT_PASSWORD=buildz mariadb:10.4
