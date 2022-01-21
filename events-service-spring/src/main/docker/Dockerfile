FROM alpine

RUN apk add openjdk11-jre

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
COPY target/*-spring.jar /deployments/app.jar

EXPOSE 8089
USER 1001

ENTRYPOINT [ "java","-jar","/deployments/app.jar" ]
