# Public Service

## Run Public Service using Docker
docker build -t public-service .      
docker images   
docker run --name public-service -p 9000:9000 public-service:latest   

## Docker commands
### clean
docker system prune      
docker system prune -a   
docker volume prune     
docker rm -v container_name   
### verify clean environment
docker images -a   
docker ps -a   
docker container ls   

## Swagger UI URL
http://localhost:9000/swagger-ui.html
