package com.cedrotech.mytravel.dao;

import android.content.Context;
import android.util.Log;

import com.cedrotech.mytravel.entity.Country;
import com.cedrotech.mytravel.helper.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by isilva on 17/09/16.
 */
public class CountryDao {

    Context mContext;
    DatabaseHelper mHelper;
    Dao<Country, Integer> mDao;

    public CountryDao(Context mContext) {
        this.mContext = mContext;
        this.mHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        try {
            this.mDao = mHelper.getCountryDao();
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "constructor: " + e.getMessage());
        }
    }

    public boolean create(Country mCountry) {

        try {
            mDao.createIfNotExists(mCountry);
            return true;
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "create:" + e.getMessage());
            return false;
        }

    }

    public boolean createMany(Collection<Country> countries) {

        for (Country country : countries) {
            try {
                mDao.createIfNotExists(country);
            } catch (SQLException e) {
                Log.e(this.getClass().toString(), "createMany: " + e.getMessage());
                Log.e(this.getClass().toString(), "createMany: " + country.toString());
                return false;
            }
        }

        return true;
    }

    public boolean update(Country country) {

        int status = 0;
        try {
            status = mDao.update(country);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "update: " + e.getMessage());
        }

        return status == 1;
    }

    public boolean updateMany(List<Country> countries) {

        int status = 0;

        for (Country country : countries) {

            try {
                status = mDao.update(country);
            } catch (SQLException e) {
                Log.e(this.getClass().toString(), "updateMany: " + e.getMessage());
                Log.e(this.getClass().toString(), "updateMany: " + country.toString());
                return false;
            }

            if (status != 1) {
                return false;
            }
        }

        return status == 1;

    }

    public boolean delete(Country country) {

        int status = 0;
        try {
            status = mDao.deleteById(country.getId());
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "delete: " + e.getMessage());
            Log.e(this.getClass().toString(), "delete: " + country.toString());
        }

        return status == 1;
    }

    public boolean deleteMany(Collection<Country> countries) {

        int status = 0;
        try {
            status = mDao.delete(countries);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "deleteMany: " + e.getMessage());
            Log.e(this.getClass().toString(), "deleteMany: " + countries.toString());
        }

        return status == countries.size();

    }

    public List<Country> getAll() {

        List<Country> countries = new ArrayList<>();

        try {
            countries = mDao.queryForAll();
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "getAll: " + e.getMessage());
        }

        return countries;

    }

    public void close() {
        mHelper.close();
        mDao = null;
    }


}
