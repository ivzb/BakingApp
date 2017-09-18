package com.udacity.baking.data;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.udacity.baking.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTClient {

    private static Retrofit sRETROFIT;
    private final static String sREST_URL = BuildConfig.BASE_URL;

    public static Retrofit getClient() {
        if (sRETROFIT == null) {
            Gson gson = new Gson();

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            sRETROFIT = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(sREST_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return sRETROFIT;
    }

    public static void destroyClient() {
        sRETROFIT = null;
    }

    // Prevent direct instantiation.
    private RESTClient() {}
}