FROM azul/zulu-openjdk-alpine:11.0.7
ADD target/buildz-api-0.9.1.jar /opt/buildz-api.jar

ENV DB_URL="jdbc:mysql://buildz_db:3306/buildz"ENV DB_USR="buildz"
ENV DB_PWD="buildz"

EXPOSE 8080
CMD ["java", "-jar", "/opt/buildz-api.jar", "--spring.datasource.url=${DB_URL}", "--spring.datasource.username=${DB_USR}", "--spring.datasource.password=${DB_PWD}"]