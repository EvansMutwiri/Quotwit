package com.evans.quotwit;

import models.NewsApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//interface to manage api call
public interface CallApi {
    @GET("top-headlines")
    Call<NewsApiResponse> callHeadlines(
            @Query("country") String country,
            @Query("category") String category,
//                @Query("sources") String sources,
            @Query("q") String query,
            @Query("apiKey") String API_KEY);
}
