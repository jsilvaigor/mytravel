package com.cedrotech.mytravel.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.databinding.FragmentAccountBinding;


/**
 * Created by isilva on 17/09/16.
 */
public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().toString(), "Chamou");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        binding.setTexto("Account");
        return binding.getRoot();
    }
}
