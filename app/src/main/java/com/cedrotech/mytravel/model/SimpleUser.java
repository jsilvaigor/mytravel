package com.cedrotech.mytravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by isilva on 22/09/16.
 */

public class SimpleUser implements Parcelable {

    @SerializedName("id")
    private String fbId;
    private String name;
    private String email;

    public SimpleUser(String fbId, String name, String email) {
        this.fbId = fbId;
        this.name = name;
        this.email = email;
    }

    public SimpleUser(Parcel in) {

        String[] dados = new String[3];

        in.readStringArray(dados);

        fbId = dados[0];
        email = dados[1];
        name = dados[2];
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static final Creator<SimpleUser> CREATOR = new Creator<SimpleUser>() {
        @Override
        public SimpleUser createFromParcel(Parcel in) {
            return new SimpleUser(in);
        }

        @Override
        public SimpleUser[] newArray(int size) {
            return new SimpleUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{
                fbId,
                email,
                name
        });


    }

    @Override
    public String toString() {
        return "SimpleUser{" +
                "fbId='" + fbId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
