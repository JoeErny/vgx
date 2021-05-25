package com.example.vgxchange.fragments.make_proposition;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.PropositionApi;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.tool.DateTool;
import com.example.vgxchange.tool.PropositionState;
import com.example.vgxchange.tool.PropositionType;
import com.example.vgxchange.tool.WhichProposalsToDisplay;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BuyingProposalDetail extends Fragment {

    BuyingProp proposalToShow;
    ProductAnnounce productToShow;
    TextView name, description, price, announcerName, category, parutionDate,
            announcerMail, announcerPseudo, proposedPrice, propDateStart,
            propDateEnd, propFrom, propTo, propRentalLabel,
            propState;
    ImageView productImage;
    Button btnMainAction, btnAcceptProposal, btnDeclineProposal;
    String userId;


    public BuyingProposalDetail() {
    }

    public static BuyingProposalDetail newInstance() {
        BuyingProposalDetail fragment = new BuyingProposalDetail();
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
        View view = inflater.inflate(R.layout.fragment_buying_proposal_detail, container, false);
        proposalToShow = (BuyingProp) getArguments().getSerializable(Constants.selectedBuyingProposalBundleKey);
        view = mapWithFields(view, proposalToShow);
        btnMainAction = view.findViewById(R.id.btnGoToRating);
        btnAcceptProposal = view.findViewById(R.id.accept_proposal);
        btnDeclineProposal = view.findViewById(R.id.decline_proposal);
        btnAcceptProposal.setBackgroundColor(Color.parseColor("green"));
        btnDeclineProposal.setBackgroundColor(Color.parseColor("red"));

        SharedPreferences sharedPrefReturn = getActivity().getPreferences(getContext().MODE_PRIVATE);
        userId = sharedPrefReturn.getString("userId", "defaultId");
        //IF MY SENT PROPOSAL
        if (proposalToShow.proposingUser.id.equals(userId)) {
            btnAcceptProposal.setVisibility(View.GONE);
            btnDeclineProposal.setVisibility(View.GONE);
            if (proposalToShow.propositionState == PropositionState.WAITING.ordinal()) {
                btnMainAction.setVisibility(View.GONE);

            } else if (proposalToShow.propositionState == PropositionState.ACCEPTED.ordinal()) {
                btnMainAction.setText("Procéder au paiement");
                btnMainAction.setOnClickListener(v -> {
                    Bundle selectedPropositionBundle = new Bundle();
                    selectedPropositionBundle.putSerializable(Constants.selectedBuyingProposalBundleKey, proposalToShow);
                    Navigation.findNavController(v).navigate(R.id.action_buyingProposalDetail_to_paymentFragment, selectedPropositionBundle);
                });

            } else if (proposalToShow.propositionState == PropositionState.DECLINED.ordinal()) {
                btnMainAction.setVisibility(View.GONE);
            } else if (proposalToShow.propositionState == PropositionState.TERMINATED.ordinal()) {
                btnMainAction.setText("Evaluer l'echange");
                btnMainAction.setOnClickListener(v -> {
                    Bundle selectedPropositionBundle = new Bundle();
                    selectedPropositionBundle.putSerializable(Constants.selectedBuyingProposalBundleKey, proposalToShow);
                    Navigation.findNavController(v).navigate(R.id.action_buyingProposalDetail_to_ratingFragment, selectedPropositionBundle);
                });
            } else {
                btnMainAction.setVisibility(View.GONE);
            }
        }
        //ELSE NOT MY SENT PROPOSAL
        else {
            btnMainAction.setVisibility(View.GONE);
            if (proposalToShow.propositionState == PropositionState.WAITING.ordinal()) {

                btnAcceptProposal.setOnClickListener(v -> {
                    HandleProposalState(v, PropositionState.ACCEPTED.ordinal());
                });
                btnDeclineProposal.setOnClickListener(v -> {
                    HandleProposalState(v, PropositionState.DECLINED.ordinal());
                });

            } else if (proposalToShow.propositionState == PropositionState.ACCEPTED.ordinal()) {
                btnAcceptProposal.setVisibility(View.GONE);
                btnDeclineProposal.setVisibility(View.GONE);

            } else if (proposalToShow.propositionState == PropositionState.DECLINED.ordinal()) {
                btnAcceptProposal.setVisibility(View.GONE);
                btnDeclineProposal.setVisibility(View.GONE);

            } else if (proposalToShow.propositionState == PropositionState.TERMINATED.ordinal()) {
                btnAcceptProposal.setVisibility(View.GONE);
                btnDeclineProposal.setVisibility(View.GONE);

            } else {
                btnAcceptProposal.setVisibility(View.GONE);
                btnDeclineProposal.setVisibility(View.GONE);
            }

        }

        return view;
    }


    public void HandleProposalState(View v, int state) {
        Retrofit retrofit = ApiRetrofit.getClient();
        PropositionApi propositionApi = retrofit.create(PropositionApi.class);
        Call call = propositionApi.handleReceivedProposal(proposalToShow.id, userId, state);
        ;
        call.enqueue(new Callback<BuyingProp>() {
            @Override
            public void onResponse(Call<BuyingProp> call, Response<BuyingProp> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        BuyingProp result = response.body();
                        if (result.propositionState == state) {
                            Toast.makeText(getContext(), "Proposition " + PropositionState.values()[state].getDisplayValue(), Toast.LENGTH_SHORT).show();
                            proposalToShow = result;
                            Bundle whichProposalsToDisplayBundle = new Bundle();
                            whichProposalsToDisplayBundle.putSerializable(Constants.whichProposalsBundleKey, WhichProposalsToDisplay.received);
                            Navigation.findNavController(v).navigate(R.id.action_buyingProposalDetail_to_usersPropositionsFragment, whichProposalsToDisplayBundle);


                        } else {
                            Toast.makeText(getContext(), "Echec modification etat", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    }
                }
            }

            @Override
            public void onFailure(Call<BuyingProp> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
                CharSequence text = "Erreur Serveur";
                Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


    public View mapWithFields(View v, BuyingProp buyingProp) {
        if (buyingProp != null) {
            name = v.findViewById(R.id.detailGameName);
            announcerName = v.findViewById(R.id.person_name);
            announcerMail = v.findViewById(R.id.person_email);
            announcerPseudo = v.findViewById(R.id.person_login);
            parutionDate = v.findViewById(R.id.detailParutionDate);
            price = v.findViewById(R.id.detailPrice);
            category = v.findViewById(R.id.detailProductCategory);
            proposedPrice = v.findViewById(R.id.propPrice);
            propFrom = v.findViewById(R.id.propFromDate);
            propTo = v.findViewById(R.id.propToDate);
            propDateStart = v.findViewById(R.id.propDateStart);
            propDateEnd = v.findViewById(R.id.propDateEnd);
            propDateEnd = v.findViewById(R.id.propDateEnd);
            propState = v.findViewById(R.id.propState);

            name.setText(buyingProp.productAnnounce.game.getName());
            announcerName.setText(buyingProp.productAnnounce.announcer.getName());
            announcerMail.setText(buyingProp.productAnnounce.announcer.getMail());
            announcerPseudo.setText(buyingProp.productAnnounce.announcer.getLogin());
            price.setText(Double.toString(buyingProp.productAnnounce.getPrice()));
            category.setText(buyingProp.productAnnounce.getGame().getCategory().getLabel());
            parutionDate.setText(DateTool.formatStringDateDDMMYY(buyingProp.productAnnounce.getParution()));
            proposedPrice.setText(Double.toString(buyingProp.getProposedAmount()));
            propState.setText(PropositionState.values()[proposalToShow.getPropositionState()].getDisplayValue());
            v = setProductImage(v, buyingProp.productAnnounce.getPhotoLink());


            try {
                propState.setBackgroundColor(Color.parseColor(PropositionState.values()[proposalToShow.propositionState].getColorString()));
            } catch (Exception e) {
                Log.d("PARSE COLOR ERROR", e.getMessage());
            }

            if (proposalToShow.propositionType == PropositionType.RENTAL.ordinal()) {
                propDateStart.setText(DateTool.formatStringDateDDMMYY(proposalToShow.getRentalStart()));
                propDateEnd.setText(DateTool.formatStringDateDDMMYY(proposalToShow.getRentalEnd()));
            } else {
                propFrom.setVisibility(View.GONE);
                propTo.setVisibility(View.GONE);
                propDateStart.setVisibility(View.GONE);
                propDateEnd.setVisibility(View.GONE);
            }

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