package com.cedrotech.mytravel.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.adapter.VisitedCountryAdapter;
import com.cedrotech.mytravel.constant.ApiConstant;
import com.cedrotech.mytravel.helper.VolleyHelper;
import com.cedrotech.mytravel.model.Country;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * Created by isilva on 19/09/16.
 */
public class VisitedCountryViewModel extends CountryViewModel {

    private Boolean checked = false;
    private int position;
    static String highResFlagUrl;

    public VisitedCountryViewModel(Country mCountry, Context mContext) {
        super(mCountry, mContext);
    }

    public VisitedCountryViewModel(Country mCountry, Context mContext, int position) {
        super(mCountry, mContext);
        this.position = position;
    }

    @Override
    public String getFlagUrl() {
        highResFlagUrl = ApiConstant.COUNTRY_FLAG_HIGH_RES.replace("{ID}",
                getmCountry().getIso().toLowerCase());
        return super.getFlagUrl();
    }

    @BindingAdapter({"bind:highFlagUrl"})
    public static void loadFlag(final ImageView view, final String imageUrl) {

        VolleyHelper.getInstance(view.getContext()).addToRequestQueue(new ImageRequest(highResFlagUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        view.setImageBitmap(response);
                    }
                }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Picasso.with(view.getContext())
                        .load(imageUrl)
                        .placeholder(com.cedrotech.mytravel.R.drawable.flag_variant)
                        .fit()
                        .into(view);
            }
        }));
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

    public Country getCountry() {
        return mCountry;
    }

    public String getCallingCode() {
        return mContext.getString(R.string.calling_code) + " " + mCountry.getCallingCode();
    }

    public String getVisitedDate() {

        if (mCountry.getVisited()) {

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTime(mCountry.getDate());

            return mContext.getString(R.string.visited_date) + " " + mCalendar.get(Calendar.DAY_OF_MONTH)
                    + "-" + mCalendar.get(Calendar.MONTH) + "-" + mCalendar.get(Calendar.YEAR);

        } else {
            return "";
        }


    }
}
