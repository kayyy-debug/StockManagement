
package dto;

public class AlertDTO {
    private int alertID;
    private String userID;
    private String ticker;
    private float threshold;
    private String direction;
    private String status;

    public AlertDTO() {
    }

    public AlertDTO(int alertID, String userID, String ticker, float threshold, String direction, String status) {
        this.alertID = alertID;
        this.userID = userID;
        this.ticker = ticker;
        this.threshold = threshold;
        this.direction = direction;
        this.status = status;
    }

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
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

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
