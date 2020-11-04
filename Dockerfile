FROM azul/zulu-openjdk-alpine:11.0.7
ADD target/buildz-api-0.0.1-SNAPSHOT.jar /opt/buildz-api.jar

ENV DB_URL="jdbc:mysql://localhost:3306/buildz"
ENV DB_USR="buildz"
ENV DB_PWD="wrong"


CMD ["java", "-jar", "/opt/buildz-api.jar", "--spring.datasource.url=${DB_URL}", "--spring.datasource.username=${DB_USR}", "--spring.datasource.password=${DB_PWD}"]