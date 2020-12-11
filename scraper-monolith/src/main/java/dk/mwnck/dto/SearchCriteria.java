package dk.mwnck.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.BsonTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "searchcriteria")
@Getter
@RequiredArgsConstructor // creates constructor for all non-null fields.
@ToString
public class SearchCriteria
{
    @NonNull
    private String manufacturer, model;
    @NonNull
    private int km, year;

    private BsonTimestamp created; // mongoDB timestamp, created automagically by mongo upon insertion.
}
