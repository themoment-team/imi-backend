FROM openjdk:21

WORKDIR /deploy

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} imi-backend.jar

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "imi-backend.jar"]