package kafka.ksqldb.client;

import io.confluent.ksql.api.client.Row;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;               // publisher = the machines that makes rows (ksqldb stream)
                                            // Subscriber<Row> -> this is consuming data from row
public class LogRowSubscriber implements Subscriber<Row> {

    // This is like a slot or box where you can store the tool.
    private Subscription subscription; //control handle to decide pace (how fast rows come, stop/resume).
    public static final Logger LOG = LoggerFactory.getLogger(LogRowSubscriber.class);

    @Override
    public void onSubscribe(Subscription s) {
        LOG.info("onSubscribe(), starting subscription");
        this.subscription = s; // it's a handle to control the rows
        this.subscription.request(1); // request the first row   Send me only 1 row for now
    }

    @Override
    public void onNext(Row item) { // this is doing 1 row processed then get next row
        LOG.info("onNext(), row: {}", item); // item actual row of data you received from the stream.
        this.subscription.request(1); // request the next row
    }

    @Override
    public void onError(Throwable throwable) {
        LOG.error("onError(), error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        LOG.info("onComplete(), all rows are consumed, subscription ended");
    }
}
