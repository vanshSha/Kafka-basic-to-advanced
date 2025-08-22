package Kafka_core_producer.api;

import Kafka_core_producer.entity.Commodity;
import Kafka_core_producer.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/commodities/v1")
public class CommodityApi {

    @Autowired
    private CommodityService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Commodity> generateAllCommodities(){
        return service.generateDummyCommodities();
    }
}
