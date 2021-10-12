package com.example.indeed_assign.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;
    private static ApiInterface apiInterface=null;

    private static ApiInterface getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        ApiInterface api=apiInterface;
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.unsplash.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }

    public static ApiInterface getApiInterface() {
        if (apiInterface==null){
            apiInterface = getClient();
        }
        return apiInterface;
    }
}
