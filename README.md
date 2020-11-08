# buildz-api

[![Build Status](https://travis-ci.com/absolutegalaber/buildz-api.svg?branch=main)](https://travis-ci.com/absolutegalaber/buildz-api)
[![codecov](https://codecov.io/gh/absolutegalaber/buildz-api/branch/main/graph/badge.svg?token=FWDNVM1EPA)](https://codecov.io/gh/absolutegalaber/buildz-api)

## Build

```shell script
./setup-db.sh
mvn clean install -Dspring.profiles.active=test
mvn clean clover:setup test clover:aggregate clover:clover -Dspring.profiles.active=test
```

## Run as Docker service

```shell script
export BUILDZ_VERSION=0.0.1
docker network create buildz_net
docker run --name buildz_db --network buildz_net -d -p 3306:3306 -e MYSQL_DATABASE=buildz -e MYSQL_USER=buildz -e MYSQL_PASSWORD=buildz -e MYSQL_ROOT_PASSWORD=buildz mariadb:10.5
docker pull absolutegalaber-docker-buildz-docker.bintray.io/absolutegalaber/buildz-api:${BUILDZ_VERSION}
docker run  --name buildz-api --network buildz_net -d absolutegalaber-docker-buildz-docker.bintray.io/absolutegalaber/buildz-api:${BUILDZ_VERSION} 
```
