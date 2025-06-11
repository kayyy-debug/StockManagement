
package dto;

import java.sql.Date;

public class NewsDTO {
    private int newsID;
    private String title;
    private String content;
    private String sectorID;
    private Date publicDate;

    public NewsDTO() {
    }

    public NewsDTO(int newsID, String title, String content, String sectorID, Date publicDate) {
        this.newsID = newsID;
        this.title = title;
        this.content = content;
        this.sectorID = sectorID;
        this.publicDate = publicDate;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSectorID() {
        return sectorID;
    }

    public void setSectorID(String sectorID) {
        this.sectorID = sectorID;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    
    
   }
