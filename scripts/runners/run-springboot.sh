echo 'Starting services...'
docker-compose -f ../../docker-compose.yml up -d

echo 'Starting Spring Boot...'
docker-compose -f ../../events-service-spring/docker-compose.yml up --remove-orphans -d