package dk.mwnck.camunda;

import dk.mwnck.rmi.RmiClient;
import dk.mwnck.utils.Picker;
import org.camunda.bpm.client.ExternalTaskClient;

import java.text.ParseException;
import java.util.logging.Logger;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.google.gson.*;


public class CamundaRmiWorker
{
    private static final Logger LOGGER = Logger.getLogger(CamundaRmiWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://camunda:8080/engine-rest")
                .asyncResponseTimeout(10000)
                .build();

        client.subscribe("change-currency")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {
                    try
                    {
                        //"{5fd364ef5c0c725a9ac924e5={"id":"5fd364ef5c0c725a9ac924e5","manufacturer":"Kia","model":"Picanto","price":"€ 3,650.-" ...}}
                        // All cars are nested in the root map we get from Camunda, each has its own id as key. Each car is represented in json format.
                        Map<String, Object> cars = externalTask.getVariable("cars");
                        // RmiClient needs Map<String, Map<String, Object>> , nested map should contain price and currency, so strip out necessary fields.
                        Map<String, Map<String, Object>> carsPriceAndCurrency = new HashMap<>(), result;


                        cars.forEach((key, value) ->
                            {
                                // Map json value of each car.
                                Map<String, Object> car = Picker.toMap(value.toString());
                                // For each car, get a map of only price and currency values.
                                Map<String, Object> priceAndCurrency = Picker.pick(car, "price", "currency");
                                try
                                {
                                    // parse the price String to double and put onto map again.
                                    Double doubleValue = Picker.priceStringToDouble(priceAndCurrency.get("price").toString());
                                    priceAndCurrency.put("price", doubleValue);
                                }
                                catch (ParseException e)
                                {
                                    System.out.println("Parsing did not succeed: " + e.getMessage());
                                }

                                // Put price and currency of each car in the map. The key is the car's id.
                                carsPriceAndCurrency.put(key, priceAndCurrency);
                            });

                        // Invoke the Rmi server, send the price and currency collection.
                        result = RmiClient.invokeConversionService(carsPriceAndCurrency, "DKK");

                        // Put recalculated price and currency back into the corresponding car in the cars collection.
                        result.forEach((key,value) -> {

                            // Convert the json with price and currency to a map.
                            Map<String, Object> car = Picker.toMap(cars.get(key).toString());

                            // Put price and currency back in the car.
                            car.put("price", value.get("price"));
                            car.put("currency", value.get("currency"));

                            cars.put(key, car);
                        });

                        Map<String, Object> res = new HashMap<>();
                        res.put("cars", cars);

                        // Send the result to camunda.
                        externalTaskService.complete(externalTask,res);
                    }
                    catch (Exception e)
                    {

                        System.out.println("RMI service failed: " + e.getMessage());
                        System.out.println("Here comes the trace");
                        System.out.println("--------------------");
                        e.printStackTrace();
                    }
                }).open();







    }
}
