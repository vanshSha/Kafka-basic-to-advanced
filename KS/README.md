# About
    
    I am using 2 Spring boot project Both are connect with each other

    - kafka_ms_order   :  Demonstrates Kafka, microservices, and database operations.
    - Kafka_ms_streams :  Demonstrates Kafka Streams processing pipelines.
   

# kafka_ms_order

        # Tech Stack

            - Java 17
            - Spring Boot 3.4.7


         # Dependency 
             **Spring-web**
             **JPA**
             **H2database**
             **commons-lang3** -> Apache library that provides extra utilities for Java.It extends the core Java library (java.lang) with useful helper methods. when i want utility methods for strings, objects, arrays, or numbers.
             **Lombok**


        # How to run 

          mvn spring-boot:run


        # Microservice Description

         - API
            : request - Data sent by a client to perform an action or retrieve information 
            : server - Handles incoming requests and returns responses

         - Brocker 
             : consumer - Sends messages to Kafka topics
             : message  – Reads messages from Kafka topics 
             : producer - Data exchanged between producer and consumer

         - Command
             : action - A specific operation executed in response to a request
             : service - Contains business logic for actions

         - Config  
              - Stores application configuration and settings.

         - Entity
              - Represents a database table

         - Repository
              -  Provides data access methods


# Kafka_ms_streams

    # Tech Stack
    
      - Java 17
      - Spring Boot 3.4.7
      - Docker Compose
            - zookeeper
            - kafka


    # Dependency 
        **kafka-stream** 
        **Core-Kafka** 
        **lombok** 
        **jackson-databind** 
        **jackson-core** 
        **jackson-annotations** 
        **jackson-datatype-jsr310** 

# How to run 

    - First execute docker compose up -d

# Microservice Description

      - message : Models for Kafka communication.  
      - stream  : Stream Processing Pipelines.
      - config  : I am configuring(JSON, KStreamconfig)
      - util    : Helper methods for data masking, filtering, and validation. 
        
    
      
     This is diagram of my Project 

     kafka_ms_order (Producer/API)
            |
            v
     Kafka Topic(s)
            |
            v
     Kafka_ms_streams (Consumer/Streams)
            |
            v
     Aggregated / Processed Data




            



            

