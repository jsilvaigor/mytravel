package com.cedrotech.mytravel.dao;

import android.content.Context;
import android.util.Log;

import com.cedrotech.mytravel.model.Country;
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

    private static Context mContext;
    private DatabaseHelper mHelper;
    private Dao<Country, Integer> mDao;
    private static CountryDao mInstance;

    public CountryDao(Context mContext) {
        CountryDao.mContext = mContext;
        this.mHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);
        try {
            this.mDao = mHelper.getCountryDao();
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "constructor: " + e.getMessage());
        }
    }

    public static CountryDao getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CountryDao(context);
        }
        return mInstance;
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

    public boolean deleteManyById(Collection<Integer> ids) {

        int status = 0;

        try {
            status = mDao.deleteIds(ids);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "deleteMany: " + e.getMessage());
            Log.e(this.getClass().toString(), "deleteMany: " + ids.toString());
        }

        return status == ids.size();
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

    public Country getById(Integer id) {

        Country found = null;

        try {
            found = mDao.queryForId(id);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "getById: " + e.getMessage());
        }

        return found;

    }

    public void close() {
        mHelper.close();
        mDao = null;
    }


}
