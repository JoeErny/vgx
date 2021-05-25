package com.example.vgxchange.fragments.browsing_products;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.tool.DateTool;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductAnnounceAdapter extends RecyclerView.Adapter<ProductAnnounceAdapter.ProductViewHolder> {

    List<ProductAnnounce> products = new ArrayList<>();
    Context context;


    public ProductAnnounceAdapter(Context context, List<ProductAnnounce> products) {

        this.products = products;
        this.context = context;
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductAnnounce product = products.get(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(v -> {

            //Transmettre le produit en bundle
            Bundle selectedProductBundle = new Bundle();
            selectedProductBundle.putSerializable(Constants.selectedAnnounceBundleKey, product);
            Navigation.findNavController(v).navigate(R.id.action_browsingFragment_to_detail_product_announce, selectedProductBundle);
        });
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_product, parent, false);
        return new ProductViewHolder(view);
    }





    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, price, announcerLogin, parutionDate;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.product_name_txt);
            price = itemView.findViewById(R.id.product_price_txt);
            announcerLogin = itemView.findViewById(R.id.buyAnnouncerLogin);
            parutionDate = itemView.findViewById(R.id.product_parution_date_txt);
        }


        public void bind(ProductAnnounce product) {
            if (product.getGame() != null) {
                name.setText(product.getGame().getName());
            }
            price.setText(String.valueOf(product.getPrice()));
            announcerLogin.setText(product.getAnnouncer().getLogin());
            parutionDate.setText(DateTool.formatStringDateDDMMYY(product.getParution()));
            setProductImage(product.getPhotoLink());
        }


        public void setProductImage(String url) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = (InputStream) new URL(url).getContent();
                        Drawable d = Drawable.createFromStream(is, url);
                        productImage.setImageDrawable(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
