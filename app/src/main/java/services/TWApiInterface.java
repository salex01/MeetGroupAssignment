package services;

/**
 * Created by alexschwartzman on 1/6/18.
 */

import com.assignment.alexs.twitterclient.model.TWSearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TWApiInterface {
    @Headers("Cache-Control: max-age=640000")
    @GET("tweets.json")
   Call<TWSearchResults> getTweets (
            @Header("Authorization") String token,
            @Query("count") int count,
            @Query("q") String q);
}

