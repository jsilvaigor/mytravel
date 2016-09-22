package com.cedrotech.mytravel.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cedrotech.mytravel.R;
import com.cedrotech.mytravel.activity.LoginActivity;
import com.cedrotech.mytravel.constant.ApiConstant;
import com.cedrotech.mytravel.dao.CountryDao;
import com.cedrotech.mytravel.databinding.FragmentAccountBinding;
import com.cedrotech.mytravel.model.Country;
import com.cedrotech.mytravel.model.SimpleUser;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by isilva on 17/09/16.
 */
public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    SimpleUser mUser;
    CircleImageView mProfile;
    Button btLogout;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        binding.setNome(mUser.getName());
        binding.setEmail(mUser.getEmail());
        mProfile = (CircleImageView) binding.getRoot().findViewById(R.id.profile_image);
        loadProfilePicture(getContext());
        btLogout = (Button) binding.getRoot().findViewById(R.id.bt_logout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return binding.getRoot();
    }

    private void logout() {
        LoginManager.getInstance().logOut();

        SharedPreferences mPreferences = getContext().getSharedPreferences("user_login",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreferences.edit();
        mEditor.remove("logged");
        mEditor.remove("token");
        mEditor.remove("user_info");
        mEditor.apply();
        CountryDao dao = CountryDao.getInstance(getContext());
        dao.deleteAll();
        Toast.makeText(getContext(), R.string.logoff, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadProfilePicture(Context context) {
        String url = ApiConstant.GRAPH_PROFILE_PICTURE.replace("{ID}", mUser.getFbId());
        Picasso.with(context)
                .load(url)
                .into(mProfile);
    }

    private void getUser() {
        SharedPreferences mPreferences = getContext().getSharedPreferences("user_login",
                Context.MODE_PRIVATE);
        String userJson = mPreferences.getString("user_info", "");
        mUser = new Gson().fromJson(userJson, SimpleUser.class);

    }

}
