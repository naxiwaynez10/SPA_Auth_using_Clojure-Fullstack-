FROM openjdk:8-alpine

COPY target/uberjar/bigopost.jar /bigopost/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/bigopost/app.jar"]
