# buildz-api

[![Build Status](https://travis-ci.com/absolutegalaber/buildz-api.svg?branch=main)](https://travis-ci.com/absolutegalaber/buildz-api)

## Build

```shell script
./setup-db.sh
mvn clean install -Dspring.profiles.active=test
mvn clean clover:setup test clover:aggregate clover:clover -Dspring.profiles.active=test
```


