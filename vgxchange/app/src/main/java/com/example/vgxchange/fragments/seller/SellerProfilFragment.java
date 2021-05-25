package com.example.vgxchange.fragments.seller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.SellerProfilApi;
import com.example.vgxchange.api.controllers.SellerProfilController;
import com.example.vgxchange.api.controllers.UserApi;
import com.example.vgxchange.fragments.menu_navigation.UserFragment;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.model.SellerProfil;
import com.example.vgxchange.model.User;
import com.example.vgxchange.network.ApiRetrofit;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SellerProfilFragment extends Fragment {


    SellerProfil sellerProfil = new SellerProfil();
    public SellerProfilFragment() {
        // Required empty public constructor
    }

    public static SellerProfilFragment newInstance(String param1, String param2) {
        SellerProfilFragment fragment = new SellerProfilFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seller_profil, container, false);
        User vendorSelected = (User) getArguments().getSerializable(Constants.selectedVendorBundleKey);

        // Infos du bundle
        TextView vendorFirstName = view.findViewById(R.id.vendor_firstName);
        vendorFirstName.setText(vendorSelected.login);

        TextView vendorEmail = view.findViewById(R.id.vendor_email);
        vendorEmail.setText(vendorSelected.mail);

        // Infos de retrofit

        Retrofit retrofit = ApiRetrofit.getClient();
        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);


        // Number of sales

        Call<Integer> call = sellerProfilApi.getNumberOfSales(vendorSelected.id);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil.sellerStat =Integer.parseInt(response.body().toString());
                        TextView vendorNumberOfSales = view.findViewById(R.id.vendor_numberOfSales);
                        vendorNumberOfSales.setText(String.valueOf(sellerProfil.sellerStat));

                    }
                    else{
                        Log.d("ReponseFail :", "Body is null");
                    }
                }
                else{
                    Log.d("ReponseFail :", "Response not successfull");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });


        // Number of rentals

        call = sellerProfilApi.getNumberOfRentals(vendorSelected.id);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil.sellerStat =Integer.parseInt(response.body().toString());
                        TextView vendorNumberOfRentals = view.findViewById(R.id.vendor_numberOfRentals);
                        vendorNumberOfRentals.setText(String.valueOf(sellerProfil.sellerStat));
                    }
                    else{
                        Log.d("ReponseFail :", "Body is null");
                    }
                }
                else{
                    Log.d("ReponseFail :", "Response not successfull");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });

        // Rating

        call = sellerProfilApi.getRating(vendorSelected.id);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil.sellerStat =Integer.parseInt(response.body().toString());
                        TextView vendorRating = view.findViewById(R.id.vendor_rating);
                        vendorRating.setText(String.valueOf(sellerProfil.sellerStat));
                    }
                    else{
                        Log.d("ReponseFail :", "Body is null");
                    }
                }
                else{
                    Log.d("ReponseFail :", "Response not successfull");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });

        return view;
    }
}