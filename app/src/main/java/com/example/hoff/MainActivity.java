package com.example.hoff;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        // Установка кастомного actionBar
        setActionBarTitle ();

    }

    private void setActionBarTitle(){
        getSupportActionBar ().setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.action_bar_layout );
    }

    // Переход на каталог-активити
    public void btnGoClick( View view ){
        Intent intent = new Intent (MainActivity.this, CatalogActivity.class);
        startActivity ( intent );
    }
}