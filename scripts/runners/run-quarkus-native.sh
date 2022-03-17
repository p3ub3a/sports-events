echo 'Starting services...'
docker-compose -f ../../docker-compose.yml up -d

sleep 7s 

echo 'Starting Quarkus native...'
docker-compose -f ../../events-service/docker-compose-native.yml up --remove-orphans -d