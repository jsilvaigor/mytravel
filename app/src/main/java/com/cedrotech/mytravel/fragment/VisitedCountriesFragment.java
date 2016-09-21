package com.cedrotech.mytravel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.adapter.VisitedCountryAdapter;
import com.cedrotech.mytravel.dao.CountryDao;
import com.cedrotech.mytravel.model.Country;

import java.util.List;

/**
 * Created by isilva on 17/09/16.
 */
public class VisitedCountriesFragment extends Fragment {

    private static RecyclerView mRecyclerView;
    private static VisitedCountryAdapter mVisitedCountryAdapter;
    private static View mView;
    private static Fragment instance;

    public VisitedCountriesFragment() {
        instance = this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_visited_countries, null);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_visited_countries);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mVisitedCountryAdapter = new VisitedCountryAdapter();
        mRecyclerView.setAdapter(mVisitedCountryAdapter);

        getCountries(getContext());
    }

    public static void getCountries(Context mContext) {

        CountryDao dao = CountryDao.getInstance(mContext);

        List<Country> mcCountries = dao.getAll();
        mView.findViewById(R.id.progress).setVisibility(View.GONE);
        if (mcCountries.size() > 0) {
            mVisitedCountryAdapter.swapCountries(mcCountries);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mView.findViewById(R.id.no_country).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        VisitedCountryAdapter.setMenuInflater(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return VisitedCountryAdapter.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        if (VisitedCountryAdapter.selectMode) {
            VisitedCountryAdapter.resetToolBar();
        }
        super.onStop();
    }
}
