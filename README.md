# buildz-api

## Build
```shell script
mvn clean install -Dspring.profiles.active=test
mvn clean clover:setup test clover:aggregate clover:clover -Dspring.profiles.active=test
```


