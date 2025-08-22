package Kafka_core_producer.scheduler;

import Kafka_core_producer.producer.GeneralLedgerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GeneralLedgerScheduler {

    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Autowired
    private GeneralLedgerProducer producer;

    @Scheduled(fixedRate = 1000)
    public void schedule(){
        producer.sendGeneralLedgerMessage("Message" + COUNTER.getAndIncrement());
    }
}
