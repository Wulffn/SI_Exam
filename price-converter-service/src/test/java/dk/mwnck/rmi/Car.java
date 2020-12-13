package dk.mwnck.rmi;

public interface Car {
    Double getPrice();
    String getCurrency();
    void setCurrency(String currency);
    void setPrice(Double price);
}
