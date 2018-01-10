package com.assignment.alexs.twitterclient.services;

/**
 * Created by alexschwartzman on 1/6/18.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TWApiClient {

    public static final String BASE_URL = "https://api.twitter.com/1.1/search/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
