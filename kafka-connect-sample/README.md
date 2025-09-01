
# Kafka-Connect Project

     This is my Spring Boot Kafka-Connect project that demonstrate move data between Kafka and other systems like databases, Elasticsearch, file systems, cloud storage, etc.


    # Tech Stack

      - Java 17
      - Maven 3.5.5
      - Docker Compose File 
           - Kafka
           - Zookeeper
           - Kafka-Connect
           - PostGres(Relational DataBase)
           - Schema Registry(Schema Registry is used to manage schema types such as Avro, JSON Schema, and Protobuf)
           - SFTP(Secure File Transfer Protocol) is a secure way to transfer files between systems using SSH.
                                                 Ex: moving .csv, .json, or .avro files from one server to another.

          - Connectors
              - jdbc
              - sftp
              - spooldir
              - postgresql
              - elasticsearch
              - shell source


    # How to Run
        
         ./mvnw spring-boot:run. -> Spring Boot

          docker compose file  -> Docker 

         

     # How to use Kafka-connect

    1-   GET    
         http://localhost:8083/connector-plugins -> Fist check how many connectors I have.

    2-      
        
