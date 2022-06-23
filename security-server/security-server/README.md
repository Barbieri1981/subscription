# Security Service   

## Run Security Service using Docker
docker build -t security-service .   
docker images   
docker run --name security-service -p 9095:9095 security-service:latest

##Docker commands
###clean
docker system prune   
docker system prune -a
docker volume prune
docker container stop container_name
docker rm -v container_name
###verify clean environment
docker images -a
docker ps -a
docker container ls

##Swagger UI URL
http://localhost:9095/swagger-ui.html
