package com.clake.ckkaqa.network;


import com.clake.ckkaqa.network.model.CasinoModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCasino {

    @GET("test4")
    Call<CasinoModel> check();

}
