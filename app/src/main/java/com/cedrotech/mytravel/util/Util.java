package com.cedrotech.mytravel.util;

import com.cedrotech.mytravel.model.Country;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by isilva on 18/09/16.
 */
public class Util {

    public static List<Country> orderCountryAlphabetically(List<Country> countries) {

        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country country, Country t1) {
                return country.getShortname().compareTo(t1.getShortname());
            }
        });

        return countries;

    }


}
