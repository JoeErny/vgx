 package com.example.vgxchange.fragments.my_product;

 import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.BrowsingApi;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.tool.ProductState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProductFragment extends Fragment {

    SharedPreferences sharedPrefReturn;
    RecyclerView recyclerView;
    Spinner spinnerStatus;
    private ProductStateSpinnerAdapter productStateSpinnerAdapter;
    List<ProductState.State> status = new ArrayList<>();


    List<ProductAnnounce> productAnnounces = new ArrayList<ProductAnnounce>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProductFragment newInstance(String param1, String param2) {
        MyProductFragment fragment = new MyProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_product, container, false);

        spinnerStatus = view.findViewById(R.id.spinnerStatusMyProduct);
        recyclerView = view.findViewById(R.id.recyclerViewMyProduct);

        sharedPrefReturn = getActivity().getPreferences(Context.MODE_PRIVATE);
        String idUser = sharedPrefReturn.getString("userId", "error id");

        status = ProductState.stateList;
        productStateSpinnerAdapter = new ProductStateSpinnerAdapter(view.getContext(), android.R.layout.simple_spinner_item,  status);
        spinnerStatus.setAdapter(productStateSpinnerAdapter);

        Retrofit retrofit = ApiRetrofit.getClient();
        BrowsingApi browsingApi = retrofit.create(BrowsingApi.class);


        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ProductState.State stateSelect = productStateSpinnerAdapter.getItem(position);

                Call<List<ProductAnnounce>> call = browsingApi.getProductByUser(idUser, stateSelect.ordinal());
                call.enqueue(new Callback<List<ProductAnnounce>>() {
                    @Override
                    public void onResponse(Call<List<ProductAnnounce>> call, Response<List<ProductAnnounce>> response) {
                        Log.d("Reponse", String.valueOf(response.code()));
                        if(response.body() != null)
                        {
                            productAnnounces = response.body();

                            MyProductAdapter pAdapter = new MyProductAdapter(view.getContext(), productAnnounces);
                            recyclerView.setAdapter(pAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getContext(), "Error System", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<ProductAnnounce>> call, Throwable t) {
                        Log.d("Reponse", t.getMessage());
                        Toast toast = Toast.makeText(getContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                });

                if (view != null) {

                    MyProductAdapter pAdapter = new MyProductAdapter(view.getContext(), productAnnounces);
                    recyclerView.setAdapter(pAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
                Toast.makeText(getContext(), "NB ELEMENTS :  " + productAnnounces.size(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });


        return view;
    }
}