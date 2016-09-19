package com.cedrotech.mytravel.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.databinding.FragmentVisitedCountriesBinding;

/**
 * Created by isilva on 17/09/16.
 */
public class VisitedCountriesFragment extends Fragment {

    FragmentVisitedCountriesBinding binding;

    public VisitedCountriesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visited_countries, container, false);
        binding.setTexto("Visited");
        return binding.getRoot();

    }
}
