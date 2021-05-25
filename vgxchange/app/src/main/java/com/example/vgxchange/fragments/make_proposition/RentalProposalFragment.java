package com.example.vgxchange.fragments.make_proposition;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.PropositionApi;
import com.example.vgxchange.api.dto.RentalPropositionToCreate;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.tool.DateTool;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RentalProposalFragment extends Fragment {



    ProductAnnounce productToShow;
    TextView name, description, price, announcerName, category, parutionDate, announcerMail, announcerPseudo;
    ImageView productImage;
    Button btnSendProposal;
    EditText txtProposedPrice;
    DatePicker dpStartDate, dpEndDate;
    Double proposedPrice = 0.0;
    Date proposedStartDate = new Date();
    Date proposedEndDate = new Date();


    public RentalProposalFragment() {
        // Required empty public constructor
    }
    public static RentalProposalFragment newInstance(String param1, String param2) {
        RentalProposalFragment fragment = new RentalProposalFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rental_proposal, container, false);
        productToShow = (ProductAnnounce) getArguments().getSerializable(Constants.selectedAnnounceBundleKey);
        view = mapWithFields(view, productToShow);



        if (productToShow != null) {
            btnSendProposal.setOnClickListener(v -> {

                try {
                    proposedPrice = Double.parseDouble(txtProposedPrice.getText().toString());
                    proposedStartDate = DateTool.getDateFromDatePicker(dpStartDate);
                    proposedEndDate = DateTool.getDateFromDatePicker(dpEndDate);

                    Toast.makeText(getContext(), "Envoi de la proposition ..." + productToShow.getGame().getName() + " PRIX PROPOSE : " + proposedPrice, Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPrefReturn = getActivity().getPreferences(getContext().MODE_PRIVATE);
                    String userId = sharedPrefReturn.getString("userId", "defaultId");

                    RentalPropositionToCreate rentalPropositionToCreate = new RentalPropositionToCreate(productToShow.id,userId, proposedPrice, proposedStartDate, proposedEndDate);
                    Retrofit retrofit = ApiRetrofit.getClient();
                    PropositionApi propositionApi = retrofit.create(PropositionApi.class);
                    Call<BuyingProp> call = propositionApi.add(rentalPropositionToCreate);
                    call.enqueue(new Callback<BuyingProp>() {
                        @Override
                        public void onResponse(Call<BuyingProp> call, Response<BuyingProp> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    Toast.makeText(getContext(), "Proposition envoyée !", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(v).navigate(R.id.action_rentalProposal_to_usersPropositionsFragment);
                                } else {
                                    Toast toast = Toast.makeText(getContext(), "Erreur envoi propositions", Toast.LENGTH_SHORT);
                                    Log.d("Reponse", "pasbon");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BuyingProp> call, Throwable t) {
                            Log.d("ReponseFail", t.getMessage());

                            Toast toast = Toast.makeText(getContext(),"Erreur serveur" , Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Saisie du prix invalide : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


        return view;
    }


    public View mapWithFields(View v, ProductAnnounce product) {
        if (product != null) {
            name = v.findViewById(R.id.detailGameName);
            announcerName = v.findViewById(R.id.person_name);
            announcerMail = v.findViewById(R.id.person_email);
            announcerPseudo = v.findViewById(R.id.person_login);
            parutionDate = v.findViewById(R.id.detailParutionDate);

            price = v.findViewById(R.id.detailPrice);
            category = v.findViewById(R.id.detailProductCategory);
            btnSendProposal = v.findViewById(R.id.btnGoToRating);

            name.setText(product.game.getName());
            announcerName.setText(product.announcer.getName());
            announcerMail.setText(product.announcer.getMail());
            announcerPseudo.setText(product.announcer.getLogin());
            price.setText(Double.toString(product.getPrice()));
            category.setText(product.getGame().getCategory().getLabel());
            parutionDate.setText(DateTool.formatStringDateDDMMYY(product.getParution()));
            v = setProductImage(v, product.getPhotoLink());

            txtProposedPrice = (EditText) v.findViewById(R.id.txtProposedPrice);
            dpStartDate = v.findViewById(R.id.datePickerEnd);
            dpEndDate =  v.findViewById(R.id.datePickerStart);
        }

        return v;
    }


    public View setProductImage(View v, String url) {
        productImage = v.findViewById(R.id.detailProductImage);
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

        return v;
    }


}