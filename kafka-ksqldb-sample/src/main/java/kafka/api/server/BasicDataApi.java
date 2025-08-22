package kafka.api.server;

import kafka.api.request.*;
import kafka.command.service.BasicDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basic-data")
public class BasicDataApi {

    @Autowired
    private BasicDataService basicDataService;

    @PostMapping(value = "/one", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataOneRequest> createBasicDataOne(@RequestBody BasicDataOneRequest request) {
        basicDataService.createBasicDataOne(request);

        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/two", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataTwoRequest> createBasicDataTwo(@RequestBody BasicDataTwoRequest request) {
        basicDataService.createBasicDataTwo(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/three", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataThreeRequest> createBasicDataThree(@RequestBody BasicDataThreeRequest request) {
        basicDataService.createBasicDataThree(request);

        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/four", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataFourRequest> createBasicDataFour(@RequestBody BasicDataFourRequest request) {
        basicDataService.createBasicDataFour(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/five", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataFiveRequest> createBasicDataFive(@RequestBody BasicDataFiveRequest request) {
        basicDataService.createBasicDataFive(request);

        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataPersonRequest> createBasicDataPerson(@RequestBody BasicDataPersonRequest request) {
        basicDataService.createBasicDataPerson(request);

        return ResponseEntity.ok(request);
    }

    @PostMapping(value = "/country", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicDataCountryRequest> createBasicDataCountry(
            @RequestBody BasicDataCountryRequest request) {
        basicDataService.createBasicDataCountry(request);

        return ResponseEntity.ok(request);
    }
}
