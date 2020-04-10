FROM openjdk:8-jdk-alpine
LABEL author="Sakki"
LABEL org="Sakki Technologies"
RUN mkdir D:\tmp\app
RUN mkdir D:\tmp\app\config
RUN mkdir D:\tmp\app\log
VOLUME D:\tmp\app\
ARG JAR_FILE=target/*.jar
ARG CONFIG_FILE=config/application-local.properties
COPY ${JAR_FILE} D:/tmp/app/app.jar
COPY ${CONFIG_FILE} D:/tmp/app/config/application-local.properties
ENV SPRING_PROFILES_ACTIVE=local
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=local","-jar","D:/tmp/app/app.jar"]