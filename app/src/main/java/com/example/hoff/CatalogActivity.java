package com.example.hoff;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hoff.adapter.MyAdapter;
import com.example.hoff.model.ProductResponse;
import com.example.hoff.retrofit.APIConfig;
import com.example.hoff.retrofit.APIService;
import com.example.hoff.retrofit.APIServiceConstructor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CatalogActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager=new GridLayoutManager ( this, 2 );
    ;
    MyAdapter myAdapter;
    Spinner spinnerProducts;
    APIService apiService;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_catalog );

        // Установка кастомного actionBar (Каталог-активити)
        setActionBarTitleCatalog ();

        // spinner find
        spinnerProducts=findViewById ( R.id.spinnerProducts );

        // Устанвка RecyclerView и получение Retrofit constructor
        Retrofit retrofit=APIServiceConstructor.getInstance ();
        apiService=retrofit.create ( APIService.class );

        recyclerView=findViewById ( R.id.recyclerView );
        recyclerView.setLayoutManager ( layoutManager );
        recyclerView.setHasFixedSize ( true );

        // Spinner
        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource ( this, R.array.products, android.R.layout.simple_spinner_item );
        spinnerAdapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinnerProducts.setAdapter ( spinnerAdapter );
        spinnerProducts.setOnItemSelectedListener ( this );

    }

    // Сортировка товаров
    // Дешевые товары
    @SuppressLint("CheckResult")
    private void fetchDataDesc() {
        // Запрос выполняется в IO потоке, а результат показывается в UI потоке
        apiService.getProducts ( APIConfig.category_id,
                                 "price",
                                 "asc",
                                 APIConfig.limit,
                                 APIConfig.offset,
                                 APIConfig.device_id,
                                 APIConfig.isAndroid,
                                 APIConfig.app_version,
                                 APIConfig.location )
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread () )
                .subscribe ( new DisposableSingleObserver<ProductResponse> () {
                    @Override
                    public void onSuccess( @NonNull ProductResponse productResponse ) {
                        // Установка полученных данных, если запрос совершен успешно
                        displayData ( productResponse );

                    }

                    @Override
                    public void onError( @NonNull Throwable e ) {
                        // Если вдруг будет ошибка...
                    }
                } );
    }


    // Дорогие товары
    @SuppressLint("CheckResult")
    private void fetchDataAsc() {
        // Запрос выполняется в IO потоке, а результат показывается в UI потоке
        apiService.getProducts ( APIConfig.category_id,
                                 "price",
                                 "desc",
                                 APIConfig.limit,
                                 APIConfig.offset,
                                 APIConfig.device_id,
                                 APIConfig.isAndroid,
                                 APIConfig.app_version,
                                 APIConfig.location )
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread () )
                .subscribe ( new DisposableSingleObserver<ProductResponse> () {
                    @Override
                    public void onSuccess( @NonNull ProductResponse productResponse ) {
                        // Установка полученных данных, если запрос совершен успешно
                        displayData ( productResponse );

                    }

                    @Override
                    public void onError( @NonNull Throwable e ) {
                        // Если вдруг будет ошибка...
                    }
                } );
    }


    // Популярные товары
    @SuppressLint("CheckResult")
    private void fetchDataPopular() {
        // Запрос выполняется в IO потоке, а результат показывается в UI потоке
        apiService.getProducts ( APIConfig.category_id,
                                 APIConfig.sort_by, // popular
                                 APIConfig.sort_type,
                                 APIConfig.limit,
                                 APIConfig.offset,
                                 APIConfig.device_id,
                                 APIConfig.isAndroid,
                                 APIConfig.app_version,
                                 APIConfig.location )
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread () )
                .subscribe ( new DisposableSingleObserver<ProductResponse> () {
                    @Override
                    public void onSuccess( @NonNull ProductResponse productResponse ) {
                        // Установка полученных данных, если запрос совершен успешно
                        displayData ( productResponse );

                    }

                    @Override
                    public void onError( @NonNull Throwable e ) {
                        // Если вдруг будет ошибка...
                    }
                } );
    }


    // Товары по скидкам
    @SuppressLint("CheckResult")
    private void fetchDataDiscount() {
        // Запрос выполняется в IO потоке, а результат показывается в UI потоке
        apiService.getProducts ( APIConfig.category_id,
                                 "discount",
                                 APIConfig.sort_type,
                                 APIConfig.limit,
                                 APIConfig.offset,
                                 APIConfig.device_id,
                                 APIConfig.isAndroid,
                                 APIConfig.app_version,
                                 APIConfig.location )
                .subscribeOn ( Schedulers.io () )
                .observeOn ( AndroidSchedulers.mainThread () )
                .subscribe ( new DisposableSingleObserver<ProductResponse> () {
                    @Override
                    public void onSuccess( @NonNull ProductResponse productResponse ) {
                        // Установка полученных данных, если запрос совершен успешно
                        displayData ( productResponse );

                    }

                    @Override
                    public void onError( @NonNull Throwable e ) {
                        // Если вдруг будет ошибка...
                    }
                } );
    }

    // Привязка данных
    private void displayData( ProductResponse responses ) {
        myAdapter=new MyAdapter ( responses.items );
        recyclerView.setAdapter ( myAdapter );
    }

    //     Клик на item`ы спиннера
    @Override
    public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
        String itemPosition=parent.getItemAtPosition ( position ).toString ();
        if (itemPosition.equals ( "Сначала дешевые" )) {
            fetchDataDesc ();
        } else if (itemPosition.equals ( "Сначала дорогие" )) {
            fetchDataAsc ();
        } else if (itemPosition.equals ( "Популярные товары" )) {
            fetchDataPopular ();
        } else if (itemPosition.equals ( "По скидкам" )) {
            fetchDataDiscount ();
        }
    }

    @Override
    public void onNothingSelected( AdapterView<?> parent ) {

    }

    private void setActionBarTitleCatalog() {
        getSupportActionBar ().setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.action_bar_catalog );
        getSupportActionBar ().setHomeAsUpIndicator ( R.drawable.ic_line_back );
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
    }
}