package com.cedrotech.mytravel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.adapter.CountryAdapter;
import com.cedrotech.mytravel.helper.CountryApiHelper;
import com.cedrotech.mytravel.helper.VolleyHelper;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.util.Util;

import java.util.List;

/**
 * Created by isilva on 17/09/16.
 */
public class CountriesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CountryAdapter mCountryAdapter;
    private View mView;

    public CountriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_countries, null);
        return mView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_countries);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCountryAdapter = new CountryAdapter();
        mRecyclerView.setAdapter(mCountryAdapter);
        getCountries(getContext());
    }

    private void getCountries(Context context) {

        CountryApiHelper.getAllActiveCountries(context, new VolleyHelper.Callback<List<Country>>() {
            @Override
            public void onSuccess(List<Country> obj) {
                mView.findViewById(R.id.progress).setVisibility(View.GONE);
                if (obj.size() > 0) {
                    mCountryAdapter.swapCountries(Util.orderCountryAlphabetically(obj));
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mView.findViewById(R.id.no_country).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(VolleyError error) {
                Log.e(getClass().toString(), "getCountries: " + error.getMessage());
                mView.findViewById(R.id.progress).setVisibility(View.GONE);
                mView.findViewById(R.id.no_country).setVisibility(View.VISIBLE);
            }
        });

    }

}
