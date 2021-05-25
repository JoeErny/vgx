package com.example.vgxchange;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductBasicInformationFragment extends Fragment {

    public ProductBasicInformationFragment() {
    }

    public static ProductBasicInformationFragment newInstance() {
        ProductBasicInformationFragment fragment = new ProductBasicInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_basic_information, container, false);
    }
}