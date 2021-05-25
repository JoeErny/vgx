package com.example.vgxchange.fragments.adding_product_announce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.ProductApi;
import com.example.vgxchange.api.dto.ProductToCreate;
import com.example.vgxchange.database.RoomDbManager;
import com.example.vgxchange.model.Game;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment {


    Spinner spinnerGame;
    List<Game> games = new ArrayList<>();
    private GameSpinnerAdapter gameSpinnerAdapter;
    Game selectedGame = null;
    SharedPreferences sharedPrefReturn;

    EditText photoLink;
    EditText price;
    String idUser;
    String idGame;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sharedPrefReturn = getActivity().getPreferences(Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        Button btn = view.findViewById(R.id.button_add_product);
        btn.setOnClickListener(v -> { onClickAddProduct(view); });

        price = view.findViewById(R.id.editTextNumber_price_add_product);
        photoLink = view.findViewById(R.id.editText_add_product_photoLink);

        RoomDbManager db = RoomDbManager.getInstance(getContext());
        spinnerGame = view.findViewById(R.id.spinnerGame);
        games = db.GameDao().getAll();

        gameSpinnerAdapter = new GameSpinnerAdapter(view.getContext(), android.R.layout.simple_spinner_item, games);
        spinnerGame.setAdapter(gameSpinnerAdapter);

        spinnerGame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Game game = gameSpinnerAdapter.getItem(position);
                selectedGame = game;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });
        return view;
    }

    private void onClickAddProduct(View view) {


        idGame = selectedGame.getId();
        idUser = sharedPrefReturn.getString("userId", "error id");

        Retrofit retrofit = ApiRetrofit.getClient();
        ProductApi productApi = retrofit.create(ProductApi.class);


        if(String.valueOf(photoLink.getText()).matches("") || String.valueOf(price.getText().toString()).matches("") || idGame.matches(""))
        {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
            return;
        }
        String photoLinkStr = String.valueOf(photoLink.getText());
        Double priceStr = Double.parseDouble(price.getText().toString());
        ProductToCreate  productToCreate= new ProductToCreate(priceStr, photoLinkStr, idUser,idGame);

        Call<ProductToCreate> call = productApi.postProduct(productToCreate);
        call.enqueue(new Callback<ProductToCreate>() {
            @Override
            public void onResponse(Call<ProductToCreate> call, Response<ProductToCreate> response) {
                Log.d("Reponse", String.valueOf(response.code()));

                if(response.body() != null)
                {
                    Toast toast = Toast.makeText(getContext(), "Ajout réussi", Toast.LENGTH_SHORT);
                    toast.show();

                    Navigation.findNavController(view).navigate(R.id.action_addProductFragment_to_myProductFragment);
                }
                else
                {
                    Toast toast = Toast.makeText(getContext(), "Ajout échoué", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<ProductToCreate> call, Throwable t) {
                Log.d("Reponse", t.getMessage());
                Toast toast = Toast.makeText(getContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}