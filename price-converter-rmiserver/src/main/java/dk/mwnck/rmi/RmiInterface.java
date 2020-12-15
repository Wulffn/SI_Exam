package dk.mwnck.rmi;

import dk.mwnck.constants.Currency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface RmiInterface extends Remote
{
    public Map<String, Map<String, Object>> calculatePrice(Map<String, Map<String, Object>> objects, String targetCurrency) throws Exception;
}
