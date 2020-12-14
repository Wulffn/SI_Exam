package dk.mwnck.rmi;

import java.rmi.Remote;
import java.util.List;

public interface RmiInterface extends Remote
{
    public void calculatePrice(List<Object> objects, String targetCurrency) throws Exception;
}
