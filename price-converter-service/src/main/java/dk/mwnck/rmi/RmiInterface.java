package dk.mwnck.rmi;

import dk.mwnck.constants.Currency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiInterface extends Remote
{
    public void calculatePrice(List<Object> objects, Currency targetCurrency) throws RemoteException;
}
