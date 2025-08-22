package kafka.core.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//@Service
public class EmployeeJsonConsumer {

    @Autowired
    private ObjectMapper objectMApper;

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

    @KafkaListener(topics = "t-employee-2", groupId = "employee-group")
    public void consume(String message) {
        try {
            Employee employee = objectMApper.readValue(message, Employee.class);
            LOG.info("Employee is {}", employee);
        } catch (Exception e) {
            LOG.error("Error parsing employee", e);
        }
    }

}
