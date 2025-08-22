package Kafka_core_producer;

import Kafka_core_producer.entity.*;
import Kafka_core_producer.producer.*;
import Kafka_core_producer.scheduler.CarLocationScheduler;
import Kafka_core_producer.service.ImageService;
import Kafka_core_producer.service.InvoiceService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
//@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCoreProducerApplication.class, args);
    }

    /* This is code related to CounterProducer
    @Autowired
    private CounterProducer counterProducer;

    @Override
    public void run(String... args) throws Exception {
        counterProducer.sendMessage(100);
    }

     */

/* This is code related to EmployeeJsonProducer,2
    @Autowired
    private EmployeeJsonProducer2 employeeJsonProducer2;

    @Override
    public void run(String... args) throws Exception {
        for (int a = 1; a < 5; a++) {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Employee" + a,
                    LocalDate.now().minusYears(20 + a));
            employeeJsonProducer2.sendMessage(employee);
        }
    }

 */

//    @Override
//    public void run(String... args) throws Exception {
//
//    }


    /*This is the code for sending messages with keys to a Kafka topic
    @Autowired
    private KafkaKeyProducer kafkakeyProducer;

    @Override
    public void run(String... args) throws Exception {
        for(int a = 1; a<= 30_000; a++){
            String key = "Key-" + a;
            String message = "Message " + a;
            kafkakeyProducer.sendMessage(key, message);
         //   TimeUnit.SECONDS.sleep(1);
        }
    }

     */


    //  System.out.println("Sent message: Hello " + randomName);

    //This is first HelloKafkaProducer code
//    @Autowired
//    private HelloKafkaProducer helloKafkaProducer;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        helloKafkaProducer.sendHello("Alice");
//    }

/*
    @Autowired
    private FoodOrderProducer producer;

    @Autowired
    private SimpleNumberProducer simpleNumberProducer;

    @Override
    public void run(String... args) throws Exception {
        var foodOrder = new FoodOrder(3, "Chicken");
        var foodOrder1 = new FoodOrder(10, "fish");
        var foodOrder2 = new FoodOrder(5, "Pizza");

        producer.sendFoodOrder(foodOrder);
        producer.sendFoodOrder(foodOrder1);
        producer.sendFoodOrder(foodOrder2);

        for (int a = 100; a < 103; a++) {
            var simpleNumber = new SimpleNumber(a);

            simpleNumberProducer.sendSimpleNumber(simpleNumber);
        }

    }

 */

    @Override
    public void run(String... args) throws Exception {

    }
/*
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageProducer2 imageProducer2;

    @Override
    public void run(String... args) throws Exception {
        Image image1 = imageService.generateImage("JPG");
        Image image2 = imageService.generateImage("SVG");
        Image image3 = imageService.generateImage("PNG");
        Image image4 = imageService.generateImage("GIF");
        Image image5 = imageService.generateImage("BMP");
        Image image6 = imageService.generateImage("TIFF");

        imageProducer2.sendImageToPartition(image1,0);
        imageProducer2.sendImageToPartition(image2, 0);
        imageProducer2.sendImageToPartition(image3, 0);
        imageProducer2.sendImageToPartition(image4, 1);
        imageProducer2.sendImageToPartition(image5, 1);
        imageProducer2.sendImageToPartition(image6,1);



 */

    }
    /*
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceProducer invoiceProducer;

    @Override
    public void run(String... args) throws Exception {
        for(int a = 0; a<10; a++){
            var invoice = invoiceService.generateInvoice();
            if(a > 5){
                invoice.setAmount(0d);
            }
            invoiceProducer.sendInvoice(invoice);
        }

    }

     */

    /*
    @Autowired
    private ImageProducer imageProducer;

    @Autowired
    private ImageService imageService;

    @Override
    public void run(String... args) throws Exception {
        Image image1 = imageService.generateImage("JPG");
        Image image2 = imageService.generateImage("PNG");
        Image image3 = imageService.generateImage("GIF");
        Image image4 = imageService.generateImage("BMP");
        Image image5 = imageService.generateImage("TIFF");
        Image image6 = imageService.generateImage("WEBP");


        imageProducer.sendImageToPartition(image1, 0);
        imageProducer.sendImageToPartition(image2, 0);
        imageProducer.sendImageToPartition(image3, 0);

        imageProducer.sendImageToPartition(image4, 1);
        imageProducer.sendImageToPartition(image5, 1);
        imageProducer.sendImageToPartition(image6, 1);
    }

     */
/*
    @Autowired
    private PaymentRequestProducer paymentRequestProducer;

    @Override
    public void run(String... args) throws Exception {

        PaymentRequest paymentRequest1 = new PaymentRequest(100, "USD", "1234567890", "Payment for services rendered", LocalDate.now());
        PaymentRequest paymentRequest2 = new PaymentRequest(200, "EUR", "0987654321", "Payment for goods purchased", LocalDate.now().minusDays(1));
        PaymentRequest paymentRequest3 = new PaymentRequest(300, "JPY", "1122334455", "Payment for consulting services", LocalDate.now().minusDays(2));
        PaymentRequest paymentRequest4 = new PaymentRequest(400, "GBP", "5566778899", "Payment for software development", LocalDate.now().minusDays(3));
        PaymentRequest paymentRequest5 = new PaymentRequest(500, "CAD", "2233445566", "Payment for project management services", LocalDate.now().minusDays(4));
        PaymentRequest paymentRequest6 = new PaymentRequest(600, "AUD", "7788990011", "Payment for training services", LocalDate.now().minusDays(5));


        paymentRequestProducer.sendPaymentRequest(paymentRequest1);
        paymentRequestProducer.sendPaymentRequest(paymentRequest2);
        paymentRequestProducer.sendPaymentRequest(paymentRequest3);
        paymentRequestProducer.sendPaymentRequest(paymentRequest4);
        paymentRequestProducer.sendPaymentRequest(paymentRequest5);
        paymentRequestProducer.sendPaymentRequest(paymentRequest6);

        // Publish two of them again
        paymentRequestProducer.sendPaymentRequest(paymentRequest1);
        paymentRequestProducer.sendPaymentRequest(paymentRequest2);
    }

     */

    /*
    @Autowired
    private PurchaseRequestProducer purchaseRequestProducer;

    @Override
    public void run(String... args) throws Exception {
        PurchaseRequest request1 = new PurchaseRequest(UUID.randomUUID(), "REQ-001", 100, "USD");
        PurchaseRequest request2 = new PurchaseRequest(UUID.randomUUID(), "REQ-002", 200, "EUR");
        PurchaseRequest request3 = new PurchaseRequest(UUID.randomUUID(), "REQ-003", 300, "JPY");

        purchaseRequestProducer.sendPurchaseRequest(request1);
        purchaseRequestProducer.sendPurchaseRequest(request2);
        purchaseRequestProducer.sendPurchaseRequest(request3);

        purchaseRequestProducer.sendPurchaseRequest(request1);
    }

     */

    /* This is my CarLocationScheduler
    @Autowired
    private CarLocationScheduler carLocationScheduler;

    @Override
    public void run(String... args) throws Exception {
        for(int a = 0; a<10; a++){
            carLocationScheduler.generateDummyData();
            TimeUnit.SECONDS.sleep(1000);
        }
    }

     */
//}

