package com.example.hoff.retrofit;

import com.example.hoff.model.ProductResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("get_products_new")
    Single<ProductResponse> getProducts( @Query ( "category_id" ) int category_id,
                                         @Query ( "sort_by" ) String sort_by,
                                         @Query ( "sort_type" ) String sort_type,
                                         @Query ( "limit" ) int limit,
                                         @Query ( "offset" ) int offset,
                                         @Query ( "device_id" ) String device_id,
                                         @Query ( "isAndroid" ) String isAndroid,
                                         @Query ( "app_version" ) String app_version,
                                         @Query ( "location" ) int location);

}