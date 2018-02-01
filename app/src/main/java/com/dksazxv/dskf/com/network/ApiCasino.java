package com.dksazxv.dskf.com.network;


import com.dksazxv.dskf.com.network.model.CasinoModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("test4")
    Call<CasinoModel> check();

}
