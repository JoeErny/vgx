package com.example.vgxchange.fragments.make_proposition;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.PropositionApi;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.tool.WhichProposalsToDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UsersPropositionsFragment extends Fragment {
    RecyclerView recyclerViewBuyingPropositions;
    List<BuyingProp> usersBuyingPropositions;
    WhichProposalsToDisplay which;

    private String mParam1;
    private String mParam2;

    public UsersPropositionsFragment() {
        // Required empty public constructor
    }

    public static UsersPropositionsFragment newInstance(String param1, String param2) {
        UsersPropositionsFragment fragment = new UsersPropositionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_propositions, container, false);
        recyclerViewBuyingPropositions = view.findViewById(R.id.recyclerViewUsersPropositions);

        if(getArguments()!=null)
        {
             which = (WhichProposalsToDisplay) getArguments().getSerializable(Constants.whichProposalsBundleKey);
        }

        SharedPreferences sharedPrefReturn = getActivity().getPreferences(getContext().MODE_PRIVATE);
        String userId = sharedPrefReturn.getString("userId", "defaultId");
        Retrofit retrofit = ApiRetrofit.getClient();
        PropositionApi propositionApi = retrofit.create(PropositionApi.class);
        Call<List<BuyingProp>> call;
        if(which == WhichProposalsToDisplay.received)
        {
            call = propositionApi.getAllReceivedProposalsByUser(userId);
        }
        else {
            call = propositionApi.getAllBuyingProposalsByUser(userId);
        }

            call.enqueue(new Callback<List<BuyingProp>>() {
                @Override
                public void onResponse(Call<List<BuyingProp>> call, Response<List<BuyingProp>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            usersBuyingPropositions = (List<BuyingProp>) response.body();
                            BuyingPropositionRecyclerViewAdapter pAdapter = new BuyingPropositionRecyclerViewAdapter(view.getContext(), usersBuyingPropositions);
                            recyclerViewBuyingPropositions.setAdapter(pAdapter);
                            recyclerViewBuyingPropositions.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        } else {
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<BuyingProp>> call, Throwable t) {
                    Log.d("ReponseFail", t.getMessage());
                    CharSequence text = "Erreur Serveur";
                    Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        return view;
    }
}