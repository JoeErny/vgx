package com.example.vgxchange.fragments.browsing_products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.api.controllers.BrowsingApi;
import com.example.vgxchange.api.controllers.BrowsingApiController;
import com.example.vgxchange.api.controllers.CategoryApi;
import com.example.vgxchange.database.RoomDbManager;
import com.example.vgxchange.database.service.BrowsingDbManager;
import com.example.vgxchange.database.service.LocalDbPopulator;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.model.Game;
import com.example.vgxchange.R;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BrowsingProductsFragment extends Fragment {
    RecyclerView recyclerView;
    List<ProductAnnounce> productAnnounces = new ArrayList<ProductAnnounce>();

    Spinner spinnerCategories;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    List<Category> categories = new ArrayList<>();
    Category selectedCategory = null;
    Game selectedGame;


    public BrowsingProductsFragment() {
        // Required empty public constructor
    }


    public static BrowsingProductsFragment newInstance() {
        BrowsingProductsFragment fragment = new BrowsingProductsFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.browsing_products, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        spinnerCategories = view.findViewById(R.id.spinnerCategories);

        RoomDbManager db = RoomDbManager.getInstance(getContext());
        productAnnounces = db.ProductAnnounceDao().getAll();


        Retrofit retrofit = ApiRetrofit.getClient();
        CategoryApi categoryApi = retrofit.create(CategoryApi.class);
        Call<List<Category>> call = categoryApi.getAll();


        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        categories = response.body();
                        categorySpinnerAdapter = new CategorySpinnerAdapter(view.getContext(),
                                android.R.layout.simple_spinner_item,
                                categories);
                        spinnerCategories.setAdapter(categorySpinnerAdapter);
                        categories.add(0, new Category("default", "All"));
                    } else {
                        Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
            }
        });

        Retrofit retrofitProd = ApiRetrofit.getClient();
        BrowsingApi browsingApi = retrofitProd.create(BrowsingApi.class);
        Call<List<ProductAnnounce>> callProducts;
        callProducts = browsingApi.getAllProducts();
        callProducts.enqueue(new Callback<List<ProductAnnounce>>() {
            @Override
            public void onResponse(Call<List<ProductAnnounce>> call, Response<List<ProductAnnounce>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        productAnnounces = response.body();
                        ProductAnnounceAdapter pAdapter = new ProductAnnounceAdapter(view.getContext(), productAnnounces);
                        recyclerView.setAdapter(pAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    } else {
                        Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProductAnnounce>> call, Throwable t) {
                Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
            }
        });


        // Set the custom adapter to the spinner
        // You can create an anonymous listener to handle the event when is selected an spinner item

        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Retrofit retrofit = ApiRetrofit.getClient();

                BrowsingApi browsingApi = retrofit.create(BrowsingApi.class);
                Call<List<ProductAnnounce>> call;

                Category selectedCategory = categorySpinnerAdapter.getItem(position);

                if (position == 0) {
                    call = browsingApi.getAllProducts();
                } else {
                    call = browsingApi.getAllProductsByCategory(selectedCategory.getId());
                }

                call.enqueue(new Callback<List<ProductAnnounce>>() {
                    @Override
                    public void onResponse(Call<List<ProductAnnounce>> call, Response<List<ProductAnnounce>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                productAnnounces = response.body();
                                ProductAnnounceAdapter pAdapter = new ProductAnnounceAdapter(view.getContext(), productAnnounces);
                                recyclerView.setAdapter(pAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            } else {
                                Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductAnnounce>> call, Throwable t) {
                        Toast.makeText(getContext(), "Erreur Api",Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        return view;
    }


}





