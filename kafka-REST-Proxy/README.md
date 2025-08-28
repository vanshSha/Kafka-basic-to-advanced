# Project Kafka-Rest-Proxy
               
     This Spring boot project send and consume record by HTTP request instead of Kafka Client.        
   

   # Tech Stack

        - Java 17
        - Spring Boot 3.5.5
        - Maven
        - Docker Compose 
            - zookeeper
            - kafka 
            - rest-proxy   

   # Work Flow of Kafka-Rest-Proxy  

             Get Cluster   
                 |
                 v
             Add Cluster Id in Variable
                 |
                 v
             Create Topic
                 |
                 v
             Produce Message
                 |
                 v
             Create Consumer Group
                 |
                 v
             Subscribe to Topic
                 |
                 v
             Consume Message   

    # How to run 

      -  mvn spring-boot:run
      -  docker compose up -d



    How to use Work Flow of Kafka-Rest-Proxy  

    - Step 1 
       
        Get Cluster Id
        GET - http://localhost:8082/v3/clusters -> It give cluster id

        8082 is a Kafka-Rest-Proxy Port

    - Step 2

        Create Topic through Post Man 
        POST - http://localhost:8082/v3/clusters/Ke7eIzu0SyqzyrvR38Ig1A/topics 

        with JSON data 

        {
        "topic_name":  "post-man",  --- this id my topic name 
        "partitions_count": 1,
        "replication_factor": 1,
        "configs": []
        }

     - Step 3 Produce Message

        Post - http://localhost:8082/topics/post-man
        { i can change the json data according to me 
      "records": [
      {
      "value": {
        "id": 1,
        "first_name": "Vansh",
        "last_name": "Sharma"
      }
      }
      ]
      }

      // Headers 
    Content-Type  - application/vnd.kafka.json.v2+json  
    
    - Step 4 Create Consumer GroupContent-Type

       POST- http://localhost:8082/consumers/my-group
       {
    "name": "my-consumer-1", - here i set my consumer name
    "format": "json",
    "auto.offset.reset": "earliest"
     }

     Headers
     Content-Type -  application/vnd.kafka.json.v2+json

    When i hit this provide "base_uri"

    Step - 5  Subscribe-topic

     POST  "base_uri"/subscription
     EX - http://localhost:8082/consumers/my-group/instances/my-consumer-1/subscription this was my previous base_uri

     {
    "topics":["post-man"] -> this can change according to my topic
     }

     Headers
     Content-Type -  application/vnd.kafka.json.v2+json

     Step - 6 Consume Message

      GET - "base_uri"/records
      EX -  http://localhost:8082/consumers/my-group/instances/my-consumer-1/records

      Headers
      Accept  - application/vnd.kafka.json.v2+json

     
          





                 
                           