package dk.mwnck.utils;

import com.google.gson.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Picker
{
    /*public static Map<String, Object> getStringObjectMapFromJson(StringBuffer content) {
        JsonElement jsonElement = new JsonParser().parse(content.toString());
        JsonArray jsonCars = jsonElement.getAsJsonArray();
        Map<String, Object> cars = new HashMap<>();
        for (JsonElement obj : jsonCars) {
            JsonObject json = obj.getAsJsonObject();
            String id = json.get("id").getAsString();
            cars.put(id, obj.toString());
        }
        return cars;
    }*/

    /**
     * Selects the properties from the json whose names are given in args.
     * The selected properties are returned in the map, e.g.:
     * json = "{\"id\":\"5fd364ef5c0c725a9ac924e5\",\"manufacturer\":\"Kia\",\"model\":\"Picanto\",\"price\":\"€ 3,650.-\"}"
     * args = "manufacturer", "model"
     * Returning map will have the key-value pairs of manufacturer and model. If no match is found in the json, the value in the map will be null.
     *
     * @param json A json formatted string.
     * @param args A series of string arguments with names of properties that should be matched.
     * @return
     */
    public static Map<String, Object> pick(Map<String, Object> input, String... args)
    {
        /*Map<String, Object> result = new HashMap<>();
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Stream.of(args).forEach(arg -> result.put(arg, jsonObject.get(arg).getAsString()));
        return result;*/

        Map<String, Object> result = new HashMap<>();
        Stream.of(args).forEach(arg -> result.put(arg, input.get(arg)));
        return result;
    }

    public static Map<String, Object> toMap(String json)
    {
        Gson gson = new Gson();
        Map<String, Object> result = gson.fromJson(json, Map.class);
        return result;
    }

    public static Double priceStringToDouble(String price) throws ParseException
    {
        System.out.println(price);

        // ([\d]+[,\\.](?=\d)[\d]+)

        //String regex = "[\\D\\s\\.,](?![\\d]+)";
        String regex = "[^0-9]";
        price = price.replaceAll(regex, "");
        price = price.trim();

        System.out.println(price);

        return Double.parseDouble(price);



    }
}
