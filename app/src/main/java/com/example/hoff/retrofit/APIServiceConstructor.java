package com.example.hoff.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServiceConstructor {

    public static <T> T CreateService( Class<T> serviceClass ) {

        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor ();
        loggingInterceptor.setLevel ( HttpLoggingInterceptor.Level.BODY );


        OkHttpClient okHttpClient=new OkHttpClient.Builder ()
                .addInterceptor ( loggingInterceptor )
                .build ();

        Retrofit retrofit=new Retrofit.Builder ()
                .baseUrl ( APIConfig.HOST_URL )
                .addConverterFactory ( GsonConverterFactory.create () )
                .client ( okHttpClient )
                .build ();

        return retrofit.create ( serviceClass );

    }
}