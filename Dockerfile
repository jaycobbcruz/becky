FROM openjdk:8
RUN mkdir -p /usr/local/beckybot
COPY src/main/resources/beckybot/ /usr/local/beckybot/
COPY target/becky-1.0.jar /
CMD ["java", "-jar", "becky-1.0.jar"]
EXPOSE 80