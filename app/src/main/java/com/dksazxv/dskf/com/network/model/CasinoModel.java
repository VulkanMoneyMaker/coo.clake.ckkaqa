package com.dksazxv.dskf.com.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Whatz on 31.01.2018.
 */

public class CasinoModel {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("result")
    @Expose
    private Boolean result;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
