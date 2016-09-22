package com.cedrotech.mytravel.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cedrotech.mytravel.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.rey.material.widget.ProgressView;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by isilva on 22/09/16.
 */

public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    CallbackManager callbackManager;
    Activity mActivity;
    ProgressView mProgressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mActivity = this;

        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
        mEditor = mPreferences.edit();

        mProgressView = (ProgressView) findViewById(R.id.progress);


        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        if (mPreferences.getBoolean("logged", false)) {
            loginButton.setVisibility(View.GONE);
            mProgressView.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), R.string.logged, Toast.LENGTH_SHORT).show();
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null && !accessToken.isExpired()) {
                mEditor.putString("token", accessToken.getToken());
                mEditor.apply();
                getUserInfo(accessToken);
            } else {
                mEditor.putBoolean("logged", false);
                this.recreate();
            }

        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.GONE);
                mProgressView.setVisibility(View.VISIBLE);
                mEditor.putBoolean("logged", true);
                mEditor.putString("token", loginResult.getAccessToken().getToken());
                mEditor.apply();
                getUserInfo(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e("Facebook", "Canceled");
                mEditor.putBoolean("logged", false);
                mEditor.putString("token", null);
                mEditor.apply();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Facebook", error.getLocalizedMessage());
                mEditor.putBoolean("logged", false);
                mEditor.putString("token", null);
                mEditor.apply();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserInfo(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (response.getError() == null) {
                            mEditor.putString("user_info", object.toString());
                            mEditor.apply();
                            openMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            mEditor.putBoolean("logged", false);
                            mEditor.apply();
                            LoginManager.getInstance().logOut();
                            mActivity.recreate();
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
