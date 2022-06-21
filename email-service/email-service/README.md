# Email Service

## Run Email Service using Docker
docker build -t email-service .   
docker images   
docker run --name email-service -p 9090:9090 email-service:latest

##Docker commands
###clean
docker system prune   
docker system prune -a
docker volume prune  
docker rm -v container_name
###verify clean environment
docker images -a
docker ps -a
docker container ls

##Swagger UI URL  
http://localhost:9090/swagger-ui.html
