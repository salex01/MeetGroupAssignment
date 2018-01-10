package com.assignment.alexs.twitterclient.ui;

/**
 * Created by alexschwartzman on 1/8/18.
 */

import com.assignment.alexs.twitterclient.model.TWStatus;

import java.util.ArrayList;

public interface IServiceCallback {
      void onFailure(String reason);
      void onSuccess(ArrayList<TWStatus> list);
}
