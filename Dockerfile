FROM openjdk:8
COPY target/becky-1.0.jar /
CMD ["java", "-jar", "becky-1.0.jar"]
EXPOSE 80