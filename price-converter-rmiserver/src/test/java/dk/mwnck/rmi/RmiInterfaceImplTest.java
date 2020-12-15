package dk.mwnck.rmi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class RmiInterfaceImplTest
{
    // UUT
    private static RmiInterfaceImpl rmiInterfaceImpl;


    @Mock
    private static Car car;

    @BeforeAll
    static public void setup() throws RemoteException
    {
        rmiInterfaceImpl = new RmiInterfaceImpl();
        car = mock(Car.class);
    }

    @Test
    public void mustCalculateGermanPrice() throws Exception
    {
        // Arrange
//        var expected = 1000D;
//        when(car.getCurrency()).thenReturn("DKK");
//        when(car.getPrice()).thenReturn(7500D).thenCallRealMethod();
//        when(car.setPrice(anyDouble())).thenCallRealMethod();
//        ArrayList<Object> cars = new ArrayList<>();
//        cars.add(car);
//        Map<String, Double> rates = new HashMap<>();
//        rates.put("DKK", 7.5D);
//        rates.put("EUR", 1D);
//        rmiInterfaceImpl.setRates(rates);
//
//        // Act
//        rmiInterfaceImpl.calculatePrice(cars, "DKK");
//        var actual = car.getPrice();
//
//        // Assert
//        assertEquals(expected, actual);
    }

    @Test
    public void mustCalculateDanishPrice() throws Exception {
//        // Arrange
//        var expected = 7500D;
//        when(car.getCurrency()).thenReturn("EUR");
//        when(car.getPrice()).thenReturn(1000D).thenCallRealMethod();
//        when(car.setPrice(anyDouble())).thenCallRealMethod();
//        ArrayList<Object> cars = new ArrayList<>();
//        cars.add(car);
//        Map<String, Double> rates = new HashMap<>();
//        rates.put("DKK", 1D);
//        rates.put("EUR", 1D/7.5);
//        rmiInterfaceImpl.setRates(rates);
//
//        // Act
//        rmiInterfaceImpl.calculatePrice(cars, "DKK");
//        var actual = car.getPrice();
//
//        // Assert
//        assertEquals(expected, actual, 1);
    }
}
