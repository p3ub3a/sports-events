mvn clean install -DskipTests
docker build . -f src/main/docker/Dockerfile -t sportsevents-springboot:1.0.0-SNAPSHOT