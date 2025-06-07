package dto;

public class StockDTO {

    private int stockID;
    private String ticker;
    private String companyName;
    private double price;
    private boolean status;

    public StockDTO() {
    }

    public StockDTO(int stockID, String ticker, String companyName, double price, boolean status) {
        this.stockID = stockID;
        this.ticker = ticker;
        this.companyName = companyName;
        this.price = price;
        this.status = status;
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
