package com.cedrotech.mytravel.constant;

/**
 * Created by isilva on 16/09/16.
 */
public class ApiConstant {

    public final static String API_BASE_PATH = "http://sslapidev.mypush.com.br/world/countries";
    public final static String ALL_ACTIVE_COUNTRIES = API_BASE_PATH + "/active";
    public final static String COUNTRY_FLAG_BASE_URI = API_BASE_PATH + "/{ID}/flag";
    public final static String COUNTRY_FLAG_HIGH_RES = "https://flagpedia.net/data/flags/normal/{ID}.png";
    public final static String GRAPH_PROFILE_PICTURE = "https://graph.facebook.com/{ID}/picture?type=large";
}
