package com.example.vgxchange.fragments.browsing_products;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.tool.DateTool;

import java.io.InputStream;
import java.net.URL;


public class DetailProductFragment extends Fragment implements View.OnClickListener {


    ProductAnnounce productToShow;
    TextView name, description, price, announcerName, category, parutionDate, announcerMail, announcerPseudo;
    ImageView productImage;
    Button btnRentalProposal, btnBuyingProposal;


    public DetailProductFragment(ProductAnnounce selectedProduct) {
        productToShow = selectedProduct;
    }

    public DetailProductFragment() {
    }

    public static DetailProductFragment newInstance(ProductAnnounce selectedProduct) {
        DetailProductFragment fragment = new DetailProductFragment(selectedProduct);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {

        View view = inflater.inflate(R.layout.detail_product_announce, container, false);
        productToShow = (ProductAnnounce) getArguments().getSerializable(Constants.selectedAnnounceBundleKey);
        view = mapProductWithFields(view, productToShow);

        if (productToShow != null) {
            btnBuyingProposal.setOnClickListener(v -> {
                Bundle selectedProductBundle = new Bundle();
                selectedProductBundle.putSerializable(Constants.selectedAnnounceBundleKey, productToShow);
                Navigation.findNavController(v).navigate(R.id.action_detail_product_announce_to_buyingProposal, selectedProductBundle);
            });
            btnRentalProposal.setOnClickListener(v -> {
                Bundle selectedProductBundle = new Bundle();
                selectedProductBundle.putSerializable(Constants.selectedAnnounceBundleKey, productToShow);
                Navigation.findNavController(v).navigate(R.id.action_detail_product_announce_to_rentalProposal, selectedProductBundle);
            });


        }
        return view;
    }


    public View mapProductWithFields(View v, ProductAnnounce product) {
        if (product != null) {
            name = v.findViewById(R.id.detailGameName);
            announcerName = v.findViewById(R.id.person_name);
            announcerMail = v.findViewById(R.id.person_email);
            announcerPseudo = v.findViewById(R.id.person_login);
            parutionDate = v.findViewById(R.id.detailParutionDate);
            productImage = v.findViewById(R.id.detailProductImage);
            price = v.findViewById(R.id.detailPrice);
            category = v.findViewById(R.id.detailProductCategory);
            btnBuyingProposal = v.findViewById(R.id.btnBuyingProposition);
            btnRentalProposal = v.findViewById(R.id.btnRentalProposition);
            name.setText(product.game.getName());
            announcerName.setText(product.announcer.getName());
            announcerMail.setText(product.announcer.getMail());
            announcerPseudo.setText(product.announcer.getLogin());
            price.setText(Double.toString(product.getPrice()));
            category.setText(product.getGame().getCategory().getLabel());

            parutionDate.setText(DateTool.formatStringDateDDMMYY(product.getParution()));

            setProductImage(product.getPhotoLink());
        }

        return v;
    }


    @Override
    public void onClick(View v) {

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
