package com.cedrotech.mytravel.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.adapter.CountryAdapter;
import com.cedrotech.mytravel.dao.CountryDao;
import com.cedrotech.mytravel.databinding.ActivityCountryDetailsBinding;
import com.cedrotech.mytravel.fragment.VisitedCountriesFragment;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.viewmodel.CountryViewModel;
import com.cedrotech.mytravel.viewmodel.VisitedCountryViewModel;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.squareup.picasso.Picasso;

import java.util.Date;


/**
 * Created by isilva on 18/09/16.
 */
public class CountryDetailsActivity extends AppCompatActivity {

    Country mCountry;
    FloatingActionMenu mFabMenu;
    FloatingActionButton mFabEdit, mFabDelete, mFabAdd;
    CountryDao dao;
    ActivityCountryDetailsBinding binding;
    int accent, accentDark, accentLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mCountry = bundle.getParcelable("country");
        }

        dao = CountryDao.getInstance(this);
        accent = ContextCompat.getColor(this, R.color.accent);
        accentDark = ContextCompat.getColor(this, R.color.accent_dark);
        accentLight = ContextCompat.getColor(this, R.color.accent_light);
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_country_details);

        binding.setCountry(new VisitedCountryViewModel(mCountry, this));

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mCountry.getShortname());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        mFabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        mFabMenu.setMenuButtonColorNormal(accent);
        mFabMenu.setMenuButtonColorPressed(accentDark);
        mFabMenu.setMenuButtonColorRipple(accentLight);
        mFabAdd.setColorNormal(accent);
        mFabAdd.setColorPressed(accentDark);
        mFabAdd.setColorRipple(accentLight);


        createFabMenu();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void createFabMenu() {

        if (mCountry.getVisited()) {
            mFabMenu.setVisibility(View.VISIBLE);
            createFabButtons();
        } else {
            mFabAdd.setVisibility(View.VISIBLE);
            mFabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editOrAdd(v.getContext());
                }
            });
        }
    }

    private void createFabButtons() {
        mFabEdit = new FloatingActionButton(this);
        mFabDelete = new FloatingActionButton(this);


        mFabEdit.setColorNormal(accent);
        mFabEdit.setColorPressed(accentDark);
        mFabEdit.setColorRipple(accentLight);
        mFabDelete.setColorNormal(accent);
        mFabDelete.setColorPressed(accentDark);
        mFabDelete.setColorRipple(accentLight);

        mFabEdit.setButtonSize(FloatingActionButton.SIZE_MINI);
        mFabEdit.setLabelText(getString(R.string.edit));
        mFabEdit.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pencil));
        mFabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrAdd(v.getContext());
            }
        });

        mFabMenu.addMenuButton(mFabEdit);


        mFabDelete.setButtonSize(FloatingActionButton.SIZE_MINI);
        mFabDelete.setLabelText(getString(R.string.delete));
        mFabDelete.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.delete));
        mFabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(v.getContext());
            }
        });


        mFabMenu.addMenuButton(mFabDelete);
    }

    private void editOrAdd(final Context mContext) {

        Date mDate;


        if (mCountry.getVisited()) {
            mDate = mCountry.getDate();
        } else {
            mDate = new Date();
        }

        Dialog.Builder builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String message;

                mCountry.setDate(new Date(dialog.getDate()));

                if (mCountry.getVisited()) {
                    dao.update(mCountry);
                    message = getString(R.string.update);
                } else {
                    mCountry.setVisited(true);
                    dao.create(mCountry);
                    message = getString(R.string.add);
                }
                notifyDatasetChanged();
                reloadFabMenu();
                binding.setCountry(new VisitedCountryViewModel(mCountry, mContext));
                binding.notifyChange();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        }.date(mDate.getTime());

        builder.positiveAction(getString(R.string.ok))
                .negativeAction(getString(R.string.cancel));

        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);


    }

    private void showConfirmationDialog(final Context mContext) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.careful);
        builder.setCancelable(true);
        builder.setMessage(R.string.delete_single_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(mContext);
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void delete(Context mContext) {
        dao.delete(mCountry);
        Toast.makeText(mContext, getString(R.string.delete), Toast.LENGTH_SHORT).show();
        notifyDatasetChanged();
        finish();
    }

    private void notifyDatasetChanged() {
        CountryAdapter.instance.notifyDataSetChanged();
        VisitedCountriesFragment.getCountries(this);
    }

    private void reloadFabMenu() {
        mFabMenu.removeAllMenuButtons();
        mFabMenu.setVisibility(View.GONE);
        mFabAdd.setVisibility(View.GONE);
        createFabMenu();
    }

}
