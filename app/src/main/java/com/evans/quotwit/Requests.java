package com.evans.quotwit;

import static com.evans.quotwit.Constants.API_KEY;
import static com.evans.quotwit.Constants.BASE_URL;

import android.content.Context;
import android.widget.Toast;

import models.NewsApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Requests {
    Context context;

    //retrofit object x make api calls
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getNewsHeadlines(OnFetchData listener, String category, String query)
    {
        CallApi callApi = retrofit.create(CallApi.class);
        Call<NewsApiResponse> call = callApi.callHeadlines("us", category, query, API_KEY);

        try{
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error!!", Toast.LENGTH_LONG).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request failed");
                }
            });
        }

        // error
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Requests(Context context) {
        this.context = context;
    }

    //interface to manage api call
    public interface CallApi {
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String query,
                @Query("apiKey") String API_KEY);
    }
}
