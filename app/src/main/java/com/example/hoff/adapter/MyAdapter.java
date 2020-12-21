package com.example.hoff.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hoff.R;
import com.example.hoff.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Product> modelList;
    SharedPreferences preferences;

    public MyAdapter( List<Product> modelList ) {
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view =LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.item_layout, parent, false );
        ViewHolder vh = new ViewHolder ( view );
        if(preferences == null){
            preferences =parent.getContext ().getSharedPreferences ( "NICE", Context.MODE_PRIVATE );
        }
        return vh;
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        holder.bind ( modelList.get ( position ) );
    }

    @Override
    public int getItemCount() {
        return modelList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        Button btnSaleProduct;
        TextView newPriceProduct;
        TextView oldPriceProduct;
        TextView textEvent;
        TextView titleProduct;
        TextView statusProduct;
        RatingBar ratingBarProduct;
        TextView numberOfReviewsProduct;
        ImageView imageLike;
        boolean like;
        Product currentProduct;


        public ViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageProduct = itemView.findViewById ( R.id.imageProduct );
            btnSaleProduct = itemView.findViewById ( R.id.btnSaleProduct );
            newPriceProduct = itemView.findViewById ( R.id.newPriceProduct );
            oldPriceProduct = itemView.findViewById ( R.id.oldPriceProduct );
            textEvent = itemView.findViewById ( R.id.textEvent );
            titleProduct = itemView.findViewById ( R.id.titleProduct );
            statusProduct = itemView.findViewById ( R.id.statusProduct );
            ratingBarProduct = itemView.findViewById ( R.id.ratingBarProduct );
            numberOfReviewsProduct = itemView.findViewById ( R.id.numberOfReviewsProduct );
            imageLike = itemView.findViewById ( R.id.imageLike );

            // Происходит клик, если иконка закрашена, то убери раскраску
            // Иначе закрась
            // И сохрани состояние иконки
            imageLike.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    if(preferences.getBoolean ( currentProduct.getId (), false )){
                        imageLike.setImageResource ( R.drawable.ic_unlike );
                        saveData ( currentProduct.getId (), false );
                    }else {
                        imageLike.setImageResource ( R.drawable.ic_like );
                        saveData ( currentProduct.getId (), true );
                    }
                }
            } );

        }

        public void bind( Product product ){
            currentProduct = product;
            // При закреплении элементов идет проверка на состояние иконки: закрашена или не закрашена
            like = preferences.getBoolean ( currentProduct.getId (), true );

            // Image set
            String imageUrl = product.getImage();
            Picasso.with ( itemView.getContext () )
                    .load ( imageUrl )
                    .into ( imageProduct );

            // Title set
            titleProduct.setText ( product.getName ());

            // numberOfReviews set
            numberOfReviewsProduct.setText ( String.valueOf ( "(" + product.getNumberOfReviews () + ")" ));

            //status product set
            statusProduct.setText ( product.getStatusText () );

            //price new and old product set
            newPriceProduct.setText ( String.valueOf ( product.getPrices ().getNew ()) + " ₽" );
            oldPriceProduct.setText ( String.valueOf ( product.getPrices ().getOld () ) + " ₽" );
            // Перечекнутый текст
            oldPriceProduct.setPaintFlags ( oldPriceProduct.getPaintFlags () | Paint.STRIKE_THRU_TEXT_FLAG );

            //rating bar set stars
            ratingBarProduct.setRating ( product.getRating () );

            // Как только заходим на активити все иконки пустые
            // Если до этого мы заходили и закрашивали иконки, то присвой им их состояние
            if(preferences.getBoolean ( currentProduct.getId (), false )){
                imageLike.setImageResource ( R.drawable.ic_like );
            }else {
                imageLike.setImageResource ( R.drawable.ic_unlike );
            }
        }
    }

    // Метод, сохоаняющий состояние иконки
    public void saveData(String id, boolean dataToSave){
        SharedPreferences.Editor editor = preferences.edit ();
        editor.putBoolean ( id, dataToSave );
        editor.apply ();
    }

}
