# Subscription microservice ecosystem  


#### Microservice: Public Service
##### Description
Is an orchestrator that interacts with the Subscription service consuming the exposed endpoints.
- Create subscription
- Retrieve Subscription by id
- Retrieves Subscriptions
##### Frameworks used
- Spring Boot
- Spring Cloud
- Eureka Client
- Lombok
- Feign
- Swagger UI


#### Microservice: Subscription Service
##### Description
Microservice with subscription business endpoint.    
- Create subscription   
- Retrieve Subscription by id (only user with ADMIN role can execute this endpoint)
 ``@PreAuthorize(hasRole(ADMIN))``
- Retrieves Subscriptions (only user with ADMIN role can execute this endpoint)   
  ``@PreAuthorize(hasRole(ADMIN))``
- Cancel Subscription by id 
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
The goal of this microservice is sends emails
- Send email
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

#### Microservice: Security Server
##### Description
Security Server
##### Frameworks used
- Spring Boot
- Spring Cloud
- Eureka Client
- Lombok



### Resilient Subscription-Service using Resilience4j patterns

- Rate Limiter pattern for retrieving subscriptions **"/subscriptions"** API inside **subscription-service** microservice


## Run microservice ecosystem
```docker-compose up```

## Stops containers and removes containers, networks, volumes, and images
```docker-compose down```

## Clean environment
```docker system prune```      
```docker system prune -a```   
```docker volume prune```   
```docker container stop container_name```   
```docker rm -v container_name```   

## Verify clean environment
```docker images -a```   
```docker ps -a```   
```docker container ls```   

##  Swagger UI URL :: Public Service
http://localhost:9000/swagger-ui.html   

##  Eureka Server
http://localhost:8070/