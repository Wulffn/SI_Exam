package dk.mwnck.rmi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RmiInterfaceImplTest
{
    // UUT
    private static RmiInterfaceImpl rmiInterfaceImpl;

    @BeforeAll
    static public void setup() throws RemoteException
    {
        rmiInterfaceImpl = new RmiInterfaceImpl();
    }

    @Test
    public void mustReturnFloatWhenPriceTypeIsFloat() {
        // Arrange
        float myFloat = 5F;
        var expected = Float.class;

        // Act
        var result = rmiInterfaceImpl.getClassOf(myFloat);
        
        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void mustCastToStringWhenPriceTypeIsString() {
        // Arrange
        var expected = "5.0";
        double price = 5;

        var targetType = String.class;

        // Act
        var result = rmiInterfaceImpl.castValueTo(price, targetType);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void mustCastToFloatWhenPriceTypeIsFloat() {
        // Arrange
        var expected = 5F;
        double price = 5;
        var targetType = Float.class;

        // Act
        var result = rmiInterfaceImpl.castValueTo(price, targetType);

        // Assert
        assertEquals(expected, result);
    }
}
