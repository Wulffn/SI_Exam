package dk.mwnck.rmi;


import dk.mwnck.constants.Currency;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RmiInterfaceImpl extends UnicastRemoteObject implements RmiInterface
{
    Map<String, Double> rates = null;

    public RmiInterfaceImpl() throws RemoteException
    {
        super();
    }

    public void calculatePrice(List<Object> objects, String targetCurrency) throws Exception
    {
        for(int i = 0; i < objects.size(); i++)
        {
            if (i == 0 && rates == null)
                setRates(getRates(targetCurrency));

            Object object = objects.get(i);

            Method[] methods = object.getClass().getDeclaredMethods();
            try
            {
                Method getPriceMethod = (Method)Arrays.stream(methods).filter(m -> m.getName().equalsIgnoreCase("getprice")).findFirst().get();
                Method setPriceMethod = (Method)Arrays.stream(methods).filter(m -> m.getName().equalsIgnoreCase("setprice")).findFirst().get();
                Method getCurrencyMethod = (Method)Arrays.stream(methods).filter(m -> m.getName().equalsIgnoreCase("getcurrency")).findFirst().get();
                Method setCurrencyMethod = (Method)Arrays.stream(methods).filter(m -> m.getName().equalsIgnoreCase("setcurrency")).findFirst().get();
                Double price = (Double)getPriceMethod.invoke(object);
                Double convertedPrice = change(price, getCurrencyMethod.invoke(object).toString());
                setCurrencyMethod.invoke(object, targetCurrency);
                setPriceMethod.invoke(object, convertedPrice);

            }
            catch(Exception e)
            {
                System.out.println("I wont do it: " + e.getMessage());
            }
        }
    }
    public void setRates(Map<String, Double> rates)
    {
        this.rates = rates;
    }
    private static Map<String, Double> getRates(String base) throws Exception
    {

        // https://api.exchangeratesapi.io/latest
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://api.exchangeratesapi.io/latest?base=" + base.toString()), Charset.forName("UTF-8")));
        JSONObject rates = json.getJSONObject("rates");

        Iterator<String> keys = rates.keys();
        Map<String, Double> result = new HashMap<>();
        while(keys.hasNext())
        {
            String key = keys.next();
            result.put(key, rates.getDouble(key));
        }

        System.out.println(result.toString());
        return result;
    }

    public Double change(Double price, String targetCurrency)
    {
        if (this.rates != null)
        {
            return price / rates.get(targetCurrency);
        }
        else {
            System.out.println("BURN, no conversion for you!");
            return null;
        }
    }
}
