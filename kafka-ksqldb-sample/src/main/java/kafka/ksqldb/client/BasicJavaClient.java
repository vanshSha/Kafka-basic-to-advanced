package kafka.ksqldb.client;

import io.confluent.ksql.api.client.*;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BasicJavaClient {

    private static final Logger LOG = LoggerFactory.getLogger(BasicJavaClient.class);

    private static final ClientOptions CLIENT_OPTIONS = ClientOptions.create()
            .setHost("localHost")
            .setPort(8088);

    // this method create stream -- > show in ksqlDB
    public void createStream() throws InterruptedException, ExecutionException {
        Client client = Client.create(CLIENT_OPTIONS); // client come from confluent.ksql.api.client
        String statement = "DROP STREAM IF EXISTS `s-java-client` DELETE TOPIC;"; // if this stream is exist then
        // drop this stream and delete topic

        ExecuteStatementResult result = client.executeStatement(statement).get();
        //executeStatement(statement) -> this method does send query if exit or not
        // .get -> wait the result to come back
        LOG.info("Stream execution result: {}", result);

        statement = """
                CREATE OR REPLACE STREAM `s-java-client` (
                  `fieldOne` VARCHAR,
                  `fieldTwo` INT,
                  `fieldThree` BOOLEAN
                ) WITH (
                  KAFKA_TOPIC = 't-java-client',
                  PARTITIONS = 2,
                  VALUE_FORMAT = 'JSON'
                );
                """;

        result = client.executeStatement(statement).get();

        LOG.info("Stream execution result: {}", result);

        client.close();
    }

    // this method describe stream
    public void describeStream() throws InterruptedException, ExecutionException {
        Client client = Client.create(CLIENT_OPTIONS);
        SourceDescription description = client.describeSource("`s-java-client`").get();

        LOG.info("{} {} has the folllowing fields : {}",
                description.type(),
                description.name(),
                description.fields());

        client.close();
    }

    // this method does . I have how many stream , topics, tables
    public void listObjects() throws InterruptedException, ExecutionException {
        var client = Client.create(CLIENT_OPTIONS);

        LOG.info("Kafka topics");
        client.listTopics().get().forEach(t -> LOG.info(
                "topic {} has {} partitions", t.getName(), t.getPartitions()));

        LOG.info("\n\n");
        LOG.info("Kafka streams");
        client.listStreams().get()
                .forEach(s -> LOG.info("stream {} is coming from topic {}", s.getName(), s.getTopic()));

        LOG.info("\n\n");
        LOG.info("Kafka tables");
        client.listTables().get()
                .forEach(tbl -> LOG.info("table {} is coming from topic {}", tbl.getName(), tbl.getTopic()));

        client.close();
    }

    /*
    This method is doing create object I insert data for fieldOne column permanent same string and current date.
    same with 2 column I insert some data for 2 field for integer data(can be negative value)
    for 3rd column add random true/false
     */
    private KsqlObject generateNewRow() {

        return new KsqlObject().put("`fieldOne`", "Now is " + LocalTime.now())
                .put("`fieldTwo`", ThreadLocalRandom.current().nextInt())
                .put("`fieldThree`", ThreadLocalRandom.current().nextBoolean());
    }


    public void insertSingle() throws InterruptedException, ExecutionException {
        Client client = Client.create(CLIENT_OPTIONS); // create client object then connect with ksqlDB server
        KsqlObject newRow = generateNewRow(); // this method is my helper method which builds a row and random data

        client.insertInto("`s-java-client`", newRow).get();

        client.close();
    }

    public void insertStream(int rows) throws InterruptedException, ExecutionException {
        Client client = Client.create(CLIENT_OPTIONS);
        InsertsPublisher insertsPublisher = new InsertsPublisher(); // bucket of rows
        CompletableFuture acksPublisher = client.streamInserts("`s-java-client`", insertsPublisher); // this is
        // i

        for (int i = 0; i < rows; i++) { // I am adding new row with random data
            insertsPublisher.accept(generateNewRow());
        }

        insertsPublisher.complete();

        acksPublisher.get();

        client.close();
    }

    public void pullQuery() throws InterruptedException, ExecutionException {
        Client client = Client.create(CLIENT_OPTIONS);
        // I am storing query in my List.
        //executeQuery this method use for sending query
        List pullQueryResult = client.executeQuery("SELECT * FROM `s-java-client` LIMIT 20;").get();

        pullQueryResult.forEach(row -> LOG.info("Row: {}", row));
        // i am iterating on row with Log . log is use debugging. log is providing timestamp,log level, main-> thread main,
        // which class is printing
        client.close();


    }

    public void pushQuerySync() throws InterruptedException, ExecutionException {
        var client = Client.create(CLIENT_OPTIONS);
        StreamedQueryResult pushQueryResult = client.streamQuery("SELECT * FROM `s-java-client` EMIT CHANGES;").get();
        // StreamedQueryResult this is interface. is the object that keeps my connection open with ksqldb watch/receive new rows in real time,
        while (true) {
            Row row = pushQueryResult.poll();
            LOG.info("Row: {}", row);
        }
        // poll return next row  if available , else null
// ROW is interface single row representing the result query. Row is one structure record (columns + values) from that stream
    }

    // There are 2 ways to get data
    // - poll(Synchronous Processing)The code waits for a task to finish before moving to the next line
    // - subscribe(ASSynchronous Processing) The code does not wait.when the task completes and continue running other code.


    public void pushQueryAsync() throws InterruptedException, ExecutionException {
        var client = Client.create(CLIENT_OPTIONS);

        // streamQuery i am subscribing data from ksqldb.
        client.streamQuery("SELECT * FROM `s-java-client` EMIT CHANGES;")
                .thenAccept( //thenAccept this method provide asynchronously processing
                        pushQueryResult -> {
                            LogRowSubscriber subscriber = new LogRowSubscriber(); // here i am creating object of
                            // LogRowSubscriber because this object know how to handle these row

                            pushQueryResult.subscribe(subscriber); // this line doing every row is coming and handle by subscriber
                        })
                .exceptionally(
                        e -> {
                            LOG.error("Error: {}", e.getMessage());
                            return null;
                        });
    }

}
