package com.example.vgxchange.fragments.my_product;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.R;
import com.example.vgxchange.model.ProductAnnounce;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ProductViewHolder> {

    List<ProductAnnounce> products = new ArrayList<>();
    Context context;


    public MyProductAdapter(Context context, List<ProductAnnounce> products) {

        this.products = products;
        this.context = context;
    }


    @NonNull
    @Override
    public MyProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_product, parent, false);
        return new MyProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ProductAnnounce product = products.get(position);
        holder.bind(product);
        holder.itemView.setOnClickListener(v -> {

            Toast.makeText(context, "CLICK CLICK sur le jeu : " + product.getGame().getName(), Toast.LENGTH_SHORT).show();

        });

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
            //announcerLogin = itemView.findViewById(R.id.product_announcer_login_txt);
            parutionDate = itemView.findViewById(R.id.product_parution_date_txt);
        }


        public void bind(ProductAnnounce product) {
            if (product.getGame() != null) {
                name.setText(product.getGame().getName());
                price.setText(String.valueOf(product.getPrice()));
                //announcerLogin.setText(String.valueOf(product.getAnnouncer().getLogin()));
                parutionDate.setText(String.valueOf(product.getParution()));
                setProductImage(String.valueOf(product.getPhotoLink()));
            }

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
