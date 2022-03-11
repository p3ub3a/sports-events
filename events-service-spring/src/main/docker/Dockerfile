FROM alpine:3.15

RUN apk add openjdk11-jre

COPY target/*-spring*.jar /deployments/app.jar

EXPOSE 8089
USER 1001

ENTRYPOINT [ "java","-Djava.security.egd=file:/dev/./urandom","-Djdk.serialSetFilterAfterRead=true","-jar","/deployments/app.jar"]
