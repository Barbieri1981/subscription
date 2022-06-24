# Subscription Service   

## Run Subscription Service using Docker
docker build -t subscription-service .   
docker images   
docker run --name subscription-service -p 8080:8080 subscription-service:latest

## Docker commands
### clean
docker system prune      
docker system prune -a   
docker volume prune   
docker container stop container_name   
docker rm -v container_name   
### verify clean environment
docker images -a   
docker ps -a   
docker container ls   

## Swagger UI URL
http://localhost:8080/swagger-ui.html
