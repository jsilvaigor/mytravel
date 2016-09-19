package com.cedrotech.mytravel.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.databinding.ItemListCountryBinding;
import com.cedrotech.mytravel.helper.CountryApiHelper;
import com.cedrotech.mytravel.helper.VolleyHelper;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.viewmodel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isilva on 18/09/16.
 */
public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    public List<Country> mCountries = new ArrayList<>();

    public CountryAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListCountryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_list_country, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemListCountryBinding binding = holder.getBinding();
        binding.setCountry(new CountryViewModel(mCountries.get(position), holder.getContext()));
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public void swapCountries(List<Country> countries) {
        this.mCountries = countries;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemListCountryBinding mBinding;
        Context mContext;
        ImageView iconVisited;

        public ViewHolder(ItemListCountryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mContext = binding.getRoot().getContext();

            iconVisited = (ImageView) binding.getRoot().findViewById(R.id.iv_icon_visited);
            iconVisited.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(),
                    R.color.primary_dark), PorterDuff.Mode.SRC_IN);


        }

        public ItemListCountryBinding getBinding() {
            return mBinding;
        }

        public Context getContext() {
            return mContext;
        }
    }


}
