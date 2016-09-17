package com.cedrotech.mytravel.helper;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cedrotech.mytravel.constant.ApiConstant;
import com.cedrotech.mytravel.entity.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isilva on 16/09/16.
 */
public class CountryApiHelper {

    public static void getAllActiveCountries(Context context,
                                             final VolleyHelper.Callback<List<Country>> callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiConstant.ALL_ACTIVE_COUNTRIES,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();

                        Type countryList = new TypeToken<List<Country>>() {
                        }.getType();

                        ArrayList<Country> countries = gson.fromJson(response.toString(), countryList);

                        callback.onSuccess(countries);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                });

        VolleyHelper.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


}
