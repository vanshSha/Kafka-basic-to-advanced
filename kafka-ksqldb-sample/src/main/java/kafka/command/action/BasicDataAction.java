package kafka.command.action;

import kafka.api.request.*;
import kafka.broker.message.*;
import kafka.broker.producer.BasicDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Component
public class BasicDataAction {

    @Autowired
    private BasicDataProducer producer;

    public void publishBasicDataOne(BasicDataOneMessage message) {
        producer.sendBasicDataOne(message);
    }

    public BasicDataOneMessage toKafkaMessage(BasicDataOneRequest request) {
        var result = new BasicDataOneMessage();

        result.setMyAnotherString(request.getMyAnotherString());
        result.setMyBoolean(request.isMyBoolean());
        result.setMyDouble(request.getMyDouble());
        result.setMyFloat(request.getMyFloat());
        result.setMyInteger(request.getMyInteger());
        result.setMyLong(request.getMyLong());
        result.setMyString(request.getMyString());
        result.setMyBigDecimal(request.getMyBigDecimal());

        return result;
    }

    public void publishBasicDataTwo(BasicDataTwoMessage message) {
        producer.sendBasicDataTwo(message);
    }

    /*It means first I get a Date, then convert it to a String,
    then convert it to a LocalDate and finally use .toEpochDay() to get the number of days */
    private long toEpochDay(BasicDataTwoRequest.Date myDate) {
        String dateString = String.format("%d-%2d-%2d",
                        myDate.getYear(),
                        myDate.getMonth(),
                        myDate.getDate())
                .replace(' ', '0');
        return LocalDate.parse(dateString).toEpochDay();
    }
    /* String.format - Builds a string form YYYY-MM-DD with the use of year,month,date.
       while creating a date replace leading space - 2025- 8- 7 becomes 2025-08-07 through(.replace)
       LocalDate.parse(dateString) converts the generated string into LocalDate
       .toEpochDay(); -> i am using this for calculate numbers of days */


    /*“This code takes a Timestamp, converts it to a LocalDateTime assuming UTC,
     and then returns the exact milliseconds since epoch (1970-01-01 UTC).”*/
    private long toEpochMillis(BasicDataTwoRequest.Timestamp myTimestamp) {
        String dateString = String.format("%d-%2d-%2dT%2d:%2d:%2d",
                        myTimestamp.getYear(),
                        myTimestamp.getMonth(),
                        myTimestamp.getDate(),
                        myTimestamp.getHour(),
                        myTimestamp.getMinute(),
                        myTimestamp.getSecond())
                .replace(' ', '0');
        return LocalDateTime.parse(dateString).toInstant(ZoneOffset.UTC).toEpochMilli();
    }/* "%d-%2d-%2dT%2d:%2d:%2d" this is local dateTime format
        LocalDateTime.parse(dateString)  string - into LocalDateTime(But no TimeZone)
        toInstant -> show precise timestamp
        toInstant(ZoneOffset.UTC) -> this show dataTime according to Universe UTC -> Coordinated Universal Time
        toEpochMilli(); -> how many milliseconds since "1970-01-01” (more precise)*/

    private long toMillisOfDay(BasicDataTwoRequest.Time myTime) {
        return LocalTime.of(myTime.getHour(), myTime.getMinute(), myTime.getSecond()).toSecondOfDay() * 1000;
    }
    /* .of(int hour, int minute, int second) method create localTime Object
       .toSecondOfDay -> this method use for how many seconds have passed since midnight (00:00:00) for that time
        basically it convert a time into single number.
        toSecondOfDay() *1000 -> i am converting into millisecond

     */

    public BasicDataTwoMessage toKafkaMessage(BasicDataTwoRequest request) {
        BasicDataTwoMessage result = new BasicDataTwoMessage();

        var epochDay = toEpochDay(request.getMyDate());
        result.setMyEpochDay(epochDay);

        var millisOfDay = toMillisOfDay(request.getMyTime());
        result.setMyMillisOfDay(millisOfDay);

        var epochMillis = toEpochMillis(request.getMyTimestamp());
        result.setMyEpochMillis(epochMillis);

        return result;
    }

    /*
     1 create object
     2 convert date to echo day
     3 Convert time to milliseconds since midnight
     4 Convert timestamp to epoch milliseconds
     5 return the fully built message

     */


    public void publishBasicDataThree(BasicDataThreeMessage message) {
        producer.sendBasicDataThree(message);
    }

    private LocalDate toLocalDate(BasicDataThreeRequest.Date myDate) {
        return LocalDate.of(myDate.getYear(), myDate.getMonth(), myDate.getDate());
    }
    // I am returning the object

    private LocalDateTime toLocalDateTime(BasicDataThreeRequest.DateTime myDateTime) {
        return LocalDateTime.of(myDateTime.getYear(), myDateTime.getMonth(), myDateTime.getDate(), myDateTime.getHour(),
                myDateTime.getMinute(), myDateTime.getSecond());
    }
    // I am creating the object of localDateTime

    private LocalTime toLocalTime(BasicDataThreeRequest.Time myTime) {
        return LocalTime.of(myTime.getHour(), myTime.getMinute(), myTime.getSecond());
    }
    // I am creating the object of LocalTime

    /*This method does create 1 object that contain full data and time and UTC then
	 I am adding functionality to how many hours a head or behind from UTC */
    private OffsetDateTime toOffsetDateTime(BasicDataThreeRequest.DateTimeWithTimeZone myDateTimeWithTimezone) {
        return OffsetDateTime.of(
                myDateTimeWithTimezone.getYear(),
                myDateTimeWithTimezone.getMonth(),
                myDateTimeWithTimezone.getDate(),
                myDateTimeWithTimezone.getHour(),
                myDateTimeWithTimezone.getMinute(),
                myDateTimeWithTimezone.getSecond(),
                0,
                ZoneOffset.of(myDateTimeWithTimezone.getTimezone())
        );
    }
/*  OffsetDateTime -> this method use for any specific country time zone like this +07:00 → offset from UTC,
	[Asia/Jakarta] → timezone ID. A class representing a full date + time + fixed offset.
	0 is a Nanoseconds (default 0)
	ZoneOffset -> A class representing a fixed offset from UTC. Only the offset in hours and minutes. */


    /*In this method I set custom DataTime and Plain also. and UTC*/
    public BasicDataThreeMessage toKafkaMessage(BasicDataThreeRequest request) {
        var result = new BasicDataThreeMessage();

        var localDate = toLocalDate(request.getMyDate());
        result.setMyLocalDate(localDate);
        result.setMyLocalDateCustomFormat(localDate);

        var localTime = toLocalTime(request.getMyTime());
        result.setMyLocalTime(localTime);
        result.setMyLocalTimeCustomFormat(localTime);

        var localDatetime = toLocalDateTime(request.getMyDateTime());
        result.setMyLocalDateTime(localDatetime);
        result.setMyLocalDateTimeCustomFormat(localDatetime);

        var offsetDateTime = toOffsetDateTime(request.getMyDateTimeWithTimezone());
        result.setMyOffsetDateTime(offsetDateTime);
        result.setMyOffsetDateTimeCustomFormat(offsetDateTime);

        return result;
    }

    public void publishBasicDataFour(BasicDataFourMessage message) {
        producer.sendBasicDataFour(message);
    }

    public BasicDataFourMessage toKafkaMessage(BasicDataFourRequest request) {
        var result = new BasicDataFourMessage();

        var array = new String[request.getArrayElementsCount()];
        IntStream.range(0, request.getArrayElementsCount()).forEach(i -> array[i] = "Array element " + i);

        var list = new ArrayList<Integer>();
        IntStream.range(0, request.getListElementsCount()).forEach(list::add);

        var set = new TreeSet<Double>();
        IntStream.range(0, request.getSetElementsCount())
                .forEach(i -> set.add(ThreadLocalRandom.current().nextDouble(1000)));

        result.setMyStringArray(array);
        result.setMyIntegerList(list);
        result.setMyDoubleSet(set);

        return result;
    }
    /*
    1 I create object of BasicDataFourMessage
    2 I create array of string and this array size according to this statement request.getArrayElementsCount()
    3 IntStream.range(0 , ......) this method does it contain data  0 to request.getListElementsCount()-1. because
    range is exclusive bound  then I am filling the array.
    4 I create empty Integer list then I create a IntStream and I added the value through method reference.
    5 I create list of TreeSet. it removes duplicates from the list.
    6 I create again stream foreach iterate 0 to request.getSetElementsCount()-1.
      then each loop generate random value 0 to 1000 per iteration and into (set).
     */

    public void publishBasicDataFive(BasicDataFiveMessage message) {
        producer.sendBasicDataFive(message);
    }

    public BasicDataFiveMessage toKafkaMessage(BasicDataFiveRequest request) {
        var result = new BasicDataFiveMessage();

        var mapAlpha = new TreeMap<Integer, String>();
        IntStream.range(0, request.getAlphaElementsCount()).forEach(i -> mapAlpha.put(i, "Map value alpha " + i));

        var mapBeta = new HashMap<UUID, String>();
        IntStream.range(0, request.getBetaElementsCount())
                .forEach(i -> mapBeta.put(UUID.randomUUID(), "Map value beta " + i));

        result.setMyMapAlpha(mapAlpha);
        result.setMyMapBeta(mapBeta);

        return result;
    }
    /*
    1 I create TreeMap<Integer, String>
    2 I create intStream.range.(0, request.getAlphaElementsCount()) then iterate on i and put value into mapAlpha

    1 I create HashMap<UUID, String>  UUID = Universally Unique Identifier
    2 I create intStream<0 , request.getBetaElementsCount()> iterate on i and put value into mapBeta
     */

    public void publishBasicDataPerson(BasicDataPersonMessage message) {
        producer.sendBasicDataPerson(message);
    }


    public BasicDataPersonMessage toKafkaMessage(BasicDataPersonRequest request) {
        var result = new BasicDataPersonMessage();

        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setBirthDate(request.getBirthDate());
        result.setContacts(request.getContacts());

        var passport = new BasicDataPassportMessage();
        passport.setNumber(request.getPassport().getNumber());
        passport.setExpirationDate(request.getPassport().getExpirationDate());
        result.setPassport(passport);

        var addresses = request.getAddresses().stream().map(address -> toKafkaMessage(address)).collect(Collectors.toList());
        result.setAddresses(addresses);

        return result;
    }
    /*
    request.getAddresses() -> get list of address from request
    stream() -> Creates a Stream<Address>
    .map(address -> toKafkaMessage(address)) -> convert BasicDataPersonRequest.Address original into BasicDataAddressMessage
    why I am converting because You are converting because the input model and the output model are different classes.
    Kafka should receive the output model, not the request model.

     */


    private BasicDataAddressMessage toKafkaMessage(BasicDataPersonRequest.Address original) {
        var result = new BasicDataAddressMessage();

        result.setStreetAddress(original.getStreetAddress());
        result.setCountry(original.getCountry());
        result.setLocation(toKafkaMessage(original.getLocation()));

        return result;
    }
/*  First you create the object →
    then you read the data from original →
    then you set those values to result →
    finally you return the object.
 */

    private BasicDataLocationMessage toKafkaMessage(BasicDataPersonRequest.Location original) {
        var result = new BasicDataLocationMessage();

        result.setLatitude(original.getLatitude());
        result.setLongitude(original.getLongitude());

        return result;
    }
    /* create object -> get data from original -> set into result -> return result. */


    public void publishBasicDataCountry(BasicDataCountryMessage message) {
        producer.sendBasicDataCountry(message);
    }

    public BasicDataCountryMessage toKafkaMessage(BasicDataCountryRequest original) {
        var result = new BasicDataCountryMessage();

        result.setCountryName(original.getCountryName());
        result.setCurrencyCode(original.getCurrencyCode());
        result.setPopulation(original.getPopulation());

        return result;
    }

}
