# Project Name : Beginning-core-kafka

In this Springboot project . 

 -> I learned the basics of kafka(Producer and Consumer). 
 -> I implemented the concept of errorHandler, 
                                 scheduler, 
                                 CacheConfig, 
                                 JSONConfig and KafkaConfig,
                                 Cache,
                                 Custom Partition in INVOICE CLASS 
                                


# Tech Stack 

    ->  JAVA 17
    ->  Spring Boot 3.5.5
    ->  Kafka + Zookeeper ( I am using confluentic images with same version)



 # How to Run    

    first i execute docker compose up -d



# API ENDPOINT

  METHOD    ------->         ENDPOINT                    ------->          DESCRIPTION
   
   GET       ------>           api/commodities/v1/all    ------->             get commodity data


# DEPENDENCIES

    **Spring Boot Starter Web** → REST APIs (when i use web then don't need of 1 to 4 dependencies)
    *1*jackson-datatype-jsr310** -> Adds support for Java 8+ date/time types 
    *2*jackson-databind** -> ObjectMapper (the main class for converting Java objects ↔ JSON).
    *3*jackson-core** -> Core streaming API for reading/writing JSON.
    *4*jackson-annotations** -> Annotations like @JsonIgnore, @JsonProperty to control JSON mapping.

    **Lombok** → Reduces boilerplate code 
    **spring-kafka** -> Kafka producer and consumer integration
    **com.github.ben-manes.caffeine/caffeine** -> High-performance in-memory caching library.

    



# About 
  This is a Spring Boot project that demonstrates producing and consuming data through Kafka.
