package dk.mwnck.rmi;


import dk.mwnck.constants.Currency;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

public class RmiInterfaceImpl extends UnicastRemoteObject implements RmiInterface
{
    public RmiInterfaceImpl() throws RemoteException
    {
        super();
    }

    public void calculatePrice(List<Object> objects, Currency targetCurrency) throws RemoteException
    {
        for (Object object : objects)
        {
            Field[] fields = object.getClass().getDeclaredFields();
            Arrays.stream(fields).filter(f -> f.getName().equalsIgnoreCase("price")).map(f -> {
                Class<?> classType = f.getClass();
                Double price;
                try
                {
                    switch (classType.getSimpleName().toLowerCase())
                    {
                        case "float":
                        case "string":
                        case "double":
                        case "integer":
                            price = (Double) f.get(object);
                            break;
                        default:
                            throw new IllegalAccessException("I cannot work under these circumstances!");
                    }
                }
                catch (IllegalAccessException e)
                {
                    System.out.println("BURN" + e.getMessage());
                }
            }
        }
    }

    public Class<?> getClassOf(Object object)
    {
        return object.getClass();
    }

    public Object castValueTo(Double price, Class<?> targetType) throws ClassCastException
    {
        if (targetType.getSimpleName().equalsIgnoreCase("string"))
            return String.valueOf(price);
        if (targetType.getSimpleName().equalsIgnoreCase("float"))
            return price.floatValue();
        return price;
    }
}
