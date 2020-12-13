package dk.mwnck.rmi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;


public class RmiInterfaceImplTest
{
    // UUT
    private static RmiInterfaceImpl rmiInterfaceImpl;

    @Mock
    Car car;

    @BeforeAll
    static public void setup() throws RemoteException
    {
        rmiInterfaceImpl = new RmiInterfaceImpl();
    }

   /* @Test
    public void mustCalculateWhenWeSaySo() throws Exception {
        // Arrange
        var expected = 7500;
        when(car.getCurrency()).thenReturn("EUR");
        when(car.getPrice()).thenReturn(1000D);
        when(car.setPrice(anyDouble())).thenReturn(anyDouble()).
        ArrayList<Object> objects = new ArrayList<Object>();
        objects.add(car);

        // Act
        var actual = rmiInterfaceImpl.calculatePrice(objects, "DKK");

        // Assert
        assertEquals(expected, actual);
    }*/




}
