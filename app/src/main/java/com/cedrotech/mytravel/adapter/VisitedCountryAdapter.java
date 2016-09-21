package com.cedrotech.mytravel.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.activity.CountryDetailsActivity;
import com.cedrotech.mytravel.activity.MainActivity;
import com.cedrotech.mytravel.dao.CountryDao;
import com.cedrotech.mytravel.databinding.ItemVisitedCountryBinding;
import com.cedrotech.mytravel.fragment.VisitedCountriesFragment;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.viewmodel.VisitedCountryViewModel;
import com.rey.material.app.Dialog;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by isilva on 18/09/16.
 */
public class VisitedCountryAdapter extends RecyclerView.Adapter<VisitedCountryAdapter.ViewHolder> {

    private static List<Country> mCountries = new ArrayList<>();
    public static SparseBooleanArray mSelected = new SparseBooleanArray();
    public static Boolean selectMode = false;
    private static ActionBar mActionBar;
    private static MainActivity mainActivity;
    private static VisitedCountryAdapter instance;
    private static Menu mMenu;
    private static MenuInflater mInflater;
    private static SparseIntArray mapCountryPosition = new SparseIntArray();
    private static Boolean allSelected = false;

    public VisitedCountryAdapter() {
        instance = this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVisitedCountryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_visited_country, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemVisitedCountryBinding binding = holder.getBinding();
        binding.setCountry(new VisitedCountryViewModel(mCountries.get(position), holder.getContext(), position));
        holder.toggleCheckBox(selectMode, mSelected.get(position, false), position);

        if (mapCountryPosition.indexOfKey(position) < 0) {
            mapCountryPosition.put(position, binding.getCountry().getmCountry().getId());
        }
        onClickHandlers(holder, position);
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public void swapCountries(List<Country> countries) {
        mCountries = countries;
        notifyDataSetChanged();
    }

    private void toggleSelection(int pos) {
        if (mSelected.get(pos, false)) {
            mSelected.delete(pos);
        } else {
            mSelected.put(pos, true);
        }
        changeToolbarText();
        notifyItemChanged(pos);
    }

    private void clearSelections() {
        mSelected.clear();
        notifyDataSetChanged();
    }

    private int getSelectedItemCount() {
        return mSelected.size();
    }

    private static List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>();

        for (int i = 0; i < mSelected.size(); i++) {
            items.add(mSelected.keyAt(i));
        }

        return items;

    }

    private void onClickHandlers(final ViewHolder holder, final int position) {

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (!selectMode) {
                    selectMode = true;
                    clearSelections();
                    mSelected.put(position, true);

                    mainActivity = (MainActivity) v.getContext();

                    mActionBar = mainActivity.getSupportActionBar();
                    mActionBar.setDisplayHomeAsUpEnabled(true);
                    changeToolbarText();
                    mInflater.inflate(R.menu.menu, mMenu);

                    notifyDataSetChanged();
                }


                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectMode) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("country", holder.getBinding().getCountry().getmCountry());
                    Intent intent = new Intent(v.getContext(), CountryDetailsActivity.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                } else {
                    toggleSelection(position);
                }

            }
        });

    }

    public static boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                resetToolBar();
                break;
            case R.id.checkall:
                selectAll();
                break;
            case R.id.delete:
                showConfirmationDialog();
                break;
            default:
                break;
        }
        return true;
    }

    public static void resetToolBar() {
        selectMode = false;
        mSelected.clear();
        mActionBar.setTitle(mainActivity.getResources().getString(R.string.app_name));
        mActionBar.setDisplayHomeAsUpEnabled(false);
        mActionBar.invalidateOptionsMenu();
        VisitedCountryAdapter.getRunningInstance().notifyDataSetChanged();

    }

    private void changeToolbarText() {
        mActionBar.setTitle(getSelectedItemCount() + " " + mainActivity.getResources().getString(R.string.selected));
    }

    public static VisitedCountryAdapter getRunningInstance() {
        return instance;
    }

    public static void setMenuInflater(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        mInflater = inflater;
    }

    private static void selectAll() {
        mSelected.clear();
        for (int i = 0; i < mCountries.size(); i++) {
            if (!allSelected) {
                mSelected.put(i, true);
            }
        }
        allSelected = !allSelected;
        VisitedCountryAdapter.getRunningInstance().notifyDataSetChanged();
    }

    private static void showConfirmationDialog() {

        if (mSelected.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity.getContext());
            builder.setTitle("Cuidado!");
            builder.setCancelable(true);
            builder.setMessage("Deseja apagar os paises selecionados?");
            builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteSelected();
                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else {
            Toast.makeText(mainActivity, R.string.no_countries_selected, Toast.LENGTH_LONG).show();
        }


    }

    private static void deleteSelected() {
        List<Integer> countries = new ArrayList<>();
        CountryDao dao = CountryDao.getInstance(mainActivity);

        for (int i = 0; i < mSelected.size(); i++) {
            countries.add(mapCountryPosition.valueAt(i));
        }

        if (countries.size() > 0) {
            dao.deleteManyById(countries);
        }

        Toast.makeText(mainActivity, R.string.all_deleted, Toast.LENGTH_SHORT).show();

        mCountries = dao.getAll();
        mapCountryPosition.clear();
        VisitedCountryAdapter.getRunningInstance().notifyDataSetChanged();
        resetToolBar();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemVisitedCountryBinding mBinding;
        Context mContext;
        private CheckBox mCheckBox;

        public ViewHolder(ItemVisitedCountryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mContext = binding.getRoot().getContext();

        }

        public ItemVisitedCountryBinding getBinding() {
            return mBinding;
        }

        public Context getContext() {
            return mContext;
        }

        public void toggleCheckBox(Boolean selectmode, Boolean selected, int position) {
            mCheckBox = (CheckBox) mBinding.getRoot().findViewById(R.id.ck_country);
            mCheckBox.setChecked(selected);
            if (selectmode) {
                mCheckBox.setVisibility(View.VISIBLE);
            } else {
                mCheckBox.setVisibility(View.GONE);
            }


        }

    }


}
