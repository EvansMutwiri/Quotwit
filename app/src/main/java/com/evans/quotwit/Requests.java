package com.evans.quotwit;

import static com.evans.quotwit.Constants.BASE_URL;

import android.content.Context;

import models.NewsApiResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class Requests {
    Context context;

    //retrofit object x make api calls
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Requests(Context context) {
        this.context = context;
    }


}
