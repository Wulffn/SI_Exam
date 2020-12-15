package dk.mwnck;

import java.util.logging.Logger;

import com.google.gson.*;
import org.camunda.bpm.client.ExternalTaskClient;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InvokeCarServiceWorker {

    private final static Logger LOGGER = Logger.getLogger(InvokeCarServiceWorker.class.getName());
    private final static String URL = "http://localhost:8080/car";

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8082/engine-rest")
                .asyncResponseTimeout(10000)
                .build();

        // subscribe to external task topic "invoke-car-service" as specified in BPMN
        client.subscribe("invoke-car-service")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    String carParams = externalTask.getVariable("car-params");
                    //String carParams = "/kia/picanto/2012/50000";
                    StringBuffer content = null;
                    // call REST here.
                    try {
                        URL url = new URL(URL + carParams);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Content-Type", "application/json");
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        content = new StringBuffer();
                        while ((inputLine = in.readLine()) != null)
                            content.append(inputLine);
                        in.close();
                        con.disconnect();
                    } catch (Exception e) {
                        System.out.println("BURN: " + e.getMessage());
                    }
                    System.out.println("Submitting result to camunda");

                    Map<String, Object> cars = getStringObjectMapFromJson(content);

                    externalTaskService.complete(externalTask, cars);
                    System.out.println("DONE");

                }).open();
    }

    private static Map<String, Object> getStringObjectMapFromJson(StringBuffer content) {
        JsonElement jsonElement = new JsonParser().parse(content.toString());
        JsonArray jsonCars = jsonElement.getAsJsonArray();
        Map<String, Object> cars = new HashMap<>();
        for (JsonElement obj : jsonCars) {
            cars.put("" + cars.size() + 1, obj);
        }
        return cars;
    }

}
