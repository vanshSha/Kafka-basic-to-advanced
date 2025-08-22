package kafka;

import kafka.ksqldb.client.BasicJavaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaKsqldbSampleApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(KafkaKsqldbSampleApplication.class, args);
    }

    public static final Logger LOG = LoggerFactory.getLogger(KafkaKsqldbSampleApplication.class);

    @Autowired
    private BasicJavaClient basicJavaClient;

    @Override
    public void run(String... args) throws Exception {
        // basicJavaClient.createStream(); 1 step

        //  basicJavaClient.describeStream(); 2 step

       //        basicJavaClient.listObjects(); // list of kafka table

      //		for (int a = 0; a < 10; a++) { // this loop automatic insert data 10 times.
        // 	    basicJavaClient.insertSingle();
      //		}

        //   basicJavaClient.insertStream(5);
        //  basicJavaClient.pullQuery();


//        LOG.info("Starting pushQuerySync() ");
//        basicJavaClient.pushQuerySync();
//        LOG.info("Started pushQuerySync() ");

        LOG.info("Starting pushQueryASync()");
        basicJavaClient.pushQueryAsync();
        LOG.info("Started pushQueryASync() ");


    }




}

