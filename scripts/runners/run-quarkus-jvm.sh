echo 'Starting services...'
docker-compose -f ../../docker-compose.yml up -d

sleep 7s 

echo 'Starting Quarkus JVM...'
docker-compose -f ../../events-service/docker-compose.yml up --remove-orphans -d