package models;

import org.parceler.Parcel;

import java.util.List;
@Parcel
public class NewsApiResponse {
    String status;
    int totalResults;
    List<Headlines> articles;

    public NewsApiResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Headlines> getArticles() {
        return articles;
    }

    public void setArticles(List<Headlines> articles) {
        this.articles = articles;
    }
}
