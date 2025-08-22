package Kafka_core_producer.producer;

import Kafka_core_producer.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
public class EmployeeJsonProducer2 {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Employee employee) {
        try {
            String json = objectMapper.writeValueAsString(employee);
            kafkaTemplate.send("t-employee-2", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
