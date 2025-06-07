package dto;

import java.sql.Timestamp;

public class TransactionDTO {

    private int transID;
    private String userID;
    private String ticker;
    private double price;
    private int quantity;
    private Timestamp transDate;

    public TransactionDTO() {
    }

    public TransactionDTO(int transID, String userID, String ticker, double price, int quantity, Timestamp transDate) {
        this.transID = transID;
        this.userID = userID;
        this.ticker = ticker;
        this.price = price;
        this.quantity = quantity;
        this.transDate = transDate;
    }

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTransDate() {
        return transDate;
    }

    public void setTransDate(Timestamp transDate) {
        this.transDate = transDate;
    }
}
