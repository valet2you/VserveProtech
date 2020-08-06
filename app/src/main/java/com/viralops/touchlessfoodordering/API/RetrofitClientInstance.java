package com.viralops.touchlessfoodordering.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viralops.touchlessfoodordering.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = BuildConfig.BASE_URL;

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public static GerritAPI getApiService() {
        return getRetrofitInstance().create(GerritAPI.class);
    }
}
