package com.masterimran.covidmeter.NetworkCall;

import com.masterimran.covidmeter.Utils.StaticVaribles;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient mInstance;
    String BASE_URL = StaticVaribles.COUNRTYWISE_DATA_JSON_URL;
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();
    private Retrofit retrofit;

    private ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public ApiInterface getApi() {
        return retrofit.create(ApiInterface.class);
    }
}
