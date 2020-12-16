package dk.mwnck.rmi;

import java.rmi.*;
import java.util.Map;
import java.util.Scanner;


public class RmiClient
{

    public static Map<String, Map<String, Object>> invokeConversionService(Map<String, Map<String, Object>> objects, String targetCurrency) throws Exception
    {
        Map<String, Map<String, Object>> result;

        // Lookup in the registry for the service interface you know by name
        RmiInterface rmiInterface = (RmiInterface) Naming.lookup("//localhost/Convert");

        // Send requests, get responses
        result = rmiInterface.calculatePrice(objects, targetCurrency);

        return result;
    }
}
