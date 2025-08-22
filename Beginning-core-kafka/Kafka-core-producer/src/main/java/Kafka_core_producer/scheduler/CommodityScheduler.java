package Kafka_core_producer.scheduler;

import Kafka_core_producer.entity.Commodity;
import Kafka_core_producer.producer.CommodityProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

//@Component
public class CommodityScheduler {

    private static final String COMMODITY_API_URL = "http://localhost:8080/api/commodities/v1/all";

    @Autowired
    private CommodityProducer commodityProducer;

    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 5000)
    public void fetchAndSendCommodities(){
        Commodity[] commodities = restTemplate.getForObject(COMMODITY_API_URL, Commodity[].class);
        if(commodities != null){
            Arrays.stream(commodities)
                    .forEach(commodityProducer::sendMessage);
        }
    }


}
