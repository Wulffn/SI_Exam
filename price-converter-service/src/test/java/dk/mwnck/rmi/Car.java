package dk.mwnck.rmi;

public abstract class Car {
    private double price;
    public double getPrice(){return price;}
    abstract String getCurrency();
    abstract void setCurrency(String currency);
    public double setPrice(double value) {
        System.out.println("setting price of: " + value); this.price = value; return value; }
}
