package com.cedrotech.mytravel.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.adapter.ViewPagerAdapter;
import com.cedrotech.mytravel.adapter.VisitedCountryAdapter;
import com.cedrotech.mytravel.fragment.AccountFragment;
import com.cedrotech.mytravel.fragment.CountriesFragment;
import com.cedrotech.mytravel.fragment.VisitedCountriesFragment;

/**
 * Created by isilva on 15/09/16.
 */
public class MainActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Context mContext;
    private MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        inflater = getMenuInflater();

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (VisitedCountryAdapter.selectMode) {
                    VisitedCountryAdapter.resetToolBar();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    @Override
    public void onBackPressed() {

        if (VisitedCountryAdapter.selectMode) {
            VisitedCountryAdapter.resetToolBar();
        } else {
            super.onBackPressed();
        }


    }

    private void setupTabIcons() {

        int color = ContextCompat.getColor(mContext, R.color.icons);

        mTabLayout.getTabAt(0).setIcon(R.drawable.account)
                .getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        mTabLayout.getTabAt(1).setIcon(R.drawable.flag_outline_variant);

        mTabLayout.getTabAt(2).setIcon(R.drawable.flag_variant);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int color = ContextCompat.getColor(mContext, R.color.icons);
                tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int color = ContextCompat.getColor(mContext, R.color.primary_light);
                tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AccountFragment(), getResources().getString(R.string.account));
        adapter.addFragment(new CountriesFragment(), getResources().getString(R.string.countries));
        adapter.addFragment(new VisitedCountriesFragment(), getResources().getString(R.string.visited));
        viewPager.setAdapter(adapter);
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }

    public Context getContext() {
        return mContext;
    }

}
