package dk.mwnck.dto;

import dk.mwnck.constants.Country;
import dk.mwnck.constants.Currency;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.BsonTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cars")
@Getter
@RequiredArgsConstructor // creates constructor for all non-null fields.
@ToString
public class Car
{
    @NonNull
    private String manufacturer, model, price;
    @NonNull
    private int km, year;
    @NonNull
    private Country country;
    @NonNull
    private Currency currency;

    //private String version;


    private BsonTimestamp created = new BsonTimestamp(); // mongoDB timestamp.
}
