package dk.mwnck.rmi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RmiClientTest
{
    // SUT
    //RmiClient rmiClient = new RmiClient();

    private static Map<String, Map<String, Object>> objects;


    @BeforeAll
    static public void setup() throws RemoteException
    {
        //car = mock(Car.class, withSettings().serializable(SerializableMode.ACROSS_CLASSLOADERS));
        objects = new HashMap<>();
    }

    @Test
    public void mustCalculateDanishPrice() throws Exception {
        // Arrange
        var expected = 7500D;

        Map<String, Object> map = new HashMap<>();
        map.put("price", 1000D);
        map.put("currency", "EUR");

        objects.put("id", map);

        // Act
        Map<String, Map<String, Object>> res = RmiClient.invokeConversionService(objects, "DKK");
        var actual = (Double) res.get("id").get("price");

        // Assert
        assertEquals(expected, actual, 100);
    }

    @Test
    public void mustCalculateGermanPrice() throws Exception {
        // Arrange
        var expected = 1000D;

        Map<String, Object> map = new HashMap<>();
        map.put("price", 7500D);
        map.put("currency", "DKK");

        objects.put("id", map);

        // Act
        Map<String, Map<String, Object>> res = RmiClient.invokeConversionService(objects, "EUR");
        var actual = (Double) res.get("id").get("price");

        // Assert
        assertEquals(expected, actual, 100);
    }
}
