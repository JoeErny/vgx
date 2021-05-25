package com.example.vgxchange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.api.controllers.PaymentApi;
import com.example.vgxchange.api.dto.PaymentToCreate;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.model.Payment;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.tool.PaymentState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentFragment extends Fragment {

    BuyingProp proposalToShow;
    Button payButton;
    TextView paymentAmount;
    Payment paymentResult;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        if (getArguments() != null) {
            proposalToShow = (BuyingProp) getArguments().getSerializable(Constants.selectedBuyingProposalBundleKey);
        }
        payButton = view.findViewById(R.id.btn_payment);
        paymentAmount = view.findViewById(R.id.paymentAmount);
        paymentAmount.setText(Double.toString(proposalToShow.proposedAmount));
        if (proposalToShow != null) {
            payButton.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Paiement en cours ... " + proposalToShow.proposedAmount+"€", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPrefReturn = getActivity().getPreferences(getContext().MODE_PRIVATE);
                String userId = sharedPrefReturn.getString("userId", "defaultId");
                PaymentToCreate paymentToCreate = new PaymentToCreate(proposalToShow.id, userId, proposalToShow.proposedAmount);
                Retrofit retrofit = ApiRetrofit.getClient();
                PaymentApi paymentApi = retrofit.create(PaymentApi.class);
                Call<Payment> call = paymentApi.add(paymentToCreate);
                call.enqueue(new Callback<Payment>() {
                    @Override
                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                paymentResult = response.body();
                                if (paymentResult.status == PaymentState.completed.ordinal()) {
                                    Toast.makeText(getContext(), "Paiement effectué !", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(v).navigate(R.id.action_paymentFragment_to_usersPropositionsFragment);
                                } else if (paymentResult.status == PaymentState.failure.ordinal()) {
                                    Toast.makeText(getContext(), "Refus de la banque, renouvelez le paiement", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Echec du paiement, erreur serveur", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast toast = Toast.makeText(getContext(), "Echec du paiement, erreur serveur", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Payment> call, Throwable t) {
                        Log.d("ReponseFail", t.getMessage());
                        Toast toast = Toast.makeText(getContext(), "Erreur serveur", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            });
        }


        return view;

    }
}