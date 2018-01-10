package com.assignment.alexs.twitterclient.ui;

/**
 * Created by alexschwartzman on 1/8/18.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.alexs.twitterclient.R;
import com.assignment.alexs.twitterclient.model.TWStatus;
import com.assignment.alexs.twitterclient.utils.Utilities;
import java.util.ArrayList;
import com.assignment.alexs.twitterclient.services.TWService;

public class MainActivity extends AppCompatActivity implements IServiceCallback {
    TWAdapter adapter;

    final static String LOG_TAG = "MainActivity";
    private EditText edtSearch;


    private ProgressDialog progressDialog;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Utilities.isNetworkAvailable(this)) {
            Utilities.showError(this, "no network"/*getString(R.string.no_network)*/);
            return;
        }
        Button btnSearch;
        RecyclerView lvTweets;

        lvTweets = findViewById(R.id.rv_tweets);
        adapter = new TWAdapter(new ArrayList<TWStatus>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTweets.setLayoutManager(layoutManager);
        lvTweets.setAdapter(adapter);
        lvTweets.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        btnSearch = findViewById(R.id.btnSearch );

        edtSearch = findViewById(R.id.editText);
        edtSearch.setText("@Android");// default
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String searchText = edtSearch.getEditableText().toString();
             if (searchText.length() > 0) {
                    progressDialog.show();
                    new TWService(MainActivity.this).getTweets(searchText);
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
    }

    public void onFailure(String reason) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Utilities.showError(this, reason);
    }

    public void onSuccess(ArrayList<TWStatus> list) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        adapter.updateItems(list);
        //hide keyboard
        edtSearch.clearFocus();
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
   }

}

