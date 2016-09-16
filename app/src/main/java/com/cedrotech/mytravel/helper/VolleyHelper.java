package com.cedrotech.mytravel.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Created by isilva on 16/09/16.
 */
public class VolleyHelper {

    private static VolleyHelper mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    public VolleyHelper(Context ctx) {
        mContext = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new VolleyHelper(ctx);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> rqt) {
        getRequestQueue().add(rqt);
    }

    //Interface de callback a ser utilizada para tratar retorno das requisições
    public interface Callback<T> {

        void onSuccess(T obj);

        void onError(VolleyError error);
    }

}
