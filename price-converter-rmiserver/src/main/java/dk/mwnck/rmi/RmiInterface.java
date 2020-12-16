package dk.mwnck.rmi;

import dk.mwnck.constants.Currency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RmiInterface extends Remote
{
    /**
     * Calculates the price based on exchange rates from the internet.
     * PRE: The nested Map of each element in objects must contain the keys 'price' and 'currency'.
     * POST: The value of the key 'price' will be updated as to reflect the corresponding value in the new currency.
     * POST: The value of the key 'currency' will be updated as to reflect the new currency.
     * @param objects A map of <String, Object> key-value pairs where each value itself is a map.
     * @param targetCurrency The currency to convert to.
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, Object>> calculatePrice(Map<String, Map<String, Object>> objects, String targetCurrency) throws Exception;
}
