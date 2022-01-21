mvn clean install
docker build . -f src/main/docker/Dockerfile.jvm -t sportsevents-quarkus-jvm:1.0.0-SNAPSHOT