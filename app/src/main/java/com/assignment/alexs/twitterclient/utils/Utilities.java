package com.assignment.alexs.twitterclient.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.assignment.alexs.twitterclient.ui.TWAlert;

/**
 * Created by alexschwartzman on 1/10/18.
 */

public class Utilities {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isAvailable = false;
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    public static void showError(Activity activityContext, String message) {
        TWAlert dialog = new TWAlert();
        dialog.setMessage(message);
        dialog.show(activityContext.getFragmentManager(), "no_internet_error_dialog");
    }
}
