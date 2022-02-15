echo 'Starting services...'
docker-compose -f ../../docker-compose.yml up -d

echo 'Starting Quarkus native...'
docker-compose -f ../../events-service/docker-compose-native.yml up -d