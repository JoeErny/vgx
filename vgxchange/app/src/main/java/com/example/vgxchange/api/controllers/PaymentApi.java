package com.example.vgxchange.api.controllers;

import com.example.vgxchange.api.dto.BuyingPropositionToCreate;
import com.example.vgxchange.api.dto.PaymentToCreate;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.model.Payment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentApi {

    @POST("/api/payment")
    Call<Payment> add(@Body PaymentToCreate paymentToCreate);
}
