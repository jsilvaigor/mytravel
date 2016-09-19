package com.cedrotech.mytravel.util;

import com.cedrotech.mytravel.model.Country;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by isilva on 18/09/16.
 */
public class UtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testOrderCountryAlphabetically() throws Exception {

        List<Country> testCountries = new ArrayList<>();
        List<Country> expectedCountries = new ArrayList<>();

        Country c1 = new Country();
        c1.setShortname("South Sudan");

        Country c2 = new Country();
        c2.setShortname("Brazil");

        Country c3 = new Country();
        c3.setShortname("Argentina");
        testCountries.add(c1);
        testCountries.add(c2);
        testCountries.add(c3);

        expectedCountries.add(c3);
        expectedCountries.add(c2);
        expectedCountries.add(c1);

        assertEquals(Util.orderCountryAlphabetically(testCountries), expectedCountries);


    }
}