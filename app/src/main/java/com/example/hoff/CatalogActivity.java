package com.example.hoff;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.hoff.adapter.MyAdapter;
import com.example.hoff.model.ProductResponse;
import com.example.hoff.retrofit.APIConfig;
import com.example.hoff.retrofit.APIService;
import com.example.hoff.retrofit.APIServiceConstructor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager= new GridLayoutManager ( this, 2 );;
    MyAdapter myAdapter;
    Spinner spinnerProducts;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_catalog );

        // Установка кастомного actionBar (Каталог-активити)
        setActionBarTitleCatalog ();


        spinnerProducts=findViewById ( R.id.spinnerProducts );

        // Работа с RecyclerView и Retrofit
        recyclerView = findViewById ( R.id.recyclerView );

        // Заполнение ключей и значений в HashMap
//        Map<String, String> data = new HashMap<> ();
//        data.put ( "category_id", String.valueOf ( APIConfig.category_id ));
//        data.put ( "sort_by", APIConfig.sort_by );
//        data.put ( "sort_type", APIConfig.sort_type );
//        data.put ( "limit", String.valueOf ( APIConfig.limit ) );
//        data.put ( "offset", String.valueOf ( APIConfig.offset ) );
//        data.put ( "device_id", APIConfig.device_id );
//        data.put ( "isAndroid", APIConfig.isAndroid );
//        data.put ( "app_version", APIConfig.app_version );
//        data.put ( "location", String.valueOf ( APIConfig.location ) );
//        data.put ( "xhoff", APIConfig.xHoff );

        // Создание сервиса и асинронного запроса
        APIService apiService=APIServiceConstructor.CreateService ( APIService.class );
        Call<ProductResponse> call=apiService.getProducts ( APIConfig.category_id,
                                                            APIConfig.sort_by,
                                                            APIConfig.sort_type,
                                                            APIConfig.limit,
                                                            APIConfig.offset,
                                                            APIConfig.device_id,
                                                            APIConfig.isAndroid,
                                                            APIConfig.app_version,
                                                            APIConfig.location);
        call.enqueue ( new Callback<ProductResponse> () {
            @Override
            public void onResponse( Call<ProductResponse> call, Response<ProductResponse> response ) {
                if(response.isSuccessful ()){
                    ProductResponse model = response.body ();

                    myAdapter = new MyAdapter ( model.items );
                    recyclerView.setLayoutManager ( layoutManager );
                    recyclerView.setHasFixedSize ( true );
                    recyclerView.setAdapter ( myAdapter );
                }
            }

            @Override
            public void onFailure( Call<ProductResponse> call, Throwable t ) {

            }
        } );

    }

    private void setActionBarTitleCatalog(){
        getSupportActionBar ().setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.action_bar_catalog );
        getSupportActionBar ().setHomeAsUpIndicator ( R.drawable.ic_line_back );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
    }
}