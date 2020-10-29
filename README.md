# buildz-api

[![Build Status](https://travis-ci.com/absolutegalaber/buildz-api.svg?branch=main)](https://travis-ci.com/absolutegalaber/buildz-api)
[![codecov](https://codecov.io/gh/absolutegalaber/buildz-api/branch/main/graph/badge.svg?token=FWDNVM1EPA)](undefined)

## Build

```shell script
./setup-db.sh
mvn clean install -Dspring.profiles.active=test
mvn clean clover:setup test clover:aggregate clover:clover -Dspring.profiles.active=test
```


