package com.example.vgxchange.fragments.make_proposition;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.tool.DateTool;
import com.example.vgxchange.tool.PropositionState;
import com.example.vgxchange.tool.PropositionType;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuyingPropositionRecyclerViewAdapter extends RecyclerView.Adapter<BuyingPropositionRecyclerViewAdapter.BuyingPropositionViewHolder> {


    List<BuyingProp> buyingPropositions = new ArrayList<>();
    Context context;
    TextView productGameName, description, proposedPrice, announcerLogin, propositionState, parutionDate, from, to, dateStart, dateEnd;
    ImageView productImage;
    CardView cardViewProposal;

    public BuyingPropositionRecyclerViewAdapter(Context context, List<BuyingProp> buyingPropositions) {
        this.buyingPropositions = buyingPropositions;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull BuyingPropositionViewHolder holder, int position) {
        BuyingProp prop = buyingPropositions.get(position);
        holder.bind(prop);
        holder.itemView.setOnClickListener(v -> {
            //Transmettre le produit en bundle
            Bundle selectedPropositionBundle = new Bundle();
            selectedPropositionBundle.putSerializable(Constants.selectedBuyingProposalBundleKey, prop);
            Navigation.findNavController(v).navigate(R.id.action_usersPropositionsFragment_to_buyingProposalDetail, selectedPropositionBundle);
        });

    }


    @NonNull
    @Override
    public BuyingPropositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_buying_proposal_row, parent, false);
        return new BuyingPropositionRecyclerViewAdapter.BuyingPropositionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return buyingPropositions.size();
    }

    public class BuyingPropositionViewHolder extends RecyclerView.ViewHolder {


        public BuyingPropositionViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.buyProductImage);
            productGameName = itemView.findViewById(R.id.buyProductName);
            proposedPrice = itemView.findViewById(R.id.buyProposedPrice);
            description = itemView.findViewById(R.id.buyProductDescription);
            announcerLogin = itemView.findViewById(R.id.buyAnnouncerLogin);
            parutionDate = itemView.findViewById(R.id.buyPropositionDate);
            propositionState = itemView.findViewById(R.id.buyPropositionState);
            dateStart = itemView.findViewById(R.id.propDateStart);
            dateEnd = itemView.findViewById(R.id.propDateEnd);
            from = itemView.findViewById(R.id.propFromDate);
            to = itemView.findViewById(R.id.propToDate);
            cardViewProposal = itemView.findViewById(R.id.cardViewProposal);
        }


        public void bind(BuyingProp proposition) {
            productGameName.setText(proposition.productAnnounce.getGame().getName());
            announcerLogin.setText(proposition.productAnnounce.announcer.getLogin());
            parutionDate.setText(DateTool.formatStringDateDDMMYY(proposition.dateSubmission));
            setProductImage(proposition.getProductAnnounce().getPhotoLink());
            proposedPrice.setText(String.valueOf(proposition.getProposedAmount()));
            propositionState.setText(PropositionState.values()[proposition.propositionState].getDisplayValue());

            try {
                propositionState.setBackgroundColor(Color.parseColor(PropositionState.values()[proposition.propositionState].getColorString()));
            } catch (Exception e) {
                Log.d("PARSE COLOR ERROR", e.getMessage());
            }

            if (proposition.propositionType == PropositionType.RENTAL.ordinal()) {
                dateStart.setText(DateTool.formatStringDateDDMMYY(proposition.getRentalStart()));
                dateEnd.setText(DateTool.formatStringDateDDMMYY(proposition.getRentalEnd()));

            } else {
                from.setVisibility(View.GONE);
                to.setVisibility(View.GONE);
                dateStart.setVisibility(View.GONE);
                dateEnd.setVisibility(View.GONE);
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
