package com.assignment.alexs.twitterclient.services;

/**
 * Created by alexschwartzman on 1/6/18.
 */

import android.util.Base64;
import android.util.Log;

import com.assignment.alexs.twitterclient.model.TWAuthenticationToken;
import com.assignment.alexs.twitterclient.model.TWSearchResults;
import com.assignment.alexs.twitterclient.ui.IServiceCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TWService {
    static final String CONSUMER_KEY = "y68Tsj2iQHUqvTMpq1ZHE0P8E";
    static final String CONSUMER_SECRET = "oRhZYi3q4XJp4QylhCuNAuIBwkbwkzytzRbKvz69WIdia8J13H";
    final static String TOKEN_URL = "https://api.twitter.com/oauth2/token";
    private final String TAG = "TWService";
    private IServiceCallback callback;
    private String query;
    private static TWAuthenticationToken authToken;

    public TWService(IServiceCallback callback) {
        this.callback = callback;
    }

    public void getTweets(String query) {
        this.query = query;
        final String searchString = query;
        if (authToken == null) {
            getToken();
            return;
        }
        TWApiInterface apiService = TWApiClient.getClient().create(TWApiInterface.class);
        Call<TWSearchResults> call = apiService.getTweets("Bearer " + authToken.getAccess_token(), 100, query);

        call.enqueue(new Callback<TWSearchResults>() {
            @Override
            public void onResponse(Call<TWSearchResults> call, Response<TWSearchResults> response) {
                if (isTokenExpired(response)) {
                    authToken = null;//force to obtain new token
                    getTweets(searchString);
                    return;
                }
                if (response.isSuccessful()) {
                    TWSearchResults result = response.body();
                    callback.onSuccess(result.getStatuses());
                } else {
                    callback.onFailure("Failed to get tweets");
                }
            }

            @Override
            public void onFailure(Call<TWSearchResults> call, Throwable t) {
                call.cancel();
                callback.onFailure(t.getLocalizedMessage());

            }
        });
    }

    public void getToken() {

        OkHttpClient client = new OkHttpClient();
        RequestBody rb = RequestBody
                .create(MediaType
                        .parse("application/x-www-form-urlencoded"), "grant_type=client_credentials");
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(rb)
                .addHeader("Authorization", getAuthHeader(CONSUMER_KEY, CONSUMER_SECRET))
                .build();

        okhttp3.Call call = client.newCall(request);

        call.enqueue(new okhttp3.Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {
                             call.cancel();
                             callback.onFailure("Failed to obtain token");
                         }

                         @Override
                         public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                             if (response.isSuccessful()) {
                                 final String myResponse = response.body().string();
                                 authToken = jsonToToken(myResponse);
                                 if (authToken == null) {
                                     callback.onFailure("Failed to parse token");
                                 } else {
                                     getTweets(query);
                                 }
                             } else {
                                 callback.onFailure("Failed to obtain token");
                             }

                         }
                     }
        );

    }

    private String getAuthHeader(String key, String secret) {
        String result = "";
        try {

            String urlApiKey = URLEncoder.encode(key, "UTF-8");
            String urlApiSecret = URLEncoder.encode(secret, "UTF-8");
            // Concatenate the encoded consumer key, a colon character, and the secret
            String combined = urlApiKey + ":" + urlApiSecret;
            // Base64 encode the string
            result = "Basic " + Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return result;
    }

    private TWAuthenticationToken jsonToToken(String rawAuthorization) {
        TWAuthenticationToken auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, TWAuthenticationToken.class);
            } catch (IllegalStateException ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        }
        return auth;
    }

    private boolean isTokenExpired(Response response) {
        //not sure how to check for this
        return false;
    }

}
