package dk.mwnck.rmi;

import java.io.Serializable;

public class Car implements Serializable
{
    private double price;
    private String currency;
    public double getPrice(){return price;}
    public String getCurrency(){return currency;}
    public void setCurrency(String currency){this.currency = currency;}
    public double setPrice(double value) {
        System.out.println("setting price of: " + value); this.price = value; return value; }
}
