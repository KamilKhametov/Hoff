package com.example.hoff.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceConstructor {

    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance=new Retrofit.Builder ()
                    .baseUrl ( APIConfig.HOST_URL )
                    .addConverterFactory ( GsonConverterFactory.create () )
                    .addCallAdapterFactory ( RxJava2CallAdapterFactory.create () )
                    .build ();
        return ourInstance;
    }
}