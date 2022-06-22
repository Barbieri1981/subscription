# Subscription microservice ecosystem  


#### Microservice: Subscription Service
##### Description
Microservice with subscription business endpoint.    
- Create subscription Endpoint   
- Retrieve Subscription by id Endpoint   
- Retrieves Subscriptions Endpoint    
##### Frameworks used
- Spring Boot   
- Spring Cloud
- Eureka Client   
- Lombok   
- Feign   
- JPA
- Swagger UI

#### Microservice: Email Service
##### Description
Microservice with send email endpoints
- Send email Endpoint
##### Frameworks used
- Spring Boot
- Spring Cloud
- Eureka Client
- Lombok
- Feign
- Swagger UI

#### Microservice: Eureka Service
##### Description
Eureka server
##### Frameworks used
- Eureka Server

## Run microservice ecosystem
```docker-compose up```

## Stops containers and removes containers, networks, volumes, and images
```docker-compose down```

## Clean environment
docker system prune   
docker system prune -a
docker volume prune
docker container stop container_name
docker rm -v container_name   

## Verify clean environment
docker images -a
docker ps -a
docker container ls

##  Swagger UI URL :: Public Service
http://localhost:9000/swagger-ui.html   

##  Eureka Server
http://localhost:8070/