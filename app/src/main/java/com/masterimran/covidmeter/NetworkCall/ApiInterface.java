package com.masterimran.covidmeter.NetworkCall;

import com.masterimran.covidmeter.Model.StatModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("countries")
    Call<ArrayList<StatModel>> getStat();
}
