package dk.mwnck.rmi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.mock.SerializableMode;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

public class RmiClientTest
{
    // SUT
    //RmiClient rmiClient = new RmiClient();

    private static Car car;


    @BeforeAll
    static public void setup() throws RemoteException
    {
        //car = mock(Car.class, withSettings().serializable(SerializableMode.ACROSS_CLASSLOADERS));
        car = new Car();
    }

    @Test
    public void mustCalculateDanishPrice() throws Exception {
        // Arrange
        var expected = 7500D;
        car.setCurrency("EUR");
        car.setPrice(1000D);

        ArrayList<Object> cars = new ArrayList<>();
        cars.add(car);

        // Act
        RmiClient.getService(cars, "DKK");
        var actual = car.getPrice();

        // Assert
        assertEquals(expected, actual, 1);
    }
}
