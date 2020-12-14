package dk.mwnck.rmi;

import java.rmi.*;
import java.util.List;
import java.util.Scanner;


public class RmiClient
{
    private static int a;
    private static int b;
    private static char op;
    public static final String OPS = "+-*/%";

    public static void getInput()
    {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter two integer numbers: ");
        a = inp.nextInt();
        b = inp.nextInt();
        do
        {
            System.out.println("Enter operator: ");
            op = inp.next().charAt(0);
        }
        while (OPS.indexOf(op) == -1);
    }

    public static void getService(List<Object> cars, String targetCurrency) throws Exception
    {
        List<Object> result;

        // Lookup in the registry for the service interface you know by name
        RmiInterface rmiInterface = (RmiInterface) Naming.lookup("//localhost/Convert");

        // Send requests, get responses
        rmiInterface.calculatePrice(cars, targetCurrency);


        for(Object object : cars)
            System.out.println(object.toString());
    }


    /*public static void main(String[] args)
    {
        try
        {
            List<Object> cars = new ArrayList();

            getInput();
            getService();
        }
        catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }*/
}
