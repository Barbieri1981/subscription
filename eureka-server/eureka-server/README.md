# Eureka Service   

## Run Eureka Service using Docker
docker build -t eureka-service .   
docker images   
docker run --name eureka-service -p 8070:8070 eureka-service:latest

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

##Eureka Server URL
http://localhost:8070   
