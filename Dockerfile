FROM openjdk:11

COPY target/demo-app.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
