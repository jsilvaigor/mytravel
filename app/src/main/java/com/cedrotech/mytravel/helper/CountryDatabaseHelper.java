package com.cedrotech.mytravel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.entity.Country;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by isilva on 16/09/16.
 */
public class CountryDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mytravel.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Country, Integer> countryDao;

    public CountryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Country.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Country.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<Country, Integer> getDao() throws SQLException {

        if (countryDao == null) {
            countryDao = getDao(Country.class);
        }
        return countryDao;
    }


}
