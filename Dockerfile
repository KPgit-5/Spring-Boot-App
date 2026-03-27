# 🔥 Lightweight base image (JRE only, not full JDK)
FROM eclipse-temurin:17-jre-jammy

# App directory
WORKDIR /app

# Copy jar
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Port
EXPOSE 8070

# Run app (optimized JVM flags)
ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]
