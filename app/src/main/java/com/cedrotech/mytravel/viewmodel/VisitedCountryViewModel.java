package com.cedrotech.mytravel.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;

import com.cedrotech.mytravel.adapter.VisitedCountryAdapter;
import com.cedrotech.mytravel.model.Country;
import com.rey.material.widget.CheckBox;

/**
 * Created by isilva on 19/09/16.
 */
public class VisitedCountryViewModel extends CountryViewModel {

    private Boolean checked = false;
    private int position;

    public VisitedCountryViewModel(Country mCountry, Context mContext, int position) {
        super(mCountry, mContext);
        this.position = position;
    }

    public View.OnClickListener checkboxClick() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VisitedCountryAdapter mAdapter = VisitedCountryAdapter.getRunningInstance();

                if (VisitedCountryAdapter.mSelected.get(position, false)) {
                    VisitedCountryAdapter.mSelected.delete(position);
                    mAdapter.notifyDataSetChanged();
                } else {
                    VisitedCountryAdapter.mSelected.put(position, true);
                    mAdapter.notifyDataSetChanged();
                }


            }
        };

    }
}
