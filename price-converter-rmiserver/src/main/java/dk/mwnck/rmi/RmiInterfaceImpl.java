package dk.mwnck.rmi;


import dk.mwnck.constants.Currency;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;

public class RmiInterfaceImpl extends UnicastRemoteObject implements RmiInterface {

    public RmiInterfaceImpl() throws RemoteException {
        super();
    }

//    ID:
//        price: :Double
//        currency: :String
//    ID:
//        price: :Double
//        currency: :String

    @Override
    /**
     * {@inhreitdoc}
     */
    public Map<String, Map<String, Object>> calculatePrice(Map<String, Map<String, Object>> objects, String targetCurrency) throws Exception {
        Map<String, Double> rates = getRates(targetCurrency, "rates");

        for (String key : objects.keySet()) {
            Map<String, Object> properties = objects.get(key);
            double price = (double) properties.get("price");
            String currency = (String) properties.get("currency");
            double convertedPrice = change(price, rates.get(currency));
            properties.put("price", convertedPrice);
            properties.put("currency", targetCurrency);
            objects.put(key, properties);
        }
        return objects;
    }


    public Double change(Double price, double rate) {
        return price / rate;
    }

    public static Map<String, Double> getRates(String targetCurrency, String folderName) throws Exception {
        String fileName = getFileName(targetCurrency, folderName);
        Map rates = null;
        try {
            FileInputStream fis = new FileInputStream("rates" + File.separator + fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rates = (Map) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException ioe) {
            ioe.printStackTrace();
        }
        return rates;
    }

    public static void writeRatesToFile(Map<String, Double> rates, String folderName, String fileName) {
        try {
            FileOutputStream fos =
                    new FileOutputStream(folderName + File.separator + fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(rates);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static String getFileName(String targetCurrency, String folderName) throws Exception {
        File folder = new File(folderName + File.separator);
        if(!folder.exists()) folder.mkdir();
        File file = null;
        String fileName = "";
        try {
            file = Arrays.stream(folder.listFiles()).filter(f -> f.getName().contains(targetCurrency)).findFirst().get();
        } catch (NoSuchElementException ex) {
            fileName = targetCurrency + "-" + System.currentTimeMillis() + ".ser";
            writeRatesToFile(getRatesFromAPI(targetCurrency), folderName, fileName);
            return fileName;
        }

        Date fileTimeStamp = new Date(Long.valueOf(file.getName().split("-")[1].split("\\.")[0]));
        Date curTimeMinus5 = new Date(System.currentTimeMillis() - (60000 * 5));

        if (fileTimeStamp.after(curTimeMinus5)) {
            return file.getName();
        } else {
            file.delete();
            fileName = targetCurrency + "-" + System.currentTimeMillis() + ".ser";
            writeRatesToFile(getRatesFromAPI(targetCurrency), folderName, fileName);
            return fileName;
        }
    }

    private static Map<String, Double> getRatesFromAPI(String base) throws Exception {

        // https://api.exchangeratesapi.io/latest
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://api.exchangeratesapi.io/latest?base=" + base), Charset.forName("UTF-8")));
        JSONObject rates = json.getJSONObject("rates");

        Iterator<String> keys = rates.keys();
        Map<String, Double> result = new HashMap<>();
        while (keys.hasNext()) {
            String key = keys.next();
            result.put(key, rates.getDouble(key));
        }

        return result;
    }

}
