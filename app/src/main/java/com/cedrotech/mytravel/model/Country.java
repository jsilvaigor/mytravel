package com.cedrotech.mytravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by isilva on 16/09/16.
 */
@DatabaseTable(tableName = "countries")
public class Country implements Parcelable {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private Integer id;

    @SerializedName("iso")
    @Expose
    @DatabaseField
    private String iso;

    @SerializedName("shortname")
    @Expose
    @DatabaseField
    private String shortname;

    @SerializedName("longname")
    @Expose
    @DatabaseField
    private String longname;

    @SerializedName("callingCode")
    @Expose
    @DatabaseField
    private String callingCode;

    @SerializedName("status")
    @Expose
    @DatabaseField
    private Integer status;

    @SerializedName("culture")
    @Expose
    @DatabaseField
    private String culture;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean visited;

    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date date;

    //Default constructor
    public Country() {
    }

    //All atributes constructor
    public Country(Integer id, String iso, String shortname, String longname,
                   String callingCode, Integer status, String culture, Boolean visited, Date date) {
        this.id = id;
        this.iso = iso;
        this.shortname = shortname;
        this.longname = longname;
        this.callingCode = callingCode;
        this.status = status;
        this.culture = culture;
        this.visited = visited;
        this.date = date;
    }

    //API Constructor
    public Country(Integer id, String iso, String shortname, String longname, String callingCode,
                   Integer status, String culture) {
        this.id = id;
        this.iso = iso;
        this.shortname = shortname;
        this.longname = longname;
        this.callingCode = callingCode;
        this.status = status;
        this.culture = culture;
    }

    //Parcelable constructor
    public Country(Parcel in) {

        String[] dados = new String[0];
        in.readStringArray(dados);

        this.id = Integer.getInteger(dados[0]);
        this.iso = dados[1];
        this.shortname = dados[2];
        this.longname = dados[3];
        this.callingCode = dados[4];
        this.status = Integer.getInteger(dados[5]);
        this.culture = dados[6];
        this.visited = Boolean.getBoolean(dados[7]);
        this.date = new Date(Long.valueOf(dados[8]));

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Country{" +
                ", id=" + id +
                ", iso='" + iso + '\'' +
                ", shortname='" + shortname + '\'' +
                ", longname='" + longname + '\'' +
                ", callingCode='" + callingCode + '\'' +
                ", status=" + status +
                ", culture='" + culture + '\'' +
                ", visited=" + visited +
                ", date=" + date +
                '}';
    }


    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                String.valueOf(this.id),
                this.iso,
                this.shortname,
                this.longname,
                this.callingCode,
                String.valueOf(this.status),
                this.culture,
                String.valueOf(this.visited),
                String.valueOf(this.date.getTime())
        });
    }
}
