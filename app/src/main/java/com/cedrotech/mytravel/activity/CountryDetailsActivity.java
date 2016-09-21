package com.cedrotech.mytravel.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.databinding.ActivityCountryDetailsBinding;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.viewmodel.CountryViewModel;

/**
 * Created by isilva on 18/09/16.
 */
public class CountryDetailsActivity extends AppCompatActivity {

    Country mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mCountry = bundle.getParcelable("country");
        }

        ActivityCountryDetailsBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_country_details);

        binding.setCountry(new CountryViewModel(mCountry, this));

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mCountry.getShortname());
        setSupportActionBar(mToolbar);

    }


}
