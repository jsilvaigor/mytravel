package com.cedrotech.mytravel.entity;

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
public class Country {

    @DatabaseField(generatedId = true)
    private Integer idCountry;

    @SerializedName("id")
    @Expose
    @DatabaseField
    private Integer idApi;

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

    public Country() {
    }

    //Construtor Padrão
    public Country(Integer idCountry, Integer idApi, String iso, String shortname, String longname,
                   String callingCode, Integer status, String culture, Boolean visited, Date date) {
        this.idCountry = idCountry;
        this.idApi = idApi;
        this.iso = iso;
        this.shortname = shortname;
        this.longname = longname;
        this.callingCode = callingCode;
        this.status = status;
        this.culture = culture;
        this.visited = visited;
        this.date = date;
    }

    //Construtor com apenas os campos retornados pela API
    public Country(Integer idApi, String iso, String shortname, String longname, String callingCode,
                   Integer status, String culture) {
        this.idApi = idApi;
        this.iso = iso;
        this.shortname = shortname;
        this.longname = longname;
        this.callingCode = callingCode;
        this.status = status;
        this.culture = culture;
    }


    public Integer getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public Integer getIdApi() {
        return idApi;
    }

    public void setIdApi(Integer idApi) {
        this.idApi = idApi;
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
                "idCountry=" + idCountry +
                ", idApi=" + idApi +
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


}
