package com.cedrotech.mytravel.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.constant.ApiConstant;
import com.cedrotech.mytravel.dao.CountryDao;
import com.cedrotech.mytravel.model.Country;
import com.squareup.picasso.Picasso;

/**
 * Created by isilva on 17/09/16.
 */
public class CountryViewModel extends BaseObservable {

    private Country mCountry;
    private Context mContext;
    private CountryDao mDao;

    public CountryViewModel(Country mCountry, Context mContext) {
        this.mCountry = mCountry;
        this.mContext = mContext;
        this.mDao = CountryDao.getInstance(mContext);
    }

    public String getShortName() {
        return mCountry.getShortname();
    }

    public synchronized int getVisitedVisibility() {

        Country savedCountry = mDao.getById(mCountry.getId());

        if (savedCountry != null && savedCountry.getVisited()) {
            mCountry = savedCountry;
            return View.VISIBLE;
        }

        return View.GONE;

    }

    public String getFlagUrl() {
        return ApiConstant.COUNTRY_FLAG_BASE_URI.replace("{ID}", mCountry.getId().toString());
    }


    @BindingAdapter({"bind:flagUrl"})
    public static void loadFlag(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.flag_variant)
                .fit()
                .into(view);
    }

    @BindingAdapter({"bind:visitedIcon"})
    public static void loadVisitedIcon(ImageView view, int visible) {

        if (visible == View.VISIBLE) {

            Picasso.with(view.getContext())
                    .load(R.drawable.flag_variant)
                    .into(view);
        }


    }


}
